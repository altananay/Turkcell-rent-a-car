package kodlama.io.rentacar.business.dto.responses.get;

import kodlama.io.rentacar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCarResponse {
    private int id;
    private double dailyPrice;
    private int modelYear;
    private String plate;
    private State state;
    private int modelId;
    private String modelName;
    private String modelBrandName;
}
