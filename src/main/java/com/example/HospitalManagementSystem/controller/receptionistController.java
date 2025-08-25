package com.example.HospitalManagementSystem.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.HospitalManagementSystem.model.Event;
import com.example.HospitalManagementSystem.model.appointment;
import com.example.HospitalManagementSystem.model.invoice;
import com.example.HospitalManagementSystem.repository.eventRepository;
import com.example.HospitalManagementSystem.service.appointmentService;
import com.example.HospitalManagementSystem.service.invoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/receptionist")
public class receptionistController {

	@Autowired
	private appointmentService appointmentService;

	@Autowired
	private eventRepository eventRepository;

	@Autowired
	private invoiceService invoiceService;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/receptionistAppointments")
	public String showReceptionistAppointments(Model model) {
		try {
			List<appointment> listAppointments = appointmentService.getAll();
			model.addAttribute("listAppointments", listAppointments);
			return "receptionistAppointments";
		} catch (Exception e) {
			model.addAttribute("error", "Error loading appointments: " + e.getMessage());
			return "receptionistAppointments";
		}
	}

	@GetMapping("/confirmAppointment/{id}")
	public String confirmAppointment(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		try {
			appointmentService.setConfirmation("confirmed", id);
			redirectAttributes.addFlashAttribute("message", "Appointment confirmed successfully!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Error confirming appointment: " + e.getMessage());
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		return "redirect:/receptionist/receptionistAppointments";
	}

	@GetMapping("/receptionistSchedule")
	public String receptionistSchedule(Model model) {
		try {
			List<Event> events = eventRepository.findAll();
			model.addAttribute("events", events);
			String eventsJson = objectMapper.writeValueAsString(events);
			model.addAttribute("eventsJson", eventsJson);
			return "receptionistSchedule";
		} catch (Exception e) {
			model.addAttribute("error", "Error loading schedule: " + e.getMessage());
			return "receptionistSchedule";
		}
	}

	@RequestMapping("/findbystart")
	public String showBydate(Model model) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		System.out.println(now);
		List<Event> event = eventRepository.findByStart(now);
		model.addAttribute("event", event);
		return "scheduleFindByName";
	}

	@GetMapping("/createInvoice")
	public String createInvoice(Model model) {
		invoice i = new invoice();
		model.addAttribute("invoice", i);
		return "invoice";
	}

	@PostMapping("/saveInvoice")
	public String saveInvoice(@ModelAttribute("invoice") invoice invoice,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		try {
			if (result.hasErrors()) {
				return "invoice";
			}

			// Validate appointment exists
			Optional<appointment> app = appointmentService.get(invoice.getAppointmentID());
			if (!app.isPresent()) {
				redirectAttributes.addFlashAttribute("message", "Invalid appointment ID!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
				return "redirect:/receptionist/createInvoice";
			}

			invoiceService.save(invoice);
			redirectAttributes.addFlashAttribute("message", "Invoice created successfully!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/receptionist/receptionistAppointments";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Error creating invoice: " + e.getMessage());
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/receptionist/createInvoice";
		}
	}
}
