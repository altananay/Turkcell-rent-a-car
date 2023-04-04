package kodlama.io.rentacar.repository;

import kodlama.io.rentacar.entities.Maintance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintanceRepository extends JpaRepository<Maintance, Integer> {
}
