package com.iiitb.consentmanagement1.receptionist.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiitb.consentmanagement1.receptionist.Beans.DemographicDetails;
import com.iiitb.consentmanagement1.receptionist.Beans.ROLE;
import com.iiitb.consentmanagement1.receptionist.Services.ActorService;
import com.iiitb.consentmanagement1.receptionist.Services.PatientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.core.Response;
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


    @Autowired
    public PatientRegistrationController(PatientRegistrationService patientRegistrationService, OTPController otpController, ActorService actorService)
    {
        this.patientRegistrationService = patientRegistrationService;
        this.otpController = otpController;
        this.actorService = actorService;

    }


    @PostMapping(path="/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addPatientDemographicData(@RequestBody final DemographicDetails details)
    {
//        System.out.println("Inside PatientRegistrationController patient firstname: "+details.getFirstName());
//        System.out.println("Inside PatientRegistrationController patient Lastname: "+details.getLastName());
//        System.out.println("Inside PatientRegistrationController patient Email: "+details.getEmail());
//        System.out.println("Inside PatientRegistrationController patient Phone: "+details.getPhoneNumber());
//

        System.out.println("[PatientRegistrationController-addPatientDemoDetails()]: Inside receptionist microservice patiernt registration controller- add patient details.");

        System.out.println("Details is: "+details);

        LocalTime endTime,loginTime,operationTime,startTime;
        String otpValid;
        String actorID;
        ROLE actorType;

        operationTime = LocalTime.now();        // time at which this operation is performed i.e addPatientDetails is called from frontend
        endTime = actorService.getActorEndTime(details.getLoginEmail());
        loginTime = actorService.getActorLoginTime(details.getLoginEmail());
        startTime = actorService.getActorStartTime(details.getLoginEmail());

        System.out.println("Came here- time is: "+ operationTime);
        System.out.println("Came here- endtime is: "+ endTime);
        System.out.println("Came here- logintime is: "+ loginTime);
        System.out.println("Came here- starttime is: "+ startTime);

        if( !(operationTime.isAfter(loginTime) && operationTime.isBefore(endTime) && loginTime.isAfter(startTime)) )
        {
            System.out.println("Sorry! You can't perform the operation at this time. Your login time is greater than endtime");

            return "OUT_OF_OFFICE_HOURS";

        }

    System.out.println("Came here---------1");
    actorType = actorService.getActorRole(details.getLoginEmail());
    actorID = actorService.getActorID(details.getLoginEmail());

    otpValid = validationOfOTP(details);

    if(!otpValid.equals("SUCCESS"))
         return otpValid;

    System.out.println("After otp validation is done. Now calling rest template");

    RestTemplate restTemplate    = new RestTemplate();
    String uri = UriComponentsBuilder.fromUriString("http://localhost:9092/api/addpatient/").queryParam("actorID",actorID).queryParam("actorRole",actorType).build().toString();

    System.out.println("Before calling rest controller");
    ResponseEntity<String>  responseEntity =  restTemplate.postForEntity(uri,details,String.class);

    System.out.println("Response is: "+ responseEntity.getBody().toString());
    System.out.println("Patient ID returned is:" + responseEntity.getBody().toString());


    return responseEntity.getBody().toString();   // everything is valid and the user can go ahead.
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
        return "SUCCESS";


        /* result = patientRegistrationService.addPatientDemographicDetails(details);
        if(result== true)
            return String.valueOf(1); //1 -> successfully saved
        else if(result==false)
            return String.valueOf(2); // Failed to save data but consent and otp are valid;
            */

    }
    else if(otpValid == 1 && details.getConsent().equals("false"))
        return "CONSENT_NOT_GIVEN";  //consent is not given;

    else if(otpValid == 0 && details.getConsent().equals("true"))
        return "INVALID_OTP";  //Inavalid OTP


        return "ERROR_OCCURRED_IN_SAVING_DATA"; //Failed to save data but consent and otp are valid;
}


}
