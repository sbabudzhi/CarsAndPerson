package ru.babudzhi.dto;

public class CarDto {

    private Long id = null;
    private String model = "";
    private Integer horsepower = null;
    private Long ownerId = null;

    public CarDto() {
    }

    public CarDto(Long id, String model, Integer horsePower, Long ownerId) {
        this.id = id;
        this.model = model;
        this.horsepower = horsePower;
        this.ownerId = ownerId;
    }

    public CarDto(String model, Integer horsePower, Long ownerId) {
        this.model = model;
        this.horsepower = horsePower;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public Integer getHorsepower() {
        return horsepower;
    }
    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }
    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long owner) {
        this.ownerId = owner;
    }
}
