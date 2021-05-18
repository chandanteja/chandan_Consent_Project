package com.iiitb.ConsentManagement.ConsentManagement.Controller;


import com.iiitb.ConsentManagement.ConsentManagement.Beans.DemographicDetails;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.PatientDemographicDetailsRepository;
import com.iiitb.ConsentManagement.ConsentManagement.HelperClasses.BasicPatientData;
import com.iiitb.ConsentManagement.ConsentManagement.Services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BasicPatientDataController {

    ActivityService activityService;
    PatientDemographicDetailsRepository patientDemographicDetailsRepository;


    @Autowired
    public BasicPatientDataController(ActivityService activityService,PatientDemographicDetailsRepository patientDemographicDetailsRepository)
    {
        this.activityService = activityService;
        this.patientDemographicDetailsRepository = patientDemographicDetailsRepository;
    }

    @PostMapping(path="/findBasicPatientForNurse")
    public @ResponseBody
    BasicPatientData findBasicPatientDataForNurse(@QueryParam("nurseID") String nurseID)
    {
        System.out.println("[BasicPatientDataController]: Inside findBasicPatientDataForNurse");
        System.out.println("NurseID is: "+nurseID);

        String patientID = activityService.getPatientIDFromActorID(nurseID);
        List<DemographicDetails> demographicDetails = patientDemographicDetailsRepository.findByPatientID(patientID);


        if(demographicDetails.size() == 0)
        {
            System.out.println("Inside demographic details size ==0");
            return null;
        }


        BasicPatientData basicPatientData = new BasicPatientData();
        basicPatientData.setPatientID(demographicDetails.get(0).getPatientID());
        basicPatientData.setAge(demographicDetails.get(0).getAge());
        basicPatientData.setBloodGroup(demographicDetails.get(0).getBloodGroup());
        basicPatientData.setPatientName(demographicDetails.get(0).getFirstName());



        return basicPatientData;
    }
}
