package com.example.HospitalManagementSystem.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.model.appointment;
import com.example.HospitalManagementSystem.model.doctor;
import com.example.HospitalManagementSystem.repository.appointmentRepository;
import com.example.HospitalManagementSystem.repository.doctorRepository;

@Service
public class doctorService {
	@Autowired
	private doctorRepository repository;
	@Autowired
	private appointmentRepository repo;

	public List<appointment> findByDoctorName(String doctorName) {
		try {
			List<appointment> appointments = repo.findByDoctorName(doctorName);
			return appointments != null ? appointments : new ArrayList<>();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public void save(doctor doc) {
		repository.save(doc);
	}

	public List<appointment> findAll() {
		return repo.findAll();
	}

	public List<appointment> findByDate(String date, String doctorName) {
		try {
			List<appointment> appointments = repo.findByDate(date, doctorName);
			return appointments != null ? appointments : new ArrayList<>();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public appointment getAppointmentById(Integer appointmentId) {
        return repo.findById(appointmentId).orElse(null);
    }

    public void saveAppointment(appointment appt) {
        repo.save(appt);
    }
}
