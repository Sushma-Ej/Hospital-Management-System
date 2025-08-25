package com.example.HospitalManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.model.prescription;
import com.example.HospitalManagementSystem.repository.prescriptionRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class PrescriptionService {
    private static final Logger logger = LoggerFactory.getLogger(PrescriptionService.class);

    @Autowired
    private prescriptionRepository repository;

    public List<prescription> findByPatientName(String patientName) {
        logger.info("Searching for prescriptions for patient: {}", patientName);
        
        // Log all prescriptions in the database for debugging
        List<prescription> allPrescriptions = repository.findAll();
        logger.info("Total prescriptions in database: {}", allPrescriptions.size());
        for (prescription p : allPrescriptions) {
            logger.info("DB Prescription - ID: {}, Patient: '{}', Doctor: '{}', AppointmentID: {}", 
                p.getPrescriptionID(), p.getPatientName(), p.getDoctorName(), p.getAppointmentID());
        }
        
        // Now get the prescriptions for the specific patient
        List<prescription> prescriptions = repository.findByPatientName(patientName);
        logger.info("Found {} prescriptions for patient: {}", prescriptions.size(), patientName);
        
        for (prescription p : prescriptions) {
            logger.info("Prescription - ID: {}, Patient: '{}', Doctor: '{}', AppointmentID: {}, Description: {}", 
                p.getPrescriptionID(), p.getPatientName(), p.getDoctorName(), 
                p.getAppointmentID(), p.getDescription());
        }
        
        return prescriptions;
    }

    public void save(prescription prescription) {
        logger.info("Saving prescription: {}", prescription);
        repository.save(prescription);
        logger.info("Prescription saved successfully");
    }

    public List<prescription> findAll() {
        List<prescription> all = repository.findAll();
        logger.info("Retrieved all {} prescriptions", all.size());
        return all;
    }

    public prescription findByAppointmentID(Integer appointmentID) {
        logger.info("Searching for prescription with appointment ID: {}", appointmentID);
        prescription p = repository.findByAppointmentID(appointmentID);
        if (p != null) {
            logger.info("Found prescription for appointment ID {}: {}", appointmentID, p);
        } else {
            logger.warn("No prescription found for appointment ID: {}", appointmentID);
        }
        return p;
    }
}
