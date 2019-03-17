package org.sergei.komarov.models.keys;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@Data
public class TimeSheetKey implements Serializable {
    @Column(name = "employee_id")
    private int employee;

    @Column(name = "issue_id")
    private int issue;

    @Column(name = "week")
    private String startDate;
}