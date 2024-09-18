package com.americanexpress.epen.EpenScheduler.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "table_a", schema = "epen")
@Getter
@Setter
public class TableA {
    @Id
    private Integer id;

    private String name;
}
