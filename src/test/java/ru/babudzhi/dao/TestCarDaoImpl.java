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
public class TestCarDaoImpl {

    @Autowired
    CarDao carDao;
    @Autowired
    PersonDao personDao;


    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        carDao = null;
    }

    @Test
    public void addCar()
    {
        Person person = new Person(1L, "Test1", Date.from(LocalDate.of(1990,1,1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertEquals(personDao.addPerson(person),person);
        Car car = new Car(1L,"Test-1",1,person);
        assertEquals(new Long(1),carDao.addCar(car).getId());
        carDao.clear();
        personDao.clear();
    }

    @Test
    public void carCount() {
        Person person = new Person(1L, "Test1", Date.from(LocalDate.of(1990,1,1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertEquals(personDao.addPerson(person),person);

        Car car1 = new Car(1L,"Test-1",1,person);
        Car car2 = new Car(2L,"Test-2",2,person);
        assertEquals(new Long(1),carDao.addCar(car1).getId());
        assertEquals(new Long(2),carDao.addCar(car2).getId());

        assertEquals(new Long(2),carDao.carCount());
        carDao.clear();
        personDao.clear();
    }

    @Test
    public void countVendor() {
        Person person = new Person(1L, "Test1", Date.from(LocalDate.of(1990,1,1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertEquals(personDao.addPerson(person),person);
        Car car1 = new Car(1L,"Test-1",1,person);
        Car car2 = new Car(2L,"Test-2",2,person);
        Car car3 = new Car(3L,"Test1-1",3,person);
        assertEquals(new Long(1),carDao.addCar(car1).getId());
        assertEquals(new Long(2),carDao.addCar(car2).getId());
        assertEquals(new Long(3),carDao.addCar(car3).getId());

        assertEquals(new Long(2),carDao.countVendor());
        carDao.clear();
        personDao.clear();
    }

    @Test
    public void clear() {
        Person person = new Person(1L, "Test1", Date.from(LocalDate.of(1990,1,1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertEquals(personDao.addPerson(person),person);
        Car car4 = new Car(4L,"Test-4",4,person);
        assertEquals(new Long(4),carDao.addCar(car4).getId());
        carDao.clear();
        personDao.clear();
    }
}