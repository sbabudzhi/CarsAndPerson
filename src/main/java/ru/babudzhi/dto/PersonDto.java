package ru.babudzhi.dto;

import java.util.Set;

public class PersonDto {
    private Long id = null;
    private String name = "";
    private String birthdate = "";
    private Set<CarDto> carDtoSet;

    public PersonDto() { }
    public PersonDto(Long id) {
        this.id = id;
    }

    public PersonDto(long id, String name, String birthday) {
        this.id = id;
        this.name = name;
        this.birthdate = birthday;
    }
    public Set<CarDto> getCarDtoSet() {
        return carDtoSet;
    }

    public void setCarDtoSet(Set<CarDto> carDtoSet) {
        this.carDtoSet = carDtoSet;
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
    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(String birthday) {
        this.birthdate = birthday;
    }
}
