package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.sergei.komarov.models.keys.ProjectTeamKey;

import javax.persistence.*;

@Entity
@Table(name = "project_team_members")
@IdClass(ProjectTeamKey.class)
@NoArgsConstructor
@Data
public class ProjectTeam {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "project_role_name")
    private ProjectRole projectRole;
}
