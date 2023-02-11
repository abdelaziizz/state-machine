package com.workmotion.statemachine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    private String name;
    private int age;
    private long contractNumber;
    private String country;

}
