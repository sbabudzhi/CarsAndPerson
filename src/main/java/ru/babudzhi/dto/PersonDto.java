package ru.babudzhi.dto;

import java.util.Set;

public class PersonDto {
    private Long id = null;
    private String name = null;
    private String birthdate = null;
    private Set<CarDto> carDtoSet;

    public PersonDto() { }
    public PersonDto( String name, String birthday) {
        this.name = name;
        this.birthdate = birthday;
    }

    public PersonDto(Long id, String name, String birthday) {
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

    public boolean equals(PersonDto personDto){
        if(personDto.getId() == this.getId()
                && personDto.getName().equals(this.name)
                && personDto.getBirthdate().equals(this.birthdate))
            return true;
        return false;
    }
}
