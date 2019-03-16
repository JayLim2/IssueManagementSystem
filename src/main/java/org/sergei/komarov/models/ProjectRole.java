package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "project_roles")
@NoArgsConstructor
@Data
public class ProjectRole {
    @Id
    private String name;
    private List<Project> projects;
}
