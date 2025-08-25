package com.example.HospitalManagementSystem.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="event")
public class Event {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String title;
	private String description;
	private LocalDateTime start;
	private LocalDateTime finish;
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", title=" + title + ", description=" + description + ", start="
				+ start + ", finish=" + finish + "]";
	}
	public Event() {
		super();
	
	}

	public Event(Long id, String name, String title, String description, LocalDateTime start, LocalDateTime finish) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.description = description;
		this.start = start;
		this.finish = finish;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getFinish() {
		return finish;
	}
	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

}
