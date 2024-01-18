package telran.cars;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;

import telran.cars.dto.*;
import telran.cars.exceptions.*;
import telran.cars.repo.ModelRepo;
import telran.cars.service.CarsService;
import telran.cars.service.model.ModelYear;


@SpringBootTest
//FIXME accordingly to SQL script
@Sql(scripts = {"classpath:test_data.sql"})
class CarsServiceTest {
	private static final String MODEL1 = "model1";
	private static final String MODEL2 = "model2";
	private static final String MODEL3 = "model3";
	private static final String MODEL4 = "model4";
	private static int YEAR1=2020;
	private static int YEAR2=2021;
	private static final int YEAR3=2023;
	public static final String COMPANY1="company1";
	public static final String COMPANY2="company2";
	public static final String COMPANY3="company3";
	public static final String COMPANY4="company4";
	
	private static final String CAR_NUMBER_1 = "111-11-111";
	private static final String CAR_NUMBER_2 = "222-22-222";
	private static final  String CAR_NUMBER_3 = "333-33-333";
	private static final  String CAR_NUMBER_4 = "444-44-444";
	private static final  String CAR_NUMBER_5 = "555-55-555";
	private static final Long PERSON_ID_1 = 123L;
	private static final String NAME1 = "name1";
	private static final String BIRTH_DATE_1 = "2000-10-10";
	private static final String EMAIL1 = "name1@gmail.com";
	private static final Long PERSON_ID_2 = 124l;
	private static final String NAME2 = "name2";
	private static final String BIRTH_DATE_2 = "2000-10-10";
	private static final String EMAIL2 = "name2@gmail.com";
	private static final Long PERSON_ID_NOT_EXISTS = 1111111111L;
	
	private static final  String NEW_EMAIL = "name1@tel-ran.co.il";
	//cars from sql
	CarDto car1 = new CarDto("111-11-111", "model1", 2020,"red",1000 , CarState.GOOD);
	CarDto car2 = new CarDto("222-11-111", "model1", 2020, "silver", 10000, CarState.OLD);
	CarDto car3 = new CarDto("333-11-111", "model4", 2023, "white", 0, CarState.NEW);
	//cars that i want to check them
	CarDto car4 = new CarDto("222-22-222", "model1", 2020, "black", 0, CarState.NEW);
	CarDto car5 = new CarDto("333-11-333", "model5", 2000, "gold", 999000, CarState.BAD);
	//persons that i have
	PersonDto personDto = new PersonDto(123L,"name1", "2000-10-10", "name1@gmail.com");
	PersonDto personDto2 = new PersonDto(124L,"name2", "1990-12-20", "name2@gmail.com");
	PersonDto personDto3 = new PersonDto(125L,"name3", "1975-12-10", "name3@gmail.com");
	//person i want to add
	PersonDto personDto4 = new PersonDto(126L,"name4", "2000-10-10", "name4@gmail.com");
	@Autowired
	CarsService carsService;
	

	
	@Test
	void scriptTest() {
		assertThrowsExactly(IllegalPersonsStateException.class,
				()->carsService.addPerson(personDto));
		
		
	}
	

	@Test
	//FIXME
	//HW #63 write test, take out @Disabled
	void testAddPerson() {
		assertEquals(personDto4, carsService.addPerson(personDto4));
		assertThrows(IllegalPersonsStateException.class,
				()->carsService.addPerson(personDto));
		
	}

	@Test
	//FIXME
	//HW #63 write test, take out @Disabled
	void testAddCar() {
		assertEquals(car4, carsService.addCar(car4));
		assertThrowsExactly(IllegalCarsStateException.class,
				()->carsService.addCar(car2));
		
	}

	@Test
	//FIXME
	//HW #63 write test, take out @Disabled
		
	void testUpdatePerson() {
		PersonDto personUpdated = new PersonDto(123L,"name1", "2000-10-10", "name100@gmail.com");
		assertEquals(personUpdated, carsService.updatePerson(personUpdated));
		assertThrowsExactly(PersonNotFoundException.class,
				() -> carsService.updatePerson(personDto4));
	}

