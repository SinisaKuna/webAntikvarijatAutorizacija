package hr.antikvarijat.service;

import hr.antikvarijat.exception.GradNotFoundException;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.model.Knjiga;
import hr.antikvarijat.repository.GradRepository;
import hr.antikvarijat.repository.IzdavacRepository;
import hr.antikvarijat.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class GradService {
    private final GradRepository gradRepository;

    @Autowired
    public GradService(GradRepository gradRepository) {
        this.gradRepository = gradRepository;
    }

    public Grad getGradById(int id) {
        return gradRepository.findById(id)
                .orElseThrow(() -> new GradNotFoundException("Grad s ID-em " + id + " nije pronađen."));
    }

    public List<Grad> getAllGradovi() {
        List<Grad> lista =  gradRepository.findAll();
        for (Grad grad : lista) {
            grad.setNazivDrzave(null);
        }
        return  lista;
    }

    public List<Grad> getSortedGrad() {
        List<Grad> lista =  gradRepository.findAll();
        for (Grad grad : lista) {
            grad.setNazivDrzave(null);
        }
        Collator collator = Collator.getInstance(new Locale("hr", "HR"));
        Collections.sort(lista, (d1, d2) -> collator.compare(d1.getNazivGrada(), d2.getNazivGrada()));
        return lista;
    }


    public Grad saveGrad(Grad grad) {
        return gradRepository.save(grad);
    }

    public void deleteGrad(int id) {
        gradRepository.deleteById(id);
    }

    @Autowired
    private IzdavacRepository izdavacRepository;

    public boolean hasIzdavac(int izdavacId) {
        return izdavacRepository.existsByGradIdGrad(izdavacId);
    }

    @Autowired
    private PartnerRepository partnerRepository;

    public boolean hasPartner(int partnerId) {
        return partnerRepository.existsByGradIdGrad(partnerId);
    }
}
