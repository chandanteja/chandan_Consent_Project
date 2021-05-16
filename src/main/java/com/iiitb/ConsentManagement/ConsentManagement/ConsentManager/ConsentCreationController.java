package com.iiitb.ConsentManagement.ConsentManagement.ConsentManager;



import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
import com.iiitb.ConsentManagement.ConsentManagement.Controller.HealthServiceController;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.ActivityRepository;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.HealthServiceRepository;
import com.iiitb.ConsentManagement.ConsentManagement.StaticMappings.ServiceStages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class ConsentCreationController {

    HealthServiceController healthServiceController;    // for accessing healthservice controller methods.
    ConsentService consentService;

    @Autowired
    public ConsentCreationController(HealthServiceController healthServiceController, ConsentService consentService)
    {
        this.healthServiceController = healthServiceController;
        this.consentService = consentService;
    }

    // from calling function, we get patientID, ConsentForOperation, AccessLevel
    public List<Consent> consentCreationBasic(String patientID, ConsentType consentGivenForOpeatrion,String actorID)
    {
        HealthService healthService = null;
        List<ActivityType> activityTypeList = null;
        AccessLevel accessLevel = AccessLevel.BASIC;
        List<Consent> consentObjectsList = null;

        System.out.println("[ConsentCreationController-consentCreation()]: came-here-1");
        healthService = healthServiceController.getHealthServiceByPatientIDAndEndTime(patientID,null);


        if(healthService == null)
        {
            System.out.println("[ConsentCreationController-consentCreation()]: Inside healthservice==null");
            return null;
        }

        activityTypeList = ServiceStages.map.get(healthService.getHealthServiceType());

        consentObjectsList = consentService.createConsentObjectsList(patientID,healthService.getHealthServiceID(),actorID,activityTypeList,consentGivenForOpeatrion,accessLevel);





    return consentObjectsList;
    }

}
