package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Activity;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthService;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthServiceType;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.HealthServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
public class HealthServicesService {

    HealthService healthService;
    ActivityService activityService;
    HealthServiceRepository healthServiceRepository;

    @Autowired
    public HealthServicesService(HealthService healthService, ActivityService activityService, HealthServiceRepository healthServiceRepository)
    {
        this.healthService = healthService;
        this.activityService = activityService;
        this.healthServiceRepository = healthServiceRepository;
    }



    public String healthSerivceCreation(String patientID, HealthServiceType healthServiceType, ActivityType currentActivityType, LocalTime healthServiceStartTime,String actorID)
    {
        System.out.println("[HealthServicesService-healthServiceCreation]: Came-here");

       String healthServiceID = UUID.randomUUID().toString();
       String activityID = null;
       Activity activity;
       List<Activity> healthServiceActivityList = new ArrayList<>();

        healthService.setHealthServiceID(healthServiceID);
        healthService.setPatientID(patientID);
        healthService.setHealthServiceType(healthServiceType);
        healthService.setCurrentActivityType(currentActivityType);
        healthService.setStartTime(healthServiceStartTime);


        // This is the activity for registration
        activityID = activityService.createActivity(patientID,healthServiceID,currentActivityType,actorID);

        if(activityID.equals("FAILED_TO_CREATE_ACTIVITY"))      // error in creating in activity
                return "FAILED_TO_GET_ACTIVITYID";  // Unable to create ACTIVITY

        activity = activityService.getActivityByID(activityID);

        if(activity == null)
            return "FAILED_TO_CREATE_ACTIVITY_FROM_HEALTH_SERVICE";   // Failed to create activity from health service


        healthServiceActivityList.add(activity);
        healthService.setActivityList(healthServiceActivityList);

        try
        {
            healthService = healthServiceRepository.save(healthService);
        }
        catch(Exception e)
        {
            System.out.println("[Exception]: Exception occurred while saving Health Service data inside healthServceCreation");
            return "FAILED_TO_CREATE_HEALTH_SERVICE";
        }

        return healthService.getHealthServiceID();    // Means successfully created the health service.
    }


    public HealthService updateCurrentHealthServiceActivityType(HealthService healthService,ActivityType currentActivity )
    {
        System.out.println("[HealthServicesService] Inside updateCurrentHealthServiceActivity() method");

        healthService.setCurrentActivityType(currentActivity);  // setting activity current type

        try{
            healthService = healthServiceRepository.save(healthService);
        }
        catch (Exception e)
        {
            System.out.println("[Exception] Exception occured when trying to save the updated healthService. Inside updateCurrentHealthServiceActivity");
            return null;    // return null if exception occurs
        }

        // update and save is pending
        return healthService; // as there will be only one healthservice for a patient running at any point of time
    }

    public HealthService  getCurrentHealthService(String patientID)
    {
        System.out.println("[HealthServicesService] Inside getCurrentHealthService() method");
        List<HealthService> healthService;

        // We will fetch patient health services which has not null start time and end time as null;
        healthService = healthServiceRepository.findByPatientIDAndStartTimeIsNotAndEndTime(patientID,null,null);

        System.out.println("After returning from findByPatientIDAndStartTimeIsNotAndEndTime() method");
        if(healthService.size() == 0)
        {
            System.out.println("Inside healthService size == 0 IF condition");
            return null;
        }
        return healthService.get(0);
    }

    public HealthService saveHealthService(HealthService healthService)
    {
        System.out.println("[HealthServicesService] Inside saveHealthService() method");
        try{
            healthService = healthServiceRepository.save(healthService);
        }
        catch (Exception e)
        {
            System.out.println("[Exception] Exception occured when trying to save the updated healthService. Inside saveHealthService");
            return null;    // return null if exception occurs
        }

        return healthService;
    }

    public HealthService getHealthServiceByID(String healthServiceID)
    {
        System.out.println("[HealthServicesService]: Inside getHealthServiceByID()");
        List<HealthService> healthServices = null;
        // STart time is not null and end time shuld be null. So, we send both as null
        healthServices = healthServiceRepository.findByHealthServiceIDAndStartTimeIsNotAndEndTime(healthServiceID,null,null);

        System.out.println("After returning from findByHealthServiceIDAndStartTimeIsNotAndEndTime() method");
        if(healthServices.size() == 0)
        {
            System.out.println("Inside healthServices size == 0 IF condition");
            return null;
        }
        return healthServices.get(0);   // bcz we will get only one health service when searched by ID.
    }

}
