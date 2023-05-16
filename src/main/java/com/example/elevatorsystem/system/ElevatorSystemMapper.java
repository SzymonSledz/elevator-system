package com.example.elevatorsystem.system;

import com.example.elevatorsystem.system.dto.ElevatorDto;
import com.example.elevatorsystem.system.dto.PickupRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface ElevatorSystemMapper {
    ElevatorSystemMapper INSTANCE = Mappers.getMapper(ElevatorSystemMapper.class);

    ElevatorDto toElevatorDto(Elevator elevator);
    Elevator toElevator(ElevatorDto elevatorDto);
    PickupRequest toPickupRequest(PickupRequestDto pickupRequestDto);
}
