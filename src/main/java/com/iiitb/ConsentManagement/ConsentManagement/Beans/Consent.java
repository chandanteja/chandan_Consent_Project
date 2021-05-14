package com.iiitb.ConsentManagement.ConsentManagement.Beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Component
@Table(name="consent_table")
public class Consent {

    @Id
    private String consentID;

    // We just need patient ID. We need just patient ID, we are creating an FK to patient ID of Demographic Details.
//    @ManyToOne  // Many consents can be given by one patient itself.
//    @JoinColumn(name = "patientID_FK",referencedColumnName = "patientID")
//    private DemographicDetails demographicDetails;      // patient ID.

    @Column(nullable = false)
    private String patientID;       // We are not taking patient ID as Foreign key because if PID is taken as FK, then
    // if we delete the patient then the history of consent objects created by him as part of different services will also be deleted.
    // But as it is sensitive data, we need to maintain history of such consents created. So we are not using FK.

    @Column(nullable = false)
    private String healthServiceID;

    @Column(nullable = false)
    private ActivityType activityType;


   @Column
    private String actorID;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Column
    @Enumerated(EnumType.STRING)
    private ConsentType consentGivenForOperation;    // Creation, updation delete etc on the respective forms.

    @Column
    @Enumerated(EnumType.STRING)
    private AccessLevel otherDataAccessLevel;


    // Access level is needed if we are giving access to data on level of access basis.
//    @Column(nullable = false)
//    private String accessLevel;


    public Consent(String consentID, String patientID, String healthServiceID, ActivityType activityType, String actorID, LocalTime startTime, LocalTime endTime, ConsentType consentGivenForOperation, AccessLevel otherDataAccessLevel) {
        this.consentID = consentID;
        this.patientID = patientID;
        this.healthServiceID = healthServiceID;
        this.activityType = activityType;
        this.actorID = actorID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.consentGivenForOperation = consentGivenForOperation;
        this.otherDataAccessLevel = otherDataAccessLevel;
    }

    public Consent() {
    }

    public String getConsentID() {
        return consentID;
    }

    public void setConsentID(String consentID) {
        this.consentID = consentID;
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

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getActorID() {
        return actorID;
    }

    public void setActorID(String actorID) {
        this.actorID = actorID;
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

    public ConsentType getConsentGivenForOperation() {
        return consentGivenForOperation;
    }

    public void setConsentGivenForOperation(ConsentType consentGivenForOperation) {
        this.consentGivenForOperation = consentGivenForOperation;
    }

    public AccessLevel getOtherDataAccessLevel() {
        return otherDataAccessLevel;
    }

    public void setOtherDataAccessLevel(AccessLevel otherDataAccessLevel) {
        this.otherDataAccessLevel = otherDataAccessLevel;
    }
}
