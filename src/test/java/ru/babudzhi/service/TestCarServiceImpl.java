package ru.babudzhi.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.dto.PersonDto;
import ru.babudzhi.model.Person;

import java.text.ParseException;

import static org.mockito.Mockito.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/application-test-config.xml"})
public class TestCarServiceImpl extends Assert {

    @Autowired
    CarService carService;

    @Autowired
    PersonService personService;

    @Before
    public void setUp() throws Exception { }

    @After
    public void tearDown() throws Exception {
        carService = null;
    }

    @Test
    public void addCar() throws ParseException {
        PersonDto personDto = new PersonDto(1L, "Test1", "01.01.1990");
        assertEquals(new Long(1),personService.addPerson(personDto).getId());

        CarDto carDto = new CarDto(1L, "Test-1", 1, 1L);
        assertEquals(new Long(1),carService.addCar(carDto).getId());
        carService.clear();
        personService.clear();
      }

    @Test
    public void clear() throws ParseException {
        PersonDto personDto = new PersonDto(1L, "Test1", "01.01.1990");
        assertEquals(new Long(1),personService.addPerson(personDto).getId());

        CarDto carDto = new CarDto(1L, "Test-1", 1, 1L);
        assertEquals(new Long(1),carService.addCar(carDto).getId());
        carService.clear();
        personService.clear();
    }


}