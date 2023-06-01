package hr.antikvarijat.service;

import hr.antikvarijat.exception.KnjigaNotFoundException;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.model.Knjiga;
import hr.antikvarijat.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
        List<Knjiga> listaKnjiga =  knjigaRepository.findAll();
        for (Knjiga knjiga : listaKnjiga) {
            knjiga.setNazivAutora(null);
            knjiga.setNazivIzdavaca(null);
        }
        return  listaKnjiga;
    }
    public List<Knjiga> getSortedKnjiga() {
        List<Knjiga> lista =  knjigaRepository.findAll();
        for (Knjiga knjiga : lista) {
            knjiga.setNazivAutora(null);
            knjiga.setNazivIzdavaca(null);
        }
        Collator collator = Collator.getInstance(new Locale("hr", "HR"));
        Collections.sort(lista, (d1, d2) -> collator.compare(d1.getNazivKnjige(), d2.getNazivKnjige()));
        return lista;
    }



    public void obrisiKnjigu(int id) {
        knjigaRepository.deleteById(id);
    }

    // Ostale metode za rad s knjigama
}
