package com.iiitb.ConsentManagement.ConsentManagement.DAO;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,String> {

    List<Activity> findByActivityID(String activityID);
    List<Activity> findByHealthServiceIDAndEndTime(String healthServiceID, LocalTime endTime);

}
