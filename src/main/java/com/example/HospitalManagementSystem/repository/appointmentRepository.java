package com.example.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HospitalManagementSystem.model.appointment;

@Repository
public interface appointmentRepository extends JpaRepository<appointment,Integer> {

    List<appointment> findAll();

    List<appointment> findByDoctorName(String doctorName);

    List<appointment> findByPatientName(String patientName);
  


    @Modifying
    @Query("update appointment a set a.confirmed = ?1 where a.appointment_id = ?2")
    int setConfirmation(String confirmation, Integer id);

    @Modifying 
    @Query("update appointment a set a.prescription = ?1 where a.appointment_id = ?2")
    int setPrescription(String prescription, Integer id);

    @Query(value="SELECT * FROM appointment a WHERE a.appointment_date = ?1 AND a.doctor_name = ?2", nativeQuery=true)
    List<appointment> findByDate(String date, String doctorName);
}
