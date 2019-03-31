package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee_positions")
@NoArgsConstructor
@Data
public class EmployeePosition {
    @Id
    @Column(nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    private List<Employee> employees;
}
