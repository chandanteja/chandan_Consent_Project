package com.iiitb.ConsentManagement.ConsentManagement.StaticMappings;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;

import java.util.HashMap;
import java.util.Map;

import static com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType.*;
import static com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType.DOCTOR_CHECKUP;

public class NextActivityMapping {

    public static final Map<ActivityType, ActivityType> nextActivity = new HashMap<>();

    static {
        nextActivity.put(REGISTRATION,VITAL_PARAMETERS);
        nextActivity.put(VITAL_PARAMETERS,DOCTOR_CHECKUP);
        nextActivity.put(DOCTOR_CHECKUP,PHARMACY_VISIT);
        nextActivity.put(PHARMACY_VISIT,null);

    }
}
