package ru.babudzhi.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/application-test-config.xml"})
public class TestPersonDaoImpl {

    @Autowired
    PersonDao personDao;

    @Autowired
    CarDao carDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        personDao = null;
    }

    @Test
    public void addPerson() {
        Person person = new Person(1L, "Test1", Date.from(LocalDate.of(1990,1,1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertEquals(personDao.addPerson(person),person);
        personDao.clear();
        carDao.clear();

    }

    @Test
    public void getPersonWithCars() {
        Person person = new Person(2L, "Test2", Date.from(LocalDate.of(1990,1,2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertEquals(personDao.addPerson(person),person);
        Car car = new Car(1L,"Test-1",1,person);
        assertEquals(new Long(1),carDao.addCar(car).getId());
        assertEquals(new Long(2),personDao.getPersonWithCars(2L).getId());
        carDao.clear();
        personDao.clear();
    }

    @Test
    public void clear() {
        Person person = new Person(3L, "Test3", Date.from(LocalDate.of(1990,1,3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertEquals(personDao.addPerson(person),person);
        carDao.clear();
        personDao.clear();
    }
}