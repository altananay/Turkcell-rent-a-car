package kodlama.io.rentacar.repository;

import kodlama.io.rentacar.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    Rental findRentalByCarId(int carId);
}
