class Consent {
   
    String personId;    //patientID
    String serviceId;
    ActivityType activityType;
    ActorType actorType;
    String actorId;
    String startTime;
    String endTime;
    String accessLevel;
}

new Consent(personId, serviceId, REGISTRATION, RECEPTIONIST, "123", activity.StartTime, null);

new Consent(personId, serviceId, VITAL_PARAMETER, NURSE, "1234", null, null);

new Consent(personId, serviceId, DOCTOR__CONSULTATION, DOCTOR, "12345", null, null);

new Consent(personId, serviceId, DISCHARGE, PHARMA, "123456", null, null);

Registration.save()

Pre Activity :
/*
1. Assign an available actor to this activity.
2. Create an activity object.
3. Save this activity object.
4. Update the service object with the current activity type;
4. Try to raise request for basic details for this personId
5. Check inside consent, if there is available activity Type and actorId = null
    if Yes, update the consent with the actorId and start time of activity and save consent
    else, throw exception
6. Try to get basic details of customer
7. In case of advanced details :
    Try to get consent for advanced patient history,
    Check if there is already consent available for actorid, personId and accessLevel
        Once, patient approves, create a new consent for Advanced details
8. Query patient service to fetch advance details.
9. Update activity with endTime.
9. Update consent for personId and actorId with endTime;
 */


Post Activity :

/*
1. Check if receptionist is in active hours
2. Try to save basic details
3. Update the activity end time.
4. Update the consent end time.
 */
