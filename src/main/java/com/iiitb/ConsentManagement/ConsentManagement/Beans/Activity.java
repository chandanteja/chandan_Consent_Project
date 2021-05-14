package com.iiitb.ConsentManagement.ConsentManagement.Beans;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;


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


//    @ManyToOne
//    @JoinColumn(name = "patientID_FK",referencedColumnName = "patientID")
//    private DemographicDetails demographicDetails;  // This refers to which activity is mapped to which patient.

    private String patientID; // Maintaining patientID as string because if we maintain PatientID as Foreign key to  Patient details (demographic details)
    // Then if we delete a patient then his previous record of Activities will be deleted which is not correct.


//    @ManyToOne
//    @JoinColumn(name = "healthServiceID_FK",referencedColumnName = "healthServiceID")
//    private HealthService healthService;

    private String healthServiceID;

// The start and end times need to be placed in different table as the same activity can have different times.

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;


    public Activity() {
    }

    public Activity(String activityID, ActivityType activityType, String patientID, String healthServiceID, LocalTime startTime, LocalTime endTime) {
        this.activityID = activityID;
        this.activityType = activityType;
        this.patientID = patientID;
        this.healthServiceID = healthServiceID;
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
