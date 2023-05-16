package com.example.elevatorsystem.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElevatorDto {
    private String elevatorId;
    private int currentFloor;
    private String status;
}
