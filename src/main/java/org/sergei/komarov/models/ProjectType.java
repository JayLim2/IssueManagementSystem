package org.sergei.komarov.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "project_types")
@NoArgsConstructor
@Data
public class ProjectType {
    @Id
    private String title;
    private List<Project> projects;
}
