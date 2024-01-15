package telran.cars;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.cars.dto.CarDto;
import telran.cars.dto.CarState;
import telran.cars.dto.PersonDto;
import telran.cars.dto.TradeDealDto;
import telran.cars.exceptions.NotFoundException;
import telran.cars.exceptions.controller.CarsExceptionsController;
import telran.cars.service.CarsService;

@WebMvcTest
class CarsControllerTest {
    private static final long PERSON_ID = 123000L;
    private static final String CAR_NUMBER = "123-01-002";
    private static final String PERSON_NOT_FOUND_MESSAGE = "person not found";
    private static final String PERSON_ALREADY_EXISTS_MESSAGE = "person already exists";
    private static final String CAR_ALREADY_EXISTS_MESSAGE = "car already exists";
    private static final String CAR_NOT_FOUND_MESSAGE = "car not found";
    static final String WRONG_EMAIL_ADDRESS = "kuku";
    static final String EMAIL_ADDRESS = "vasya@gmail.com";
    private static final long WRONG_PERSON_ID = 123L;
    static final String WRONG_PERSON_ID_TYPE = "abc";
    static final String WRONG_CAR_NUMBER = "kikuk";
    static final CarState carState=CarState.BAD;

    @MockBean
    CarsService carsService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    CarDto carDto = new CarDto(CAR_NUMBER, "model", 2012, "vasyas", "black", 100,carState );
    CarDto carDto1 = new CarDto(CAR_NUMBER, "model", 2012, "vasyas", "black", 100,carState);

    CarDto carDtoMissingFields = new CarDto(null, null, 0, null, null, 0, null);

    PersonDto personDto = new PersonDto(PERSON_ID, "Vasya", "2000-10-10", EMAIL_ADDRESS);
    PersonDto personDtoUpdated = new PersonDto(PERSON_ID, "Vasya", "2000-10-10", "vasya@tel-ran.com");
    PersonDto personWrongEmail = new PersonDto(PERSON_ID, "Vasya", "2000-10-10", WRONG_EMAIL_ADDRESS);
    PersonDto personNoId = new PersonDto(null, "Vasya", "2000-10-10", EMAIL_ADDRESS);
    PersonDto personWrongId = new PersonDto(100000000000L, "Vasya", "2000-10-10", EMAIL_ADDRESS);

    TradeDealDto tradeDeal = new TradeDealDto(CAR_NUMBER, PERSON_ID,LocalDate.now());

    @Test
    void testAddCar() throws Exception {
        when(carsService.addCar(carDto)).thenReturn(carDto);
        String jsonCarDto = mapper.writeValueAsString(carDto);
        String actualJSON = mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCarDto))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(jsonCarDto, actualJSON);
    }

    @Test
    void testAddPerson() throws Exception {
        when(carsService.addPerson(personDto)).thenReturn(personDto);
        String jsonPersonDto = mapper.writeValueAsString(personDto);
        String actualJSON = mockMvc.perform(post("/cars/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPersonDto))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(jsonPersonDto, actualJSON);
    }

    @Test
    void testUpdatePerson() throws Exception {
        when(carsService.updatePerson(personDtoUpdated)).thenReturn(personDtoUpdated);
        String jsonPersonDtoUpdated = mapper.writeValueAsString(personDtoUpdated);
        String actualJSON = mockMvc.perform(put("/cars/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPersonDtoUpdated))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(jsonPersonDtoUpdated, actualJSON);
    }

    @Test
    void testPurchase() throws Exception {
        when(carsService.purchase(tradeDeal)).thenReturn(tradeDeal);
        String jsonTradeDeal = mapper.writeValueAsString(tradeDeal);
        String actualJSON = mockMvc.perform(put("/cars/trade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTradeDeal))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(jsonTradeDeal, actualJSON);
    }

}