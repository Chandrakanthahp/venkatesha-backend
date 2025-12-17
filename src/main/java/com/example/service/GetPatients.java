package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Patients;
import com.example.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class GetPatients {
		
	@Autowired
	private PatientRepository patientRepository;
	
	public List<Patients> getAllPatients(){
		return patientRepository.findAllByOrderByOrderIndexAsc();
	}
	
	@Transactional
	public List<Patients> handleQueueByDate(LocalDate filterDate) {

	    // CASE 1: No date selected
//	    if (filterDate == null) {
//	        patientRepository.resetAllOrderIndex();
	        return patientRepository.findAll();
//	    }

	    // CASE 2: Date selected

	    // Step 1: Clear order_index for all OTHER dates
//	    patientRepository.clearOrderIndexForOtherDates(filterDate);

	    // Step 2: Get patients of selected date (ordered by ID initially)
//	    LocalDateTime start = filterDate.atStartOfDay();
//	    LocalDateTime end = filterDate.atTime(23, 59, 59);

//	    List<Patients> todaysPatients =
//	        patientRepository.findByCreatedAtBetweenOrderByIdAsc(start, end);

	    // Step 3: Assign order_index ONLY to these patients
//	    int index = 1;
//	    for (Patients p : todaysPatients) {
//	        p.setOrderIndex(index++);
//	    }

//	    patientRepository.saveAll(todaysPatients);

//	    return todaysPatients;
	}


}
