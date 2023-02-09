package com.workmotion.statemachine.model.entity;

import com.workmotion.statemachine.model.stateEnums.SecuritySubState;
import com.workmotion.statemachine.model.stateEnums.State;
import com.workmotion.statemachine.model.stateEnums.WorkPermitSubState;
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

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "SECURITY_SUB_STATE")
    @Enumerated(EnumType.STRING)
    private SecuritySubState securitySubState;

    @Column(name = "WORK_PERMIT_SUB_STATE")
    @Enumerated(EnumType.STRING)
    private WorkPermitSubState workPermitSubState;

}
