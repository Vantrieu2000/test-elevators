package com.elevator.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "elevator_requests")
public class ElevatorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int sourceFloor;
    private int destinationFloor;
    
    @Enumerated(EnumType.STRING)
    private RequestDirection direction;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    
    private LocalDateTime requestTime;
    
    @ManyToOne
    @JoinColumn(name = "elevator_id")
    private Elevator assignedElevator;
    
    public enum RequestDirection {
        UP, DOWN
    }
    
    public enum RequestStatus {
        PENDING, ASSIGNED, COMPLETED, CANCELLED
    }
    
    public ElevatorRequest() {
        this.requestTime = LocalDateTime.now();
        this.status = RequestStatus.PENDING;
    }
    
    public ElevatorRequest(int sourceFloor, int destinationFloor) {
        this();
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = sourceFloor < destinationFloor ? RequestDirection.UP : RequestDirection.DOWN;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public void setSourceFloor(int sourceFloor) {
        this.sourceFloor = sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public RequestDirection getDirection() {
        return direction;
    }

    public void setDirection(RequestDirection direction) {
        this.direction = direction;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public Elevator getAssignedElevator() {
        return assignedElevator;
    }

    public void setAssignedElevator(Elevator assignedElevator) {
        this.assignedElevator = assignedElevator;
    }
} 