package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Consent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
public class ActorAssignmentService {


    public ActorAssignmentService()
    {

    }

    @Async("ActorAssignementThreadPool")
    public void assignNurseToPatient(String patientID, Consent nurseConsent)
    {
        System.out.println("[ActorAssignementService-assignNurseToPatient]: Inside assignNurseToPatient method");

        RestTemplate restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder.fromUriString("http://localhost:9094/api/findavailablenurse/").build().toString();
        System.out.println("Before sending postforentity");

        ResponseEntity<String> responseEntity =  restTemplate.postForEntity(uri,null,String.class);
        System.out.println("[ActorAssignmentService]: Inside assignNurseToPatient method. After the postForEntity() call");
        System.out.println("Response from nurse is: "+responseEntity.getBody().toString());
    }
}
