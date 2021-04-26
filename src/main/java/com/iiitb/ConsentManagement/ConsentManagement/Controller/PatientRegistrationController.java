package com.iiitb.ConsentManagement.ConsentManagement.Controller;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.DemographicDetails;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ROLE;
import com.iiitb.ConsentManagement.ConsentManagement.Services.ActorLoginService;
import com.iiitb.ConsentManagement.ConsentManagement.Services.PatientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientRegistrationController {

    private PatientRegistrationService patientRegistrationService;
    private OTPController otpController;
    private ActorLoginService actorLoginService;
    @Autowired
    public PatientRegistrationController(PatientRegistrationService patientRegistrationService, OTPController otpController,ActorLoginService actorLoginService )
    {
        this.patientRegistrationService = patientRegistrationService;
        this.otpController = otpController;
        this.actorLoginService = actorLoginService;
    }

    public int validateActivityRules(String consent,String operation, ROLE role, )
    {
        // will check consent is true of not


        return 0;
    }


    @PostMapping(path="/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addPatientDemographicData(@RequestBody final DemographicDetails details)
    {
        /*System.out.println("Inside PatientRegistrationController patient firstname: "+details.getFirstName());
        System.out.println("Inside PatientRegistrationController patient Lastname: "+details.getLastName());
        System.out.println("Inside PatientRegistrationController patient Email: "+details.getEmail());
        System.out.println("Inside PatientRegistrationController patient Phone: "+details.getPhoneNumber());
        System.out.println("Inside PatientRegistrationController patient Age: "+details.getAge());
        System.out.println("Inside PatientRegistrationController patient Address: "+details.getAddress());
        System.out.println("Inside PatientRegistrationController patient Bloodgroup: "+details.getBloodGroup());
        System.out.println("Inside PatientRegistrationController patient Gender: "+details.getGender());

        System.out.println("===================================================================");
        System.out.println("Inside PatientRegistrationController OTP: "+details.getOtp());
        System.out.println("Inside PatientRegistrationController COnsent: "+details.getConsent() );


        System.out.println("Inside PatientRegistrationController loginEmail of actor is "+ details.getLoginEmail());
        */

        ROLE loginEmail;
        boolean result=false;
        int otpValid;





        loginEmail =  actorLoginService.getActorRole(details.getLoginEmail());


        // First we check if the actor and operation is valid or invalid.
        otpValid = otpController.validateOtp(details.getOtp(),details.getEmail());


        System.out.println("The return value of OTP validation is "+otpValid);

        if(otpValid == 1 && details.getConsent().equals("true")) //if otp is valid and consent is given then save data and return success
        {
            result = patientRegistrationService.addPatientDemographicDetails(details);
            if(result== true)
                    return String.valueOf(1); //1 -> successfully saved
            else if(result==false)
                    return String.valueOf(2); // Failed to save data but consent and otp are valid;
        }
        else if(otpValid == 1 && details.getConsent().equals("false"))
                return String.valueOf(3);  //consent is not given;

        else if(otpValid == 0 && details.getConsent().equals("true"))
                return String.valueOf(4);  //Inavalid OTP


       return String.valueOf(2); //Failed to save data but consent and otp are valid;
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


    @PostMapping(path="/validateOTP", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Response validateOTPController(@RequestBody final Map<String, Object> allDetails)
    {
        System.out.println("Inside validateOTP Controller");
        /*System.out.println("OTP is: "+otpDetails.getOtp());
        System.out.println("Email is:"+otpDetails.getEmail());*/
        System.out.println("all details are: "+allDetails);
        System.out.println("otp: "+allDetails.get("otp"));
        System.out.println("email: "+allDetails.get("email"));

        int otpValid = otpController.validateOtp(Integer.parseInt(allDetails.get("otp").toString()),allDetails.get("email").toString());

        if(otpValid != 1)
            return Response.status(401).build();


        return Response.ok().build();
    }



}
