package com.iiitb.consentmanagement2.nurse.DAO;


import com.iiitb.consentmanagement2.nurse.Beans.Actor;
import com.iiitb.consentmanagement2.nurse.Beans.ROLE;
import com.iiitb.consentmanagement2.nurse.Beans.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByEmailIDAndPasswordAndRole(String email, String password, ROLE actorRole);

    List<Actor> findByEmailID(String email);

    List<Actor> findByStatusAndRole(Status status,ROLE actorRole );

    List<Actor> findByActorID(String actorID);
}
