package org.sergei.komarov.models;

import javax.persistence.*;

@Entity
@Table(name = "filters")
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filter_id")
    private int id;

    private String name;

    private String query;

    @ManyToOne
    private User user;
}
