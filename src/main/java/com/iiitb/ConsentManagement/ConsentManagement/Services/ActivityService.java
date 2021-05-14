package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Activity;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Named
public class ActivityService {

    Activity activity;
    ActivityRepository activityRepository;

    @Autowired
    public ActivityService(Activity activity, ActivityRepository activityRepository)
    {
        this.activity = activity;
        this.activityRepository = activityRepository;
    }

    public String createActivity(String patientID, String healthServiceID, ActivityType activityType)
    {
        System.out.println("[ActivityService-createActivity()]: Came-here");


        LocalTime activityStartTime = LocalTime.now();
        LocalTime activityEndTime = null;
        String activityID = UUID.randomUUID().toString();

        activity.setActivityID(activityID);
        activity.setPatientID(patientID);
        activity.setHealthServiceID(healthServiceID);
        activity.setActivityType(activityType);
        activity.setStartTime(activityStartTime);
        activity.setEndTime(activityEndTime);


        try
        {
            activity = activityRepository.save(activity);
        }
        catch (Exception e)
        {
            System.out.println("[Exception]: Inside ActivityService-createActvity() - Failed to save the Activity Object. ");
            return "FAILED_TO_CREATE_ACTIVITY"; // If we fail to create activity, we return error but not activity ID.
        }


        return activity.getActivityID();       // On successful creation of Activity, we return activity-ID.
    }



    public Activity getActivityByID(String activityID)
    {

        System.out.println("[ActivityService-getActivityByID()]: Came-here");

        List<Activity> activity=null;

        if(activityID != null)
        {
            activity = activityRepository.findByActivityID(activityID);

        }

        if(activity.size() == 0)
                return null;    // Activity doesn't exist.

        return activity.get(0);
    }
}
