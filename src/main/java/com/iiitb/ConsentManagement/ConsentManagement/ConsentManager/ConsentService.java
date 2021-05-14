package com.iiitb.ConsentManagement.ConsentManagement.ConsentManager;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.*;


import javax.inject.Named;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
public class ConsentService {


    public List<Consent> createConsentObjects(String patientID, String healthServiceID, String actorID,List<ActivityType> activityTypesList, ConsentType consentGivenForOpeatrion, AccessLevel accessLevel)
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
//            if(activityType.equals(ActivityType.REGISTRATION))
//            {
//                // This consent Object is for Registration. So,we can create access level as ADVANCED but, we need to hardcode it.
//                consentID = UUID.randomUUID().toString();
//                consent = new Consent(consentID,patientID,healthServiceID,activityType,actorID, LocalTime.now(),null,consentGivenForOpeatrion,AccessLevel.ADVANCED);
//            }
//            else
//            {
//
//            }
            if(activityType.equals(ActivityType.REGISTRATION))
                    continue;   // We are skipping creation of consent object for Registration activity.

            consentID = UUID.randomUUID().toString();
            consent = new Consent(consentID,patientID,healthServiceID,activityType,null, null,null,consentGivenForOpeatrion,accessLevel);
            consentObjectList.add(consent);

        }

        System.out.println("Before returning from consentservice");

        return consentObjectList;
    }
}
