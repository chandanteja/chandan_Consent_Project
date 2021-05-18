package com.iiitb.ConsentManagement.ConsentManagement.ConsentManager;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Consent;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.ConsentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ConsentRepository extends JpaRepository<Consent,String>
{
   List<Consent> findByPatientIDAndActorIDAndConsentGivenForOperationAndEndTimeAndStartTimeIsNotNull(String patientID, String actorID,ConsentType consentGivenForOperation, LocalTime endTime);
}
