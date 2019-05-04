package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.IssueWorkflowStatus;
import org.sergei.komarov.repositories.IssueWorkflowStatusesRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class IssueWorkflowStatusesService implements JpaService<IssueWorkflowStatus, Integer> {
    private final IssueWorkflowStatusesRepository issueWorkflowStatusesRepository;

    @Override
    public List<IssueWorkflowStatus> getAll() {
        return issueWorkflowStatusesRepository.findAll();
    }

    @Override
    public IssueWorkflowStatus getById(Integer id) {
        return issueWorkflowStatusesRepository.getOne(id);
    }

    public List<IssueWorkflowStatus> getByIds(List<Integer> ids) {
        return ids != null ?
                issueWorkflowStatusesRepository.findByIdIn(ids) :
                new ArrayList<>();
    }

    @Override
    public boolean isExistsById(Integer id) {
        return issueWorkflowStatusesRepository.existsById(id);
    }

    @Override
    public void save(IssueWorkflowStatus entity) {
        issueWorkflowStatusesRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<IssueWorkflowStatus> iterable) {
        issueWorkflowStatusesRepository.saveAll(iterable);
    }

    @Override
    public void delete(IssueWorkflowStatus entity) {
        issueWorkflowStatusesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        issueWorkflowStatusesRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateWorkflowStatusData(name);
        if (message == null) {
            IssueWorkflowStatus status = new IssueWorkflowStatus();
            status.setName(name);
            message = trySave(status);
        }

        addMessageToAttributes(attrs, message, "Успешно создан.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateWorkflowStatusData(name);

            IssueWorkflowStatus status = getById(id);
            if (message == null) {
                status.setName(name);
                message = trySave(status);
            } else {
                status.setName(null);
            }
            attrs.put("entity", status);
        } else {
            message = "Статус рабочего процесса с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Успешно изменен.");
    }
}
