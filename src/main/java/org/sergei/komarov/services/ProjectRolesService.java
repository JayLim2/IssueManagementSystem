package org.sergei.komarov.services;

import org.sergei.komarov.models.ProjectRole;
import org.sergei.komarov.repositories.ProjectRolesRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectRolesService implements JpaService<ProjectRole, Integer> {

    private final ProjectRolesRepository projectRolesRepository;

    public ProjectRolesService(ProjectRolesRepository projectRolesRepository) {
        this.projectRolesRepository = projectRolesRepository;
    }

    @Override
    public List<ProjectRole> getAll() {
        return projectRolesRepository.findAll();
    }

    @Override
    public ProjectRole getById(Integer id) {
        return projectRolesRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return projectRolesRepository.existsById(id);
    }

    @Override
    public void save(ProjectRole entity) {
        projectRolesRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<ProjectRole> iterable) {
        projectRolesRepository.saveAll(iterable);
    }

    @Override
    public void delete(ProjectRole entity) {
        projectRolesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        projectRolesRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateProjectRoleData(name);
        if (message == null) {
            ProjectRole role = new ProjectRole();
            role.setName(name);
            message = trySave(role);
        }

        addMessageToAttributes(attrs, message, "Успешно создана.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateProjectRoleData(name);

            ProjectRole role = getById(id);
            if (message == null) {
                role.setName(name);
                message = trySave(role);
            } else {
                role.setName(null);
            }
            attrs.put("entity", role);
        } else {
            message = "Проектная роль с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Успешно изменена.");
    }
}
