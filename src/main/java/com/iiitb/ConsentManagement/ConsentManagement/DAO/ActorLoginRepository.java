package com.iiitb.ConsentManagement.ConsentManagement.DAO;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorLoginRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByEmailIDAndPassword(String email, String password);
    List<Actor> findByEmailID(String email);
}
