package hr.antikvarijat.repository;

import hr.antikvarijat.model.Izdavac;
import org.springframework.data.repository.CrudRepository;

public interface IzdavacRepository extends CrudRepository<Izdavac, Integer> {
    boolean existsByGradIdGrad(int gradId);
}
