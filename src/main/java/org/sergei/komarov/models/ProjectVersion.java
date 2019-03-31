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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_version_id_seq")
    @SequenceGenerator(name = "project_version_id_seq")
    @Column(name = "version_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
