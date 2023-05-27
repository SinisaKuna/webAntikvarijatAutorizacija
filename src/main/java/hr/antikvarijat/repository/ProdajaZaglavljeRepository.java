package hr.antikvarijat.repository;

import hr.antikvarijat.model.ProdajaZaglavlje;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdajaZaglavljeRepository extends JpaRepository<ProdajaZaglavlje, Integer> {


    boolean existsByPartnerIdPartner(int id);
}
