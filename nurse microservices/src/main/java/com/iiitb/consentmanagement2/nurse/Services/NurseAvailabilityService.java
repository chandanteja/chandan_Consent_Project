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
        actorRepository.save(availableActor);
        System.out.println("Changed the status of Nurse to Busy from IDLE");

        // Need to change the status of the actor selected to BUSY.

        return availableActor.getActorID();
    }


}
