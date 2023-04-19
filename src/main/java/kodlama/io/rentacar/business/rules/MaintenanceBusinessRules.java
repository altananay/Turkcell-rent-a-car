package kodlama.io.rentacar.business.rules;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.common.constants.Messages;
import kodlama.io.rentacar.core.exceptions.BusinessException;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MaintenanceBusinessRules {
    private final MaintenanceRepository repository;

    public void checkIfMaintenancesExists(int id)
    {
        if (!repository.existsById(id))
            throw new BusinessException(Messages.Maintenance.NotExists);
    }

    public void checkIfCarUnderMaintenance(int carId)
    {
        if (repository.existsByCarIdAndIsCompletedFalse(carId))
            throw new BusinessException(Messages.Maintenance.CarExists);
    }


    public void checkIfCarIsNotUnderMaintenance(int carId)
    {
        if (!repository.existsByCarIdAndIsCompletedFalse(carId))
            throw new BusinessException("Bakımda böyle bir araç yok");
    }

    public void checkCarAvailabilityForMaintenance(State state)
    {
        if (state.equals(State.RENTED))
            throw new BusinessException(Messages.Maintenance.CarIsRented);
    }

}