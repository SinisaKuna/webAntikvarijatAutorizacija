package hr.antikvarijat.service;

import hr.antikvarijat.exception.DrzavaNotFoundException;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.repository.DrzavaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrzavaService {

    private final DrzavaRepository drzavaRepository;

    @Autowired
    public DrzavaService(DrzavaRepository drzavaRepository) {
        this.drzavaRepository = drzavaRepository;
    }

//    public Drzava dohvatiDrzavu(int idDrzava) {
//        return drzavaRepository.findById(idDrzava).orElse(null);
//    }
    public List<Drzava> getAllDrzave() {
        return drzavaRepository.findAll();
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
}
