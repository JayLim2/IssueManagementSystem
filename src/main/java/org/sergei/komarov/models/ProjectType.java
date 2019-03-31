package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project_types")
@NoArgsConstructor
@Data
public class ProjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_type_id_seq")
    @SequenceGenerator(name = "project_type_id_seq")
    @Column(name = "project_type_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<Project> projects;
}
