package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Entity
//@Table(name = "project_types")
@NoArgsConstructor
@Data
public class ProjectType {
    //@Id
    private String title;
    private List<Project> projects;
}
