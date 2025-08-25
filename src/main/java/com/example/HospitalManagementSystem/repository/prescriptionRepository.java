package com.example.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HospitalManagementSystem.model.prescription;

@Repository
public interface prescriptionRepository extends JpaRepository<prescription,Long> {
    @Query("SELECT p FROM prescription p WHERE LOWER(p.patientName) = LOWER(:patientName)")
    public List<prescription> findByPatientName(@Param("patientName") String patientName);
    
    public List<prescription> findAll();
    
    public prescription findByAppointmentID(Integer appointmentID);
}
