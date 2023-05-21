package hr.antikvarijat.service;


import hr.antikvarijat.exception.OperaterNotFoundException;
import hr.antikvarijat.model.Operater;
import hr.antikvarijat.repository.OperaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperaterService {
    @Autowired
    private OperaterRepository repo;

    public List<Operater> listAll() {
        return (List<Operater>) repo.findAll();
    }

    public void save(Operater operater) {
        repo.save(operater);
    }

    public Operater get(Integer id) throws OperaterNotFoundException {
        Optional<Operater> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new OperaterNotFoundException("Could not find any record with ID " + id);
    }

    public void delete(Integer id) throws OperaterNotFoundException {

        Long count = repo.countByIdOperater(id);
        if (count == null || count == 0) {
            throw new OperaterNotFoundException("Could not find any record with ID " + id);
        }
        repo.deleteById(id);
    }
}
