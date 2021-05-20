package com.iiitb.consentmanagement2.nurse.Services;

import com.iiitb.consentmanagement2.nurse.Beans.Actor;
import com.iiitb.consentmanagement2.nurse.Beans.ROLE;
import com.iiitb.consentmanagement2.nurse.Beans.Status;
import com.iiitb.consentmanagement2.nurse.DAO.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import javax.inject.Named;
import java.util.List;
import java.util.Random;

@Named
public class NurseAvailabilityService {

    ActorRepository actorRepository;

    @Autowired
    public NurseAvailabilityService(ActorRepository actorRepository)
    {

                this.actorRepository = actorRepository;

    }

    public String getAvailableNurse(Status actorStatus, ROLE actorRole)
    {
        List<Actor> actor = null;
        Actor availableActor = null;
        Random randomNumber = new Random();

        System.out.println("[NurseAvailabilityService]: Inside getAvailableNurse");
        actor = actorRepository.findByStatusAndRole(actorStatus,actorRole);

        if(actor.size()==0)
                return "FAILED_TO_FIND_NURSE";

        int result = randomNumber.nextInt((actor.size()) ) + 0 ;

        availableActor = actor.get(result);
        System.out.println("[NurseAvailabilityService]: After finding an actor randomly by choosing random number. Returning now from nurseservice");

        availableActor.setStatus(Status.BUSY);

        try
        {
            actorRepository.save(availableActor);
        }
        catch(Exception e)
        {
            System.out.println("[Exception]: Exception occurred when saving nurse data");
            return "FAILED_TO_UPDATE_NURSE_STATUS";
        }
        System.out.println("Changed the status of Nurse to Busy from IDLE");

        // Need to change the status of the actor selected to BUSY.

        return availableActor.getActorID();
    }

    public String setNurseStatus(String actorID, Status status)
    {
        System.out.println("[NurseAvailabilityService]: Inside setNurseStatus()");
        List<Actor> actor = null;

        actor = actorRepository.findByActorID(actorID);

        if(actor.size() == 0)
        {
            System.out.println("Inside actor size ==0 in set status");
            return "FAILED_TO_CHANGE_STATUS";
        }

        Actor temp;
         actor.get(0).setStatus(status);

        System.out.println("After setting actor status : "+ actor.get(0).getStatus());

        try{
            temp = actorRepository.save(actor.get(0));
        }
        catch(Exception e)
        {
            System.out.println("[Exception]: Exception occurred while setting actor status and aving");
            return "FAILED_TO_CHANGE_STATUS";
        }
        return  "SUCCESS";
    }


}
