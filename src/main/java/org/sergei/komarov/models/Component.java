package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "components")
@NoArgsConstructor
@Data
public class Component {
    @Id
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
