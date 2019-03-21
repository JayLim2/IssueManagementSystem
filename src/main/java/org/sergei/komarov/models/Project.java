package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "project_id_seq")
    @Column(name = "project_id")
    private Integer id;

    @Column(unique = true)
    private String title;

    @ManyToOne
    private ProjectType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Issue> issues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectComponent> components;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectVersion> versions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectTeam> projectTeams;
}
