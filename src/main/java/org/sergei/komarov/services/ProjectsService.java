package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Project;
import org.sergei.komarov.models.ProjectType;
import org.sergei.komarov.repositories.ProjectsRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProjectsService implements JpaService<Project, Integer> {

    private final ProjectsRepository projectsRepository;

    @Override
    public List<Project> getAll() {
        return projectsRepository.findAll();
    }

    @Override
    public Project getById(Integer id) {
        return projectsRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return projectsRepository.existsById(id);
    }

    @Override
    public void save(Project entity) {
        projectsRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<Project> iterable) {
        projectsRepository.saveAll(iterable);
    }

    @Override
    public void delete(Project entity) {
        projectsRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        projectsRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String title, ProjectType projectType) {
        if (title == null || projectType == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateProjectData(title);
        if (message == null) {
            Project project = new Project();
            project.setTitle(title);
            project.setType(projectType);
            message = trySave(project);
        }

        addMessageToAttributes(attrs, message, "Проект успешно создан.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id, String title, ProjectType projectType) {
        if (title == null || projectType == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateProjectData(title);

            Project project = getById(id);
            if (message == null) {
                project.setTitle(title);
                project.setType(projectType);
                message = trySave(project);
            } else {
                project.setTitle(null);
            }
            attrs.put("entity", project);
        } else {
            message = "Проект с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Проект успешно изменен.");
    }
}
