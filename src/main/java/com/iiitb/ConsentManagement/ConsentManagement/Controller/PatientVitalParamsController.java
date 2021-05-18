package com.iiitb.ConsentManagement.ConsentManagement.Controller;

import com.iiitb.ConsentManagement.ConsentManagement.ActivityRuleValidator.ActivityRuleValidator;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.VitalParametersRepository;
import com.iiitb.ConsentManagement.ConsentManagement.Services.VitalParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientVitalParamsController {

    private ActivityRuleValidator activityRuleValidator;
    VitalParamsService vitalParamsService;

    @Autowired
    public PatientVitalParamsController(ActivityRuleValidator activityRuleValidator,VitalParamsService vitalParamsService)
    {
        this.activityRuleValidator = activityRuleValidator;
        this.vitalParamsService = vitalParamsService;

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

            // create an activity for doctor
            // add that activity object to health service list
            // update consent object
            //
        }

        else
        {
            System.out.println("Activity rule validation failed. Rule validation result is: "+ruleValidationResult);
            return ruleValidationResult;
        }



        return ruleValidationResult;
    }


    }
