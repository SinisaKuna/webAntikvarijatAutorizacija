package hr.antikvarijat.service;

import hr.antikvarijat.exception.KnjigaNotFoundException;
import hr.antikvarijat.model.Knjiga;
import hr.antikvarijat.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnjigaService {
    private final KnjigaRepository knjigaRepository;

    @Autowired
    public KnjigaService(KnjigaRepository knjigaRepository) {
        this.knjigaRepository = knjigaRepository;
    }

    public Knjiga spremiKnjigu(Knjiga knjiga) {
        return knjigaRepository.save(knjiga);
    }

    public Knjiga dohvatiKnjiguPoId(int id) {
        return knjigaRepository.findById(id)
                .orElseThrow(() -> new KnjigaNotFoundException("Knjiga s ID-om " + id + " nije pronađena."));
    }

    public List<Knjiga> dohvatiSveKnjige() {
        return knjigaRepository.findAll();
    }

    public void obrisiKnjigu(int id) {
        knjigaRepository.deleteById(id);
    }

    // Ostale metode za rad s knjigama
}
