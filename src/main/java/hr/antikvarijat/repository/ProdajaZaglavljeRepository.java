package hr.antikvarijat.repository;

import hr.antikvarijat.model.ProdajaZaglavlje;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdajaZaglavljeRepository extends JpaRepository<ProdajaZaglavlje, Integer> {


//    @Transactional
//    default <S extends ProdajaZaglavlje> S saveWithNullPartner(S entity) {
//        if (entity.getPartner() == null) {
//            entity.setPartner(null);
//        }
//        return save(entity);
//    }
}
