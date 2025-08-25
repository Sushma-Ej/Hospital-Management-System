package com.example.HospitalManagementSystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.HospitalManagementSystem.dto.appointmentDto;
import com.example.HospitalManagementSystem.model.appointment;
import com.example.HospitalManagementSystem.service.appointmentService;

@Controller
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private appointmentService service;

    @GetMapping("/myAppointments")
    public String myAppointments(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String patientName = authentication.getName();
        List<appointment> listAppointments = service.findByPatientName(patientName);
        model.addAttribute("listAppointments", listAppointments);
        return "myAppointments";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        appointment appointment = new appointment();
        appointment.setConfirmed("Not yet confirmed");
        appointment.setPrescription("Not prescribed");
        model.addAttribute("appointment", appointment);
        return "add";
    }

    @PostMapping("/add")
    public String createAppointment(@Valid @ModelAttribute("appointment") appointment appointment,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        appointment.setPatientName(auth.getName());
        System.out.println("PatientController: Setting patient name to: " + auth.getName() + " for new appointment.");
        appointment.setConfirmed("Not yet confirmed");
        appointment.setPrescription("Not prescribed");

        service.save(appointment);
        redirectAttributes.addFlashAttribute("message", "Appointment booked successfully!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/patients/myAppointments";
    }

    @GetMapping("/cancelAppointment")
    public String cancelAppointment(Model model) {
        appointment cancelAppointment = new appointment();
        model.addAttribute("appointment", cancelAppointment);
        return "cancelAppointment";
    }

    @GetMapping("/updateAppointment/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        appointment appointment = service.findAppointmentById(id);

        if (!appointment.getPatientName().equals(auth.getName())) {
            return "redirect:/patients/myAppointments";
        }

        model.addAttribute("appointmentDTO", appointment);
        return "updateAppointment";
    }

    @PostMapping("/updateAppointment")
    public String updateAppointment(@ModelAttribute("appointmentDTO") appointmentDto dto,
            RedirectAttributes redirectAttributes) {
        service.update(dto);
        redirectAttributes.addFlashAttribute("message", "Appointment updated successfully!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/patients/myAppointments";
    }
}
