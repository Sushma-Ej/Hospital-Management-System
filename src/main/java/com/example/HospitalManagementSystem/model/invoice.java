package com.example.HospitalManagementSystem.model;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="invoice",schema="hospital")
@DynamicUpdate
public class invoice {
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer invoiceID;
	
	@Column
	private String patientName;
	
	@Column
	private Integer appointmentID;
	
	@Column 
	private String invoice;

	public Integer getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(Integer invoiceID) {
		this.invoiceID = invoiceID;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Integer getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(Integer appointmentID) {
		this.appointmentID = appointmentID;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public invoice(Integer invoiceID, String patientName, Integer appointmentID, String invoice) {
		super();
		this.invoiceID = invoiceID;
		this.patientName = patientName;
		this.appointmentID = appointmentID;
		this.invoice = invoice;
	}

	public invoice() {
		
	}
	
	

}
