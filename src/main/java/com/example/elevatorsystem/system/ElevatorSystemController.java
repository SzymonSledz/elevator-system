package com.example.elevatorsystem.system;

import com.example.elevatorsystem.system.dto.ElevatorDto;
import com.example.elevatorsystem.system.dto.PickupRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
class ElevatorSystemController {
    ElevatorSystemFacade elevatorSystemFacade;

    @PostMapping("/elevator")
    //TODO zwrocic id w return
    void addElevator(@RequestBody ElevatorDto elevatorDto) {
        elevatorSystemFacade.addElevator(elevatorDto);
    }

    @GetMapping("/elevator")
    List<ElevatorDto> getStatus() {
        return elevatorSystemFacade.status();
    }

    @PostMapping("/elevator/pickup")
    void pickup(@RequestBody PickupRequestDto pickupRequestDto) {
        elevatorSystemFacade.pickup(pickupRequestDto);
    }

    @PostMapping("/elevator/step")
    void step() {
        elevatorSystemFacade.step();
    }
}
