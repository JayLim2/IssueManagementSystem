package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "versions")
@NoArgsConstructor
@Data
public class ProjectVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "version_id")
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
