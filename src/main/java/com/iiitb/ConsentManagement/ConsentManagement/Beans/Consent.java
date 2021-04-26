package com.iiitb.ConsentManagement.ConsentManagement.Beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Component
@Table(name="consent_table")
public class Consent {

    @Id
    private String consentID;

    @ManyToOne  // Many consents can be given by one patient itself.
    @JoinColumn(name = "patientID_FK",referencedColumnName = "patientID")
    private DemographicDetails demographicDetails;

    @OneToOne
    private HealthService serviceID;

    @Column(nullable = false)
    private ActivityType activityType;

    @Column
    private ROLE actorType;

    @OneToOne //doubtful. It shuld be OneToMany
    private Actor actorID;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private AccessLevel accessLevel;

    public Consent(String consentID, DemographicDetails demographicDetails, HealthService serviceID, ActivityType activityType, Actor actorID, LocalDateTime startTime, LocalDateTime endTime, AccessLevel accessLevel) {
        this.consentID = consentID;
        this.demographicDetails = demographicDetails;
        this.serviceID = serviceID;
        this.activityType = activityType;
        this.actorID = actorID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.accessLevel = accessLevel;
    }

    public Consent() {
    }

    public ROLE getActorType() {
        return actorType;
    }

    public void setActorType(ROLE actorType) {
        this.actorType = actorType;
    }

    public String getConsentID() {
        return consentID;
    }

    public void setConsentID(String consentID) {
        this.consentID = consentID;
    }

    public DemographicDetails getdemographicDetails() {
        return demographicDetails;
    }

    public void setdemographicDetails(DemographicDetails demographicDetails) {
        this.demographicDetails = demographicDetails;
    }

    public HealthService getServiceID() {
        return serviceID;
    }

    public void setServiceID(HealthService serviceID) {
        this.serviceID = serviceID;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Actor getActorID() {
        return actorID;
    }

    public void setActorID(Actor actorID) {
        this.actorID = actorID;
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

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
