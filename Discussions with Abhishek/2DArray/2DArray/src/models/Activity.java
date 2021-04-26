package models;

public class Activity {
    public String id;
    public ActivityType activityType;
    public String startTime;
    public String endTime;
    // These 2 are for tracking which activity is mapped to whoch service of a patient.
    public String patientID;
    public String serviceID;

}


1 Registration 2.00 3.00 1 1
2 Vital params 3.15 4.00 1 1
3 Registration 3.00 4.00 2 SURGERY







