package kodlama.io.rentacar.business.dto.responses.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalResponse {
    private int id;
    private int carId;
    private double dailyPrice, totalPrice;
    private int rentedForDays;
    private LocalDateTime startDate;
}
