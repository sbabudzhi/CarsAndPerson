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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonDao personDao;

    @Override
    public PersonDto addPerson(PersonDto pDto) throws ParseException {
            Person person = getPersonOfPersonDto(pDto);
            person = personDao.addPerson(person);
            if (person != null)
                return getPersonDtoOfPerson(person);
        else
            return  null;
    }

    @Override
    public PersonWithCars getPersonWithCars(Long id) throws ParseException {
        if(personDao.getPersonWithCars(id)!= null)
            return  getPersonWithCarsOfPerson(personDao.getPersonWithCars(id));
        else
            return null;
    }


    @Override
    public Long personCount() {
        return personDao.personCount();
    }

    public PersonDto getPersonDtoOfPerson(Person p){
        PersonDto personDto = new PersonDto(p.getId(),p.getName(),p.getBirthdate().toString());

        Set<CarDto> carDtoSetReturnable = new HashSet<>();
        leadCarDtoSetOfCarSet(p.getCarSet(), carDtoSetReturnable, personDto.getId());

        personDto.setCarDtoSet(carDtoSetReturnable);
        return personDto;
    }

    public Person getPersonOfPersonDto( PersonDto pDto) throws ParseException {
        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");
        Matcher model = pattern.matcher(pDto.getBirthdate());

        Person person = new Person(pDto.getId(),pDto.getName());
        if( pDto.getBirthdate()!= null && !pDto.getBirthdate().equals("")  && model.find())
            person.setBirthdate(new SimpleDateFormat("dd.MM.yyyy").parse(pDto.getBirthdate()));
        else
            person.setBirthdate(null);

        Set<Car> carSetReturnable = new HashSet<>();
        if(pDto.getCarDtoSet() != null)
            leadCarSetOfCarDtoSet(pDto.getCarDtoSet(),carSetReturnable,person);
        person.setCarSet(carSetReturnable);
        return person;
    }

    public PersonWithCars getPersonWithCarsOfPerson(Person p) throws ParseException {
        PersonWithCars personWithCars = new PersonWithCars(p.getId(),p.getName(),p.getBirthdate());

        Set<CarDto> carDtoSetReturnable = new HashSet<>();
        leadCarDtoSetOfCarSet(p.getCarSet(), carDtoSetReturnable, personWithCars.getId());
        ArrayList<CarDto> carDtoArray = new ArrayList<>();
        for (CarDto carDto : carDtoSetReturnable) {
            carDtoArray.add(carDto);
        }
        personWithCars.setCars(carDtoArray);
        return personWithCars;
    }

    public void leadCarDtoSetOfCarSet(Set<Car> carSet, Set<CarDto> carDtoSetReturnable,  Long idPerson) {
        for (Car car : carSet) {
            CarDto carDto = new CarDto();
            carDto.setId(car.getId());
            carDto.setModel(car.getModel());
            carDto.setHorsepower(car.getHorsePower());
            carDto.setOwnerId(idPerson);
            carDtoSetReturnable.add(carDto);
        }
    }

    public void leadCarSetOfCarDtoSet( Set<CarDto> carDtoSet,Set<Car> carSetReturnable,Person person){
        for (CarDto carDto : carDtoSet) {
            Car car = new Car();
            car.setId(carDto.getId());
            car.setHorsePower(carDto.getHorsepower());
            car.setModel(carDto.getModel());
            car.setOwner(person);
            carSetReturnable.add(car);
        }
    }

    @Override
    public void clear(){
        personDao.clear();
    }
}

