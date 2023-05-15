package com.example.elevatorsystem.system;

import java.util.List;

interface ElevatorRepository {
    void save(Elevator elevator);
    Elevator get(String elevatorId);
    List<Elevator> getAll();
    List<Elevator> findUnoccupiedElevators();
    List<Elevator> findOccupiedElevators();
}
