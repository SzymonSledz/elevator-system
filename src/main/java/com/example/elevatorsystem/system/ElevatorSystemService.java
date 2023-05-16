package com.example.elevatorsystem.system;

import com.example.elevatorsystem.system.dto.ElevatorDto;
import com.example.elevatorsystem.system.dto.PickupRequestDto;
import com.example.elevatorsystem.system.exception.ElevatorAlreadyExistsException;
import com.example.elevatorsystem.system.exception.PickupAndDestinationFloorCannotBeTheSameException;
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
        log.debug("Handling new pickup request, pickupFloor : {}, destinationFloor : {}",
                pickupRequestDto.getPickupFloor(), pickupRequestDto.getDestinationFloor());
        var pickupRequest = elevatorSystemMapper.toPickupRequest(pickupRequestDto);

        if (pickupRequest.getPickupFloor() == pickupRequest.getDestinationFloor()) {
            throw new PickupAndDestinationFloorCannotBeTheSameException("Pickup floor cannot be the same as destination floor");
        }
        requestsToHandle.add(pickupRequest);
    }

    @Override
    public void step() {
        var elevators = elevatorRepository.findOccupiedElevators();

        for (Elevator elevator : elevators) {
            elevator.nextStep();
        }
        assignPickupRequestsToHandle();
        logElevatorStatuses();
    }

    @Override
    public List<ElevatorDto> status() {
        return elevatorRepository.getAll().stream()
                .map(elevatorSystemMapper::toElevatorDto)
                .toList();
    }

    private void logElevatorStatuses() {
        log.debug(this.elevatorRepository.getAll().stream()
                .toList().toString());
    }

    @Override
    public void addElevator(ElevatorDto elevatorDto) {
        var elevator = elevatorSystemMapper.toElevator(elevatorDto);
        var elevatorId = elevator.getElevatorId();

        if (elevatorRepository.get(elevatorId).isPresent()) {
            throw new ElevatorAlreadyExistsException(String.format("Elevator with id : %s already exists", elevatorId));
        }
        elevatorRepository.save(elevatorSystemMapper.toElevator(elevatorDto));
    }

    private void assignPickupRequestsToHandle() {
        if (!requestsToHandle.isEmpty()) {
            requestsToHandle.removeIf(this::scheduleElevator);
        }
    }

    private boolean scheduleElevator(PickupRequest pickupRequest) {
        var unoccupiedElevators = elevatorRepository.findUnoccupiedElevators();

        if (isElevatorAlreadyHandlingSameRequest(pickupRequest)) {
            return true;
        }
        if (hasUnoccupiedElevators(unoccupiedElevators)) {
            findElevatorWithMinimumDistanceAndAssignPickup(pickupRequest, unoccupiedElevators);
            return true;
        }

        return false;
    }

    private static void findElevatorWithMinimumDistanceAndAssignPickup(PickupRequest pickupRequest, List<Elevator> unoccupiedElevators) {
        var elevator = unoccupiedElevators.stream()
                .min(distanceToPickupFloor(pickupRequest))
                .get();
        elevator.assignPickup(pickupRequest);
    }

    private static Comparator<Elevator> distanceToPickupFloor(PickupRequest pickupRequest) {
        return Comparator.comparingInt(element -> Math.abs(element.getCurrentFloor() - pickupRequest.getPickupFloor()));
    }

    private boolean isElevatorAlreadyHandlingSameRequest(PickupRequest pickupRequest) {
        return elevatorRepository.isHandlingRequest(pickupRequest).isPresent();
    }

    private boolean hasUnoccupiedElevators(List<Elevator> unoccupiedElevators) {
        return !unoccupiedElevators.isEmpty();
    }
}
