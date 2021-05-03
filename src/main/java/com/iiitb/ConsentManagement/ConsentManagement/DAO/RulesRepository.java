package com.iiitb.ConsentManagement.ConsentManagement.DAO;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.Actor;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.MethodType;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RulesRepository  extends JpaRepository<Rules, Integer>  {


    List<Rules> findByMethodTypeAndTableName(MethodType methodType,String tableName );

}
