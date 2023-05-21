package hr.antikvarijat.repository;

import hr.antikvarijat.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    boolean existsByDrzavaIdDrzava(int drzavaId);
}
