package com.example.elevatorsystem.system;

import java.util.List;
import java.util.Optional;

interface ElevatorRepository {
    void save(Elevator elevator);
    Optional<Elevator> get(String elevatorId);
    List<Elevator> getAll();
    List<Elevator> findUnoccupiedElevators();
    List<Elevator> findOccupiedElevators();
    Optional<Elevator> isHandlingRequest(PickupRequest pickupRequest);
}
