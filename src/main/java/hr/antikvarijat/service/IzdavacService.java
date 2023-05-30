package hr.antikvarijat.service;

import hr.antikvarijat.exception.IzdavacNotFoundException;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.model.Izdavac;
import hr.antikvarijat.model.Partner;
import hr.antikvarijat.repository.IzdavacRepository;
import hr.antikvarijat.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class IzdavacService {
    private final IzdavacRepository izdavacRepository;

    @Autowired
    public IzdavacService(IzdavacRepository izdavacRepository) {
        this.izdavacRepository = izdavacRepository;
    }

    public List<Izdavac> getAllIzdavaci() {
        List<Izdavac> lista = izdavacRepository.findAll();
        for (Izdavac izdavac : lista) {
            izdavac.setNazivGrada(null);
        }
        return  lista;
    }

    public List<Izdavac> getSortedIzdavac() {
        List<Izdavac> lista =  izdavacRepository.findAll();
        for (Izdavac grad : lista) {
            grad.setNazivGrada(null);
        }
//        Collections.sort(lista, (d1, d2) -> d1.getNazivIzdavaca().compareToIgnoreCase(d2.getNazivIzdavaca()));
        Collator collator = Collator.getInstance(new Locale("hr", "HR"));
        Collections.sort(lista, (d1, d2) -> collator.compare(d1.getNazivIzdavaca(), d2.getNazivIzdavaca()));

        return lista;
    }



    public Izdavac getIzdavacById(int id) {
        Optional<Izdavac> optionalIzdavac = izdavacRepository.findById(id);
        if (optionalIzdavac.isPresent()) {
            return optionalIzdavac.get();
        }
        throw new IzdavacNotFoundException("Izdavac s ID-om " + id + " nije pronađen.");
    }

    public Izdavac saveIzdavac(Izdavac izdavac) {
        return izdavacRepository.save(izdavac);
    }

    public void deleteIzdavac(int id) {
        izdavacRepository.deleteById(id);
    }

    @Autowired
    private KnjigaRepository knjigaRepository;

    public boolean hasKnjiga(int izdavacId) {
        return knjigaRepository.existsByIzdavacIdIzdavac(izdavacId);
    }
}
