package com.multitrans.wasalliya.controller;

import com.multitrans.wasalliya.model.dto.WarehouseDTO;
import com.multitrans.wasalliya.model.mapper.WarehouseMapper;
import com.multitrans.wasalliya.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseSer;
    private  final WarehouseMapper wMapper;

    @Autowired
    public WarehouseController(WarehouseService warehouseService,WarehouseMapper warehouseMapper){
        this.warehouseSer= warehouseService;
        this.wMapper = warehouseMapper;
    }

    @PostMapping("")
    public ResponseEntity<WarehouseDTO> save(@Valid @RequestBody WarehouseDTO dto){
        return ResponseEntity.ok(warehouseSer.createWarehouse(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> find(@PathVariable Long id){
        return ResponseEntity.ok(warehouseSer.findWarehouseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(warehouseSer.deleteWarehouseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> update(@RequestBody WarehouseDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(warehouseSer.updateWarehouseById(id,dto));
    }

}
