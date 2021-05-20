package com.iiitb.ConsentManagement.ConsentManagement.DAO;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Activity;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,String> {

    List<Activity> findByActivityID(String activityID);
    List<Activity> findByHealthServiceIDAndEndTime(String healthServiceID, LocalTime endTime);
    List<Activity> findByPatientIDAndActivityTypeAndEndTime(String patientID, ActivityType activityType, LocalTime endtime);
    List<Activity> findByActorIDAndStartTimeIsNotNullAndEndTime(String actorID, LocalTime endTime);
    List<Activity> findByActorIDAndActivityTypeAndStartTimeIsNotNullAndEndTimeAndPatientID(String actorID,ActivityType activityType,LocalTime endTime,String patientID);

}
