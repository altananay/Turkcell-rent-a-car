package kodlama.io.rentacar.business.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCarResponse {
    private int id;
    private double dailyPrice;
    private int modelYear;
    private String state;
    private int modelId;
}
