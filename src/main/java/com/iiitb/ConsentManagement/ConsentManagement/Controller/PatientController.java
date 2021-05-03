package com.iiitb.ConsentManagement.ConsentManagement.Controller;

import com.iiitb.ConsentManagement.ConsentManagement.ActivityRuleValidator.ActivityRuleValidator;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
//import com.iiitb.ConsentManagement.ConsentManagement.Services.ActorService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.PatientRegistrationService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientController {

    private PatientRegistrationService patientRegistrationService;
    //private OTPController otpController;
    //private ActorService actorService;
    private RulesService rulesService;
    private ActivityRuleValidator activityRuleValidator;
    @Autowired
    public PatientController(PatientRegistrationService patientRegistrationService,  RulesService rulesService, ActivityRuleValidator activityRuleValidatior)
    {
        this.patientRegistrationService = patientRegistrationService;
        //this.otpController = otpController;
        //this.actorService = actorService;
        this.rulesService = rulesService;
        this.activityRuleValidator = activityRuleValidatior;
    }


    @PostMapping(path="/addpatient", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addPatient( @QueryParam("actorID") String actorID, @QueryParam("actorRole") ROLE actorRole,@RequestBody final DemographicDetails details)
    {

       System.out.println("[PatientController-addPatient()]: Inside Consent management micro service");
       System.out.println("Values are");
       System.out.println("Demographic details obj: "+details);
       System.out.println("Actor ID: "+ actorID);
       System.out.println("Actor Role: "+ actorRole);
        //ROLE actorRole = null;
       System.out.println("Patient name: "+ details.getFirstName());

       LocalTime operationTime = LocalTime.now();
       final String TABLENAME = "demographic_details" ;

       String ruleValidaationResult = activityRuleValidator.validateRegistrationActivityRule(details.getConsent(),details.getOperation(), operationTime,details.getPurpose(),TABLENAME,actorRole);

       System.out.println("Result of rule validation: "+ruleValidaationResult);
            return "SUCCESS";
        }

}
