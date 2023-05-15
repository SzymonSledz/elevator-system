package com.example.elevatorsystem.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Elevator {
    private String elevatorId;
    private int currentFloor;
    private ElevatorStatus status = ElevatorStatus.IDLE;
    //TODO env variable - number of floors
    private List<Integer> floorsToVisit = new ArrayList<>(15);

    public void assignPickup(PickupRequest pickupRequest) {
        this.floorsToVisit.add(pickupRequest.getPickupFloor());
        this.floorsToVisit.add(pickupRequest.getDestinationFloor());
        assignStatus(pickupRequest);
    }

    public void nextStep() {
        moveFloors();
        if (shouldVisitCurrentFloor()) {
            //TODO push/pop?
            this.floorsToVisit.remove(0);
            if (floorsToVisit.isEmpty()) {
                this.status = ElevatorStatus.IDLE;
            } else {
                if (shouldChangeDirection()) {
                    changeDirection();
                }
            }
        }
    }

    private void assignStatus(PickupRequest pickupRequest) {
        //TODO and eq?
        if (pickupRequest.getPickupFloor() < this.currentFloor) {
            this.status = ElevatorStatus.MOVING_DOWN;
        } else {
            this.status = ElevatorStatus.MOVING_UP;
        }
    }

    private boolean shouldChangeDirection() {
        var nextStop = this.floorsToVisit.get(0);
        return (this.currentFloor > nextStop && this.status.equals(ElevatorStatus.MOVING_UP)) ||
                (this.currentFloor < nextStop && this.status.equals(ElevatorStatus.MOVING_DOWN)) ||
                this.status.equals(ElevatorStatus.IDLE);
    }

    private void changeDirection() {
        if (isMovingUp()) {
            this.status = ElevatorStatus.MOVING_DOWN;
        } else {
            this.status = ElevatorStatus.MOVING_UP;
        }
    }

    private boolean shouldVisitCurrentFloor() {
        return this.floorsToVisit.get(0).equals(this.currentFloor);
    }

    private void moveFloors() {
        if (isMovingUp()) {
            moveUp();
        } else {
            moveDown();
        }
    }

    private void moveUp() {
        this.currentFloor += 1;
    }

    private void moveDown() {
        this.currentFloor -= 1;
    }

    private boolean isMovingUp() {
        return this.status.equals(ElevatorStatus.MOVING_UP);
    }
}
