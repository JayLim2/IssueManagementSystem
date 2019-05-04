package org.sergei.komarov.services;

import org.sergei.komarov.models.IssueType;
import org.sergei.komarov.models.IssueWorkflowStatus;
import org.sergei.komarov.repositories.IssueTypesRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IssueTypesService implements JpaService<IssueType, Integer> {
    private final IssueTypesRepository issueTypesRepository;

    @Autowired
    public IssueTypesService(IssueTypesRepository issueTypesRepository) {
        this.issueTypesRepository = issueTypesRepository;
    }

    @Override
    public List<IssueType> getAll() {
        return issueTypesRepository.findAll();
    }

    @Override
    public IssueType getById(Integer id) {
        return issueTypesRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return issueTypesRepository.existsById(id);
    }

    @Override
    public void save(IssueType entity) {
        issueTypesRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<IssueType> iterable) {
        issueTypesRepository.saveAll(iterable);
    }

    @Override
    public void delete(IssueType entity) {
        issueTypesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        issueTypesRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String name, List<IssueWorkflowStatus> statuses) {
        if (name == null || statuses == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateIssueTypeData(name, statuses);
        if (message == null) {
            IssueType type = new IssueType();
            type.setName(name);
            type.setWorkflowStatuses(statuses);
            message = trySave(type);
        }

        addMessageToAttributes(attrs, message, "Успешно создан.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id, String name, List<IssueWorkflowStatus> statuses) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateIssueTypeData(name, statuses);

            IssueType type = getById(id);
            if (message == null) {
                type.setName(name);
                type.setWorkflowStatuses(statuses);
                message = trySave(type);
            } else {
                type.setName(null);
            }
            attrs.put("entity", type);
        } else {
            message = "Тип задач с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Успешно изменен.");
    }
}
