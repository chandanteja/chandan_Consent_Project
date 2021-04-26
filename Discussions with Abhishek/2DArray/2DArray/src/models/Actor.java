package models;

import java.time.LocalDateTime;

public class Actor {
    public String id;
    public ActorType actorType;
    public String fullName;
    String fatherName;
    String dob;
    String address;
    String emailId;
    String contact;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public boolean activeBusy;  // to track whether actor is active or busy

}
