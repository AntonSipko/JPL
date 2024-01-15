package telran.cars;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.cars.dto.CarDto;
import telran.cars.dto.CarState;
import telran.cars.dto.PersonDto;
import telran.cars.dto.TradeDealDto;
import telran.cars.service.CarsService;
import telran.cars.service.CarsServiceImpl;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class CarsServiceTest {

    private static final String MODEL = "model";
    private static final String CAR_NUMBER_1 = "111-11-111";
    private static final String CAR_NUMBER_2 = "222-22-222";
    private static final int YEAR = 2022;
    private static final String OWNER_NAME_1 = "owner1";
    private static final String OWNER_NAME_2 = "owner2";
    private static final String COLOR_1 = "color1";
    private static final String COLOR_2 = "color2";
    private static final int KILOMETERS_1 = 10000;
    private static final int KILOMETERS_2 = 15000;
    private static final CarState CAR_STATE_1 = CarState.NEW;
    private static final CarState CAR_STATE_2 = CarState.BAD;
    private static final String EMAIL_1 = "email1@gmail.com";
    private static final String EMAIL_2 = "email2@gmail.com";
    private static final String BIRTH_DATE_1 = "2000-10-10";
    private static final String BIRTH_DATE_2 = "2000-10-11";
    private static final Long PERSON_ID_1 = 123L;
    private static final Long PERSON_ID_2 = 124L;
    private static final Long PERSON_ID_NOT_EXISTS = 1111111111L;

    CarDto car1 = new CarDto(CAR_NUMBER_1, MODEL, YEAR, OWNER_NAME_1, COLOR_1, KILOMETERS_1, CAR_STATE_1);
    CarDto car2 = new CarDto(CAR_NUMBER_2, MODEL, YEAR, OWNER_NAME_2, COLOR_2, KILOMETERS_2, CAR_STATE_2);

    PersonDto personDto = new PersonDto(PERSON_ID_NOT_EXISTS, OWNER_NAME_1, BIRTH_DATE_1, EMAIL_1);
    PersonDto personDto1 = new PersonDto(PERSON_ID_1, OWNER_NAME_1, BIRTH_DATE_1, EMAIL_1);
    PersonDto personDto2 = new PersonDto(PERSON_ID_2, OWNER_NAME_2, BIRTH_DATE_2, EMAIL_2);

    @Autowired
    CarsService carsService;

    @BeforeEach
    void setUp() {
        carsService.addCar(car1);
        carsService.addCar(car2);
        carsService.addPerson(personDto1);
        carsService.addPerson(personDto2);
        carsService.purchase(new TradeDealDto(CAR_NUMBER_1, PERSON_ID_1,LocalDate.now()));
        carsService.purchase(new TradeDealDto(CAR_NUMBER_2, PERSON_ID_2,LocalDate.now()));
    }

    @AfterEach
    void tearDown() {
        carsService = new CarsServiceImpl(); // Reset the service for the next test
    }

    @Test
    void testAddPerson() {
        PersonDto dto = new PersonDto(111111L, "OWNER3", "2000-10-10", "EMAIL3@gmail.com");
        assertEquals(dto, carsService.addPerson(dto));
        assertThrows(IllegalStateException.class, () -> carsService.addPerson(personDto1));
    }

    @Test
    void testAddCar() {
        CarDto newCar1 = new CarDto("555-11-224", MODEL, YEAR, OWNER_NAME_1, "color3", 12000, CarState.NEW);
        assertEquals(newCar1, carsService.addCar(newCar1));

        CarDto newCar2 = new CarDto("666-22-335", MODEL, YEAR, OWNER_NAME_2, "color4", 18000, CarState.OLD);
        assertEquals(newCar2, carsService.addCar(newCar2));
    }

    @Test
    void testPurchase() {
        TradeDealDto tradeDealDto = new TradeDealDto("777-33-777", PERSON_ID_1,LocalDate.now());
        assertEquals(tradeDealDto, carsService.purchase(tradeDealDto));
        assertThrows(IllegalStateException.class, () -> carsService.purchase(tradeDealDto));
    }


}
