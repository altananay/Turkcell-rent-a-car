package kodlama.io.rentacar.business.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalResponse {
    private int id,carId;
    private double dailyPrice, totalPrice;
    private int rentedForDays;
    private LocalDateTime startDate;
}
