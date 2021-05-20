package com.iiitb.ConsentManagement.ConsentManagement.Controller;

import com.iiitb.ConsentManagement.ConsentManagement.ActivityRuleValidator.ActivityRuleValidator;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
import com.iiitb.ConsentManagement.ConsentManagement.ConsentManager.ConsentService;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.VitalParametersRepository;
import com.iiitb.ConsentManagement.ConsentManagement.Services.ActivityService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.DoctorAssignmentService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.VitalParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.QueryParam;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientVitalParamsController {

    private ActivityRuleValidator activityRuleValidator;
    VitalParamsService vitalParamsService;
    DoctorAssignmentService doctorAssignmentService;
    ActivityService activityService;
    ConsentService consentService;

    @Autowired
    public PatientVitalParamsController(ActivityRuleValidator activityRuleValidator,VitalParamsService vitalParamsService,
                                        DoctorAssignmentService doctorAssignmentService,ActivityService activityService,ConsentService consentService)
    {
        this.activityRuleValidator = activityRuleValidator;
        this.vitalParamsService = vitalParamsService;
        this.doctorAssignmentService = doctorAssignmentService;
        this.activityService = activityService;
        this.consentService = consentService;

    }


    @PostMapping(path="/addpatientvital", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addPatientVitalParameters(@QueryParam("actorID") String actorID, @QueryParam("actorRole") ROLE actorRole, @RequestBody final VitalParams vitalParams)
    {
        System.out.println("[PatientVitalParamsController-addPatient()]: Inside Consent management micro service");
        System.out.println("Values are");
        System.out.println("Vital Params details obj: "+vitalParams);
        System.out.println("Actor ID: "+ actorID);
        System.out.println("Actor Role: "+ actorRole);
        System.out.println("Patient name: "+ vitalParams.getPatientName());
        System.out.println("Nurse ID is: "+vitalParams.getLoginEmail());


        final String TABLENAME = "vital_params" ;
        String  patientID = vitalParams.getPatientID() ;
        String healthServiceID;
        ActivityType currentActivityType;
        List<ActivityType> activityTypesList = null;
        List<Consent> consentObjectsList = null;
        String vitalParametersID = null;
        VitalParams vitalParamsSaveData = null;

        ConsentType consentForOperation = ConsentType.CREATE;   // we can havi this as static field as this method is corresponding to save/create of data.



        // Activity RUles Validation
        String ruleValidationResult = activityRuleValidator.activityRuleValidation(patientID,actorID,consentForOperation,vitalParams.getOperation(),TABLENAME,actorRole);
        System.out.println("Result of rule validation: "+ruleValidationResult);

        if(ruleValidationResult.equals("SUCCESS"))
        {
            vitalParametersID = UUID.randomUUID().toString();
            currentActivityType = ActivityType.VITAL_PARAMETERS;

            vitalParams.setVitalParamsID(vitalParametersID);

            vitalParamsSaveData  = vitalParamsService.saveVitalParams(vitalParams);

            if(vitalParamsSaveData == null)
            {
                System.out.println("Failed to save vital params data");
                return "FAILED_TO_SAVE_VITAL_PARAMS";
            }


            System.out.println("Patient ID from the object after vital param data is saved: "+ vitalParamsSaveData.getPatientID());

            Activity activityObject = activityService.getActivityByActorIDAndTypeAndEndTimeNullAndPatientID(actorID,currentActivityType,patientID);

            activityObject.setEndTime(LocalTime.now());     // setting the end time of activity. it means the activity is ended

            activityObject = activityService.saveActivity(activityObject);

            if(activityObject == null)
            {
                System.out.println("Failed to end  activity of nurse");
                return "FAILED_TO_SAVE_ACTIVITY_OBJECT";
            }

            Consent consetObj = consentService.getConsent(patientID,actorID,consentForOperation,null);
            String consentRevocation = consentService.revokeConsent(consetObj);

            if(!consentRevocation.equals("SUCCESS"))
            {
                System.out.println("Failed to revoke consent for nurse");
                return "FAILED_TO_REVOKE_CONSENT_FOR_NURSE";
            }

            RestTemplate restTemplate    = new RestTemplate();
            String uri = UriComponentsBuilder.fromUriString("http://localhost:9094/api/changeNurseStatusIdle/").queryParam("actorID",actorID).build().toString();

            System.out.println("Before calling rest controller to Nurse micro service to change nurse status to idle");
            ResponseEntity<String> responseEntity =  restTemplate.postForEntity(uri,null,String.class);

            if(responseEntity.getBody().toString().equals("FAILED_TO_CHANGE_STATUS"))
            {
                System.out.println("Failed to change status of nurse. Nurse is available for other activity");
                return "FAILED_TO_CHANGE_STATUS";
            }

            // End vitalparams - Nurse activity.
            // Doctor assignment will be done by nurse.
            // Flow is after adding vital params data to DB nurse activity will be ended.
            // In front end nurse will take patient ID and will select department and click "Assign Doctor"
            // then call will go to nurse micro service, then a nurse micro service will send a request to patient micro service
            // patient micro service will query the doctor microservice and take free doctor and assign to patient.
            // create an activity for doctor
            // add that activity object to health service list
            // update consent object
            //

//            int currentActivityIndex = activityTypesList.indexOf(currentActivityType);
//            int activityTypesListSize = activityTypesList.size();
//           // ActivityType nextActivityType = null;
//            int nextActivityIndex;
//
//            if((currentActivityIndex == activityTypesListSize-1))
//            {
//                System.out.println("Inside size-index case");
//                nextActivityIndex = -1;      // this occurs when there is no next activity or current activity is the last activity.
//
//            }
//            else
//            {
//                // As this is registration activity, we need to assign the actor for next activity. So for this we are adding +1.
//                nextActivityIndex = currentActivityIndex+1;
//                // nextActivityType = activityTypesList.get(nextActivityIndex);
//            }
//
//            System.out.println("Sending request to async method- assignNurseToPatient()");
////            if(nextActivityIndex!=-1)    // assign actor
////               nurseAssignmentService.assignNurseToPatient(patientID,consentObjectsList.get(nextActivityIndex));
////                //assignedActorID = actorAssignmentService.assignNurseToPatient(patientID,consentObjectsList.get(nextActivityIndex));
////
////            else
////                return "NO_NEXT_ACTIVITY"; // If we fail to assign actor we return back saying failed to assign actor.



        }

        else
        {
            System.out.println("Activity rule validation failed. Rule validation result is: "+ruleValidationResult);
            return ruleValidationResult;
        }



        return ruleValidationResult;
    }


    }
