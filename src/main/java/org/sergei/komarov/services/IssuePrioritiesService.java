package org.sergei.komarov.services;

import org.sergei.komarov.models.IssuePriority;
import org.sergei.komarov.repositories.IssuePrioritiesRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IssuePrioritiesService implements JpaService<IssuePriority, Integer> {

    private final IssuePrioritiesRepository issuePrioritiesRepository;

    public IssuePrioritiesService(IssuePrioritiesRepository issuePrioritiesRepository) {
        this.issuePrioritiesRepository = issuePrioritiesRepository;
    }

    @Override
    public List<IssuePriority> getAll() {
        return issuePrioritiesRepository.findAll();
    }

    @Override
    public void save(IssuePriority priority) {
        issuePrioritiesRepository.save(priority);
    }

    @Override
    public void saveAll(Iterable<IssuePriority> priorities) {
        issuePrioritiesRepository.saveAll(priorities);
    }

    @Override
    public IssuePriority getById(Integer id) {
        return issuePrioritiesRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return issuePrioritiesRepository.existsById(id);
    }

    @Override
    public void delete(IssuePriority entity) {
        issuePrioritiesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        issuePrioritiesRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateIssuePriorityData(name);
        if (message == null) {
            IssuePriority priority = new IssuePriority();
            priority.setName(name);
            message = trySave(priority);
        }

        addMessageToAttributes(attrs, message, "Успешно создан.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateIssuePriorityData(name);

            IssuePriority priority = getById(id);
            if (message == null) {
                priority.setName(name);
                message = trySave(priority);
            } else {
                priority.setName(null);
            }
            attrs.put("entity", priority);
        } else {
            message = "Приоритет с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Успешно изменен.");
    }
}
