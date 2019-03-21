package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "projectComponents")
@NoArgsConstructor
@Data
public class ProjectComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id")
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
