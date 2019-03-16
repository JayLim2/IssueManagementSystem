package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "time_sheets")
@NoArgsConstructor
@Data
public class TimeSheet {
    @Id
    private int id;
    private Employee employee;
    private Issue issue;
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
