package constants;

import models.ActivityType;
import models.ActorType;

import java.util.HashMap;
import java.util.Map;

import static models.ActivityType.*;
import static models.ActorType.*;

public class ActivityActorMapping {

    public static Map<ActivityType, ActorType> map = new HashMap<>();

    static {
        map.put(REGISTRATION, RECEPTIONIST);
        map.put(VITAL_PARAMETERS, NURSE);
        map.put(DOCTOR_CONSULTAION, DOCTOR);
        map.put(DISCHARGE, PHARMA);
    }

}
