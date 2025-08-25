package com.example.HospitalManagementSystem.dto;

import jakarta.persistence.Entity;

public class appointmentDto {
	private Integer appointment_id;
	//private String patientName;
	private String patientName;
	private String doctorName;
	private String date;
	private String prescription;
	@Override
	public String toString() {
		return "appointmentDto [appointment_id=" + appointment_id + ", patientName=" + patientName + ", doctorName="
				+ doctorName + ", date=" + date + ", prescription=" + prescription + "]";
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
//	@Override
//	public String toString() {
//		return "appointmentDto [appointment_id=" + appointment_id + ", patientName=" + patientName + ", doctorName="
//				+ doctorName + ", date=" + date + "]";
//	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public appointmentDto() {
		
	}

	public appointmentDto(Integer appointment_id, String patientName, String doctorName,String date) {
		super();
		this.appointment_id = appointment_id;
		this.patientName = patientName;
		this.doctorName = doctorName;
		this.date=date;
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
	
	

}
