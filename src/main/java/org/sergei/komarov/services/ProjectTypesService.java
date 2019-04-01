package org.sergei.komarov.services;

import org.sergei.komarov.models.ProjectType;
import org.sergei.komarov.repositories.ProjectTypesRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectTypesService implements JpaService<ProjectType, Integer> {

    private final ProjectTypesRepository projectTypesRepository;

    public ProjectTypesService(ProjectTypesRepository projectTypesRepository) {
        this.projectTypesRepository = projectTypesRepository;
    }

    @Override
    public List<ProjectType> getAll() {
        return projectTypesRepository.findAll();
    }

    @Override
    public ProjectType getById(Integer id) {
        return projectTypesRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return projectTypesRepository.existsById(id);
    }

    @Override
    public void save(ProjectType entity) {
        projectTypesRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<ProjectType> iterable) {
        projectTypesRepository.saveAll(iterable);
    }

    @Override
    public void delete(ProjectType entity) {
        projectTypesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        projectTypesRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateEmployeePositionData(name);
        if (message == null) {
            ProjectType type = new ProjectType();
            type.setName(name);
            message = trySave(type);
        }

        addMessageToAttributes(attrs, message, "Успешно создан.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateEmployeePositionData(name);

            ProjectType type = getById(id);
            if (message == null) {
                type.setName(name);
                message = trySave(type);
            } else {
                type.setName(null);
            }
            attrs.put("entity", type);
        } else {
            message = "Тип проекта с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Успешно изменен.");
    }
}
