package com.elevator.service;

import com.elevator.model.Elevator;
import com.elevator.model.ElevatorRequest;
import com.elevator.repository.ElevatorRepository;
import com.elevator.repository.ElevatorRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class ElevatorService {
    
    @Autowired
    private ElevatorRepository elevatorRepository;
    
    @Autowired
    private ElevatorRequestRepository requestRepository;
    
    private static final int TOTAL_ELEVATORS = 3;
    private static final int MAX_FLOOR = 10;
    private static final int MIN_FLOOR = 1;
    
    @PostConstruct
    public void initialize() {
        if (elevatorRepository.count() == 0) {
            for (int i = 0; i < TOTAL_ELEVATORS; i++) {
                Elevator elevator = new Elevator();
                elevatorRepository.save(elevator);
            }
        }
    }
    
    public List<Elevator> getAllElevators() {
        return elevatorRepository.findAll();
    }
    
    public ElevatorRequest createRequest(int sourceFloor, int destinationFloor) {
        if (sourceFloor < MIN_FLOOR || sourceFloor > MAX_FLOOR ||
            destinationFloor < MIN_FLOOR || destinationFloor > MAX_FLOOR) {
            throw new IllegalArgumentException("Invalid floor number");
        }
        
        ElevatorRequest request = new ElevatorRequest(sourceFloor, destinationFloor);
        Elevator bestElevator = findBestElevator(sourceFloor, request.getDirection());
        
        if (bestElevator != null) {
            request.setAssignedElevator(bestElevator);
            request.setStatus(ElevatorRequest.RequestStatus.ASSIGNED);
            bestElevator.addDestination(sourceFloor);
            bestElevator.addDestination(destinationFloor);
            
            elevatorRepository.save(bestElevator);
        }
        
        return requestRepository.save(request);
    }
    
    private Elevator findBestElevator(int targetFloor, ElevatorRequest.RequestDirection direction) {
        List<Elevator> elevators = elevatorRepository.findAll();
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Elevator elevator : elevators) {
            if (elevator.getState() == Elevator.State.MAINTENANCE) {
                continue;
            }
            
            int distance = Math.abs(elevator.getCurrentFloor() - targetFloor);
            
            if (elevator.getDirection() == Elevator.Direction.IDLE) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            } else if (elevator.canStopAtFloor(targetFloor)) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            }
        }
        
        return bestElevator;
    }
    
    public void updateElevatorStatus(Long elevatorId, int currentFloor, boolean doorOpen) {
        Optional<Elevator> optionalElevator = elevatorRepository.findById(elevatorId);
        if (optionalElevator.isPresent()) {
            Elevator elevator = optionalElevator.get();
            elevator.setCurrentFloor(currentFloor);
            elevator.setDoorOpen(doorOpen);
            
            if (!elevator.getDestinationFloors().isEmpty() && 
                elevator.getCurrentFloor() == elevator.getDestinationFloors().get(0)) {
                elevator.getDestinationFloors().remove(0);
            }
            
            elevator.updateDirection();
            elevatorRepository.save(elevator);
        }
    }
    
    public void toggleDoor(Long elevatorId) {
        Optional<Elevator> optionalElevator = elevatorRepository.findById(elevatorId);
        if (optionalElevator.isPresent()) {
            Elevator elevator = optionalElevator.get();
            elevator.setDoorOpen(!elevator.isDoorOpen());
            elevatorRepository.save(elevator);
        }
    }
} 