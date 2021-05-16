package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Activity;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.Consent;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthService;
import com.iiitb.ConsentManagement.ConsentManagement.ConsentManager.ConsentRepository;
import com.iiitb.ConsentManagement.ConsentManagement.ConsentManager.ConsentService;
import com.iiitb.ConsentManagement.ConsentManagement.StaticMappings.NextActivityMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Named;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.*;


// Here we do pre-activity processing of nurse related things

@Named
public class NurseAssignmentService {

    ConsentService consentService;
    TaskExecutor taskExecutor;
    HealthServicesService healthServicesService;
    ActivityService activityService ;
    //final ActivityType NURSE_ACTIVITY = ActivityType.VITAL_PARAMETERS;


    @Autowired
    public NurseAssignmentService(ConsentService consentService,TaskExecutor taskExecutor,HealthServicesService healthServicesService
    ,ActivityService activityService)
    {
            this.taskExecutor = taskExecutor;
            this.consentService = consentService;
            this.healthServicesService = healthServicesService;
            this.activityService = activityService;
    }

    @Async("ActorAssignementThreadPool")
    public String assignNurseToPatient(String patientID, Consent nurseConsent)
    {

        System.out.println("[NurseAssignmentService-assignNurseToPatient]: Inside assignNurseToPatient method");


        RestTemplate restTemplate = new RestTemplate();

        String uri = UriComponentsBuilder.fromUriString("http://localhost:9094/api/findavailablenurse/").build().toString();
        System.out.println("Before sending postforentity");

         class MyTask implements  Callable<String> {
            @Override
             public String call() throws  Exception {
                System.out.println("Inside Inner class MyTask-call()");
                ResponseEntity<String> tempResponse = restTemplate.postForEntity(uri,null,String.class);
                System.out.println("After postForENtity Inner class MyTask-call()");

                return tempResponse.getBody().toString();
            }
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        String nurseID = null;
        while(true)
        {
            System.out.println("Inside While(true) to get nurse ID");
            Future<String> futureCall = executorService.submit(new MyTask());


            try {
                System.out.println("Before get() of futurecall ");
                nurseID = futureCall.get(10, TimeUnit.SECONDS);
                System.out.println("Got NurseID from Nurse Microservice ID is: "+ nurseID);
            }
            catch (Exception e)
            {
                System.out.println("[Exception]: Exception occured when trying to get available nurseID. Inside Nurse Assignment Service after inner class"+ e.getMessage());

            }

            if (nurseID == null)
                {
                    System.out.println("Didn't get Nurse ID. Retrying again.....");
                    continue;

                }
                else
                    break;

        }
        // the responseEntity Will have the actorID from nurse microservice
        // ResponseEntity<String> responseEntity =  restTemplate.postForEntity(uri,null,String.class);

        System.out.println("[NurseAssignmentService]: Inside assignNurseToPatient method. After the postForEntity() call");
        System.out.println("Response from nurse microservice, NurseID is: "+ nurseID);
       // System.out.println("Response from nurse microservice is: "+responseEntity.getBody().toString());

        //After getting available nurse ID, it means nurse activity has started, so we shuld update the healthservice current activity withactivity related to nurse.

        // Update current activity in healthservice for given patient
        HealthService healthService = healthServicesService.getCurrentHealthService(patientID);


        ActivityType currentActivity = healthService.getCurrentActivityType();
        ActivityType nextActivityType = NextActivityMapping.nextActivity.get(currentActivity);
        HealthService updatedHealthService = healthServicesService.updateCurrentHealthServiceActivityType(healthService,nextActivityType);

        System.out.println("Patient ID is:"+ patientID);
        System.out.println("Patient ID of saved H.S is:"+healthService.getPatientID());
        System.out.println("Current Activity type is:"+ healthService.getCurrentActivityType());


        // Updating the nurse consent with nurseID
      nurseConsent = consentService.updateConsent(nurseConsent,nurseID, LocalTime.now());
        // creation and activity updation with actor ID came from nurse microservice is done here.

        // Activity Creation
        String activityID = activityService.createActivity(patientID,updatedHealthService.getHealthServiceID(),nextActivityType,nurseID);

        if(activityID.equals("FAILED_TO_CREATE_ACTIVITY"))      // error in creating in activity
             return "FAILED_TO_GET_ACTIVITYID";  // Unable to create ACTIVITY

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

       if(nurseConsent!=null)
       {
           System.out.println("NurseConsent: ActorID after updating Consent is: " + nurseConsent.getActorID());
           System.out.println("NurseConsent: StartTime after updating Consent is: " + nurseConsent.getStartTime());
       }

       return nurseID;
       // return responseEntity.getBody().toString();     // sending actor ID back to caller
    }
}
