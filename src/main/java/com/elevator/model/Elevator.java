package com.elevator.model;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "elevators")
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int currentFloor;
    
    @Enumerated(EnumType.STRING)
    private Direction direction;
    
    @Enumerated(EnumType.STRING)
    private State state;
    
    @ElementCollection
    private List<Integer> destinationFloors = new ArrayList<>();
    
    private boolean doorOpen;
    
    public enum Direction {
        UP, DOWN, IDLE
    }
    
    public enum State {
        MOVING, STOPPED, MAINTENANCE
    }
    
    public Elevator() {
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
        this.state = State.STOPPED;
        this.doorOpen = false;
    }
    
    public void addDestination(int floor) {
        if (!destinationFloors.contains(floor)) {
            destinationFloors.add(floor);
            updateDirection();
        }
    }
    
    public void updateDirection() {
        if (destinationFloors.isEmpty()) {
            this.direction = Direction.IDLE;
            return;
        }
        
        int nextFloor = destinationFloors.get(0);
        if (nextFloor > currentFloor) {
            this.direction = Direction.UP;
        } else if (nextFloor < currentFloor) {
            this.direction = Direction.DOWN;
        }
    }
    
    public boolean canStopAtFloor(int floor) {
        if (direction == Direction.IDLE) return true;
        if (direction == Direction.UP && floor > currentFloor) return true;
        if (direction == Direction.DOWN && floor < currentFloor) return true;
        return false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Integer> getDestinationFloors() {
        return destinationFloors;
    }

    public void setDestinationFloors(List<Integer> destinationFloors) {
        this.destinationFloors = destinationFloors;
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    public void setDoorOpen(boolean doorOpen) {
        this.doorOpen = doorOpen;
    }
} 