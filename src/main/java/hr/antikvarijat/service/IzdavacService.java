package hr.antikvarijat.service;

import hr.antikvarijat.exception.IzdavacNotFoundException;
import hr.antikvarijat.model.Izdavac;
import hr.antikvarijat.repository.IzdavacRepository;
import hr.antikvarijat.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IzdavacService {
    private final IzdavacRepository izdavacRepository;

    @Autowired
    public IzdavacService(IzdavacRepository izdavacRepository) {
        this.izdavacRepository = izdavacRepository;
    }

    public List<Izdavac> getAllIzdavaci() {
        return (List<Izdavac>) izdavacRepository.findAll();
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
