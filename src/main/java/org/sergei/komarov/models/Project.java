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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_seq")
    @SequenceGenerator(name = "project_id_seq")
    @Column(name = "project_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProjectType type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private IssueWorkflowStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Issue> issues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectVersion> versions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectTeamMember> projectTeams;
}
