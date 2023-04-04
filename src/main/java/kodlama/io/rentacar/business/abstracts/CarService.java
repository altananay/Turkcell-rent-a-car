package kodlama.io.rentacar.business.abstracts;

import kodlama.io.rentacar.business.dto.requests.create.CreateCarRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateCarRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateCarResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllCarsResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetCarResponse;
import kodlama.io.rentacar.business.dto.responses.maintance.UpdateCarStateReturnMaintanceResponse;
import kodlama.io.rentacar.business.dto.responses.maintance.UpdateCarStateSendMaintanceResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateCarResponse;

import java.util.List;

public interface CarService {

    List<GetAllCarsResponse> getAllByState(Boolean filter);
    List<GetAllCarsResponse> getAll();
    CreateCarResponse add(CreateCarRequest request);
    void delete(int id);
    UpdateCarResponse update(int id, UpdateCarRequest request);
    GetCarResponse getById(int id);

    void checkIfCarExists(int id);
}