package com.example.elevatorsystem.system;

import com.example.elevatorsystem.system.dto.ElevatorDto;
import com.example.elevatorsystem.system.dto.PickupRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
class ElevatorSystemService implements ElevatorSystemFacade {
    private final ElevatorRepository elevatorRepository = new InMemoryElevatorRepository();
    private final ElevatorSystemMapper elevatorSystemMapper = ElevatorSystemMapper.INSTANCE;
    private final LinkedList<PickupRequest> requestsToHandle = new LinkedList<>();

    @Override
    public void pickup(PickupRequestDto pickupRequestDto) {
        //TODO change string format
        log.debug(String.format("pickupFloor : %d, destinationFloor : %d",
                pickupRequestDto.getPickupFloor(), pickupRequestDto.getDestinationFloor()));
        scheduleElevator(elevatorSystemMapper.toPickupRequest(pickupRequestDto));
    }

    @Override
    public void update(String elevatorId, int currentFloor, int destinationFloor) {

    }

    @Override
    public void step() {
        var elevators = elevatorRepository.findOccupiedElevators();

        for (Elevator elevator : elevators) {
            elevator.nextStep();
        }
        //TODO remove!
        System.out.println(status());
        //TODO work on it and extract
        if (!requestsToHandle.isEmpty()) {
            for (PickupRequest pickupRequest : requestsToHandle) {
                scheduleElevator(pickupRequest);
            }
        }
    }

    @Override
    public List<ElevatorDto> status() {
        return elevatorRepository.getAll().stream()
                .map(elevatorSystemMapper::toElevatorDto)
                .toList();
    }

    @Override
    public void addElevator(ElevatorDto elevatorDto) {
        //TODO handling exceptions - elevator already exists!
        elevatorRepository.save(elevatorSystemMapper.toElevator(elevatorDto));
    }

    private void scheduleElevator(PickupRequest pickupRequest) {
        var unoccupiedElevators = elevatorRepository.findUnoccupiedElevators();
        if (hasUnoccupiedElevators(unoccupiedElevators)) {
            var elevator = unoccupiedElevators.stream()
                    //TODO extract
                    .min(Comparator.comparingInt(s -> Math.abs(s.getCurrentFloor() - pickupRequest.getPickupFloor())))
                    .get();
            elevator.assignPickup(pickupRequest);
        } else {
            if (!requestsToHandle.contains(pickupRequest)){
                requestsToHandle.add(pickupRequest);
            }
        }
    }

    private boolean hasUnoccupiedElevators(List<Elevator> unoccupiedElevators) {
        return !unoccupiedElevators.isEmpty();
    }
}
