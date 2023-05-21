package hr.antikvarijat.service;

import hr.antikvarijat.exception.NacinPlacanjaNotFoundException;
import hr.antikvarijat.model.NacinPlacanja;
import hr.antikvarijat.repository.NacinPlacanjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NacinPlacanjaService {
    private final NacinPlacanjaRepository nacinPlacanjaRepository;

    @Autowired
    public NacinPlacanjaService(NacinPlacanjaRepository nacinPlacanjaRepository) {
        this.nacinPlacanjaRepository = nacinPlacanjaRepository;
    }

    public List<NacinPlacanja> getAllNacinPlacanja() {
        return nacinPlacanjaRepository.findAll();
    }

    public NacinPlacanja getNacinPlacanjaById(int id) {
        Optional<NacinPlacanja> optionalNacinPlacanja = nacinPlacanjaRepository.findById(id);
        return optionalNacinPlacanja.orElseThrow(() -> new NacinPlacanjaNotFoundException("Nacin placanja s ID-em " + id + " nije pronađen."));
    }

    public NacinPlacanja createNacinPlacanja(NacinPlacanja nacinPlacanja) {
        return nacinPlacanjaRepository.save(nacinPlacanja);
    }

    public NacinPlacanja updateNacinPlacanja(int id, NacinPlacanja nacinPlacanjaDetails) {
        NacinPlacanja nacinPlacanja = getNacinPlacanjaById(id);
        nacinPlacanja.setNazivNacinaPlacanja(nacinPlacanjaDetails.getNazivNacinaPlacanja());
        nacinPlacanja.setOznakaNacinaPlacanja(nacinPlacanjaDetails.getOznakaNacinaPlacanja());
        return nacinPlacanjaRepository.save(nacinPlacanja);
    }

    public void deleteNacinPlacanja(int id) {
        NacinPlacanja nacinPlacanja = getNacinPlacanjaById(id);
        nacinPlacanjaRepository.delete(nacinPlacanja);
    }
}
