public class Activity {
    String id;
    ActivityType activityType;
    String startTime;
    String endTime;
}

enum ActivityType {
    REGISTRATION,
    VITAL_PARAMETERS,
    DOCTOR_CONSULTAION,
    DISCHARGE
}