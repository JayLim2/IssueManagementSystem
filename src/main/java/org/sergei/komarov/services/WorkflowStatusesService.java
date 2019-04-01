package org.sergei.komarov.services;

import org.sergei.komarov.models.WorkflowStatus;
import org.sergei.komarov.repositories.WorkflowStatusesRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkflowStatusesService implements JpaService<WorkflowStatus, Integer> {
    private final WorkflowStatusesRepository workflowStatusesRepository;

    public WorkflowStatusesService(WorkflowStatusesRepository workflowStatusesRepository) {
        this.workflowStatusesRepository = workflowStatusesRepository;
    }

    @Override
    public List<WorkflowStatus> getAll() {
        return workflowStatusesRepository.findAll();
    }

    @Override
    public WorkflowStatus getById(Integer id) {
        return workflowStatusesRepository.getOne(id);
    }

    public List<WorkflowStatus> getByIds(List<Integer> ids) {
        return ids != null ?
                workflowStatusesRepository.findByIdIn(ids) :
                new ArrayList<>();
    }

    @Override
    public boolean isExistsById(Integer id) {
        return workflowStatusesRepository.existsById(id);
    }

    @Override
    public void save(WorkflowStatus entity) {
        workflowStatusesRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<WorkflowStatus> iterable) {
        workflowStatusesRepository.saveAll(iterable);
    }

    @Override
    public void delete(WorkflowStatus entity) {
        workflowStatusesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        workflowStatusesRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateWorkflowStatusData(name);
        if (message == null) {
            WorkflowStatus status = new WorkflowStatus();
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

            WorkflowStatus status = getById(id);
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
