package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Activity;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthService;
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
   // HealthServicesService healthServicesService;

    @Autowired
    public ActivityService(Activity activity, ActivityRepository activityRepository)//,HealthServicesService healthServicesService)
    {
        this.activity = activity;
        this.activityRepository = activityRepository;
     //   this.healthServicesService = healthServicesService;
    }

    public String createActivity(String patientID, String healthServiceID, ActivityType activityType, String actorID)
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
        activity.setActorID(actorID);


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

        if(activity.size() == 0) {
            System.out.println("Inside size ==0");
            return null;    // Activity doesn't exist.
        }
        return activity.get(0);
    }

    public Activity getActivityByPatientIDAndActivityTypeAndEndTime(String patientID,ActivityType activityType)
    {
        System.out.println("[ActivityService-getActivityByPatientIDAndActivityTypeAndEndTime()]: Came-here");

        List<Activity> activity=null;

        if(patientID != null)
        {
            activity = activityRepository.findByPatientIDAndActivityTypeAndEndTime(patientID,activityType,null);

        }

        if(activity.size() == 0)
            return null;    // Activity doesn't exist.

        return activity.get(0);
    }

    public Activity endActivity(HealthService healthService,ActivityType activityType, LocalTime endTime)
    {

            List<Activity> activities = healthService.getActivityList();
            Activity endednActivity = null;

            int temp=0;
            while(activities.size() != temp)
            {

                if(activities.get(temp).getActivityType().equals(activityType))     // if activity is equal to given activity type
                {
                    activities.get(temp).setEndTime(endTime);
                    try {
                        endednActivity = activityRepository.save(activities.get(temp));
                    }
                    catch(Exception e)
                    {
                        System.out.println("[Exception ]: Failed to save the ended activity");
                        return null;
                    }
                    break;
                }
                temp++;

            }
            return  endednActivity;
    }

    public String getPatientIDFromActorID(String actorID)
    {
        List<Activity> patientID = null;
        patientID = activityRepository.findByActorIDAndStartTimeIsNotNullAndEndTime(actorID,null);

            if(patientID.size()==0)
            {
                System.out.println("Inside patientID.size == 0");
                return "FAILED_TO_FETCH_ACTIVITY";
            }

            return patientID.get(0).getPatientID();

    }

    public Activity getActivityByActorIDAndTypeAndEndTimeNullAndPatientID(String actorID, ActivityType activityType ,String patientID)
    {
        System.out.println("[ActivityService-getActivityByActorIDAndTypeAndEndTimeAndPatientID()]: Came-here");

        List<Activity> activity=null;

        if(actorID != null)
        {
            activity = activityRepository.findByActorIDAndActivityTypeAndStartTimeIsNotNullAndEndTimeIsNullAndPatientID(actorID,activityType,patientID);
            System.out.println("After fetching activity");
        }

        if(activity.size() == 0) {

            System.out.println("activity.size == 0. So returning null");
            return null;    // Activity doesn't exist.
        }
        return activity.get(0);
    }


    public Activity saveActivity(Activity activity)
    {
        System.out.println("Inside ActivityService-saveActivity");

        try{
            activity = activityRepository.save(activity);
        }
        catch (Exception e)
        {
            System.out.println("[Exception]: Exception occured while saving activity object");
            return null;
        }
        return activity;
    }

}
