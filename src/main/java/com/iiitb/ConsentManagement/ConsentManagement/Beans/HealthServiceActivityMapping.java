package com.iiitb.ConsentManagement.ConsentManagement.Beans;


import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name="health_service_activity_mapping")

public class HealthServiceActivityMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "health_service_ID",referencedColumnName = "healthServiceID")
    private HealthService healthServiceID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ativity_ID",referencedColumnName = "activityID")
    private Activity activityID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="roleID", referencedColumnName = "roleID")
    private Roles roleID;

    // These 2 columns in tables are needed bcz every health service will have different set of activities so, we can have that flexibility to skip some activities here.

    @ManyToOne
    @JoinColumn(name = "previousActivity",referencedColumnName = "activityID")
    private Activity prevActivity;  // refers to activity ID for maintainig previous activity of this activity

    @ManyToOne
    @JoinColumn(name="nextActivity",referencedColumnName = "activityID")
    private Activity nextActivity;     //refers to next activity of this activity


    public HealthServiceActivityMapping() {
    }

    public HealthServiceActivityMapping(Integer ID, HealthService healthServiceID, Activity activityID, Roles roleID, Activity prevActivity, Activity nextActivity) {
        this.ID = ID;
        this.healthServiceID = healthServiceID;
        this.activityID = activityID;
        this.roleID = roleID;
        this.prevActivity = prevActivity;
        this.nextActivity = nextActivity;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public HealthService getHealthServiceID() {
        return healthServiceID;
    }

    public void setHealthServiceID(HealthService healthServiceID) {
        this.healthServiceID = healthServiceID;
    }

    public Activity getActivityID() {
        return activityID;
    }

    public void setActivityID(Activity activityID) {
        this.activityID = activityID;
    }

    public Roles getRoleID() {
        return roleID;
    }

    public void setRoleID(Roles roleID) {
        this.roleID = roleID;
    }

    public Activity getPrevActivity() {
        return prevActivity;
    }

    public void setPrevActivity(Activity prevActivity) {
        this.prevActivity = prevActivity;
    }

    public Activity getNextActivity() {
        return nextActivity;
    }

    public void setNextActivity(Activity nextActivity) {
        this.nextActivity = nextActivity;
    }
}
