package hr.antikvarijat.service;
import hr.antikvarijat.repository.AutorRepository;
import hr.antikvarijat.repository.GradRepository;
import hr.antikvarijat.service.GradService;
import hr.antikvarijat.exception.DrzavaNotFoundException;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.repository.DrzavaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DrzavaService {

    private final DrzavaRepository drzavaRepository;

    @Autowired
    public DrzavaService(DrzavaRepository drzavaRepository) {
        this.drzavaRepository = drzavaRepository;
    }

    @Autowired
    private GradService gradService;

    public List<Drzava> getAllDrzave() {
        return drzavaRepository.findAll();
    }


    public List<Drzava> getSortedDrzave() {

        List<Drzava> drzave = getAllDrzave();
        Collections.sort(drzave, (d1, d2) -> d1.getNazivDrzave().compareToIgnoreCase(d2.getNazivDrzave()));
        return drzave;
    }


    public Drzava getDrzavaById(int id) {
        Optional<Drzava> optionalDrzava = drzavaRepository.findById(id);
        if (optionalDrzava.isPresent()) {
            return optionalDrzava.get();
        } else {
            throw new DrzavaNotFoundException("Drzava s ID-em " + id + " nije pronađena.");
        }
    }

    public Drzava saveDrzava(Drzava drzava) {
        return drzavaRepository.save(drzava);
    }

    public void deleteDrzava(int id) {
        drzavaRepository.deleteById(id);
    }

    @Autowired
    private GradRepository gradRepository;

    public boolean hasGrad(int drzavaId) {
        return gradRepository.existsByDrzavaIdDrzava(drzavaId);
    }

    @Autowired
    private AutorRepository autorRepository;

    public boolean hasAutor(int autorId) {
        return autorRepository.existsByDrzavaIdDrzava(autorId);
    }

}
