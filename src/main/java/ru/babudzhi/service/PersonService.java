package ru.babudzhi.service;

import ru.babudzhi.dto.PersonDto;

import java.text.ParseException;

public interface PersonService {
    public PersonDto addPerson(PersonDto person) throws ParseException;
    public PersonDto getPersonWithCars(Long id);
    public boolean personExist(Long id);
}
