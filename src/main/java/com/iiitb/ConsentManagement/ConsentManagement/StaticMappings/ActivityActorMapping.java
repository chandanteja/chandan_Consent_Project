package com.iiitb.ConsentManagement.ConsentManagement.StaticMappings;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ROLE;

import java.util.HashMap;
import java.util.Map;

import static com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType.*;
import static com.iiitb.ConsentManagement.ConsentManagement.Beans.ROLE.*;

/**
 * This class contains the static mappings of Activity to Actor Role mapping.
 * This can be used as lookup for assigning particular actor for particular activity.
 */



public class ActivityActorMapping {

    public static Map<ActivityType, ROLE> map = new HashMap<>();

    static {
        map.put(REGISTRATION, ROLE_RECEPTIONIST);
        map.put(VITAL_PARAMETERS, ROLE_NURSE);
        map.put(DOCTOR_CHECKUP, ROLE_DOCTOR);
        map.put(PHARMACY_VISIT, ROLE_PHARMACIST);
    }
}
