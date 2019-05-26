package org.sergei.komarov.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project_roles")
@NoArgsConstructor
@Getter
@Setter
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_role_id_seq")
    @SequenceGenerator(name = "project_role_id_seq")
    @Column(name = "project_role_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectRole")
    private List<ProjectTeamMember> projectTeams;

    @Override
    public String toString() {
        return name;
    }
}