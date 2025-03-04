package com.receipe.manager.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "receipe")
@Entity
@Getter
@Setter
public class Receipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "receipename", nullable = false)
    private String receipeName;

    @Column
    private int servings;

    @Column(name = "isvegetarian", nullable = false)
    private boolean isVegetarian;

    @Column(nullable = false)
    private String instructions;

    @Column(nullable = false)
    private String ingredients;
}