	@Test
	//FIXME
	//HW #63 write test, take out @Disabled
	void testDeletePerson() {
		//assertEquals(personDto, carsService.deletePerson(123L));
		assertThrowsExactly(PersonNotFoundException.class, () -> carsService.deletePerson(126L));
		
	}

	@Test
	//FIXME
	//HW #63 write test, take out @Disabled
	void testDeleteCar() {
		assertEquals(car1, carsService.deleteCar(CAR_NUMBER_1));
		assertThrowsExactly(CarNotFoundException.class, () -> carsService.deleteCar(CAR_NUMBER_1));
		
	}

	@Test
	//FIXME
	//HW #63 write test, take out @Disabled
	
	void testPurchaseNewCarOwner() {
		TradeDealDto tradeDeal = new TradeDealDto(CAR_NUMBER_1, PERSON_ID_2, "2020-10-11");
		assertEquals(tradeDeal, carsService.purchase(tradeDeal));
		
		
	}
	@Test
	//FIXME
	//HW #63 write test, take out @Disabled
		
	void testPurchaseNotFound() {
		TradeDealDto tradeDealCarNotFound = new TradeDealDto(CAR_NUMBER_3, PERSON_ID_1, null);
		TradeDealDto tradeDealOwnerNotFound = new TradeDealDto(CAR_NUMBER_1,
				PERSON_ID_NOT_EXISTS, null);
		assertThrowsExactly(PersonNotFoundException.class, () -> carsService.purchase(tradeDealOwnerNotFound));
		assertThrowsExactly(CarNotFoundException.class, () -> carsService.purchase(tradeDealCarNotFound));
		
	}
	@Test
	//FIXME
	//HW #63 write test, take out @Disabled
		
	void testPurchaseNoCarOwner() {
		TradeDealDto tradeDeal = new TradeDealDto(CAR_NUMBER_1,null, "2020-11-10");
		assertEquals(tradeDeal, carsService.purchase(tradeDeal));
		assertNull(carsService.getCarOwner(CAR_NUMBER_1));
	}
	@Test
	//HW #63 write test, take out @Disabled
		@Disabled
	void testPurchaseSameOwner() {
		TradeDealDto tradeDeal = new TradeDealDto(CAR_NUMBER_1,PERSON_ID_1, null);
		assertThrowsExactly(TradeDealIllegalStateException.class,
				() -> carsService.purchase(tradeDeal));
	}

	@Test
	@Disabled
	void testGetOwnerCars() {
		List<CarDto> cars = carsService.getOwnerCars(PERSON_ID_1);
		assertEquals(1, cars.size());
		assertEquals(car1, cars.get(0));
		assertThrowsExactly(NotFoundException.class,
				() -> carsService.getOwnerCars(PERSON_ID_NOT_EXISTS));
	}

	@Test
	@Disabled
	void testGetCarOwner() {
		PersonDto ownerActual = carsService.getCarOwner(CAR_NUMBER_1);
		assertEquals(personDto, ownerActual);
		assertThrowsExactly(NotFoundException.class, () -> carsService.getCarOwner(CAR_NUMBER_3));
	}
	@Test
	@Disabled
	void testMostPopularModels() {
		carsService.addCar(car3);
		carsService.addCar(car4);
		carsService.addCar(car5);
		carsService.purchase(new TradeDealDto(CAR_NUMBER_3, PERSON_ID_1, null));
		carsService.purchase(new TradeDealDto(CAR_NUMBER_4, PERSON_ID_2, null));
		carsService.purchase(new TradeDealDto(CAR_NUMBER_5, PERSON_ID_2, null));
		List<String> mostPopularModels = carsService.mostPopularModels();
		String[] actual = mostPopularModels.toArray(String[]::new);
		Arrays.sort(actual);
		String[] expected = {
				MODEL1, MODEL2
		};
		assertArrayEquals(expected, actual);
		
	}



	
	

}