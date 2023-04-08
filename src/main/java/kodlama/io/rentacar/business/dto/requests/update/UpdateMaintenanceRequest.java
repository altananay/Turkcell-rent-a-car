package kodlama.io.rentacar.business.dto.requests.update;

import kodlama.io.rentacar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMaintenanceRequest {
    private int id;
    private Date sendDate;
    private Date returnDate;
    private int carId;
    private State state;
}
