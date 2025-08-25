package com.example.HospitalManagementSystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.HospitalManagementSystem.model.prescription;
import com.example.HospitalManagementSystem.service.PrescriptionService;
import com.example.HospitalManagementSystem.service.appointmentService;

@Controller
@RequestMapping("/patients")
public class PrescriptionController {
    private static final Logger logger = LoggerFactory.getLogger(PrescriptionController.class);

    @Autowired
    private PrescriptionService service;
    
    @Autowired
    private appointmentService service1;
    
    @GetMapping("/viewPrescription")
    public String viewPrescription(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String patientName = auth.getName();
            logger.info("Viewing prescriptions for patient: {}", patientName);
            
            List<prescription> prescriptions = service.findByPatientName(patientName);
            logger.info("Found {} prescriptions for patient: {}", prescriptions.size(), patientName);
            
            model.addAttribute("prescriptions", prescriptions);
            return "viewPrescription";
        } catch (Exception e) {
            logger.error("Error viewing prescriptions", e);
            model.addAttribute("error", "Error loading prescriptions: " + e.getMessage());
            return "viewPrescription";
        }
    }
    
    @PostMapping("/savePrescription")
    public String saveProduct(@ModelAttribute("prescription") prescription prescription,
            BindingResult result, ModelMap model,
            RedirectAttributes redirectAttributes) {
        try {
            logger.info("Saving prescription: {}", prescription);
            Integer appointmentId = prescription.getAppointmentID();
            
            // Retrieve the appointment to get the patientName
            com.example.HospitalManagementSystem.model.appointment appointment = service1.getAppointmentById(appointmentId);
            if (appointment != null) {
                String patientName = appointment.getPatientName();
                logger.info("Found appointment for patient: {}", patientName);
                prescription.setPatientName(patientName);
            } else {
                String errorMsg = "Appointment not found with ID: " + appointmentId;
                logger.error(errorMsg);
                redirectAttributes.addFlashAttribute("message", errorMsg);
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/doctors/doctorAppointments";
            }

            // Set doctor name from authenticated user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String doctorName = auth.getName();
            prescription.setDoctorName(doctorName);
            logger.info("Setting doctor name to: {}", doctorName);

            // Check if a prescription already exists for this appointment
            prescription existingPrescription = service.findByAppointmentID(appointmentId);
            if (existingPrescription != null) {
                // Update existing prescription
                logger.info("Updating existing prescription: {}", existingPrescription);
                existingPrescription.setDescription(prescription.getDescription());
                existingPrescription.setDoctorName(prescription.getDoctorName());
                existingPrescription.setPatientName(prescription.getPatientName());
                service.save(existingPrescription);
                logger.info("Updated prescription: {}", existingPrescription);
            } else {
                // Save new prescription
                logger.info("Creating new prescription");
                service.save(prescription);
                logger.info("Created new prescription: {}", prescription);
            }

            // Mark the appointment as prescribed
            service1.setPrescription("prescribed", appointmentId);
            logger.info("Marked appointment {} as prescribed", appointmentId);

            String message = "Prescription was successfully saved";
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/patients/viewPrescription";
            
        } catch (Exception e) {
            logger.error("Error saving prescription", e);
            redirectAttributes.addFlashAttribute("message", 
                "Error saving prescription: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/patients/viewPrescription";
        }
    }
}
