package com.iiitb.ConsentManagement.ConsentManagement.ConsentManager;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;
import org.springframework.beans.factory.annotation.Autowired;


import javax.inject.Named;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
public class ConsentService {

    ConsentRepository consentRepository;

    @Autowired
    public ConsentService(ConsentRepository consentRepository)
    {
        this.consentRepository = consentRepository;
    }


    public List<Consent> createConsentObjectsList(String patientID, String healthServiceID, String actorID,List<ActivityType> activityTypesList, ConsentType consentGivenForOpeatrion, AccessLevel accessLevel)
    {
        System.out.println("[ConsentService-createConsent]: Inside createConsent");

        List<Consent> consentObjectList = new ArrayList<>();
        String consentID = null;


        System.out.println("ActivitypeList is:");
        for (ActivityType activityType : activityTypesList)
        {
            System.out.println(activityType);
        }
        System.out.println("PatientID: "+patientID);
        System.out.println("HealthServiceID: "+healthServiceID);
        System.out.println("Consentgiven For operation: "+consentGivenForOpeatrion);
        System.out.println("Access Level: "+accessLevel);
        System.out.println("===============================");

        for (ActivityType activityType : activityTypesList)
        {
            System.out.println("Inside for-each loop of consentService");
            Consent consent;

            consentID = UUID.randomUUID().toString();


            if( activityType.equals(ActivityType.REGISTRATION) )      // Registration activity-type
            {
                // We are doing this as Registration activity will have Update consent to update the details of patient when patient wants to update his details
                consent = new Consent(consentID, patientID, healthServiceID, activityType, null, null, null, ConsentType.UPDATE, accessLevel);
                consentObjectList.add(consent);
            }
            else if( activityType.equals(ActivityType.PHARMACY_VISIT) ) // If  activity is pharmacy visit
            {       // for pharmacist only view is given
                consent = new Consent(consentID, patientID, healthServiceID, activityType, null, null, null, ConsentType.VIEW, accessLevel);
                consentObjectList.add(consent);
            }
           else
            {

                consent = new Consent(consentID, patientID, healthServiceID, activityType, null, null, null, consentGivenForOpeatrion, accessLevel);
                consentObjectList.add(consent);
            }


        }

        int temp=0;

        System.out.println("Saving consent objects in consent Service.");
        while(consentObjectList.size() != temp)
        {
            consentRepository.save(consentObjectList.get(temp));
            temp++;
        }


        System.out.println("Before returning from consentservice");

        return consentObjectList;
    }

    public Consent updateConsent(Consent consent,String actorID, LocalTime startTime)
    {
        // set the actorID and start time fields in the consent Object
        // save the consent obj.
        // This method is called when an Actor is assigned to patient.

        if(consent == null)
        {
            return consent;
        }


        consent.setActorID(actorID);
        consent.setStartTime(startTime);


        try
        {
            consent = consentRepository.save(consent);
        }
        catch(Exception e)
        {
            System.out.println("[Exception]: Exception occuered while saving the consent afer updating it. Inside updateConsent()");
            return null;    // failed to save consent Obj
        }

        return consent;   // CHange this later if needed
    }

    public String revokeConsent(Consent consent)
    {
        // take current time at which this method is called
        // change the end time
        // save the object
        if(consent == null)
        {
            return "FAILED_TO_REVOKE_CONSENT";
        }

        LocalTime endTime = LocalTime.now();
        consent.setEndTime(endTime);

        try {
           consent = consentRepository.save(consent);
        }
        catch(Exception e)
        {
            System.out.println("[Exception]: Exception occuerred while saving the consent after revoking it. Inside revokeCOnsent()");
            return "FAILED_TO_REVOKE_CONSENT";
        }

        return "SUCCESS";
    }


//    public String createConsentObject(String patientID,String healthServiceID, String actorID,ActivityType activityType,LocalTime startTime, LocalTime endTime,ConsentType consentForOperation,AccessLevel accessLevel)
//    {
//        // requires all fields in the consent object.
//
//        return "SUCCESS";
//    }


}
