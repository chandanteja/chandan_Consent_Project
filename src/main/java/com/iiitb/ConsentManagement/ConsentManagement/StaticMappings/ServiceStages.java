package com.iiitb.ConsentManagement.ConsentManagement.StaticMappings;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthServiceType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType.*;

public class ServiceStages {

    public static final Map<HealthServiceType, List<ActivityType>> map = new HashMap<>();

    static {
        map.put(HealthServiceType.OPD, Arrays.asList(REGISTRATION, VITAL_PARAMETERS, DOCTOR_CHECKUP, PHARMACY_VISIT));
        // Doubt: here there is no option to skip an Activity. So, how to do that if any activity is not needed.
    }

}
