package service;

import models.Activity;
import models.ActivityType;
import models.Actor;
import models.BasicPatient;
import models.Consent;
import models.Service;
import models.ServiceType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VitalParameterService {

    public static ActivityType activityType = ActivityType.VITAL_PARAMETERS;

    public void handleVitalParameters(BasicPatient basicPatient, Actor nurse, ServiceType serviceType) throws Exception {

        // fetch consent from repository
        Consent consent = null;

        if(consent.actorId == null) {
            // Fetch available actor for the actorType from repo
            consent.actorId = nurse.id;
            consent.startTime = LocalDateTime.now().toString();
            //save consent
        } else {
            return;
        }

        // fetch consent for the actorId and patientId

        if(consent != null) {
            Activity activity = new Activity();
            activity.id = UUID.randomUUID().toString();
            activity.activityType = activityType;
            activity.startTime = LocalDateTime.now().toString();

            //fetch Service from serviceTable
            Service service = new Service();

            service.currentActivity = activityType;
            service.activityList.add(activity);

            // save/update service

            activity.endTime = LocalDateTime.now().toString();

            //save activity;
        }

    }

}
