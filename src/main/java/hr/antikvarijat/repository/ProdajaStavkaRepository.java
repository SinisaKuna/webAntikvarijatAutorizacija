package hr.antikvarijat.repository;

import hr.antikvarijat.model.ProdajaStavka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdajaStavkaRepository extends JpaRepository<ProdajaStavka, Integer> {
}
