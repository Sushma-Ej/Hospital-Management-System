package com.example.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HospitalManagementSystem.model.appointment;
import com.example.HospitalManagementSystem.model.doctor;

@Repository
public interface doctorRepository extends JpaRepository<doctor,Long> {
	
}
