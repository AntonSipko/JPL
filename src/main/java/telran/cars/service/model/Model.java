package telran.cars.service.model;
import jakarta.persistence.*;
import lombok.*;
import telran.cars.dto.ModelDto;
@Entity
@Table(name="models")
@Getter
@Setter
public class Model {
	@EmbeddedId
	ModelYear modelYear;
	@Column(nullable = false)
	String company;
	@Column(name="engine_power", nullable = false)
	int enginePower;
	@Column(name="engine_capacity", nullable = false)
	int engineCapacity;
	public static Model of(ModelDto modelDto) {
        Model model = new Model();
        model.setModelYear(new ModelYear(modelDto.getName(), modelDto.getYear()));
        model.setCompany(modelDto.getCompany());
        model.setEnginePower(modelDto.getEnginePower());
        model.setEngineCapacity(modelDto.getEngineCapacity());
        return model;
    }

    public ModelDto buildDto() {
        return new ModelDto(
                this.getModelYear().getName(),
                this.getModelYear().getYear(),
                this.getCompany(),
                this.getEnginePower(),
                this.getEngineCapacity()
        );
    }
	 
}