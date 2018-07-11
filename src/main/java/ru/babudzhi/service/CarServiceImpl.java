package ru.babudzhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.babudzhi.dao.CarDao;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.model.Car;
import ru.babudzhi.model.Person;

import javax.transaction.Transactional;

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

    @Override
    public Long carCount() {
        return carDao.carCount();
    }

    @Override
    public Long countVendor() {
        return carDao.countVendor();
    }

    public Car getCarOfCarDto(CarDto cDto){

        Car car =  new Car(cDto.getId(),cDto.getModel(),cDto.getHorsepower());
        if(cDto.getOwnerId() != null && !cDto.getOwnerId().toString().equals(""))
            car.setOwner(new Person(cDto.getOwnerId()));
        else
            car.setOwner(null);
        return car;
    }

    public CarDto getCarDtoOfCar(Car car){
        CarDto cDto = new CarDto();
        cDto.setId(car.getId());
        cDto.setModel(car.getModel());
        cDto.setHorsepower(car.getHorsePower());
        cDto.setOwnerId(car.getOwner().getId());
        return cDto;
    }

    @Override
    public void clear(){
        carDao.clear();
    }
}
