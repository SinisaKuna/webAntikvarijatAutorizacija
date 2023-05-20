package hr.antikvarijat.repository;

import hr.antikvarijat.model.Operater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperaterRepository extends JpaRepository<Operater, Integer> {
//    public Long countById(Integer id);
}
