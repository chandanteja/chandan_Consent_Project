package com.iiitb.ConsentManagement.ConsentManagement.Services;

import com.iiitb.ConsentManagement.ConsentManagement.Beans.DoctorFormDetails;
import com.iiitb.ConsentManagement.ConsentManagement.Beans.VitalParams;
import com.iiitb.ConsentManagement.ConsentManagement.DAO.DoctorFormDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Named
public class DoctorFormDetailsService {

    DoctorFormDataRepository doctorFormDataRepository;

    @Autowired
    public  DoctorFormDetailsService( DoctorFormDataRepository doctorFormDataRepository)
    {
        this.doctorFormDataRepository =doctorFormDataRepository;
    }



    public DoctorFormDetails saveDoctorFormData(DoctorFormDetails doctorFormDetails)
    {
        System.out.println("[DoctorFormDetailsService] Inside saveDoctorFormData()");

        try
        {
            doctorFormDetails = doctorFormDataRepository.save(doctorFormDetails);
            System.out.println("After saving doctor form data");
        }
        catch(Exception e)
        {
            System.out.println("[Exception]: Exception occuerd when trying to save Doctor FormData data");
            return null;
        }
        return doctorFormDetails;
    }
}
