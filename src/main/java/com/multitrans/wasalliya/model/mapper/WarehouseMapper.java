package com.multitrans.wasalliya.model.mapper;

import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.model.Warehouse;
import com.multitrans.wasalliya.model.dto.WarehouseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    @Mapping(source = "tours", target = "tourIds")
    WarehouseDTO toDTO(Warehouse warehouse);

    List<WarehouseDTO> toDTOList(List<Warehouse> warehouses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tours", ignore = true)
    Warehouse toEntity(WarehouseDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tours", ignore = true)
    void updateFromDTO(WarehouseDTO dto, @MappingTarget Warehouse entity);

    default List<Long> mapToursToIds(List<Tour> tours) {
        if (tours == null || tours.isEmpty()) {
            return Collections.emptyList();
        }
        return tours.stream()
                .map(Tour::getId)
                .toList();
    }
}
