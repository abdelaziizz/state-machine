package com.workmotion.statemachine.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @Column(name = "CONTRACT_NO", unique = true)
    private long contractNumber;

    @Column(name = "COUNTRY")
    private String country;

}
