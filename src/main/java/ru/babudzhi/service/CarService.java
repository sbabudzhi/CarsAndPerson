package ru.babudzhi.service;

import ru.babudzhi.dto.CarDto;

public interface CarService {
    public CarDto addCar(CarDto carDto);
    public CarDto getCarDtoById(Long id);
}