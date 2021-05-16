package com.iiitb.ConsentManagement.ConsentManagement.Controller;


import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthService;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthServiceType;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.HealthServiceRepository;
import com.iiitb.ConsentManagement.ConsentManagement.Services.HealthServicesService;
import com.iiitb.ConsentManagement.ConsentManagement.StaticMappings.ServiceStages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HealthServiceController {

    HealthServiceRepository healthServiceRepository;
    HealthServicesService healthServicesService;

    @Autowired
    public HealthServiceController(HealthServicesService healthServicesService, HealthServiceRepository healthServiceRepository)
    {
        this.healthServicesService = healthServicesService;
        this.healthServiceRepository = healthServiceRepository;
    }



    public String createHealthService(String patientID, String purpose, ActivityType currentActivityType,String actorID)
    {
        System.out.println("[HealthserviceController-createHealthService]: Came-Here");
        String healthServiceCreationResult;
        HealthServiceType healthServiceType;
        LocalTime healthServiceStartTime;


        try
        {
            healthServiceType = HealthServiceType.valueOf(purpose.toUpperCase());
        }
        catch(Exception e)
        {
            System.out.println("[EXCEPTION]: Inside HealthServiceController-createHealthService(): Failed to get HealthServiceType from given Purpose");
            return "PURPOSE_NOT_DEFINED";       // if we are unable to get healthservice from purpose then we get exception, we catch it and return from it.
        }

        healthServiceStartTime = LocalTime.now();   // start of healthService time.

        healthServiceCreationResult = healthServicesService.healthSerivceCreation(patientID,healthServiceType,currentActivityType,healthServiceStartTime,actorID);

        System.out.println("Creation of health service is successful with healthService ID: "+ healthServiceCreationResult);

        if(healthServiceCreationResult.matches("^FAILED.*"))
        {
            System.out.println("[HealthServiceController-createHealthService()]: Inside IF creation of health service creation return value");
            return healthServiceCreationResult; // if failed send same thing back
        }

       return healthServiceCreationResult;
    }


    public HealthService getHealthServiceByPatientIDAndEndTime(String patientID,LocalTime endTime)
    {
        List<HealthService> healthService = null;

        healthService = healthServiceRepository.findByPatientIDAndEndTime(patientID,endTime);

        if(healthService.size()==0)
        {
            System.out.println("[HealthServiceController-getHealthServiceByPatientIDAndEndTime()]: Inside if healthservice.size ==0");
            return null;
        }

        return healthService.get(0);    // Here we assume there will be only one health service in active state at any point of time for given patient.

    }

}
