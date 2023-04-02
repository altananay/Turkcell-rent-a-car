package kodlama.io.rentacar.business.dto.responses.maintance;

import kodlama.io.rentacar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarStateSendMaintanceResponse {
    private int carId;
    private State state;
}