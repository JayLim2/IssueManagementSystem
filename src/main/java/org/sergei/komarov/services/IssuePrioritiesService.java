package org.sergei.komarov.services;

import org.sergei.komarov.models.IssuePriority;
import org.sergei.komarov.repositories.IssuePrioritiesRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Supplier;

@Service
public class IssuePrioritiesService {

    private final IssuePrioritiesRepository issuePrioritiesRepository;

    public IssuePrioritiesService(IssuePrioritiesRepository issuePrioritiesRepository) {
        this.issuePrioritiesRepository = issuePrioritiesRepository;
    }

    public List<IssuePriority> getAll() {
        return issuePrioritiesRepository.findAll();
    }

    public void save(IssuePriority priority) {
        issuePrioritiesRepository.save(priority);
    }

    public void saveAll(Iterable<IssuePriority> priorities) {
        issuePrioritiesRepository.saveAll(priorities);
    }

    public IssuePriority getById(int id) {
        return issuePrioritiesRepository.findById(id).orElse(null);
    }

    public boolean isExistsById(int id) {
        return issuePrioritiesRepository.existsById(id);
    }

    public void deleteById(int id) {
        issuePrioritiesRepository.deleteById(id);
    }
}
