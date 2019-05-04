package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.sergei.komarov.models.keys.ProjectTeamMemberKey;

import javax.persistence.*;

@Entity
@Table(name = "project_team_members")
@IdClass(ProjectTeamMemberKey.class)
@NoArgsConstructor
@Data
public class ProjectTeamMember {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "project_role_id", nullable = false)
    private ProjectRole projectRole;
}
