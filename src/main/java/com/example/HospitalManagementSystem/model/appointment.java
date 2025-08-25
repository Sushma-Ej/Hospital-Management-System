package com.example.HospitalManagementSystem.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="appointment",schema="hospital")
@DynamicUpdate
public class appointment {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="appointment_id")
	private Integer appointment_id;
	@Column(name="patient_Name")
	private String patientName;
	@Column(name="doctor_name")
	private String doctorName;
	@Column(name="disease")
	private String disease;
	@Column(name="appointment_date")
	private LocalDate date;
	@Column(name="prescription")
	private String prescription;
	@Column(name="confirmed")
	private String confirmed;
public appointment() {
		
	}
	@Override
	public String toString() {
		return "appointment [appointment_id=" + appointment_id + ", patientName=" + patientName + ", doctorName="
				+ doctorName + ", disease=" + disease + ", date=" + date + ", prescription=" + prescription + ", confirmed=" + confirmed + "]";
	}
	public appointment(Integer appointment_id, String patientName, String doctorName, String disease, LocalDate date, String prescription,
			String confirmed) {
		super();
		this.appointment_id = appointment_id;
		this.patientName = patientName;
		this.doctorName = doctorName;
		this.disease = disease;
		this.date = date;
		this.prescription = prescription;
		this.confirmed = confirmed;
	}
	public Integer getAppointment_id() {
		return appointment_id;
	}
	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	
	

}
