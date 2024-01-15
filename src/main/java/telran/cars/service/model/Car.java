package telran.cars.service.model;

import lombok.*;
import telran.cars.dto.CarDto;
import telran.cars.dto.CarState;
import jakarta.persistence.*;
@Entity
@Getter
@Setter
@Table(name="cars")
public class Car {
	@Id
	String number;
	@ManyToOne
	@JoinColumns({@JoinColumn(name="model_name", nullable = false),
		@JoinColumn(name="model_year", nullable = false)})
	Model model;
	@ManyToOne
	@JoinColumn(name="owner_id", nullable=true)
	CarOwner carOwner;
	String color;
	int kilometers;
	@Enumerated(EnumType.STRING) // value in the table will be a string (by default a number)
	CarState state;
	
	public static Car of(CarDto carDto, Model model, CarOwner carOwner) {
        Car car = new Car();
        car.number=carDto.number();
        car.model=model;
        car.carOwner=carOwner;
        car.color=carDto.color();
        car.kilometers=carDto.kilometers();
        car.state=carDto.carState();
        return car;
    }

    public CarDto buildDto() {
        return new CarDto(
                this.getNumber(),
                this.getModel().getCompany(), 
                this.getModel().getModelYear().getYear(), 
                this.getCarOwner().getName(), 
                this.getColor(),
                this.getKilometers(),
                this.getState()
        );
    }
	
	
}