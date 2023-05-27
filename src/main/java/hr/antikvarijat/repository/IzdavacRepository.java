package hr.antikvarijat.repository;

import hr.antikvarijat.model.Izdavac;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IzdavacRepository extends JpaRepository<Izdavac, Integer> {
    boolean existsByGradIdGrad(int gradId);
}
