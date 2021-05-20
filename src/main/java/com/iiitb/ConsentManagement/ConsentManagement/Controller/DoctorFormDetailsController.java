package com.iiitb.ConsentManagement.ConsentManagement.Controller;


import com.iiitb.ConsentManagement.ConsentManagement.ActivityRuleValidator.ActivityRuleValidator;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
import com.iiitb.ConsentManagement.ConsentManagement.ConsentManager.ConsentService;
import com.iiitb.ConsentManagement.ConsentManagement.HelperClasses.BasicPatientData;
import com.iiitb.ConsentManagement.ConsentManagement.Services.ActivityService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.DoctorFormDetailsService;
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
public class DoctorFormDetailsController {

    private ActivityRuleValidator activityRuleValidator;
    private ActivityService activityService;
    private ConsentService consentService;
    private DoctorFormDetailsService doctorFormDetailsService;
    @Autowired
    public DoctorFormDetailsController(ActivityRuleValidator activityRuleValidator,ActivityService activityService,ConsentService consentService,DoctorFormDetailsService doctorFormDetailsService)
    {
        this.activityRuleValidator = activityRuleValidator;
        this.activityService = activityService;
        this.consentService = consentService;
        this.doctorFormDetailsService = doctorFormDetailsService;
    }


    @PostMapping(path="/doctorformdetails", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String saveDoctorFormData(@QueryParam("doctorID") String doctorID, @QueryParam("actorRole") ROLE actorRole, @RequestBody final DoctorFormDetails doctorFormDetails )
    {
        System.out.println("Doctor ID is: "+doctorID);
        System.out.println("Actor ROle is: "+actorRole);
        System.out.println("Patient ID is: "+ doctorFormDetails.getPatientID());
        System.out.println("Patient Name"+doctorFormDetails.getPatientName());

        ConsentType consentForOperation = ConsentType.CREATE;
        final String TABLENAME = "doctor_form_table" ;
        String healthServiceID;
        ActivityType currentActivityType;
        List<ActivityType> activityTypesList = null;
        List<Consent> consentObjectsList = null;
        String doctorFormDataID = null;
        DoctorFormDetails doctorFormDetailsSaveData = null;
        String patientID = doctorFormDetails.getPatientID();

        String ruleValidationResult = activityRuleValidator.activityRuleValidation(patientID,doctorID,consentForOperation,doctorFormDetails.getOperation(),TABLENAME,actorRole);
        System.out.println("Result of rule validation: "+ruleValidationResult);

        if(ruleValidationResult.equals("SUCCESS"))
        {
            doctorFormDataID = UUID.randomUUID().toString();
            currentActivityType = ActivityType.DOCTOR_CHECKUP;

            doctorFormDetails.setDoctorFormID(doctorFormDataID);

            doctorFormDetailsSaveData  = doctorFormDetailsService.saveDoctorFormData(doctorFormDetails);

            if(doctorFormDetailsSaveData == null)
            {
                System.out.println("Failed to save  doctor observations data");
                return "FAILED_TO_SAVE_DOCTOR_FORM_DATA";
            }


            System.out.println("Patient ID from the object after doctor form data is saved: "+ doctorFormDetailsSaveData.getPatientID());

            Activity activityObject = activityService.getActivityByActorIDAndTypeAndEndTimeNullAndPatientID(doctorID,currentActivityType,patientID);

            activityObject.setEndTime(LocalTime.now());     // setting the end time of activity. it means the activity is ended

            activityObject = activityService.saveActivity(activityObject);

            if(activityObject == null)
            {
                System.out.println("Failed to end  activity of doctor");
                return "FAILED_TO_SAVE_ACTIVITY_OBJECT";
            }

            Consent consetObj = consentService.getConsent(patientID,doctorID,consentForOperation,null);
            String consentRevocation = consentService.revokeConsent(consetObj);

            if(!consentRevocation.equals("SUCCESS"))
            {
                System.out.println("Failed to revoke consent for nurse");
                return "FAILED_TO_REVOKE_CONSENT_FOR_NURSE";
            }



        }

        else{
            System.out.println("Failed to validate Activity rules for doctor");
            return ruleValidationResult;
        }


        return ruleValidationResult;
    }
}
