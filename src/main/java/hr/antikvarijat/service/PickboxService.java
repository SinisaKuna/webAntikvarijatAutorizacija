package hr.antikvarijat.service;


import hr.antikvarijat.model.Pickbox;
import hr.antikvarijat.repository.PickboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PickboxService {

    private final PickboxRepository pickboxRepository;

    @Autowired
    public PickboxService(PickboxRepository pickboxRepository) {
        this.pickboxRepository = pickboxRepository;
    }

    public void deleteAllPickboxRecords() {
        pickboxRepository.deleteAll();
    }

    public Pickbox savePickBox(Pickbox pickbox) {
        return pickboxRepository.save(pickbox);
    }

    public Optional<Pickbox> getPickboxById(int id) {
        return pickboxRepository.findById(id);
    }

}

