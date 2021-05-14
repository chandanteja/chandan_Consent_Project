package com.iiitb.consentmanagement1.receptionist.DAO;

import com.iiitb.consentmanagement1.receptionist.Beans.Actor;
import com.iiitb.consentmanagement1.receptionist.Beans.ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByEmailIDAndPasswordAndRole(String email, String password, ROLE actorRole);
    List<Actor> findByEmailID(String email);
}
