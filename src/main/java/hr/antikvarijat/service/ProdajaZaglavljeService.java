package hr.antikvarijat.service;

import hr.antikvarijat.exception.ProdajaZaglavljeNotFoundException;
import hr.antikvarijat.model.ProdajaZaglavlje;
import hr.antikvarijat.repository.PartnerRepository;
import hr.antikvarijat.repository.ProdajaZaglavljeRepository;
import hr.antikvarijat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdajaZaglavljeService {

    private final ProdajaZaglavljeRepository prodajaZaglavljeRepository;
    private final UserRepository userRepository;
    public final PartnerRepository partnerRepository;

    @Autowired
    public ProdajaZaglavljeService(ProdajaZaglavljeRepository prodajaZaglavljeRepository, UserRepository userRepository, PartnerRepository partnerRepository) {
        this.prodajaZaglavljeRepository = prodajaZaglavljeRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
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
        return prodajaZaglavljeRepository.save(prodajaZaglavlje);
    }

    public void deleteProdajaZaglavlje(int id) {
        if (prodajaZaglavljeRepository.existsById(id)) {
            prodajaZaglavljeRepository.deleteById(id);
        } else {
            throw new ProdajaZaglavljeNotFoundException("Prodaja zaglavlje s ID-om " + id + " ne postoji.");
        }
    }

    @Autowired
    private ProdajaZaglavljeRepository prodajaRepository;

    public boolean hasProdajaZaglavlje(int id) {
        return prodajaRepository.existsByPartnerIdPartner(id);
    }
}
