package com.iiitb.ConsentManagement.ConsentManagement.Controller;

import com.iiitb.ConsentManagement.ConsentManagement.ActivityRuleValidator.ActivityRuleValidator;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
//import com.iiitb.ConsentManagement.ConsentManagement.Services.ActorService;
import com.iiitb.ConsentManagement.ConsentManagement.ConsentManager.ConsentCreationController;
import com.iiitb.ConsentManagement.ConsentManagement.Services.ActorAssignmentService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.PatientRegistrationService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.RulesService;
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
    private ActorAssignmentService actorAssignmentService;
    @Autowired
    public PatientController(PatientRegistrationService patientRegistrationService,  RulesService rulesService, ActivityRuleValidator activityRuleValidatior, HealthServiceController healthServiceController,
                             ConsentCreationController consentCreationController, ActorAssignmentService actorAssignmentService)
    {
        this.patientRegistrationService = patientRegistrationService;
        this.rulesService = rulesService;
        this.activityRuleValidator = activityRuleValidatior;
        this.healthServiceController = healthServiceController;
        this.consentCreationController = consentCreationController;
        this.actorAssignmentService = actorAssignmentService;
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

       LocalTime operationTime = LocalTime.now();
       final String TABLENAME = "demographic_details" ;
       String  patientID = null ;
       String healthServiceCreation;
       ActivityType currentActivityType;
       DemographicDetails savePatientData = null;
       List<ActivityType> activityTypesList = null;
       List<Consent> consentObjectsList = null;



        // Activity RUles Validation
       String ruleValidationResult = activityRuleValidator.validateRegistrationActivityRule(details.getConsent(),details.getOperation(), operationTime,details.getPurpose(),TABLENAME,actorRole);
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


            // We will get some value into healthservicecreation variable. So no nullpointer exception
           healthServiceCreation = healthServiceController.createHealthService(patientID,details.getPurpose(),currentActivityType);
           System.out.println("[patientController-addPatient()]: Health service creation is successful/not: "+healthServiceCreation);

           if(!healthServiceCreation.equals("SUCCESS"))
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


           try {
               System.out.println("Sleeping for ---------- 5sec");
               Thread.sleep(5000);

           }
           catch(InterruptedException e)
           {
              System.out.println("Interrupted Exception occurred, Sleep is over");
           }

           System.out.println("Sending request to async method");
           actorAssignmentService.assignNurseToPatient(patientID, consentObjectsList.get(0));
           System.out.println("after Sending request to async method");

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
