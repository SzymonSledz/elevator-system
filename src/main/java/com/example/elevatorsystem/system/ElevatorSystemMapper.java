package com.example.elevatorsystem.system;

import com.example.elevatorsystem.system.dto.ElevatorDto;
import com.example.elevatorsystem.system.dto.PickupRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
interface ElevatorSystemMapper {
    ElevatorSystemMapper INSTANCE = Mappers.getMapper(ElevatorSystemMapper.class);

    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatus")
    ElevatorDto toElevatorDto(Elevator elevator);
    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatus")
    Elevator toElevator(ElevatorDto elevatorDto);
    PickupRequest toPickupRequest(PickupRequestDto pickupRequestDto);

    @Named("mapStatus")
    default ElevatorStatus mapStatus(ElevatorStatus elevatorStatus) {
        return ElevatorStatus.valueOf(elevatorStatus.getStatus().toUpperCase());
    }
}
