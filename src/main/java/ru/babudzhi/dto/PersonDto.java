package ru.babudzhi.dto;

import java.util.Set;

public class PersonDto {
    private Long id = null;
    private String name;
    private String birthDay;
    private Set<CarDto> carDtoSet;

    public PersonDto() { }

    public PersonDto(long id, String name, String birthday) {
        this.id = id;
        this.name = name;
        this.birthDay = birthday;
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
    public String getBirthday() {
        return birthDay;
    }
    public void setBirthday(String birthday) {
        this.birthDay = birthday;
    }
}
