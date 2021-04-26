package models;

import java.util.List;

public class Service {
    public String id;
    public String patientID;
    public ServiceType serviceType;
   // public Activity ID; // to track activity of particular service.
    public ActivityType currentActivity;
    public List<Activity> activityList;
    public String startTime;
    public String endTime;
}

1 OPD REG [REG,Vital,Doc,Pharma],2.00 NULL