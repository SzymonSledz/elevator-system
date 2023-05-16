package com.example.elevatorsystem.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class PickupRequest {
    private int pickupFloor;
    private int destinationFloor;
}
