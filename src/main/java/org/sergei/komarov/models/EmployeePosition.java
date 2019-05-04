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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_position_id_seq")
    @SequenceGenerator(name = "employee_position_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    private List<Employee> employees;
}
