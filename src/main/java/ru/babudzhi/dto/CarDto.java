package ru.babudzhi.dto;

public class CarDto {

    private Long id = null;
    private String model;
    private int horsePower;
    private Long owner;

    public CarDto() {
    }

    public CarDto(Long id, String model, int horsePower, Long owner) {
        this.id = id;
        this.model = model;
        this.horsePower = horsePower;
        this.owner = owner;
    }

    public CarDto(String model, int horsePower, Long owner) {
        this.model = model;
        this.horsePower = horsePower;
        this.owner = owner;
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
    public int getHorsePower() {
        return horsePower;
    }
    public void setHorsePower(int horsepower) {
        this.horsePower = horsepower;
    }
    public Long getOwner() {
        return owner;
    }
    public void setOwnerId(Long owner) {
        this.owner = owner;
    }
}
