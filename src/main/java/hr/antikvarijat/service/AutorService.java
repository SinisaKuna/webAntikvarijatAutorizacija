package hr.antikvarijat.service;

import hr.antikvarijat.exception.AutorNotFoundException;
import hr.antikvarijat.model.Autor;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.repository.AutorRepository;
import hr.antikvarijat.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository,
                        KnjigaRepository knjigaRepository) {
        this.autorRepository = autorRepository;
        this.knjigaRepository = knjigaRepository;
    }

    public List<Autor> getAllAutori() {
        List<Autor> lista =  autorRepository.findAll();
        for (Autor autor : lista) {
            autor.setNazivDrzave(null);
        }
        return  lista;
    }

    public Autor getAutorById(int id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new AutorNotFoundException("Autor s ID-em " + id + " nije pronađen."));
    }

    public Autor saveAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    public void deleteAutor(int id) {
        autorRepository.deleteById(id);
    }

    @Autowired
    private KnjigaRepository knjigaRepository;

    public boolean hasKnjiga(int autorId) {
        return knjigaRepository.existsByAutorIdAutor(autorId);
    }
}
