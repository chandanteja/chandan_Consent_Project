package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Activity;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.Consent;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthService;
import com.iiitb.ConsentManagement.ConsentManagement.ConsentManager.ConsentService;
import com.iiitb.ConsentManagement.ConsentManagement.StaticMappings.NextActivityMapping;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.time.LocalTime;
import java.util.List;

@Named
public class DoctorAssignmentService {

    HealthServicesService healthServicesService;
    ConsentService consentService;
    ActivityService activityService ;


    @Autowired
    public DoctorAssignmentService(HealthServicesService healthServicesService,ConsentService consentService,ActivityService activityService)
    {
        this.healthServicesService = healthServicesService;
        this.activityService = activityService;
        this.consentService = consentService;
    }


    public String doctorActivity(String patientID, Consent doctorConsent, String doctorID)
    {
        System.out.println("[DoctorAssignmentService-doctorActivity]: Inside doctorActivity method");

        System.out.println(" doctorID is: "+ doctorID);
        // System.out.println("Response from nurse microservice is: "+responseEntity.getBody().toString());

        //After getting available nurse ID, it means nurse activity has started, so we shuld update the healthservice current activity withactivity related to nurse.

        // Update current activity in healthservice for given patient
        System.out.println("getting current health services of a patient");
        HealthService healthService = healthServicesService.getCurrentHealthService(patientID);


        ActivityType currentActivity = healthService.getCurrentActivityType();
        ActivityType nextActivityType = NextActivityMapping.nextActivity.get(currentActivity);
        HealthService updatedHealthService = healthServicesService.updateCurrentHealthServiceActivityType(healthService,nextActivityType);

        System.out.println("Patient ID is:"+ patientID);
        System.out.println("Patient ID of saved H.S is:"+healthService.getPatientID());
        System.out.println("Current Activity type is:"+ healthService.getCurrentActivityType());


        // Updating the nurse consent with nurseID

        doctorConsent = consentService.updateConsent(doctorConsent,doctorID, LocalTime.now());

        System.out.println("After updating doctor consent, Actor ID is : "+doctorConsent.getActorID());
        System.out.println("Hiiiiiiiiii: "+doctorConsent.getStartTime());
        // Activity Creation
        String activityID = activityService.createActivity(patientID,updatedHealthService.getHealthServiceID(),nextActivityType,doctorID);

        System.out.println("Activity ID after creation is: "+ activityID);

        if(activityID.equals("FAILED_TO_CREATE_ACTIVITY"))      // error in creating in activity
            return "FAILED_TO_GET_ACTIVITYID";  // Unable to create ACTIVITY

        System.out.println("Before getActivityByID() call");
        Activity activity = activityService.getActivityByID(activityID);

        if(activity == null)
            return "FAILED_TO_CREATE_ACTIVITY_FROM_HEALTH_SERVICE";   // Failed to create activity from health service

        // Adding activity to activity list in healthservice
        List<Activity> healthServiceActivityList = updatedHealthService.getActivityList();

        healthServiceActivityList.add(activity);
        updatedHealthService.setActivityList(healthServiceActivityList);

        updatedHealthService = healthServicesService.saveHealthService(updatedHealthService);

        if(updatedHealthService == null)
            return "FAILED_TO_SAVE_HEALTH_SERVICE";

      return "SUCCESS";
    }
}
