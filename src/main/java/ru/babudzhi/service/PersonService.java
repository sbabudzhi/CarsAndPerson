package ru.babudzhi.service;

import ru.babudzhi.dto.PersonDto;
import ru.babudzhi.dto.PersonWithCars;

import java.text.ParseException;

public interface PersonService {
    public PersonDto addPerson(PersonDto person) throws ParseException;
    public PersonWithCars getPersonWithCars(Long id) throws ParseException;
    public Long personCount();
    public void clear();
}
