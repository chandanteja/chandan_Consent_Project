package com.iiitb.ConsentManagement.ConsentManagement.Beans;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * This is the class for Activities that a Hospital Offers.
 * This class contains basic set of Activities with which we are proceeding with. We can add more Activities depending on hospital policy.
 *
 */



@Entity
@Component
@Table(name="activity_table")
public class Activity {

    @Id
    private String activityID;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;


    @ManyToOne
    @JoinColumn(name = "patientID_FK",referencedColumnName = "patientID")
    private DemographicDetails demographicDetails;


    @ManyToOne
    @JoinColumn(name = "healthServiceID_FK",referencedColumnName = "healthServiceID")
    private HealthService healthService;

// The start and end times need to be placed in different table as the same activity can have different times.

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;


    public Activity() {
    }

    public DemographicDetails getdemographicDetails() {
        return demographicDetails;
    }

    public void setdemographicDetails(DemographicDetails demographicDetails) {
        this.demographicDetails = demographicDetails;
    }

    public HealthService gethealthService() {
        return healthService;
    }

    public void sethealthService(HealthService healthService) {
        this.healthService = healthService;
    }

    public Activity(String activityID, ActivityType activityType, DemographicDetails demographicDetails, HealthService healthService, LocalDateTime startTime, LocalDateTime endTime) {
        this.activityID = activityID;
        this.activityType = activityType;
        this.demographicDetails = demographicDetails;
        this.healthService = healthService;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public DemographicDetails getDemographicDetails() {
        return demographicDetails;
    }

    public void setDemographicDetails(DemographicDetails demographicDetails) {
        this.demographicDetails = demographicDetails;
    }

    public HealthService getHealthService() {
        return healthService;
    }

    public void setHealthService(HealthService healthService) {
        this.healthService = healthService;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
