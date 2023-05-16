package com.example.elevatorsystem.system;

import com.example.elevatorsystem.system.dto.ElevatorDto;
import com.example.elevatorsystem.system.dto.PickupRequestDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.elevatorsystem.testsupport.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ElevatorSystemMapperTest {
    private static final ElevatorSystemMapper ELEVATOR_SYSTEM_MAPPER = ElevatorSystemMapper.INSTANCE;

    @Test
    public void shouldMapToElevator() {
        //given
        var elevatorDto = new ElevatorDto(ELEVATOR_ID1, CURRENT_FLOOR1, ELEVATOR_STATUS);
        var expected = new Elevator(ELEVATOR_ID1, CURRENT_FLOOR1, ELEVATOR_STATUS, List.of());
        //when
        var elevator = ELEVATOR_SYSTEM_MAPPER.toElevator(elevatorDto);
        //then
        assertThat(elevator).isEqualTo(expected);
    }

    @Test
    public void shouldMapToElevatorDto() {
        //given
        var elevator = new Elevator(ELEVATOR_ID1, CURRENT_FLOOR1, ELEVATOR_STATUS, List.of());
        var expected = new ElevatorDto(ELEVATOR_ID1, CURRENT_FLOOR1, ELEVATOR_STATUS);
        //when
        var elevatorDto = ELEVATOR_SYSTEM_MAPPER.toElevatorDto(elevator);
        //then
        assertThat(elevatorDto).isEqualTo(expected);
    }

    @Test
    public void shouldMapToPickupRequest() {
        //given
        var pickupRequestDto = new PickupRequestDto(PICKUP_FLOOR1, DESTINATION_FLOOR1);
        var expected = new PickupRequest(PICKUP_FLOOR1, DESTINATION_FLOOR1);
        //when
        var pickupRequest = ELEVATOR_SYSTEM_MAPPER.toPickupRequest(pickupRequestDto);
        //then
        assertThat(pickupRequest).isEqualTo(expected);
    }
}
