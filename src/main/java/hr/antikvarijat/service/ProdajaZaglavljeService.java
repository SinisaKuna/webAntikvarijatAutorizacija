package hr.antikvarijat.service;

import hr.antikvarijat.exception.ProdajaZaglavljeNotFoundException;
import hr.antikvarijat.model.ProdajaZaglavlje;
import hr.antikvarijat.repository.ProdajaZaglavljeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdajaZaglavljeService {

    private final ProdajaZaglavljeRepository prodajaZaglavljeRepository;

    @Autowired
    public ProdajaZaglavljeService(ProdajaZaglavljeRepository prodajaZaglavljeRepository) {
        this.prodajaZaglavljeRepository = prodajaZaglavljeRepository;
    }

    public List<ProdajaZaglavlje> getAllProdajaZaglavlje() {
        return prodajaZaglavljeRepository.findAll();
    }

    public ProdajaZaglavlje getProdajaZaglavljeById(int id) {
        Optional<ProdajaZaglavlje> optionalProdajaZaglavlje = prodajaZaglavljeRepository.findById(id);
        if (optionalProdajaZaglavlje.isPresent()) {
            return optionalProdajaZaglavlje.get();
        } else {
            throw new ProdajaZaglavljeNotFoundException("Prodaja zaglavlje s ID-om " + id + " ne postoji.");
        }
    }

    public ProdajaZaglavlje saveProdajaZaglavlje(ProdajaZaglavlje prodajaZaglavlje) {
         if (prodajaZaglavlje.getPartner().getIdPartner() == 0) {
            prodajaZaglavlje.setPartner(null);
        }
        return prodajaZaglavljeRepository.save(prodajaZaglavlje);
    }

    public void deleteProdajaZaglavlje(int id) {
        if (prodajaZaglavljeRepository.existsById(id)) {
            prodajaZaglavljeRepository.deleteById(id);
        } else {
            throw new ProdajaZaglavljeNotFoundException("Prodaja zaglavlje s ID-om " + id + " ne postoji.");
        }
    }
}
