package com.iiitb.ConsentManagement.ConsentManagement.DAO;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface HealthServiceRepository extends JpaRepository<HealthService,String> {
    
    List<HealthService> findByPatientIDAndEndTime(String patientID, LocalTime endTime);
    List<HealthService> findByPatientIDAndStartTimeIsNotAndEndTime(String patientID, LocalTime startTime, LocalTime endTime);
    List<HealthService> findByHealthServiceIDAndStartTimeIsNotAndEndTime(String healthServiceID, LocalTime startTime, LocalTime endTime);
}
