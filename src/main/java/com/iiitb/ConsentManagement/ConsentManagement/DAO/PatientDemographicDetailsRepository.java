package com.iiitb.ConsentManagement.ConsentManagement.DAO;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.DemographicDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientDemographicDetailsRepository extends JpaRepository<DemographicDetails,Integer> {

List<DemographicDetails> findByPatientID(String patientID);

}
