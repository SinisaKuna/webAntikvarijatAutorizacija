package hr.antikvarijat.service;

import hr.antikvarijat.exception.ProdajaStavkaNotFoundException;
import hr.antikvarijat.model.ProdajaStavka;
import hr.antikvarijat.repository.ProdajaStavkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdajaStavkaService {
    private ProdajaStavkaRepository prodajaStavkaRepository;

    @Autowired
    public ProdajaStavkaService(ProdajaStavkaRepository prodajaStavkaRepository) {
        this.prodajaStavkaRepository = prodajaStavkaRepository;
    }

    public List<ProdajaStavka> getAllProdajaStavke() {
        return prodajaStavkaRepository.findAll();
    }

    public List<ProdajaStavka> getProdajaStavkeZaRacun(int idProdajaZaglavlje) {
        return prodajaStavkaRepository.findByProdajaZaglavljeIdProdajaZaglavlje(idProdajaZaglavlje);
    }

    public ProdajaStavka getProdajaStavkaById(int id) {
        return prodajaStavkaRepository.findById(id)
                .orElseThrow(() -> new ProdajaStavkaNotFoundException("Prodaja stavka s ID-em " + id + " nije pronađena."));
    }

    public ProdajaStavka saveProdajaStavka(ProdajaStavka prodajaStavka) {
        return prodajaStavkaRepository.save(prodajaStavka);
    }

    public void deleteProdajaStavka(int id) {
        prodajaStavkaRepository.deleteById(id);
    }



}
