package com.iiitb.ConsentManagement.ConsentManagement.DAO;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.DoctorFormDetails;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.HealthService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorFormDataRepository  extends JpaRepository<DoctorFormDetails,String> {



}
