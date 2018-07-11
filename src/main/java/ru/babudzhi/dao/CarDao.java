package ru.babudzhi.dao;

import ru.babudzhi.model.Car;

public interface CarDao {
    public Car addCar(Car car);
    public Long carCount();
    public Long countVendor();
    public void clear();
}
