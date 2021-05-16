package com.iiitb.ConsentManagement.ConsentManagement.ConsentManager;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentRepository extends JpaRepository<Consent,String> {

}
