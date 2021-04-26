package com.iiitb.ConsentManagement.ConsentManagement.Beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Component
@Table(name="health_service_table")
public class HealthService {

    @Id
    private String healthServiceID; // auto generated ID

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HealthServiceType healthServiceType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType currentActivity;

    @Column
    @OneToMany
    private List<Activity> activityList;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @OneToOne  // here we r using one to one because at any point of time patient will have only one servic in running.
    // So, when patient visits next time then we need to create a new service ID for same old patient.
    private DemographicDetails demographicDetails;


    public HealthService() {
    }

    public HealthService(String healthServiceID, HealthServiceType healthServiceType, ActivityType currentActivity, List<Activity> activityList, String startTime, String endTime, DemographicDetails demographicDetails) {
        this.healthServiceID = healthServiceID;
        this.healthServiceType = healthServiceType;
        this.currentActivity = currentActivity;
        this.activityList = activityList;
        this.startTime = startTime;
        this.endTime = endTime;
        this.demographicDetails = demographicDetails;
    }

    public DemographicDetails getDemographicDetails() {
        return demographicDetails;
    }

    public void setDemographicDetails(DemographicDetails demographicDetails) {
        this.demographicDetails = demographicDetails;
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

    public ActivityType getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(ActivityType currentActivity) {
        this.currentActivity = currentActivity;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
