package com.example.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HospitalManagementSystem.model.invoice;

@Repository
public interface invoiceRepository extends JpaRepository<invoice,Long>{

}
