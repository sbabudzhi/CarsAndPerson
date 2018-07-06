package ru.babudzhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.babudzhi.dao.PersonDao;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.dto.PersonDto;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonDao personDao;

    @Override
    public PersonDto addPerson(PersonDto pDTO) throws ParseException {
        Person person = getPersonOfPersonDto(pDTO);
        person = personDao.addPerson(person);
        if(person != null)
            return getPersonDtoOfPerson(person);
        else  return  null;
    }

    @Override
    public PersonDto getPersonWithCars(Long id) {
        if(personDao.getPersonWithCars(id)!= null)
            return getPersonDtoOfPerson(personDao.getPersonWithCars(id));
        else  return null;
    }

    @Override
    public boolean personExist(Long id){
        if(personDao.getPersonWithCars(id) == null)
            return false;
        else return true;
    }

    public PersonDto getPersonDtoOfPerson(Person p){
        PersonDto personDto = new PersonDto();
        personDto.setId(p.getId());
        personDto.setName(p.getName());
        personDto.setBirthday(p.getBirthday().toString());

        Set<CarDto> carDtoSetReturnable = new HashSet<>();
        leadCarDtoSetOfCarSet(p.getCarSet(), carDtoSetReturnable, personDto);
        personDto.setCarDtoSet(carDtoSetReturnable);
        return personDto;
    }

    public Person getPersonOfPersonDto( PersonDto pDto) throws ParseException {
        Person person = new Person();
        person.setName(pDto.getName());
        person.setBirthday(new SimpleDateFormat("dd.MM.yyyy").parse(pDto.getBirthday()));

        Set<Car> carSetReturnable = new HashSet<>();
        if(pDto.getCarDtoSet() != null)
            leadCarSetOfCarDtoSet(pDto.getCarDtoSet(),carSetReturnable,person);
        person.setCarSet(carSetReturnable);
        return person;
    }

    public void leadCarDtoSetOfCarSet(Set<Car> carSet, Set<CarDto> carDtoSetReturnable,  PersonDto personDto) {
        for (Car car : carSet) {
            CarDto carDto = new CarDto();
            carDto.setModel(car.getModel());
            carDto.setHorsePower(car.getHorsePower());
            carDto.setOwnerId(personDto.getId());
            carDtoSetReturnable.add(carDto);
        }
    }

    public void leadCarSetOfCarDtoSet( Set<CarDto> carDtoSet,Set<Car> carSetReturnable,Person person){
        for (CarDto carDto : carDtoSet) {
            Car car = new Car();
            car.setHorsePower(carDto.getHorsePower());
            car.setModel(carDto.getModel());
            car.setOwner(person);
            carSetReturnable.add(car);
        }
    }
}

