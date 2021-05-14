package com.iiitb.consentmanagement2.nurse.Controller;

import com.iiitb.consentmanagement2.nurse.Beans.VitalDetails;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientVitalController {

    @PostMapping(path = "/enterVital", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addPatientDemographicData(@RequestBody final VitalDetails details) {
        return "Hello from nurse";
    }
}
