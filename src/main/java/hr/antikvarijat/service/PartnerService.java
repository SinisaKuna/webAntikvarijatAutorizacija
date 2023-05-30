package hr.antikvarijat.service;

import hr.antikvarijat.exception.PartnerNotFoundException;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.model.Partner;
import hr.antikvarijat.repository.GradRepository;
import hr.antikvarijat.repository.IzdavacRepository;
import hr.antikvarijat.repository.PartnerRepository;
import hr.antikvarijat.repository.ProdajaZaglavljeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<Partner> getAllPartners() {

        List<Partner> lista =  partnerRepository.findAll();
        for (Partner partner : lista) {
            partner.setNazivGrada(null);
        }
        return  lista;
    }

    public List<Partner> getSortedPartner() {
        List<Partner> lista =  partnerRepository.findAll();
        for (Partner partner : lista) {
            partner.setNazivGrada(null);
        }
        Collator collator = Collator.getInstance(new Locale("hr", "HR"));
        Collections.sort(lista, (d1, d2) -> collator.compare(d1.getNazivPartnera(), d2.getNazivPartnera()));
        return lista;
    }




    public Partner getPartnerById(int id) {
        return partnerRepository.findById(id)
                .orElseThrow(() -> new PartnerNotFoundException("Partner with id " + id + " not found."));
    }

    public Partner savePartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    public void deletePartner(int id) {
        Partner existingPartner = getPartnerById(id);
         partnerRepository.delete(existingPartner);
    }




}
