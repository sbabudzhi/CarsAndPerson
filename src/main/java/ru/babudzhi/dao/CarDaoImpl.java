package ru.babudzhi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;
import javax.transaction.Transactional;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class CarDaoImpl implements CarDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Car addCar(Car car) {
        if (carNotExist(car.getId())
                && validRequest(car)) {
            sessionFactory.getCurrentSession().persist(car);
            return sessionFactory.getCurrentSession().load(Car.class, car.getId());
        }
        return null;
    }

    @Override
    public Long carCount() {
        List<Long> listId = sessionFactory.getCurrentSession().createQuery("select id from Car").getResultList();
        Long count = Long.valueOf(0);
        for (Long aLong : listId) {
            count++;
        }
        return count;
    }

    @Override
    public Long countVendor() {
        List<String> listId = sessionFactory.getCurrentSession().createQuery("select model from Car GROUP BY model").getResultList();
        Long count = Long.valueOf(0);
        for (String aLong : listId) {
            count++;
        }
        return count;
    }


    public boolean carNotExist(Long id){
        List<Long> carIdList = sessionFactory.getCurrentSession().createQuery("select id from Car").getResultList();
        for (Long anCarIdList : carIdList) {            //объект с таким id еще не добавлялся
            if (anCarIdList == id && anCarIdList != null)
                return false;
        }
        return true;
    }

    public boolean personExist(Long idPerson){
        Person p = new Person();
        if(idPerson != null)
            p = sessionFactory.getCurrentSession().get(Person.class,idPerson);
        if(p == null)
                return false;
        return true;
    }

    public boolean validRequest( Car car){
        if(car.getId()!= null
                &&!car.getId().toString().equals("")
                && car.getModel() != null
                &&!car.getModel().equals("")
                && car.getHorsePower() != null
                && car.getHorsePower() > 0
                && car.getOwner() != null
                && personExist(car.getOwner().getId())) {

            car.setOwner(sessionFactory.getCurrentSession().load(Person.class, car.getOwner().getId()));
            int agePersonCorrect = Period.between(car.getOwner().getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),  //возраст владельца
                    new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .getYears();
            if(agePersonCorrect< 18)// // Person младше 18 лет
                return false;

            Pattern pattern = Pattern.compile("^\\w+-\\w+$"); // буквы и цифры_ любое количество раз, кроме нуля-буквы и цифры_ люое количество раз кроме нуля
            Matcher model = pattern.matcher(car.getModel());
            if (model.find()) { //если подобная конструкция найдена
                String modelCar = model.group();
                car.setModel(modelCar);
                return true;
            }else
                return false;
        }else
            return false;
    }

    @Override
    @Transactional
    public void clear(){
        sessionFactory.getCurrentSession().createQuery("delete from Car").executeUpdate();
    }
}

