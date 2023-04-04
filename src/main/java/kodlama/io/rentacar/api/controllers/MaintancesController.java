package kodlama.io.rentacar.api.controllers;

import kodlama.io.rentacar.business.abstracts.MaintanceService;
import kodlama.io.rentacar.business.dto.requests.create.CreateMaintanceRequest;
import kodlama.io.rentacar.business.dto.requests.update.UpdateMaintanceRequest;
import kodlama.io.rentacar.business.dto.responses.create.CreateMaintanceResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetAllMaintancesResponse;
import kodlama.io.rentacar.business.dto.responses.get.GetMaintanceResponse;
import kodlama.io.rentacar.business.dto.responses.update.UpdateMaintanceResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/maintances")
@AllArgsConstructor
@RestController
public class MaintancesController {

    private final MaintanceService maintanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateMaintanceResponse add(@RequestBody CreateMaintanceRequest request)
    {
        return maintanceService.add(request);
    }

    @PutMapping("/{id}")
    public UpdateMaintanceResponse update(@PathVariable int id, @RequestBody UpdateMaintanceRequest request)
    {
        return maintanceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id)
    {
        maintanceService.delete(id);
    }

    @GetMapping
    public List<GetAllMaintancesResponse> getAll()
    {
        return maintanceService.getAll();
    }

    @GetMapping("/{id}")
    public GetMaintanceResponse getById(@PathVariable int id)
    {
        return maintanceService.getById(id);
    }
}