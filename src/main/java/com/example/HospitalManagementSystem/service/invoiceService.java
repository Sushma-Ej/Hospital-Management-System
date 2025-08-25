package com.example.HospitalManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HospitalManagementSystem.model.invoice;
import com.example.HospitalManagementSystem.repository.invoiceRepository;



@Service
@Transactional
public class invoiceService {
	

		@Autowired
		private invoiceRepository repository;
		
		
		public void save(invoice entity) {
			repository.save(entity);
		}
		
		public List<invoice> view(){
			return repository.findAll();
		}
	}


