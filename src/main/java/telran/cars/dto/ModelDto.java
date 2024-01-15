package telran.cars.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import static telran.cars.api.ValidationConstants.*;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelDto {

    @NotEmpty(message = MISSING_NAME)
    private String name;

    @NotNull(message = YEAR_IS_NULL)
    private int year;

    @NotEmpty(message = MISSING_COMPANY)
    private String company;

    @NotNull(message = ENGINE_CAPACITY_IS_NULL)
    private int enginePower;

    @NotNull(message = MISSING_ENGINE_CAPACITY)
    private int engineCapacity;
	
}
