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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_role_id_seq")
    @SequenceGenerator(name = "project_role_id_seq")
    @Column(name = "project_role_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectRole")
    private List<ProjectTeam> projectTeams;
}