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
    //TODO not needed?
    private int destinationFloor;
    private boolean isMoving = false;
    //TODO env variable - number of floors
    private List<Integer> floorsToVisit = new ArrayList<>(15);

    public void assignPickup(PickupRequest pickupRequest) {
        this.floorsToVisit.add(pickupRequest.getPickupFloor());
        this.floorsToVisit.add(pickupRequest.getDestinationFloor());
        this.isMoving = true;
    }

    public void nextStep() {
        var nextFloorToVisit = floorsToVisit.get(0);
        ////
        moveFloors(nextFloorToVisit);
        if (this.currentFloor == nextFloorToVisit) {
            this.floorsToVisit.remove(0);
            if (floorsToVisit.isEmpty()) {
                this.isMoving = false;
            }
        }
//        if (this.currentFloor != nextFloorToVisit) {
//            moveFloors(nextFloorToVisit);
//        } else {
//            this.floorsToVisit.remove(0);
//            if (floorsToVisit.isEmpty()) {
//                this.isMoving = false;
//            }
//        }
    }

    private void moveFloors(int nextFloorToVisit) {
        if (isMovingUp(nextFloorToVisit)) {
            this.currentFloor += 1;
        } else {
            this.currentFloor -= 1;
        }
    }

    private boolean isMovingUp(int nextFloorToVisit) {
        return this.currentFloor - nextFloorToVisit < 0;
    }
}
