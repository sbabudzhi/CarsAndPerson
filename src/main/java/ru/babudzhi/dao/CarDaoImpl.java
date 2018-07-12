package ru.babudzhi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;
import javax.transaction.Transactional;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
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
        List<String> listModel = sessionFactory.getCurrentSession().createQuery("select model from Car GROUP BY model").getResultList();
        List<String> listUniqueVendor = new ArrayList<>();
        for (String aLong : listModel) {
            String vendor = aLong.split("-")[0];
            int i =0;
            for (String s : listUniqueVendor) {
                if(s.toUpperCase().equals(vendor.toUpperCase()))
                    i++;
            }
            if(i == 0)
                listUniqueVendor.add(vendor);
        }
        return (long) listUniqueVendor.size();
    }

    private boolean carNotExist(Long id){
        List<Long> carIdList = sessionFactory.getCurrentSession().createQuery("select id from Car where id=:id").setParameter("id",id).getResultList();
        if(carIdList.size() != 0)
                return false;
        return true;
    }

    private boolean personExist(Long idPerson){
        if(idPerson!= null && sessionFactory.getCurrentSession().get(Person.class,idPerson) == null)
                return false;
        return true;
    }

    private boolean validRequest(Car car){
        if(car.getId()!= null
                &&!car.getId().toString().equals("")
                && car.getModel() != null
                &&!car.getModel().equals("")
                && car.getHorsePower() != null
                && car.getHorsePower() > 0
                && car.getOwner() != null
                && personExist(car.getOwner().getId())) {
            car.setOwner(sessionFactory.getCurrentSession().load(Person.class, car.getOwner().getId()));
            if (correctNameModel(car.getModel()) != null && correctAgePerson(car.getOwner().getBirthdate())) {
                car.setModel(correctNameModel(car.getModel()));
                return true;
            }
        }
        return false;
    }

    private String correctNameModel(String nameModel){
        Pattern pattern = Pattern.compile("^\\w+-\\S+"); // буквы и цифры_ любое количество раз, кроме нуля-буквы и цифры_ люое количество раз кроме нуля
        Matcher model = pattern.matcher(nameModel);
        if (model.find()) { //если подобная конструкция найдена
            return model.group();
        }
        return null;
    }

    private boolean correctAgePerson(Date birthdate){
        int agePersonCorrect = Period.between(birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),  //возраст владельца
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .getYears();
        if(agePersonCorrect >= 18)// // Person младше 18 лет
            return true;
        else return false;
    }

    @Override
    @Transactional
    public void clear(){
        sessionFactory.getCurrentSession().createQuery("delete from Car").executeUpdate();
    }
}

