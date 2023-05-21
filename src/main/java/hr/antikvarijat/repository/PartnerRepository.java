package hr.antikvarijat.repository;

import hr.antikvarijat.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {
    boolean existsByGradIdGrad(int gradId);
}
