package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "employee_positions")
@NoArgsConstructor
@Data
public class EmployeePosition {
    @Id
    private String title;
    private List<Employee> employees;
}
