package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.business.abstracts.MaintenanceService;
import kodlama.io.rentacar.business.abstracts.RentalService;
import kodlama.io.rentacar.business.dto.requests.create.CreateRentalRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateRentalRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateRentalResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllRentalsResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetRentalResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateRentalResponse;
import kodlama.io.rentacar.entities.Car;
import kodlama.io.rentacar.entities.Rental;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class RentalManager implements RentalService {

    private final RentalRepository rentalRepository;
    private final ModelMapper mapper;
    private final CarService carService;
    private final MaintenanceService maintenanceService;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        List<Rental> rentals = rentalRepository.findAll();
        List<GetAllRentalsResponse> responses = rentals.stream().map(rental -> mapper.map(rental, GetAllRentalsResponse.class)).toList();
        return responses;
    }

    @Override
    public GetRentalResponse getById(int id) {
        checkIfRentalExists(id);
        Rental rental = rentalRepository.findById(id).orElseThrow();
        GetRentalResponse response = mapper.map(rental, GetRentalResponse.class);
        return response;
    }

    @Override
    public GetRentalResponse returnCarFromRented(int carId) {
        carService.checkIfCarExists(carId);
        Rental rental = rentalRepository.findRentalByCarId(carId);
        rental.setEndDate(LocalDateTime.now());
        carService.changeState(carId, State.AVAILABLE);
        rentalRepository.save(rental);
        GetRentalResponse response = mapper.map(rental, GetRentalResponse.class);
        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        checkIfCarUnderRented(request.getCarId());
        maintenanceService.checkIfCarUnderMaintenance(request.getCarId());
        Rental rental = mapper.map(request, Rental.class);
        rental.setId(0);
        rental.setStartDate(LocalDateTime.now());
        carService.changeState(request.getCarId(), State.RENTED);
        getTotalPrice(rental);
        rentalRepository.save(rental);
        CreateRentalResponse response = mapper.map(rental, CreateRentalResponse.class);
        return response;
    }

    @Override
    public UpdateRentalResponse update(int id, UpdateRentalRequest request) {
        checkIfRentalExists(id);
        Rental rental = mapper.map(request, Rental.class);
        rental.setId(id);
        rentalRepository.save(rental);
        UpdateRentalResponse response = mapper.map(rental, UpdateRentalResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfRentalExists(id);
        makeCarAvailable(id);
        setEndDate(id);
        //rentalRepository.deleteById(id);
    }

    private Rental getTotalPrice(Rental rental)
    {
        double totalPrice = rental.getDailyPrice() * rental.getRentedForDays();
        rental.setTotalPrice(totalPrice);
        return rental;
    }

    private void checkIfRentalExists(int id)
    {
        if (!rentalRepository.existsById(id))
            throw new RuntimeException("Kira bulunamadı");
    }
    private void checkIfCarUnderRented(int carId)
    {
        Car car = mapper.map(carService.getById(carId), Car.class);
        if (car.getState().equals(State.RENTED))
            throw new RuntimeException("Araba zaten kirada");
    }

    private void makeCarAvailable(int id)
    {
        int carId = rentalRepository.findById(id).get().getCar().getId();
        carService.changeState(carId, State.AVAILABLE);
    }

    private void setEndDate(int id)
    {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        rental.setEndDate(LocalDateTime.now());
        rentalRepository.save(rental);
    }
}
