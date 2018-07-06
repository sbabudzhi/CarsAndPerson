package ru.babudzhi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.dto.PersonDto;
import ru.babudzhi.service.CarService;
import ru.babudzhi.service.PersonService;
import java.text.ParseException;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private PersonService personService;
    @Autowired
    private CarService carService;

    @RequestMapping(name = "/person", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")

    public ResponseEntity<Void> addPerson(@RequestBody PersonDto personDto) throws ParseException {
        if((personService.addPerson(personDto)) != null)
            return new ResponseEntity<Void>(HttpStatus.OK);
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(name = "/car", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> addCar(@RequestBody CarDto carDto){
        if(carService.addCar((carDto))!=null)
            return new ResponseEntity<Void>(HttpStatus.OK) ;
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
