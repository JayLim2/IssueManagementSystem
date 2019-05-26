package org.sergei.komarov.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee_positions")
@NoArgsConstructor
@Getter
@Setter
public class EmployeePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_position_id_seq")
    @SequenceGenerator(name = "employee_position_id_seq")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    private List<Employee> employees;

    @Override
    public String toString() {
        return name;
    }
}
