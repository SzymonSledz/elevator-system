package com.example.elevatorsystem.system;

import java.util.*;
import java.util.function.Predicate;

class InMemoryElevatorRepository implements ElevatorRepository {
    private final Map<String, Elevator> elevatorMap = new HashMap<>();

    @Override
    public void save(Elevator elevator) {
        if (!elevatorMap.containsKey(elevator.getElevatorId())) {
            elevatorMap.put(elevator.getElevatorId(), elevator);
        }
    }

    @Override
    public Optional<Elevator> get(String elevatorId) {
        return elevatorMap.entrySet().stream()
                .filter(elevator -> elevator.getKey().equals(elevatorId))
                .map(Map.Entry::getValue)
                .findFirst();
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

    @Override
    public Optional<Elevator> isHandlingRequest(PickupRequest pickupRequest) {
        var pickupFloor = pickupRequest.getPickupFloor();
        var destinationFloor = pickupRequest.getDestinationFloor();

        return this.findOccupiedElevators().stream()
                .filter(isGoingToGivenFloors(pickupFloor, destinationFloor))
                .filter(isGoingToGivenFloorsInCorrectOrder(pickupFloor, destinationFloor))
                .findFirst();
    }

    private static Predicate<Elevator> isGoingToGivenFloorsInCorrectOrder(int pickupFloor, int destinationFloor) {
        return elevator -> elevator.getFloorsToVisit().get(0).equals(pickupFloor) && elevator.getFloorsToVisit().get(1).equals(destinationFloor);
    }

    private static Predicate<Elevator> isGoingToGivenFloors(int pickupFloor, int destinationFloor) {
        return elevator -> new HashSet<>(elevator.getFloorsToVisit()).containsAll(List.of(pickupFloor, destinationFloor));
    }
}
