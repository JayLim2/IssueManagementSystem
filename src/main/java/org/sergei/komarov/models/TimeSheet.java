package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.sergei.komarov.models.keys.TimeSheetKey;

import javax.persistence.*;

@Entity
@Table(name = "time_sheets")
@IdClass(TimeSheetKey.class)
@NoArgsConstructor
@Data
public class TimeSheet {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Id
    @Column(name = "week")
    private String startDate;

    private String comment;
    private float monday;
    private float tuesday;
    private float wednesday;
    private float thursday;
    private float friday;
    private float saturday;
    private float sunday;

    public float[] getWeekValues() {
        return new float[] {
                monday, tuesday, wednesday, thursday, friday, saturday, sunday
        };
    }
}