package com.iiitb.ConsentManagement.ConsentManagement.DAO;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.VitalParams;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VitalParametersRepository extends JpaRepository<VitalParams,String> {
}
