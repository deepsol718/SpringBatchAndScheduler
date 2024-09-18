package com.americanexpress.epen.EpenScheduler.model;

import jakarta.persistence.*;


import lombok.*;

@Entity
@Table(name = "indian_names", schema = "epen")
@Getter
@Setter
public class IndianName {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_id")
    @SequenceGenerator(name = "sq_id", sequenceName = "epen.indian_names_id_seq", allocationSize = 1)
    private Integer id;

    private String name;
}
