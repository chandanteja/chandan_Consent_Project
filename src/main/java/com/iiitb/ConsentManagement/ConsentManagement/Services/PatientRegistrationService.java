package com.iiitb.ConsentManagement.ConsentManagement.Services;


import com.iiitb.ConsentManagement.ConsentManagement.Beans.DemographicDetails;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthServiceType;
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

   public DemographicDetails savePatientDemographicDetails(DemographicDetails demoDetails, String patientID)
   {
       System.out.println("[PatientRegistrationService-savePatientDetails()]: Came-here");

       System.out.println("Firstname: "+demoDetails.getFirstName());
       System.out.println("Email: "+demoDetails.getEmail());
       System.out.println("Consent: "+demoDetails.getConsent());
       System.out.println("Purpose: "+ HealthServiceType.valueOf(demoDetails.getPurpose()));


// Set the purpose and other fields.
       demo.setFirstName(demoDetails.getFirstName());
       demo.setLastName(demoDetails.getLastName());
       demo.setEmail(demoDetails.getEmail());
       demo.setPhoneNumber(demoDetails.getPhoneNumber());
       demo.setAddress(demoDetails.getAddress());
       demo.setAge(demoDetails.getAge());
       demo.setBloodGroup(demoDetails.getBloodGroup());
       demo.setGender(demoDetails.getGender());
       demo.setConsent(demoDetails.getConsent());
       demo.setPurpose(demoDetails.getPurpose().toUpperCase());
       demo.setPatientID(patientID);

       try
       {
           demo = patientDemoDetailsRepo.save(demo);
       }
       catch(Exception e)
       {
           System.out.println("[EXCEPTION]: Exception occurred while saving patient Data");
           return null;
       }



       return demo;
   }
}
