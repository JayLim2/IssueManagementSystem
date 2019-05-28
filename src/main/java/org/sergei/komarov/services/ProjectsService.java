package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.repositories.ProjectTeamMembersRepository;
import org.sergei.komarov.repositories.ProjectsRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProjectsService implements JpaService<Project, Integer> {

    private final ProjectsRepository projectsRepository;
    private final ProjectTeamMembersRepository projectTeamMembersRepository;

    @Override
    public List<Project> getAll() {
        return projectsRepository.findAll();
    }

    @Override
    public Project getById(Integer id) {
        return isExistsById(id) ? projectsRepository.getOne(id) : null;
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
            }
            //attrs.put("entity", project);
        } else {
            message = "Проект с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Проект успешно изменен.");
    }

    public List<ProjectTeamMember> getProjectTeam(Project project) {
        return projectTeamMembersRepository.findProjectMembers(project);
    }

    public ProjectTeamMember addProjectTeamMember(Project project, Employee employee, ProjectRole role) {
        if (project == null || employee == null || role == null) {
            throw new NullPointerException();
        }

        ProjectTeamMember projectTeamMember = new ProjectTeamMember();
        projectTeamMember.setEmployee(employee);
        projectTeamMember.setProject(project);
        projectTeamMember.setProjectRole(role);

        return projectTeamMembersRepository.save(projectTeamMember);
    }

    public ProjectTeamMember editProjectTeamMember(Project project, Employee employee, ProjectRole role) {
        if (project == null || employee == null || role == null) {
            throw new NullPointerException();
        }

        ProjectTeamMember projectTeamMember = projectTeamMembersRepository.findByProjectAndEmployee(
                project,
                employee
        );
        projectTeamMember.setProjectRole(role);

        return projectTeamMembersRepository.save(projectTeamMember);
    }

    @Transactional
    public void deleteProjectTeamMember(Project project, Employee employee) {
        if (project == null || employee == null) {
            throw new NullPointerException();
        }

        projectTeamMembersRepository.deleteProjectTeamMemberByProjectAndEmployee(project, employee);
    }
}
