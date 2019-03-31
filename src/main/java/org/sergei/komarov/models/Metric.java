package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "metrics")
@NoArgsConstructor
@Data
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metric_id_seq")
    @SequenceGenerator(name = "metric_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "filter_id", nullable = false)
    private Filter filter;
}
