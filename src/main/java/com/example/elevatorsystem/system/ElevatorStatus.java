package com.example.elevatorsystem.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
enum ElevatorStatus {
    IDLE("Idle"),
    MOVING_UP("Moving up"),
    MOVING_DOWN("Moving down");

    private String status;
}
