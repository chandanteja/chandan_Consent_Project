package com.iiitb.ConsentManagement.ConsentManagement.Controller;

import com.iiitb.ConsentManagement.ConsentManagement.ActivityRuleValidator.ActivityRuleValidator;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
//import com.iiitb.ConsentManagement.ConsentManagement.Services.ActorService;
import com.iiitb.ConsentManagement.ConsentManagement.ConsentManager.ConsentCreationController;
import com.iiitb.ConsentManagement.ConsentManagement.Services.*;
import com.iiitb.ConsentManagement.ConsentManagement.StaticMappings.ServiceStages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.QueryParam;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientController {

    private PatientRegistrationService patientRegistrationService;
    private RulesService rulesService;
    private ActivityRuleValidator activityRuleValidator;
    private HealthServiceController healthServiceController;
    private ConsentCreationController consentCreationController;
    private NurseAssignmentService nurseAssignmentService;
    private HealthServicesService healthServicesService;
    private ActivityService activityService;

    @Autowired
    public PatientController(PatientRegistrationService patientRegistrationService, RulesService rulesService, ActivityRuleValidator activityRuleValidatior, HealthServiceController healthServiceController,
                             ConsentCreationController consentCreationController, NurseAssignmentService nurseAssignmentService, ActivityService activityService, HealthServicesService healthServicesService)
    {
        this.patientRegistrationService = patientRegistrationService;
        this.rulesService = rulesService;
        this.activityRuleValidator = activityRuleValidatior;
        this.healthServiceController = healthServiceController;
        this.consentCreationController = consentCreationController;
        this.nurseAssignmentService = nurseAssignmentService;
        this.activityService = activityService;
        this.healthServicesService = healthServicesService;
    }


    @PostMapping(path="/addpatient", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addPatient( @QueryParam("actorID") String actorID, @QueryParam("actorRole") ROLE actorRole,@RequestBody final DemographicDetails details) {

       System.out.println("[PatientController-addPatient()]: Inside Consent management micro service");
       System.out.println("Values are");
       System.out.println("Demographic details obj: "+details);
       System.out.println("Actor ID: "+ actorID);
       System.out.println("Actor Role: "+ actorRole);
       System.out.println("Patient name: "+ details.getFirstName());


       final String TABLENAME = "demographic_details" ;
       String  patientID = null ;
       String healthServiceID;
       ActivityType currentActivityType;
       DemographicDetails savePatientData = null;
       List<ActivityType> activityTypesList = null;
       List<Consent> consentObjectsList = null;
      // String assignedActorID = null;



        // Activity RUles Validation
       String ruleValidationResult = activityRuleValidator.validateRegistrationActivityRule(details.getConsent(),details.getOperation(),details.getPurpose(),TABLENAME,actorRole);
       System.out.println("Result of rule validation: "+ruleValidationResult);


       if( ruleValidationResult.equals("SUCCESS") )
       {
            // Means rules are validated successfully. So, now I create a UID for Patient which will be used in later stage of the process
            // for identifying patient.

           patientID = UUID.randomUUID().toString();
           currentActivityType = ActivityType.REGISTRATION;     // As adding patient corresponds to registration activity, we have taken statically this field.

           // We are saving patient data first because if we save at last then if patient has any duplicate entry the the servics are getting created before itself
           // but patient data is not getting saved bcz of unique email constraint. But the services are getting created.

           savePatientData =  patientRegistrationService.savePatientDemographicDetails(details,patientID);
           System.out.println("[patientController-addPatient()]: Returnened value of save data: "+savePatientData);

           if(savePatientData == null)
               return "FAILED_TO_SAVE_DATA";

           System.out.println("Patient ID from the object after patient is saved: "+ savePatientData.getPatientID());


            // We will get some value into healthServiceID variable. So no nullpointer exception
           healthServiceID = healthServiceController.createHealthService(patientID,details.getPurpose(),currentActivityType,actorID);
           System.out.println("[patientController-addPatient()]: Health service creation is successful/not: "+healthServiceID);

           if( healthServiceID.matches("^FAILED.*") )
                return "FAILED_TO_CREATE_HEALTH_SERVICE";



           // Consent part
           activityTypesList = ServiceStages.map.get(HealthServiceType.valueOf(details.getPurpose()));

           System.out.println("===============================");
           System.out.println("printing activity type list after fetching using purpose");
           System.out.println("Before entering for-each loop for printing activity types");

           // from here make a call for conesnt creation

           for (ActivityType activityType: activityTypesList)
           {
                System.out.println(activityType );
           }

           System.out.println("===============================");

           consentObjectsList = consentCreationController.consentCreationBasic(patientID,ConsentType.CREATE,actorID);

           System.out.println("Consent Objects created are: ");

           for (Consent consent: consentObjectsList)
           {
               System.out.println("patient Id from consent Obj:"+consent.getPatientID() );
               System.out.println(" Activity type from consent Obj:"+consent.getActivityType() );
           }
           System.out.println("===============================");

           int currentActivityIndex = activityTypesList.indexOf(currentActivityType);
           int activityTypesListSize = activityTypesList.size();
           ActivityType nextActivityType = null;
           int nextActivityIndex;

           if((currentActivityIndex == activityTypesListSize-1))
           {
               System.out.println("Inside size-index case");
               nextActivityIndex = -1;      // this occurs when there is no next activity or current activity is the last activity.

           }
           else
           {
               // As this is registration activity, we need to assign the actor for next activity. So for this we are adding +1.
               nextActivityIndex = currentActivityIndex+1;
              // nextActivityType = activityTypesList.get(nextActivityIndex);
           }

           System.out.println("Sending request to async method- assignNurseToPatient()");
           if(nextActivityIndex!=-1)    // assign actor
               nurseAssignmentService.assignNurseToPatient(patientID,consentObjectsList.get(nextActivityIndex));
               //assignedActorID = actorAssignmentService.assignNurseToPatient(patientID,consentObjectsList.get(nextActivityIndex));

           else
               return "NO_NEXT_ACTIVITY"; // If we fail to assign actor we return back saying failed to assign actor.

           // End of REGISTRATION Activity
           System.out.println("Before ending the current activity. Fetching health service");
           HealthService healthService = healthServicesService.getCurrentHealthService(patientID);

           if(healthService == null)
                return "FAILED_TO_GET_HEALTH_SERVICE";

           Activity activity = activityService.endActivity(healthService,currentActivityType,LocalTime.now());

           if(activity == null)
                return "FAILED_TO_END_ACTIVITY";

           System.out.println("ACTIVITY ENDED time is: "+ activity.getEndTime());

       }
       else
       {
           return ruleValidationResult; // Means ruleValidatonResult is not success.
       }



       System.out.println("Sending success to receptionist.");
       // Note before saving patient data, we need to set patient ID using UUID.
            return "SUCCESS";      // This is success i.e means rule validation, creation of health service,activity, saving of data and COnsent all are successful. Then only we return success.
        }

}
