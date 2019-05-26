package org.sergei.komarov.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_seq")
    @SequenceGenerator(name = "project_id_seq")
    @Column(name = "project_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProjectType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Issue> issues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectVersion> versions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectTeamMember> projectTeams;

    @Override
    public String toString() {
        return title;
    }
}
