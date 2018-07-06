package ru.babudzhi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;
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
        if (!carExist(car.getId()) && !personExist(car.getOwner().getId()) && car.getOwner().getBirthday().getTime() >= (new Date().getTime() - 568036800000L))
            return null;  //объект с таким id еще не добавлялся,существует объект Person с таким id, Person старше 18 лет

        if(validRequest(car)){ //данные запроса валидны
            sessionFactory.getCurrentSession().persist(car);
            return sessionFactory.getCurrentSession().load(Car.class, car.getId());
        }
        return null;
    }

     public boolean carExist(Long id){
        List<Long> carIdList = sessionFactory.getCurrentSession().createQuery("select id from Car").getResultList();
        for (Long anCarIdList : carIdList) {            //объект с таким id еще не добавлялся
            if (anCarIdList == id && anCarIdList != null)
                return false;
        }
        return true;
    }

    public boolean personExist(Long idPerson){
        List<Long> personIdList = sessionFactory.getCurrentSession().createQuery("select id from Person").getResultList();
        for (Long anPersonIdList : personIdList) {      //существует объект Person с таким id
            if (!(anPersonIdList == idPerson || anPersonIdList == null))
                return false;
        }
        return true;
    }

    public boolean validRequest( Car car){
        if(!car.getModel().equals("") && car.getHorsePower() > 0 && car.getOwner() != null //название модели не пустое,лошадиная сила больше нуля,id хозяина не пустой
                && car.getModel().getClass() == String.class //классы объектов соответствуют заявленным
                && car.getOwner().getClass() == Person.class) {
            Pattern pattern = Pattern.compile("^\\w+-\\w+$"); // А2_ любое количество раз, кроме нуля-А2_ люое количество раз кроме нуля
            Matcher model = pattern.matcher(car.getModel());
            if (model.find()) {
                String modelCar = model.group();
                car.setModel(modelCar);
                return true;
            }else
                return false;
        }else
            return false;
    }
}
