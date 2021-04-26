package com.iiitb.ConsentManagement.ConsentManagement.Services;


import com.iiitb.ConsentManagement.ConsentManagement.Beans.DemographicDetails;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.PatientDemographicDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Named
public class PatientRegistrationService {
   PatientDemographicDetailsRepository patientDemoDetailsRepo;
   DemographicDetails demo ;

   @Autowired
   public PatientRegistrationService(PatientDemographicDetailsRepository patientDemoDetailsRepo, DemographicDetails demo)
   {
       this.patientDemoDetailsRepo = patientDemoDetailsRepo;
       this.demo = demo;
   }

   public boolean addPatientDemographicDetails(DemographicDetails demoDetails)
   {
       demo.setFirstName(demoDetails.getFirstName());
       demo.setLastName(demoDetails.getLastName());
       demo.setEmail(demoDetails.getEmail());
       demo.setPhoneNumber(demoDetails.getPhoneNumber());
       demo.setAddress(demoDetails.getAddress());
       demo.setAge(demoDetails.getAge());
       demo.setBloodGroup(demoDetails.getBloodGroup());
       demo.setGender(demoDetails.getGender());
       demo.setConsent(demoDetails.getConsent());

      // demo = patientDemoDetailsRepo.save(demo);

       if(demo == null)
             return false;

       return true;
   }
}
