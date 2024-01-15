package telran.cars.service;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import telran.cars.dto.*;
import telran.cars.service.model.*;
@Service
@Slf4j
public class CarsServiceImpl implements CarsService {
HashMap<Long, CarOwner> owners = new HashMap<>();
HashMap<String, Car> cars = new HashMap<>();
@Override
public PersonDto addPerson(PersonDto personDto) {
    long id = personDto.id();
    if (owners.containsKey(id)) {
        throw new IllegalStateException("Person with ID " + id + " already exists");
    }

    CarOwner newCarOwner = new CarOwner(personDto);
    owners.put(id, newCarOwner);

    log.debug("Person was added: {}", personDto);
    return newCarOwner.build();
}

@Override
public CarDto addCar(CarDto carDto) {
    String carNumber = carDto.number();
    if (cars.containsKey(carNumber)) {
        throw new IllegalStateException("Car with number " + carNumber + " already exists");
    }
    Car newCar = new Car(carDto);
    cars.put(carNumber, newCar);
    log.debug("Car was added: {}", carDto);
    return newCar.build();
}

@Override
public PersonDto updatePerson(PersonDto personDto) {
    long id = personDto.id();
    CarOwner updatedPerson = owners.replace(id, new CarOwner(personDto));
    if (updatedPerson == null) {
        throw new IllegalStateException("Person with ID " + id + " doesn't exist");
    }

    log.debug("Person was updated: {}", personDto);
    return updatedPerson.build();
}

@Override
public PersonDto deletePerson(long id) {
    CarOwner existingOwner = owners.remove(id);
    if (existingOwner == null) {
        throw new IllegalStateException("Person with ID " + id + " doesn't exist");
    }

    log.debug("Person was deleted with ID: {}", id);
    return existingOwner.build();
}

@Override
public CarDto deleteCar(String carNumber) {
    Car car = cars.remove(carNumber);
    if (car == null) {
        throw new IllegalStateException("Car with number " + carNumber + " doesn't exist");
    }

    log.debug("Car was deleted with number: {}", carNumber);
    return car.build();
}

	

	@Override
	public TradeDealDto purchase(TradeDealDto tradeDeal) {
		CarOwner newCarOwner=owners.get(tradeDeal.personId());
		Car car=cars.get(tradeDeal.carNumber());
		if(newCarOwner==null) {
				throw new IllegalStateException();
		}
		if(car==null) {
				throw new IllegalStateException();
		}
		CarOwner previousOwner=cars.get(tradeDeal.carNumber()).getOwner();
		if(previousOwner!=null) {
			owners.get(previousOwner).getCars().remove(car);
			cars.get(car).setOwner(newCarOwner);
			owners.get(tradeDeal.personId()).getCars().add(car);
		}
		return tradeDeal;
		
	}

	@Override
	public List<CarDto> getOwnerCars(long id) {
		CarOwner carOwner=owners.get(id);
		List<CarDto>ownersCars=new ArrayList<>();
		if(carOwner==null) {
		
				throw new IllegalStateException();
			
		}
		for(Car car:carOwner.getCars()) {
			ownersCars.add(car.build());
		}
		return ownersCars;
	}

	@Override
	public PersonDto getCarOwner(String carNumber) {
		Car car=cars.get(carNumber);
		if(car==null) {
				throw new IllegalStateException();
		}
		return car.getOwner().build();
	}

}