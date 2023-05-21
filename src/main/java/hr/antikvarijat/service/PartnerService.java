package hr.antikvarijat.service;

import hr.antikvarijat.exception.PartnerNotFoundException;
import hr.antikvarijat.model.Partner;
import hr.antikvarijat.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
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
