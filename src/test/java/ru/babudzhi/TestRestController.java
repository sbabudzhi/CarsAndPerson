package ru.babudzhi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.dto.PersonDto;
import ru.babudzhi.dto.PersonWithCars;
import ru.babudzhi.dto.Statistics;
import ru.babudzhi.service.PersonService;

import javax.transaction.Transactional;
import java.text.ParseException;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/application-test-config.xml"})
@Transactional
public class TestRestController{

    @Autowired
    RestController restController;

    @Before
    public void setUp() throws Exception {
        System.out.println("runner setUP");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("runner teardown");
    }

    @Test
    public void addPerson() throws ParseException {
        PersonDto personDto1 = new PersonDto(1L, "Test1", "01.01.1990");
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addPerson(personDto1));

        PersonDto personDto2 = new PersonDto(2L, "Test1", "01.51.1990");
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),restController.addPerson(personDto2));

        PersonDto personDto3 = new PersonDto("Test1", "01.01.1990");
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),restController.addPerson(personDto3));
    }

    @Test
    public void addCar() throws ParseException {
        PersonDto personDto = new PersonDto(1L, "Test1", "01.01.1990");
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addPerson(personDto));

        CarDto carDto1 = new CarDto(1L, "Test-1", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addCar(carDto1));

        CarDto carDto2 = new CarDto(1L, "Test-1", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),restController.addCar(carDto2));

        CarDto carDto3 = new CarDto("Test-1", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),restController.addCar(carDto3));

        CarDto carDto4 = new CarDto(2L, "Q-", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),restController.addCar(carDto4));

        CarDto carDto5 = new CarDto(3L, "-QW", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),restController.addCar(carDto5));

        PersonDto personDto2 = new PersonDto(2L, "Test1", "01.01.2003");
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addPerson(personDto2));

        CarDto carDto6 = new CarDto(1L, "Test-1", 1, 2L);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),restController.addCar(carDto6));

    }

    @Test
    public void getPersonWithCars() throws ParseException {
        PersonDto personDto = new PersonDto(1L, "Test1", "01.01.1990");
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addPerson(personDto));

        CarDto carDto1 = new CarDto(1L, "Test-1", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addCar(carDto1));

        assertEquals(HttpStatus.OK,restController.getPersonWithCars(1L).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND,restController.getPersonWithCars(2L).getStatusCode());
    }

    @Test
    public void getStatistic() throws ParseException {
        PersonDto personDto = new PersonDto(1L, "Test1", "01.01.1990");
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addPerson(personDto));

        CarDto carDto1 = new CarDto(1L, "Test-1", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addCar(carDto1));

        PersonDto personDto2 = new PersonDto(2L, "Test2", "01.01.1990");
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addPerson(personDto2));

        CarDto carDto3 = new CarDto(3L, "Test-2", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addCar(carDto3));

        CarDto carDto2 = new CarDto(2L, "Test2-1", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addCar(carDto2));

        Statistics statistics = new Statistics(2L,3L,2L);

        assertEquals(statistics.getPersoncount(),restController.getStatistic().getBody().getPersoncount());
        assertEquals(statistics.getCarcount(),restController.getStatistic().getBody().getCarcount());
        assertEquals(statistics.getUniquevendorcount(),restController.getStatistic().getBody().getUniquevendorcount());

    }

    @Test
    public void clear() throws ParseException {

        PersonDto personDto = new PersonDto(1L, "Test1", "01.01.1990");
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addPerson(personDto));

        CarDto carDto1 = new CarDto(1L, "Test-1", 1, 1L);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),restController.addCar(carDto1));

        assertEquals(HttpStatus.OK,restController.clear().getStatusCode());
    }
}