package ru.babudzhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.babudzhi.dao.PersonDao;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.dto.PersonDto;
import ru.babudzhi.dto.PersonWithCars;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonDao personDao;

    @Override
    public PersonDto addPerson(PersonDto pDto) {
            Person person = getPersonOfPersonDto(pDto);
            person = personDao.addPerson(person);
            if (person != null)
                return getPersonDtoOfPerson(person);
        else
            return  null;
    }

    @Override
    public PersonWithCars getPersonWithCars(Long id)  {
        Person p = personDao.getPersonWithCars(id);
        if(p != null)
            return  getPersonWithCarsOfPerson(p);
        return null;
    }

    @Override
    public Long personCount() {
        return personDao.personCount();
    }

    @Override
    public void clear(){
        personDao.clear();
    }

    private PersonDto getPersonDtoOfPerson(Person p){
        PersonDto personDto = new PersonDto(p.getId(),p.getName(),p.getBirthdate().toString());

        Set<CarDto> carDtoSetReturnable = new HashSet<>();
        getCarDtoSetOfCarSet(p.getCarSet(), carDtoSetReturnable, personDto.getId());

        personDto.setCarDtoSet(carDtoSetReturnable);
        return personDto;
    }

    private Person getPersonOfPersonDto( PersonDto pDto){

        Person person = new Person(pDto.getId(), pDto.getName());
        if( pDto.getBirthdate()!= null && !pDto.getBirthdate().equals("")) {
             try {
                 SimpleDateFormat sm = new SimpleDateFormat("dd.MM.yyyy");
                 sm.setLenient(false);
                 person.setBirthdate(sm.parse(pDto.getBirthdate()));
             }catch (ParseException p) {
                 person.setBirthdate(null);
             }
        }

        Set<Car> carSetReturnable = new HashSet<>();
        if(pDto.getCarDtoSet() != null)
            getCarSetOfCarDtoSet(pDto.getCarDtoSet(),carSetReturnable,person);
        person.setCarSet(carSetReturnable);
        return person;
    }

    private PersonWithCars getPersonWithCarsOfPerson(Person p){
        PersonWithCars personWithCars = new PersonWithCars(p.getId(),p.getName(),p.getBirthdate());

        Set<CarDto> carDtoSetReturnable = new HashSet<>();
        getCarDtoSetOfCarSet(p.getCarSet(), carDtoSetReturnable, personWithCars.getId());
        ArrayList<CarDto> carDtoArray = new ArrayList<>();
        for (CarDto carDto : carDtoSetReturnable) {
            carDtoArray.add(carDto);
        }
        personWithCars.setCars(carDtoArray);
        return personWithCars;
    }

    private void getCarDtoSetOfCarSet(Set<Car> carSet, Set<CarDto> carDtoSetReturnable, Long idPerson) {
        for (Car car : carSet) {
            CarDto carDto = new CarDto();
            carDto.setId(car.getId());
            carDto.setModel(car.getModel());
            carDto.setHorsepower(car.getHorsePower());
            carDto.setOwnerId(idPerson);
            carDtoSetReturnable.add(carDto);
        }
    }

    private void getCarSetOfCarDtoSet( Set<CarDto> carDtoSet,Set<Car> carSetReturnable,Person person){
        for (CarDto carDto : carDtoSet) {
            Car car = new Car();
            car.setId(carDto.getId());
            car.setHorsePower(carDto.getHorsepower());
            car.setModel(carDto.getModel());
            car.setOwner(person);
            carSetReturnable.add(car);
        }
    }
}

