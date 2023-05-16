package com.example.elevatorsystem.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ElevatorStatus {
    IDLE("Idle"),
    MOVING_UP("MOVING_UP"),
    MOVING_DOWN("MOVING_DOWN");

    private String status;
}
