package com.iiitb.ConsentManagement.ConsentManagement.Beans;


import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name="activity_table")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;     // This is auto generated ID

    @Column(nullable = false,unique = true)
    private String activityID;      // This is ID for unique Identification, auto gen ID won't work because of auto increment


    @Column(nullable = false)
    private String activityName;




    public Activity() {
    }

    public Activity(Integer ID, String activityID, String activityName) {
        this.ID = ID;
        this.activityID = activityID;
        this.activityName = activityName;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


}
