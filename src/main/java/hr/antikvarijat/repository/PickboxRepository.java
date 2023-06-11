package hr.antikvarijat.repository;

import hr.antikvarijat.model.Pickbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickboxRepository extends JpaRepository<Pickbox, Integer> {
}
