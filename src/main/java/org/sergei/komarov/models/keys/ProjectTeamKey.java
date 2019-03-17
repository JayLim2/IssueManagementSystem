package org.sergei.komarov.models.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectTeamKey implements Serializable {
    @Column(name = "employee_id")
    private int employee;

    @Column(name = "project_id")
    private int project;
}