package com.example.HospitalManagementSystem.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

import com.example.HospitalManagementSystem.model.appointment;
import com.example.HospitalManagementSystem.model.invoice;
import com.example.HospitalManagementSystem.model.prescription;
import com.example.HospitalManagementSystem.service.appointmentService;

@Controller
public class HomeController {
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	@GetMapping({ "/", "/main" })
	public String showMain() {
		return "main";
	}

	@GetMapping("/patients")
	public String showPatient(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		// String confirmation ="Your appointment has been successfully booked. ID=";
		// model.addAttribute("confirmation",confirmation);
		String id = (String) model.asMap().get("appointmentId");
		model.addAttribute("appointmentId", id);
		return "patients";
	}

	@GetMapping("/doctors")
	public String showDoctors(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "doctors";
	}

	@GetMapping("/showPostLogin")
	public String showPostLogin() {

		return "postlogin";
	}

	@GetMapping("/receptionist")
	public String showReceptionist(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);

		return "receptionist";
	}

	@Autowired
	private appointmentService service;

	@GetMapping("/add")
	public String newAppointment(Model model) {
		appointment appointment = new appointment();
		appointment.setConfirmed("Not yet confirmed");
		model.addAttribute("appointment", appointment);
		return "add.html";
	}

	@PostMapping("/save")
	public String saveAppointment(@ModelAttribute("appointment") appointment appointment,
			BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		appointment.setPatientName(authentication.getName()); // Set patient name from authenticated user
		appointment.setConfirmed("Not yet confirmed");
		service.save(appointment);
		String appointmentId = appointment.getAppointment_id().toString();
		String message = "Appointment was successfully booked, your id is: " + appointmentId;
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		redirectAttributes.addFlashAttribute("appointmentId", appointmentId);
		return "redirect:/patients";

	}

	@GetMapping("/cancel")
	public String cancel(@ModelAttribute("appointment") appointment appointment,
			BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		Integer id = appointment.getAppointment_id();
		service.delete(id);
		String message = "Appointment was successfully canceled!";
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/patients";

	}

	@RequestMapping("/confirm")
	public String confirm(@ModelAttribute("appointment") appointment appointment, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		
		String confirmation = "confirmed";
		Integer id = appointment.getAppointment_id();
		service.setConfirmation(confirmation, id);
		String message = "Appointment was successfully confirmed!";
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/receptionist/receptionistAppointments";

	}

	@GetMapping("/confirmm")
	public String showConfirmm(Model model) {
		appointment confirmation = new appointment();
		model.addAttribute("confirmation", confirmation);
		return "confirm";
	}

	@GetMapping("/sush")
	public String createPrescription(Model model) {
		prescription ps = new prescription();
		model.addAttribute("prescription", ps);
		return "sush";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/signupp")
	public String signupp(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Account created successfully");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/";
	}

	@Autowired
	public com.example.HospitalManagementSystem.service.invoiceService invoiceService;

	@GetMapping("/createInvoice")
	public String createInvoice(Model model) {
		invoice i = new invoice();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		i.setPatientName(auth.getName());
		model.addAttribute("invoice", i);
		return "invoice";
	}

	@PostMapping("/saveInvoice")
	public String saveInvoice(@ModelAttribute("invoice") invoice invoice,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "invoice";
		}

		try {
			invoiceService.save(invoice);
			redirectAttributes.addFlashAttribute("message", "Invoice created successfully!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/patients";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Error creating invoice: " + e.getMessage());
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/createInvoice";
		}
	}

}
