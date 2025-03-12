package com.elevator.controller;

import com.elevator.model.Elevator;
import com.elevator.model.ElevatorRequest;
import com.elevator.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elevators")
@CrossOrigin(origins = "*")
public class ElevatorController {
    
    @Autowired
    private ElevatorService elevatorService;
    
    @GetMapping
    public ResponseEntity<List<Elevator>> getAllElevators() {
        return ResponseEntity.ok(elevatorService.getAllElevators());
    }
    
    @PostMapping("/request")
    public ResponseEntity<ElevatorRequest> createRequest(
            @RequestParam int sourceFloor,
            @RequestParam int destinationFloor) {
        return ResponseEntity.ok(elevatorService.createRequest(sourceFloor, destinationFloor));
    }
    
    @PutMapping("/{elevatorId}/status")
    public ResponseEntity<Void> updateElevatorStatus(
            @PathVariable Long elevatorId,
            @RequestParam int currentFloor,
            @RequestParam boolean doorOpen) {
        elevatorService.updateElevatorStatus(elevatorId, currentFloor, doorOpen);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{elevatorId}/toggle-door")
    public ResponseEntity<Void> toggleDoor(@PathVariable Long elevatorId) {
        elevatorService.toggleDoor(elevatorId);
        return ResponseEntity.ok().build();
    }
} 