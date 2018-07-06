package ru.babudzhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.babudzhi.dao.CarDao;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    CarDao carDao;

    @Override
    public CarDto addCar(CarDto carDto) {
        Car car = carDao.addCar(getCarOfCarDto(carDto));
        if(car != null)
            return getCarDtoOfCar(car);
        return null;
    }

    @Override
    public CarDto getCarDtoById(Long id) {
        return null;
    }

    public Car getCarOfCarDto(CarDto cDto){
        Car car =  new Car();
        car.setModel(cDto.getModel());
        car.setHorsePower(cDto.getHorsePower());
        Person p = new Person();
        p.setId(cDto.getOwner());
        car.setOwner(p);
        return car;
    }

    public CarDto getCarDtoOfCar(Car car){
        CarDto cDto = new CarDto();
        cDto.setId(car.getId());
        cDto.setModel(car.getModel());
        cDto.setHorsePower(car.getHorsePower());
        cDto.setOwnerId(car.getOwner().getId());
        return cDto;
    }

}
//{
//        "model":"BMV-X5",
//        "horsepower":200,
//        "owner":2
//        }
//проверять имя пользователя на числа!
