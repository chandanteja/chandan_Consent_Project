package constants;

import models.ActivityType;
import models.ServiceType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.ActivityType.*;

public class ServiceStages {

    public static final Map<ServiceType, List<ActivityType>> map = new HashMap<>();

    static {
        map.put(ServiceType.OPD, Arrays.asList(REGISTRATION, VITAL_PARAMETERS, DOCTOR_CONSULTAION, DISCHARGE));
    }

}
