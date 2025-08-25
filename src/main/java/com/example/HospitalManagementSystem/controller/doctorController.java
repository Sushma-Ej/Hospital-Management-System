package com.example.HospitalManagementSystem.controller;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.HospitalManagementSystem.model.appointment;
import com.example.HospitalManagementSystem.service.doctorService;
import com.example.HospitalManagementSystem.model.Event;
import com.example.HospitalManagementSystem.repository.eventRepository;
import com.example.HospitalManagementSystem.service.PrescriptionService; // Added import for PrescriptionService
import com.example.HospitalManagementSystem.model.prescription; // Added import for prescription model
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/doctors")
public class doctorController {
	@Autowired
	private doctorService service;

	@Autowired
	private eventRepository eventRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PrescriptionService prescriptionService; // Injected PrescriptionService

	@GetMapping("/doctorAppointments")
	public String showDoctorAppointments(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String doctorName = authentication.getName();
		List<appointment> doctorAppointments = service.findByDoctorName(doctorName);
		for (appointment appt : doctorAppointments) {
            System.out.println("Appointment ID: " + appt.getAppointment_id() + ", Prescription: " + appt.getPrescription());
        }
		model.addAttribute("doctorAppointments", doctorAppointments);
		return "doctorAppointments";
	}

	@GetMapping("/schedule")
	public String showSchedule(Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String doctorName = authentication.getName();

			List<Event> events = eventRepository.findByName(doctorName);
			if (events == null) {
				events = new ArrayList<>();
			}
			model.addAttribute("events", events);
			String eventsJson = objectMapper.writeValueAsString(events);
			model.addAttribute("eventsJson", eventsJson);
			return "doctorSchedule";
		} catch (Exception e) {
			model.addAttribute("error", "Error loading schedule: " + e.getMessage());
			return "doctorSchedule";
		}
	}

	@PostMapping("/saveEvent")
    public String saveEvent(@ModelAttribute Event event, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        event.setName(authentication.getName());
        eventRepository.save(event);
        redirectAttributes.addFlashAttribute("message", "Event saved successfully!");
        return "redirect:/doctors/schedule";
    }

	@GetMapping("/createPrescription")
	public String createPrescription(@RequestParam("appointmentId") Integer appointmentId, Model model) {
		model.addAttribute("appointmentId", appointmentId);
		return "createPrescription";
	}

	@PostMapping("/savePrescription")
	public String savePrescription(@RequestParam("appointmentId") Integer appointmentId, 
                                  @RequestParam("prescriptionText") String prescriptionText, 
                                  RedirectAttributes redirectAttributes) {
		System.out.println("Received appointmentId: " + appointmentId + ", prescriptionText: " + prescriptionText);

        appointment appt = service.getAppointmentById(appointmentId);
        if (appt != null) {
            System.out.println("Appointment before update: " + appt);
            appt.setPrescription(prescriptionText);
            appt.setConfirmed("prescribed"); // Mark as prescribed
            System.out.println("Appointment after setting prescription and confirmed: " + appt);
            service.saveAppointment(appt); // Save changes to the appointment entity
            System.out.println("Appointment saved. Now retrieving to verify...");
            appointment updatedAppt = service.getAppointmentById(appointmentId);
            System.out.println("Appointment after save and re-retrieve: " + updatedAppt);

            // --- Start: Update/Create prescription entity ---
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String doctorName = authentication.getName();
            String patientName = appt.getPatientName(); // Get patient name from the appointment

            prescription pres = prescriptionService.findByAppointmentID(appointmentId);

            if (pres != null) {
                // Update existing prescription entity
                pres.setDescription(prescriptionText);
                pres.setDoctorName(doctorName);
                pres.setPatientName(patientName);
                prescriptionService.save(pres);
                System.out.println("Updated existing prescription entity: " + pres);
            } else {
                // Create new prescription entity
                prescription newPrescription = new prescription();
                newPrescription.setAppointmentID(appointmentId);
                newPrescription.setDescription(prescriptionText);
                newPrescription.setDoctorName(doctorName);
                newPrescription.setPatientName(patientName);
                prescriptionService.save(newPrescription);
                System.out.println("Created new prescription entity: " + newPrescription);
            }
            // --- End: Update/Create prescription entity ---

            redirectAttributes.addFlashAttribute("message", "Prescription saved successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Appointment not found.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
		return "redirect:/doctors/doctorAppointments";
	}

}
