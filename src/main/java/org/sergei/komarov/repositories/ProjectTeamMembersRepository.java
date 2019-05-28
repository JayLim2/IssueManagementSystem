package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.Project;
import org.sergei.komarov.models.ProjectTeamMember;
import org.sergei.komarov.models.keys.ProjectTeamMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectTeamMembersRepository extends JpaRepository<ProjectTeamMember, ProjectTeamMemberKey> {
    @Query("SELECT projectTeamMember FROM ProjectTeamMember projectTeamMember WHERE projectTeamMember.project = :project")
    List<ProjectTeamMember> findProjectMembers(@Param("project") Project project);

    ProjectTeamMember findByProjectAndEmployee(Project project, Employee employee);

    void deleteProjectTeamMemberByProjectAndEmployee(Project project, Employee employee);
}
