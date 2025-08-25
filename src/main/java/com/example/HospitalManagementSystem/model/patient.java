package com.example.HospitalManagementSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="patient",schema="hospital")
public class patient {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	public String patientName;
	@OneToOne
	private doctor doctorName;
	@OneToOne
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public doctor getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(doctor doctorName) {
		this.doctorName = doctorName;
	}

}
