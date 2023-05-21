package hr.antikvarijat.repository;

import hr.antikvarijat.model.Knjiga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnjigaRepository extends JpaRepository<Knjiga, Integer> {
    boolean existsByAutorIdAutor(int autorId);
    boolean existsByIzdavacIdIzdavac(int izdavacId);
}
