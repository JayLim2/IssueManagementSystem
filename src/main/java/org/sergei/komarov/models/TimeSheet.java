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
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Id
    @Column(name = "week", nullable = false)
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

    public void setWeekValues(float[] values) {
        if (values == null) {
            throw new NullPointerException();
        }

        if (values.length != 7) {
            throw new RuntimeException("Массив со значениями часов по дням недели не соответствует количеству дней в неделе.");
        }

        setMonday(values[0]);
        setTuesday(values[1]);
        setWednesday(values[2]);
        setThursday(values[3]);
        setFriday(values[4]);
        setSaturday(values[5]);
        setSunday(values[6]);
    }
}
