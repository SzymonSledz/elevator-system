package com.example.elevatorsystem.system;

import com.example.elevatorsystem.system.dto.ElevatorDto;
import com.example.elevatorsystem.system.dto.PickupRequestDto;
import com.example.elevatorsystem.system.exception.ElevatorAlreadyExistsException;
import com.example.elevatorsystem.system.exception.PickupAndDestinationFloorCannotBeTheSameException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.elevatorsystem.testsupport.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSystemServiceTest {
    private final ElevatorRepository elevatorRepository = new InMemoryElevatorRepository();
    private final ElevatorSystemFacade elevatorSystemFacade = new ElevatorSystemService(elevatorRepository);
    private static final ElevatorSystemMapper ELEVATOR_SYSTEM_MAPPER = ElevatorSystemMapper.INSTANCE;

    @Test
    public void shouldCreateNewElevator() {
        //given
        var elevatorDto = createElevatorDto1();
        //when
        elevatorSystemFacade.addElevator(elevatorDto);
        //then
        assertEquals(elevatorRepository.get(ELEVATOR_ID1).get(), ELEVATOR_SYSTEM_MAPPER.toElevator(elevatorDto));
    }

    @Test
    public void shouldThrowWhenElevatorAlreadyExists() {
        //given
        var elevatorDto = createElevatorDto1();
        var expectedMessage = "Elevator with id : 1 already exists";
        //when
        elevatorSystemFacade.addElevator(elevatorDto);
        var exception = assertThrows(ElevatorAlreadyExistsException.class, () -> elevatorSystemFacade.addElevator(elevatorDto));
        //then
        var actualMessage = exception.getMessage();
        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    public void shouldNotThrowWhenRequestingPickup() {
        //given
        var pickupRequestDto = createPickupRequestDto1();
        //when then
        assertDoesNotThrow(() -> elevatorSystemFacade.pickup(pickupRequestDto));
    }

    @Test
    public void shouldThrowWhenPickupFloorIsSameAsDestinationFloor() {
        //given
        var pickupRequestDto = new PickupRequestDto(PICKUP_FLOOR1, PICKUP_FLOOR1);
        var expectedMessage = "Pickup floor cannot be the same as destination floor";
        //when
        var exception = assertThrows(PickupAndDestinationFloorCannotBeTheSameException.class, () -> elevatorSystemFacade.pickup(pickupRequestDto));
        //then
        var actualMessage = exception.getMessage();
        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    public void shouldReturnCorrectStatusWhenOnePickup() {
        //given
        var pickupRequestDto = createPickupRequestDto1();
        var elevatorDto1 = createElevatorDto1();
        var elevatorDto2 = createElevatorDto2();
        var expected = List.of(new ElevatorDto(ELEVATOR_ID1, 1, ElevatorStatus.IDLE), new ElevatorDto(ELEVATOR_ID2, 2, ElevatorStatus.MOVING_UP));
        elevatorSystemFacade.addElevator(elevatorDto1);
        elevatorSystemFacade.addElevator(elevatorDto2);
        elevatorSystemFacade.pickup(pickupRequestDto);
        elevatorSystemFacade.step();
        //when
        var result = elevatorSystemFacade.status();
        //then
        assertEquals(result, expected);
    }

    @Test
    public void shouldReturnCorrectStatusWhenTwoPickups() {
        //given
        var pickupRequestDto1 = createPickupRequestDto1();
        var pickupRequestDto2 = createPickupRequestDto2();
        var elevatorDto1 = createElevatorDto1();
        var elevatorDto2 = createElevatorDto2();
        var expected = List.of(new ElevatorDto(ELEVATOR_ID1, 1, ElevatorStatus.MOVING_DOWN), new ElevatorDto(ELEVATOR_ID2, 2, ElevatorStatus.MOVING_UP));
        elevatorSystemFacade.addElevator(elevatorDto1);
        elevatorSystemFacade.addElevator(elevatorDto2);
        elevatorSystemFacade.pickup(pickupRequestDto1);
        elevatorSystemFacade.pickup(pickupRequestDto2);
        elevatorSystemFacade.step();
        //when
        var result = elevatorSystemFacade.status();
        //then
        assertEquals(result, expected);
    }

    @Test
    public void shouldStopWhenFinishedRequest() {
        //given
        var pickupRequestDto = createPickupRequestDto2();
        var elevatorDto = createElevatorDto1();
        var expected = List.of(new ElevatorDto(ELEVATOR_ID1, 1, ElevatorStatus.IDLE));
        elevatorSystemFacade.addElevator(elevatorDto);
        elevatorSystemFacade.pickup(pickupRequestDto);
        for (int i = 0; i < 4; i++) {
            elevatorSystemFacade.step();
        }
        //when
        var result = elevatorSystemFacade.status();
        //then
        assertEquals(result, expected);
    }

    private static PickupRequestDto createPickupRequestDto1() {
        return new PickupRequestDto(PICKUP_FLOOR1, DESTINATION_FLOOR1);
    }

    private static PickupRequestDto createPickupRequestDto2() {
        return new PickupRequestDto(PICKUP_FLOOR2, DESTINATION_FLOOR2);
    }

    private static ElevatorDto createElevatorDto1() {
        return new ElevatorDto(ELEVATOR_ID1, CURRENT_FLOOR1, ElevatorStatus.IDLE);
    }

    private static ElevatorDto createElevatorDto2() {
        return new ElevatorDto(ELEVATOR_ID2, CURRENT_FLOOR2, ElevatorStatus.IDLE);
    }
}
