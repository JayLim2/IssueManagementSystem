package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project_roles")
@NoArgsConstructor
@Data
public class ProjectRole {
    @Id
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectRole")
    private List<ProjectTeam> projectTeams;
}