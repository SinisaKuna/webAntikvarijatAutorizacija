package hr.antikvarijat.repository;

import hr.antikvarijat.model.Grad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradRepository extends JpaRepository<Grad, Integer> {
}
