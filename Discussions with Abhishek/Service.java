import java.util.List;

public class Service {
    String id;
    ServiceType serviceType;
    ActivityType currentActivity;       // to track current activity in progress
    List<Activity> activityList;  // each service will have a set of activities associated with it. SO, this list will maintain it.
    String startTime;           // start and end time of the service.
    String endTime;
}

enum ServiceType {
    OPD,
    SURGERY
}
