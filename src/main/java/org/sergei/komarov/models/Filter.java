package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "filters")
@NoArgsConstructor
@Data
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filter_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String query;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne
    private Metric metric;
}
