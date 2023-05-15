package com.example.elevatorsystem.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class InMemoryElevatorRepository implements ElevatorRepository {
    private final Map<String, Elevator> elevatorMap = new HashMap<>();

    @Override
    public void save(Elevator elevator) {
        if (!elevatorMap.containsKey(elevator.getElevatorId())) {
            elevatorMap.put(elevator.getElevatorId(), elevator);
        }
    }

    @Override
    public Elevator get(String elevatorId) {
        return elevatorMap.entrySet().stream()
                .filter(elevator -> elevator.getKey().equals(elevatorId))
                .findFirst()
                .orElseThrow()
                .getValue();
    }

    @Override
    public List<Elevator> getAll() {
        return elevatorMap.values().stream()
                .toList();
    }

    @Override
    public List<Elevator> findUnoccupiedElevators() {
        return elevatorMap.values().stream()
                .filter(elevator -> elevator.getStatus().equals(ElevatorStatus.IDLE))
                .toList();
    }

    @Override
    public List<Elevator> findOccupiedElevators() {
        return elevatorMap.values().stream()
                .filter(elevator -> !elevator.getStatus().equals(ElevatorStatus.IDLE))
                .toList();
    }
}
