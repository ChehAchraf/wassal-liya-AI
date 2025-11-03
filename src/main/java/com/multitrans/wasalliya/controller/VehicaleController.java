package com.multitrans.wasalliya.controller;

import com.multitrans.wasalliya.model.dto.VehicalDTO;
import com.multitrans.wasalliya.model.mapper.VehicaleMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.multitrans.wasalliya.service.VehicaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehicales")
public class VehicaleController {

    private final VehicaleService vehicaleSer;
    private final VehicaleMapper vmapper;

    public VehicaleController(VehicaleService vehicaleService, VehicaleMapper vehicaleMapper) {
        this.vehicaleSer = vehicaleService;
        this.vmapper = vehicaleMapper;

    }

    @PostMapping("")
    public ResponseEntity<VehicalDTO> save(@Valid @RequestBody VehicalDTO dto) {
        VehicalDTO createdDto = vehicaleSer.crateVehicale(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicalDTO> find(@PathVariable Long id){
            VehicalDTO dto = vehicaleSer.findById(id);
            return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicalDTO> update(@RequestBody VehicalDTO dto,@PathVariable Long id){
        VehicalDTO updatedDTO = vehicaleSer.updateVehicla(id,dto);
        return ResponseEntity.ok(updatedDTO);
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(@RequestParam String type){
        Long result = vehicaleSer.CountVehical(type);

        return ResponseEntity.status(200).body("vehical under " + type+" is " + result);
    }


}
