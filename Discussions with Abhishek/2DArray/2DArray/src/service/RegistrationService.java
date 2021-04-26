package service;

import constants.ServiceStages;
import models.Activity;
import models.ActivityType;
import models.Actor;
import models.BasicPatient;
import models.Consent;
import models.Service;
import models.ServiceType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static models.AccessLevel.BASIC;
import static models.ActivityType.REGISTRATION;


public class RegistrationService {

    public void handleBasicPatientData(BasicPatient basicPatient, Actor receptionist, ServiceType serviceType) throws Exception {

        if(receptionist.endTime != null) {
            throw new Exception("Nurse is not in active hours");
        }
        // some more checks to come like operation,consent,Role,purpose

        Service service = new Service();

        service.id = UUID.randomUUID().toString();
        service.serviceType = serviceType;      // how to get service type from application form ?? have a field asking for purpose?
        service.startTime = LocalDateTime.now().toString();
        Activity activity = new Activity();
        activity.id = UUID.randomUUID().toString();
        activity.activityType = REGISTRATION;
        activity.startTime = LocalDateTime.now().toString();
        service.activityList = Arrays.asList(activity);
        service.currentActivity = REGISTRATION;

        //save service
        //save activity

        List<ActivityType> activityTypeList = ServiceStages.map.get(serviceType);

        for(ActivityType activityType : activityTypeList) {
            Consent consent;
            if(activity.activityType.equals(activityType)) {
                consent = new Consent(basicPatient.id, service.id, activityType, receptionist.id, LocalDateTime.now().toString(), BASIC);
            } else {
                consent = new Consent(consentid,basicPatient.id, service.id, activityType, null, null, BASIC);
            }
        }

        activity.endTime = LocalDateTime.now().toString();
    }

}
