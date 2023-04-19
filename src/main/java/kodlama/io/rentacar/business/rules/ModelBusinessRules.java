package kodlama.io.rentacar.business.rules;

import kodlama.io.rentacar.core.exceptions.BusinessException;
import kodlama.io.rentacar.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ModelBusinessRules {

    private final ModelRepository repository;

    public void checkIfModelExists(int id)
    {
        if (!repository.existsById(id))
            throw new BusinessException("Model bulunamadı");
    }

}
