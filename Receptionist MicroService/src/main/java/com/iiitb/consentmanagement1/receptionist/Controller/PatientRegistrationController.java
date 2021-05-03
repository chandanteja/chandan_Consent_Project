package com.iiitb.consentmanagement1.receptionist.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiitb.consentmanagement1.receptionist.Beans.DemographicDetails;
import com.iiitb.consentmanagement1.receptionist.Beans.ROLE;
import com.iiitb.consentmanagement1.receptionist.Services.ActorService;
import com.iiitb.consentmanagement1.receptionist.Services.PatientRegistrationService;
import org.apache.tomcat.jni.Local;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.core.Cookie;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientRegistrationController {

    private PatientRegistrationService patientRegistrationService;
    private OTPController otpController;
    private ActorService actorService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // private RulesService rulesService;
   // private ActivityRuleValidator activityRuleValidator;

    @Autowired
    public PatientRegistrationController(PatientRegistrationService patientRegistrationService, OTPController otpController, ActorService actorService)
    {
        this.patientRegistrationService = patientRegistrationService;
        this.otpController = otpController;
        this.actorService = actorService;
       // this.rulesService = rulesService;
       // this.activityRuleValidator = activityRuleValidatior;
    }

  /*  public int validateReceptionistRegistrationRule(String consent, String operation, String purpose, String actorEmail)
    {
        // role need not be verified as it will be depenedent on the rest api invocation
        MethodType methodType = null;
        LocalDateTime endTime;
        HealthServiceType healthServiceType = null;
        int validationResult;
        ROLE actorRole = ROLE.ROLE_INVALID;

        LocalDateTime operationTime = LocalDateTime.now();
        final String TABLENAME = "demographic_details" ;
        actorRole = actorService.getActorRole(actorEmail);


        validationResult = activityRuleValidator.validateRegistrationActivityRule(consent,operation,operationTime,purpose,actorEmail,TABLENAME,actorRole);


        //validationResult = activityRuleValidator.validateRegistrationActivityRule(consent,methodType.toString(),operationTime,healthServiceType,permittedRole,actorRole);




        /*
        if(!(consent.equals("true")))
                System.out.println("[PatientRegController-validateReceptionistRegRule] Inside consent not equal case");
        if(operationTime.isAfter(endTime));
            System.out.println("[PatientRegController-validateReceptionistRegRule] isAfter endtime case");










        return 0;
    }
*/


    @PostMapping(path="/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addPatientDemographicData(@RequestBody final DemographicDetails details)
    {
        System.out.println("Inside PatientRegistrationController patient firstname: "+details.getFirstName());
        System.out.println("Inside PatientRegistrationController patient Lastname: "+details.getLastName());
        System.out.println("Inside PatientRegistrationController patient Email: "+details.getEmail());
        System.out.println("Inside PatientRegistrationController patient Phone: "+details.getPhoneNumber());
        /*System.out.println("Inside PatientRegistrationController patient Age: "+details.getAge());
        System.out.println("Inside PatientRegistrationController patient Address: "+details.getAddress());
        System.out.println("Inside PatientRegistrationController patient Bloodgroup: "+details.getBloodGroup());
        System.out.println("Inside PatientRegistrationController patient Gender: "+details.getGender());

        System.out.println("===================================================================");
        System.out.println("Inside PatientRegistrationController OTP: "+details.getOtp());
        System.out.println("Inside PatientRegistrationController COnsent: "+details.getConsent() );


        System.out.println("Inside PatientRegistrationController loginEmail of actor is "+ details.getLoginEmail());
        */

        // first do login time and end time validation
        // Loginemail() is the email-ID of actor logged in and performing operation.

        System.out.println("[PatientRegistrationController-addPatientDemoDetails()]: Inside receptionist microservice patiernt registration controller- add patient details.");

        System.out.println("Details is: "+details);

        LocalTime endTime,loginTime,operationTime;
        ROLE loginEmail;
        String otpValid;


        operationTime = LocalTime.now();        // time at which this operation is performed i.e addPatientDetails is called from frontend
        endTime = actorService.getActorEndTime(details.getLoginEmail());
        loginTime = actorService.getActorLoginTime(details.getLoginEmail());

        System.out.println("Came here- time is: "+ operationTime);
        System.out.println("Came here- endtime is: "+ endTime);
        System.out.println("Came here- logintime is: "+ operationTime);


        if(!(operationTime.isAfter(loginTime) && operationTime.isBefore(endTime)))
        {
            System.out.println("Sorry! You can't perform the operation at this time. Your login time is greater than endtime");

            //return 5;
            // return saying the operation is being performed off duty hours. so reject the operation
        }

        System.out.println("Came here---------1");
// before validating OTP, validate whether the ROLE is having permission to perform this operation or not
        
    String actorID;
    ROLE actorType;


    actorType = actorService.getActorRole(details.getLoginEmail());
    actorID = actorService.getActorID(details.getLoginEmail());


    // Check consent here and if consent is not given come  out.

        otpValid = validationOfOTP(details);
        if(!otpValid.equals("1"))
            return otpValid;        // there is issue in otp validation

    System.out.println("After otp validation is done. Now calling rest template");

    RestTemplate restTemplate    = new RestTemplate();
    String uri = UriComponentsBuilder.fromUriString("http://localhost:9092/api/addpatient/").queryParam("actorID",actorID).queryParam("actorRole",actorType).build().toString();

    System.out.println("After calling rest controller");


    ResponseEntity<String>  responseEntity =  restTemplate.postForEntity(uri,details,String.class);

    System.out.println("Response is: "+ responseEntity.getBody().toString());




        // First we check if the actor and operation is valid or invalid. Then we check OTP validation

    return String.valueOf(1);   // everything is valid and the user can go ahead.
    }

    @PostMapping(path="/generateOTP", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Response generateOTPController(@RequestBody final DemographicDetails details)
    {
        System.out.println("Inside PatientDetails COntroller generateOTP email: "+details.getEmail());
            String res=otpController.generateOtp(details.getEmail());

            if(res!= "OTP Sent Successfully")
                    return Response.status(401).build();

            return Response.ok().build();

    }


  /*
    @PostMapping(path="/validateOTP", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Response validateOTPController(@RequestBody final Map<String, Object> allDetails)
    {
        System.out.println("Inside validateOTP Controller");
        /*System.out.println("OTP is: "+otpDetails.getOtp());
        System.out.println("Email is:"+otpDetails.getEmail());
        System.out.println("all details are: "+allDetails);
        System.out.println("otp: "+allDetails.get("otp"));
        System.out.println("email: "+allDetails.get("email"));

        int otpValid = otpController.validateOtp(Integer.parseInt(allDetails.get("otp").toString()),allDetails.get("email").toString());

        if(otpValid != 1)
            return Response.status(401).build();


        return Response.ok().build();
    }

   */

public String validationOfOTP(DemographicDetails details)
{

    int otpValid = otpController.validateOtp(details.getOtp(),details.getEmail());
    boolean result=false;

    System.out.println("The return value of OTP validation is "+otpValid);
    System.out.println("Consent is: "+details.getConsent());

    if(otpValid == 1 && details.getConsent().equals("true")) //if otp is valid and consent is given then save data and return success
    {
        System.out.println("Sending '1' back to caller");
        return String.valueOf(1);
       /* result = patientRegistrationService.addPatientDemographicDetails(details);
        if(result== true)
            return String.valueOf(1); //1 -> successfully saved
        else if(result==false)
            return String.valueOf(2); // Failed to save data but consent and otp are valid;
            */

    }
    else if(otpValid == 1 && details.getConsent().equals("false"))
        return String.valueOf(3);  //consent is not given;

    else if(otpValid == 0 && details.getConsent().equals("true"))
        return String.valueOf(4);  //Inavalid OTP


    return String.valueOf(2); //Failed to save data but consent and otp are valid;
}


}
