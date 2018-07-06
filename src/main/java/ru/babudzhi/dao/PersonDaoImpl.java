package ru.babudzhi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.babudzhi.model.Person;
import java.util.Date;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Person addPerson(Person person) {

        List<Long> personIdList = sessionFactory.getCurrentSession().createQuery("select id from Person").getResultList();
        for (Long anPersonIdList : personIdList) {   //объект с таким id еще не добавлялся
            if (anPersonIdList == person.getId() && anPersonIdList != null)
                return null;
        }
         if (person.getBirthday().before(new Date()) //дата в прошлом
                && person.getBirthday().getClass()== Date.class && person.getName().getClass() == String.class){ // все поля соответствуют заявленным типам
            this.sessionFactory.getCurrentSession().persist(person);
            return sessionFactory.getCurrentSession().load(Person.class,person.getId());
        }
        else   return null;
    }

    @Override
    public Person getPersonWithCars(long id) {
        Person p = sessionFactory.getCurrentSession().load(Person.class,id);
        if(p.getId() == id) {
            return p;
        } else return null;
    }
}
