package com.example.HospitalManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HospitalManagementSystem.dto.appointmentDto;
import com.example.HospitalManagementSystem.model.appointment;
import com.example.HospitalManagementSystem.repository.appointmentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class appointmentService {
	
@Autowired

private appointmentRepository repository;
public List<appointment> getAll(){
	return repository.findAll();
}
public void save(appointment appointment) {
	repository.save(appointment);
}
public void delete(Integer id) {
	repository.deleteById(id);
}
public int setConfirmation(String confirmation,Integer id) {
	return repository.setConfirmation(confirmation, id);
			
}
public int setPrescription(String prescription,Integer id) {
	return repository.setPrescription(prescription, id);
}
public Optional<appointment> get(Integer id){
	return repository.findById(id);
}
public List<appointment> findByDoctorName(String doctorName){
	return repository.findByDoctorName(doctorName);
}
public List<appointment> findByPatientName(String patientName){
	List<appointment> foundAppointments = repository.findByPatientName(patientName);
	return foundAppointments;
}
public List<appointment> findByDate(String date,String doctorName) {
	return repository.findByDate(date, doctorName);
}
public List<appointment> findAllAppointments() {

    return repository.findAll();

}
public appointment update(appointmentDto dto) {
	Integer id=dto.getAppointment_id();
appointment app=get(id).get();
	app.setDoctorName(dto.getDoctorName());
	app.setPatientName(dto.getPatientName());
	app.setAppointment_id(dto.getAppointment_id());
	repository.save(app);
	return app;
}
public appointment findAppointmentById(int id) {
	// TODO Auto-generated method stub
	return repository.findById(id).get();
}

public com.example.HospitalManagementSystem.model.appointment getAppointmentById(Integer appointmentId) {
	return repository.findById(appointmentId).orElse(null);
}

}

