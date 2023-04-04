package kodlama.io.rentacar.business.abstracts;

import kodlama.io.rentacar.business.dto.requests.create.CreateMaintanceRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateMaintanceRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateMaintanceResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllMaintancesResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetMaintanceResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateMaintanceResponse;

import java.util.List;

public interface MaintanceService {
    List<GetAllMaintancesResponse> getAll();
    GetMaintanceResponse getById(int id);
    CreateMaintanceResponse add(CreateMaintanceRequest request);
    UpdateMaintanceResponse update(int id, UpdateMaintanceRequest request);
    void delete(int id);
}