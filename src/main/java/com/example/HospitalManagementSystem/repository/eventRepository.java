package com.example.HospitalManagementSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.HospitalManagementSystem.model.Event;

@Repository
public interface eventRepository extends JpaRepository<Event,Long>{

	public List<Event> findByStartGreaterThanEqualAndFinishLessThanEqual(LocalDateTime start, LocalDateTime end);
		public List<Event> findByName(String name);
		public List<Event> findByStart(LocalDateTime start);
		@Query("select b from Event b where b.start >= ?1 and b.finish <= ?2")
		public List<Event> findByDateBetween(LocalDateTime start, LocalDateTime end);
	

}
