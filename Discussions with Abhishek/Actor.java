public class Actor {
    String id;
    ActorType actorType;
    String fullName;
    String fatherName;
    String dob;
    String address;
    String emailId;
    String contact;
    String startTime;           //duty start time
    String endTime;             // duty end time
}

enum ActorType {
    NURSE,
    RECEPTIONIST,
    DOCTOR,
    PHARMA
}