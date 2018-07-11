package ru.babudzhi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.babudzhi.dto.CarDto;
import ru.babudzhi.dto.PersonDto;
import ru.babudzhi.dto.PersonWithCars;
import ru.babudzhi.dto.Statistics;
import ru.babudzhi.service.CarService;
import ru.babudzhi.service.PersonService;
import java.text.ParseException;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private PersonService personService;
    @Autowired
    private CarService carService;

    @RequestMapping(value = "/person", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> addPerson(@RequestBody PersonDto personDto) throws ParseException {
        if((personService.addPerson(personDto)) != null)
            return new ResponseEntity<Void>(HttpStatus.OK);
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/car", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> addCar(@RequestBody CarDto carDto){
        if(carService.addCar((carDto))!=null)
            return new ResponseEntity<Void>(HttpStatus.OK) ;
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/personwithcars", method = RequestMethod.GET, params = "personid", produces = "application/json")
    public ResponseEntity<PersonWithCars> getPersonWithCars(@RequestParam(value = "personid") Long personId) throws ParseException {

        if(personId.getClass() != Long.class)
            return new ResponseEntity<PersonWithCars>(HttpStatus.BAD_REQUEST);

        PersonWithCars pWC = personService.getPersonWithCars(personId);
        if(pWC!= null)
            return new ResponseEntity<PersonWithCars>(pWC,HttpStatus.OK);
        else   return new ResponseEntity<PersonWithCars>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ResponseEntity<Statistics> getStatistic(){
        return new ResponseEntity<>(new Statistics(personService.personCount(),carService.carCount(),carService.countVendor()),HttpStatus.OK);

    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public ResponseEntity<Void> clear(){
        carService.clear();
        personService.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
