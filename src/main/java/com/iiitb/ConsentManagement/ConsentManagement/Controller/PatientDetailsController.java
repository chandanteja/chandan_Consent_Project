package com.iiitb.ConsentManagement.ConsentManagement.Controller;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.DemographicDetails;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.OTPEmailDetails;
import com.iiitb.ConsentManagement.ConsentManagement.Services.PatientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientDetailsController {

    private PatientDetailsService patientDetailsService;
    private OTPController otpController;
    @Autowired
    public PatientDetailsController(PatientDetailsService patientDetailsService,OTPController otpController )
    {
        this.patientDetailsService = patientDetailsService;
        this.otpController = otpController;
    }

    @PostMapping(path="/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addPatientDemographicData(@RequestBody final DemographicDetails details)
    {
        System.out.println("Inside PatientDetailsController patient firstname: "+details.getFirstName());
        System.out.println("Inside PatientDetailsController patient Lastname: "+details.getLastName());
        System.out.println("Inside PatientDetailsController patient Email: "+details.getEmail());
        System.out.println("Inside PatientDetailsController patient Phone: "+details.getPhoneNumber());
        System.out.println("Inside PatientDetailsController patient Age: "+details.getAge());
        System.out.println("Inside PatientDetailsController patient Address: "+details.getAddress());
        System.out.println("Inside PatientDetailsController patient Bloodgroup: "+details.getBloodGroup());
        System.out.println("Inside PatientDetailsController patient Gender: "+details.getGender());

        System.out.println("===================================================================");
        System.out.println("Inside PateientDetailsCOntroller OTP: "+details.getOtp());
        System.out.println("Inside PateientDetailsCOntroller COnsent: "+details.getConsent() );

        boolean result=false;

        int otpValid = otpController.validateOtp(details.getOtp(),details.getEmail());

        System.out.println("The return value of OTP validation is "+otpValid);

        if(otpValid == 1 && details.getConsent().equals("true")) //if otp is valid and consent is given then save data and return success
        {
            result = patientDetailsService.addPatientDemographicDetails(details);
            if(result== true)
                    return String.valueOf(1); //1 -> successfully saved
            else if(result==false)
                    return String.valueOf(2); // Failed to save data but consent and otp are valid;
        }
        else if(otpValid == 1 && details.getConsent().equals("false"))
                return String.valueOf(3);  //consent is not given;

        else if(otpValid == 0 && details.getConsent().equals("true"))
                return String.valueOf(4);  //Inavalid OTP


       return String.valueOf(2); //data is not saved
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
