package ru.babudzhi.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.babudzhi.dao.PersonDao;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.dto.PersonDto;
import ru.babudzhi.model.Person;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/application-test-config.xml"})
public class TestPersonServiceImpl extends  Assert{

    @Autowired
    PersonService personService;

    @Autowired
    CarService carService;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        personService = null;
    }

    @Test
    public void addPerson() throws ParseException {
        PersonDto personDto = new PersonDto(1L, "Test1", "01.01.1990");
        assertEquals(new Long(1),personService.addPerson(personDto).getId());
        carService.clear();
        personService.clear();
    }

    @Test
    public void getPersonWithCars() throws ParseException {
        PersonDto personDto = new PersonDto(2L, "Test2", "02.01.1990");
        assertEquals(new Long(2),personService.addPerson(personDto).getId());
        CarDto carDto = new CarDto(1L, "Test-1", 1, 2L);
        assertEquals(new Long(1),carService.addCar(carDto).getId());
        assertEquals(new Long(2),personService.getPersonWithCars(2L).getId());
        carService.clear();
        personService.clear();
    }

    @Test
    public void clear() throws ParseException {
        PersonDto personDto = new PersonDto(3L, "Test3", "03.01.1990");
        assertEquals(new Long(3),personService.addPerson(personDto).getId());
        carService.clear();
        personService.clear();
    }
}