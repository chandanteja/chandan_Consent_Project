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
                /// Receptionist will have access level of advanced to update details of patient.
                // We are doing this as Registration activity will have Update consent to update the details of patient when patient wants to update his details
                consent = new Consent(consentID, patientID, healthServiceID, activityType, null, null, null, ConsentType.UPDATE, AccessLevel.ADVANCED);
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

        System.out.println("Inside updateCOnsent() of consent service ");
        if(consent == null)
        {
            System.out.println("Inside consent == null");
            return consent;
        }


        consent.setActorID(actorID);
        consent.setStartTime(startTime);

        System.out.println("After setting required values");

        try
        {
            consent = consentRepository.save(consent);
            System.out.println("After updating and saving consent obj");
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
        System.out.println("Inside revokeconsent() of consent service ");
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

    public Consent getConsent(String patientID, String actorID,ConsentType consentForOperation, LocalTime endTime)
    {
        System.out.println("[ConsentService]: Inside getConsent()");
        List<Consent> consentObject = null;

        //Same patient can give same actor consent on same operation but start time and endtime will differentiate in such cases.

        consentObject = consentRepository.findByPatientIDAndActorIDAndConsentGivenForOperationAndEndTimeAndStartTimeIsNotNull(patientID,actorID,consentForOperation,endTime);

        if(consentObject.size()==0)
        {
            System.out.println("Inside if consenobject size == 0");
            return null;
        }
        return consentObject.get(0);
    }

    public Consent getConsentNotStarted(String patientID,ActivityType activityType ,ConsentType consentForOperation)
    {
        // This method is for getting consent object which is not yet started.

        System.out.println("[ConsentService]: Inside getConsentNotStarted()");
        System.out.println("Patient ID is: "+ patientID);
        System.out.println("ActivityType is: "+activityType);
        System.out.println("Consent for operation is: "+consentForOperation);

        List<Consent> consentObject = null;

        //Same patient can give same actor consent on same operation but start time and endtime will differentiate in such cases.

        consentObject = consentRepository.findByPatientIDAndActivityTypeAndConsentGivenForOperationAndStartTimeIsNullAndEndTimeIsNull(patientID,activityType,consentForOperation);

        if(consentObject.size()==0)
        {
            System.out.println("Inside if consenobject size == 0");
            return null;
        }
        return consentObject.get(0);
    }

}
