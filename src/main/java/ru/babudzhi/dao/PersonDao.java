package ru.babudzhi.dao;

import ru.babudzhi.model.Person;

public interface PersonDao {
    public Person addPerson(Person person);
    public Person getPersonWithCars(Long id);
    public Long personCount();
    public void clear();
}
