package com.iiitb.consentmanagement1.receptionist.Controller;


import com.iiitb.consentmanagement1.receptionist.Services.EmailService;
import com.iiitb.consentmanagement1.receptionist.Services.OTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class OTPController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public OTPService otpService;
    public EmailService myEmailService;

    @Autowired
    public OTPController(OTPService otpService, EmailService myEmailService) {
        this.otpService = otpService;
        this.myEmailService = myEmailService;
    }


    public String generateOtp(String email) {

        //   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //   String username = auth.getName();
        System.out.println("Inside OTPController generateOTP email: " + email);

        int otp = otpService.generateOTP(email);
        logger.info("OTP : " + otp);

        String message = "This OTP is for your Consent to Store you Health Records in Electronic Format.\n" + "OTP is " + otp;
        myEmailService.sendOtpMessage(email, "OTP -SpringBoot", message);

        return "OTP Sent Successfully";

    }

    //   @RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
    public @ResponseBody
    int validateOtp(@RequestParam("otpnum") Integer otpnum, String email) {

        final int SUCCESS = 1;      // OTP is valid
        final int FAIL = 0; // OTP is invalid

        System.out.println("Inside OTPController-validateOtp \n ===============================");
        System.out.println("OTP By user: " + otpnum);
        System.out.println("Email of user: " + email);
        //  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // String username = auth.getName();
        String userEmail = email;

        logger.info(" Inside validateotp of otpcontroller-Otp Number : " + otpnum);

//Validate the Otp
        int serverOtp = otpService.getOtp(userEmail);
        System.out.println("Inside OTP Validation: " + serverOtp);

        if (otpnum > 0) {
            if (otpnum == serverOtp) {
                System.out.println("Inside optnum==serverotp");
                return SUCCESS;
            } else {
                System.out.println("Inside otpnum != serverotp");
                return FAIL;
            }
        }
        return FAIL;
    }
}
