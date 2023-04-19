package kodlama.io.rentacar.business.dto.requests.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import kodlama.io.rentacar.common.constants.Regex;
import kodlama.io.rentacar.common.utils.annotations.NotFutureYear;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
    @Min(1)
    private double dailyPrice;
    @Min(1998)
    @NotFutureYear
    private int modelYear;
    @Pattern(regexp = Regex.plate, message = "pattern isn't correct")
    private String plate;
    private String state;
    private int modelId;
}