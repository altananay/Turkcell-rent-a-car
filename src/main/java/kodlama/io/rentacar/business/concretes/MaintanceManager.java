package kodlama.io.rentacar.business.concretes;

import kodlama.io.rentacar.business.abstracts.CarService;
import kodlama.io.rentacar.business.abstracts.MaintanceService;
import kodlama.io.rentacar.business.dto.requests.create.CreateMaintanceRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateCarRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateMaintanceRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateMaintanceResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllMaintancesResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetCarResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetMaintanceResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateMaintanceResponse;
import kodlama.io.rentacar.entities.Car;
import kodlama.io.rentacar.entities.Maintance;
import kodlama.io.rentacar.entities.enums.State;
import kodlama.io.rentacar.repository.MaintanceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MaintanceManager implements MaintanceService {

    /*arabalar bakıma (maintenance) gönderilebilmelidir.
     Bakımdan gelen araba yeniden kiralanabilir duruma gelmelidir.
      Zaten bakımda olan araba bakıma gönderilememez. Kirada olan araba bakıma gönderilemez.
       Bakımda olan araba araba listesinde görüntülenip görüntülenmeyeceğine kullanıcıdan bir parametre alarak gelmelidir
        veya gelmemelidir.
     */

    private final MaintanceRepository maintanceRepository;
    private final ModelMapper mapper;
    private final CarService carService;

    @Override
    public List<GetAllMaintancesResponse> getAll() {
        List<Maintance> maintances = maintanceRepository.findAll();
        List<GetAllMaintancesResponse> responses = maintances.stream().map(maintance -> mapper.map(maintance, GetAllMaintancesResponse.class)).toList();
        return responses;
    }

    @Override
    public GetMaintanceResponse getById(int id) {
        Maintance maintance = maintanceRepository.findById(id).orElseThrow();
        GetMaintanceResponse response = mapper.map(maintance, GetMaintanceResponse.class);
        return response;
    }

    @Override
    public CreateMaintanceResponse add(CreateMaintanceRequest request) {
        carService.checkIfCarExists(request.getCarId());
        checkIfCarCanBeSentToMaintance(request.getCarId());
        Maintance maintance = mapper.map(request, Maintance.class);
        maintance.setId(0);
        maintance.setSendDate(new Date());
        maintanceRepository.save(maintance);
        sentToMaintance(request.getCarId());
        CreateMaintanceResponse response = mapper.map(maintance, CreateMaintanceResponse.class);
        return response;
    }

    @Override
    public UpdateMaintanceResponse update(int id, UpdateMaintanceRequest request) {

        checkIfMaintanceIdExists(id);
        Maintance maintance = mapper.map(request, Maintance.class);
        maintance.setId(id);
        if (request.getState() == State.AVAILABLE)
        {
            returnFromMaintance(request.getCarId());
            maintance.setReturnDate(new Date());
        }
        else if (request.getState() == State.RENTED)
            throw new RuntimeException("Bakımda olan araç kiralanamaz");
        maintanceRepository.save(maintance);
        UpdateMaintanceResponse response = mapper.map(maintance, UpdateMaintanceResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        maintanceRepository.deleteById(id);
    }

    private void sentToMaintance(int carId)
    {
        GetCarResponse response = carService.getById(carId);
        response.setState(State.MAINTANCE.name());
        UpdateCarRequest request = mapper.map(response, UpdateCarRequest.class);
        carService.update(carId, request);
    }

    private void returnFromMaintance(int carId)
    {
        GetCarResponse response = carService.getById(carId);
        checkIfCarRented(response.getId());
        checkIfCarInAvailable(response.getId());
        response.setState(State.AVAILABLE.name());
        UpdateCarRequest request = mapper.map(response, UpdateCarRequest.class);
        carService.update(carId, request);
    }

    //aggregate function
    private void checkIfCarCanBeSentToMaintance(int carId)
    {
        checkIfCarRented(carId);
        checkIfCarInMaintance(carId);
    }

    private void checkIfCarInMaintance(int carId)
    {
        GetCarResponse car = carService.getById(carId);
        if (car.getState() == State.MAINTANCE.name())
            throw new RuntimeException("Car is already in maintance");
    }

    private void checkIfCarInAvailable(int carId)
    {
        GetCarResponse car = carService.getById(carId);
        if (car.getState() == State.AVAILABLE.name())
            throw new RuntimeException("Car is already in available");
    }

    private void checkIfCarRented(int carId)
    {
        GetCarResponse car = carService.getById(carId);
        if (car.getState() == State.RENTED.name())
            throw new RuntimeException("Car is already in rented");
    }

    private void checkIfMaintanceIdExists(int id)
    {
        if (!maintanceRepository.existsById(id))
            throw new RuntimeException("Id bulunamadı");
    }
}
