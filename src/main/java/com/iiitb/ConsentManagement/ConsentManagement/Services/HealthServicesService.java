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



    public String healthSerivceCreation(String patientID, HealthServiceType healthServiceType, ActivityType currentActivityType, LocalTime healthServiceStartTime)
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

      //  healthService.setActivityList(healthServiceActivityType);

        activityID = activityService.createActivity(patientID,healthServiceID,currentActivityType);

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



}
