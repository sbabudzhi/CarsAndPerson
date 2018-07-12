package ru.babudzhi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PersonDaoImpl implements PersonDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Person addPerson(Person person) {
        if(validRequestData(person)) {
            List<Long> personIdList = sessionFactory.getCurrentSession().createQuery("select id from Person where id=:id").setParameter("id",person.getId()).getResultList();
            if(personIdList.size()!=0)
                    return null;

            if (person.getBirthdate().before(new Date())) { // дата в прошлом
                this.sessionFactory.getCurrentSession().persist(person);
                return sessionFactory.getCurrentSession().load(Person.class, person.getId());
            }
        }
        return null;
    }

    @Override
    public Person getPersonWithCars(Long id) {
        if(id != null) {
            Person p = sessionFactory.getCurrentSession().get(Person.class, id);
            if (p != null) {
                Set<Car> carSet = getCarSet(p.getId());
                if (p.getId().equals(id)) {
                    p.setCarSet(carSet);
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public Long personCount() {
        List<Long> listId = sessionFactory.getCurrentSession().createQuery("select id from Person").getResultList();
        Long count = Long.valueOf(0);
        for (Long aLong : listId) {
            count++;
        }
        return count;
    }

    private Set<Car> getCarSet(Long personId){
        Set<Car> carSet = new HashSet<>();
        List<Car> list = sessionFactory.getCurrentSession().createQuery("from Car where owner.id = :id").setParameter("id", personId).getResultList();
        for (Car car : list) {
            carSet.add(car);
        }
        return carSet;
    }

    private boolean validRequestData(Person person){
        if(person.getId()!= null
                && !person.getId().toString().equals("")
                &&  person.getName() != null
                &&  person.getBirthdate() != null
                && !person.getBirthdate().toString().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void clear(){
         sessionFactory.getCurrentSession().createQuery("delete from Person").executeUpdate();
    }
}