package com.example.elevatorsystem.system;

import com.example.elevatorsystem.system.dto.ElevatorDto;
import com.example.elevatorsystem.system.dto.PickupRequestDto;

import java.util.List;

public interface ElevatorSystemFacade {
    void pickup(PickupRequestDto pickupRequestDto);
    void step();
    List<ElevatorDto> status();
    void addElevator(ElevatorDto elevatorDto);
}
