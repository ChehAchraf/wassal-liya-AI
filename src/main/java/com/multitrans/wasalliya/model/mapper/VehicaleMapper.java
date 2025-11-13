package com.multitrans.wasalliya.model.mapper;

import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.model.dto.VehicalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicaleMapper {


    @Mapping(source = "tours", target = "tourIDs")
    VehicalDTO toDTO(Vehicale vehicale);

    List<VehicalDTO> toDTOList(List<Vehicale> vehicales);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tours", ignore = true)
    Vehicale toEntity(VehicalDTO dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tours", ignore = true)
    void updateFromDto(VehicalDTO dto, @MappingTarget Vehicale entity);


    default List<Long> mapToursToIds(List<Tour> tours) {
        if (tours == null || tours.isEmpty()) {
            return Collections.emptyList();
        }
        return tours.stream()
                .map(Tour::getId)
                .toList();
    }
}
