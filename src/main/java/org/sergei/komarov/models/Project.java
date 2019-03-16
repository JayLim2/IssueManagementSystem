package org.sergei.komarov.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Data
public class Project {
    private int id;
    private String title;
    private ProjectType projectType;
    private List<Issue> issues;
    private List<Component> components;
    private List<Employee> employees;
}
