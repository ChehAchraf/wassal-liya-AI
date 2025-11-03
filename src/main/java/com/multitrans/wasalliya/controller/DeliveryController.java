package com.multitrans.wasalliya.controller;

import com.multitrans.wasalliya.enums.DeliveryStatus;
import com.multitrans.wasalliya.model.dto.DeliveryDTO;
import com.multitrans.wasalliya.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {


    private final DeliveryService deliverySer;
    @Autowired
    public DeliveryController(DeliveryService deliveryService ){
        this.deliverySer = deliveryService;
    }

    @GetMapping("")
    public ResponseEntity<List<DeliveryDTO>> index(){
        return ResponseEntity.ok(deliverySer.getAllDeliveries());
    }

    @PostMapping("")
    public ResponseEntity<DeliveryDTO> create(@Valid @RequestBody DeliveryDTO dto){
        return  ResponseEntity.ok(deliverySer.createDelivery(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(deliverySer.findDeliveryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        return ResponseEntity.ok(deliverySer.deleteDeliveryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDTO> edit(@RequestBody DeliveryDTO dto, Long id){
        return ResponseEntity.ok(deliverySer.editDeliveryById(id,dto));
    }

    @GetMapping("/bystatus")
    public ResponseEntity<List<DeliveryDTO>> getAllDelivriesByStatusAndWeight(@RequestParam("status")DeliveryStatus status,@RequestParam("wieght") Double weight){
        return ResponseEntity.ok(deliverySer.getByStatusAndWeight(status,weight));
    }


}
