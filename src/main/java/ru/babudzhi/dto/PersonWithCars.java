package ru.babudzhi.dto;

import java.util.ArrayList;
import java.util.Date;

public class PersonWithCars {
    private Long id = null;
    private String name;
    private Date birthdate;
    private ArrayList<CarDto> cars;

    public PersonWithCars() { }

    public PersonWithCars(long id, String name, Date birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }
    public ArrayList<CarDto> getCars() {
        return cars;
    }

    public void setCars(ArrayList<CarDto> carDtoSet) {
        this.cars = carDtoSet;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) { this.id = id; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthday) {
        this.birthdate = birthday;
    }
}

