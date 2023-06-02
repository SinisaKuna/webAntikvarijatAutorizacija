package hr.antikvarijat.repository;

import hr.antikvarijat.model.ProdajaZaglavlje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdajaZaglavljeRepository extends JpaRepository<ProdajaZaglavlje, Integer> {


    boolean existsByPartnerIdPartner(int id);

    @Query("SELECT MAX(p.idProdajaZaglavlje) FROM ProdajaZaglavlje p")
    int findMaxIdProdajaZaglavlje();

}
