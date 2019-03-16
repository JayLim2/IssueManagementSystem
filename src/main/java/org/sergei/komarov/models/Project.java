package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Entity
//@Table(name = "projects")
@NoArgsConstructor
@Data
public class Project {
    //@Id
    private int id;
    private String title;
    private ProjectType projectType;
    private List<Issue> issues;
    private List<Component> components;
    private List<Employee> employees;
}
