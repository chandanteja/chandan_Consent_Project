package com.iiitb.ConsentManagement.ConsentManagement.StaticMappings;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthServiceType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType.*;
import static com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType.PHARMACY_VISIT;

public class PreviousActivityMapping {
    public static final Map<ActivityType, ActivityType> previousActivity = new HashMap<>();

    static {
        previousActivity.put(REGISTRATION,null);
        previousActivity.put(VITAL_PARAMETERS,REGISTRATION);
        previousActivity.put(DOCTOR_CHECKUP,VITAL_PARAMETERS);
        previousActivity.put(PHARMACY_VISIT,DOCTOR_CHECKUP);

    }
}
