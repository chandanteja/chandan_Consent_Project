package com.iiitb.ConsentManagement.ConsentManagement.Beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Entity
@Component
@Table(name="health_service_table")
public class HealthService {

    @Id
    private String healthServiceID;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HealthServiceType healthServiceType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType currentActivityType;

    @Column
    @OneToMany(fetch = FetchType.EAGER)
    private List<Activity> activityList;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Column
    private String patientID;       // Maintaining patientID as string because if we maintain PatientID as Foreign key to  Patient details (demographic details)
    // Then if we delete a patient then his previous record of health services will be deleted which is not correct.

    public HealthService() {
    }


    public HealthService(String healthServiceID, HealthServiceType healthServiceType, ActivityType currentActivityType, List<Activity> activityList, LocalTime startTime, LocalTime endTime, String patientID) {
        this.healthServiceID = healthServiceID;
        this.healthServiceType = healthServiceType;
        this.currentActivityType = currentActivityType;
        this.activityList = activityList;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientID = patientID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getHealthServiceID() {
        return healthServiceID;
    }

    public void setHealthServiceID(String healthServiceID) {
        this.healthServiceID = healthServiceID;
    }

    public HealthServiceType getHealthServiceType() {
        return healthServiceType;
    }

    public void setHealthServiceType(HealthServiceType healthServiceType) {
        this.healthServiceType = healthServiceType;
    }

    public ActivityType getCurrentActivityType() {
        return currentActivityType;
    }

    public void setCurrentActivityType(ActivityType currentActivityType) {
        this.currentActivityType = currentActivityType;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
