package com.elevator.repository;

import com.elevator.model.ElevatorRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ElevatorRequestRepository extends JpaRepository<ElevatorRequest, Long> {
    List<ElevatorRequest> findByStatus(ElevatorRequest.RequestStatus status);
} 