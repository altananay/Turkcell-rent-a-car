package kodlama.io.rentacar.business.rules;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.common.constants.Messages;
import kodlama.io.rentacar.core.exceptions.BusinessException;
import kodlama.io.rentacar.entities.Car;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalBusinessRules {
    private final RentalRepository repository;
    private final CarService service;
    private final ModelMapper mapper;
    public void checkIfRentalExists(int id)
    {
        if (!repository.existsById(id))
            throw new BusinessException("Kira bulunamadı");
    }
    public void checkIfCarUnderRented(int carId)
    {
        Car car = mapper.map(service.getById(carId), Car.class);
        if (car.getState().equals(State.RENTED))
            throw new BusinessException("Araba zaten kirada");
    }

    public void checkIfNotCarUnderRented(int carId)
    {
        Car car = mapper.map(service.getById(carId), Car.class);
        if (!car.getState().equals(State.RENTED))
            throw new BusinessException("Araba kirada değil.");
    }

    public void checkIfCarAvailable(State state) {
        if (!state.equals(State.AVAILABLE)) {
            throw new BusinessException(Messages.Car.NotAvailable);
        }
    }
}
