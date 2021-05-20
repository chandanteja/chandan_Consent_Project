package com.iiitb.consentmanagement2.nurse.Controller;


import com.iiitb.consentmanagement2.nurse.Beans.VitalParams;
import com.iiitb.consentmanagement2.nurse.HelperClasses.DoctorAssignmentData;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DoctorAssignmentController {



    @PostMapping(path = "/assigndoctor", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String assignDoctor(@RequestBody final DoctorAssignmentData doctorAssignmentData)
    {
        System.out.println("[DoctorAssignmentController]: Inside assignDoctor()");
        System.out.println("Patient ID is: "+ doctorAssignmentData.getPatientID());
        System.out.println("Department is: "+ doctorAssignmentData.getDepartment());

        String nurseEmail = doctorAssignmentData.getLoginEmail();
        String patientID = doctorAssignmentData.getPatientID();

        String uri = UriComponentsBuilder.fromUriString("http://localhost:9092/api/doctorassignment/").queryParam("nurseEmail",nurseEmail).queryParam("patientID",patientID).build().toString();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(uri,null,String.class);

        System.out.println("response is: "+ response.getBody().toString());

//        if(response.getBody().toString().equals("FAILED_TO_FIND_DOCTOR"))
//        {
//            System.out.println("Failed to find doctor. Doctor may not be available");
//            return response.getBody().toString();
//        }

        return response.getBody().toString();
    }


}
