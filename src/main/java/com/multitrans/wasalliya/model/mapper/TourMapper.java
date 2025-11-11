package com.multitrans.wasalliya.model.mapper;

import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Tour;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.model.Warehouse;
import com.multitrans.wasalliya.model.dto.TourDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TourMapper {


    @Mapping(source = "vehicale.id", target = "vehicaleId")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "deliveries", target = "deliveryIds")
    TourDTO toDTO(Tour tour);

    List<TourDTO> toDTOList(List<Tour> tours);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicale", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "deliveries", ignore = true)
    Tour toEntity(TourDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicale", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "deliveries", ignore = true)
    void updateFromDTO(TourDTO dto, @MappingTarget Tour entity);

    default List<Long> mapDeliveriesToIds(List<Delivery> deliveries) {
        if (deliveries == null || deliveries.isEmpty()) {
            return Collections.emptyList();
        }
        return deliveries.stream()
                .map(Delivery::getId)
                .toList();
    }
}
