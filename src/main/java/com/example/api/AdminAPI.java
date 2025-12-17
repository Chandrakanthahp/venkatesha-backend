package com.example.api;
import com.example.repository.PatientRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Patients;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "http://localhost:4200")
public class AdminAPI {

	@Autowired
    private PatientRepository patientRepository;
	
	@PutMapping("/patients/{id}/status")
	public Patients updateStatus(@PathVariable Long id, @RequestBody Patients updatedPatient) {
		Patients patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found"));
		patient.setActivated(updatedPatient.isActivated());
	    patient.setDone(updatedPatient.isDone());
	    patient.setOnHold(updatedPatient.isOnHold());
	    return patientRepository.save(patient);
	    
	}
	
	@PutMapping("/patients/reorder")
	public void reorderPatients(@RequestBody List<Patients> patients) {
	    for (Patients p : patients) {
	        patientRepository.updateOrderIndex(p.getId(), p.getOrderIndex());
	    }
	}

	
	@PutMapping("/patients/reset-order")
	public void resetOrderIndex() {
	    patientRepository.resetAllOrderIndex();
	}
	
	@PutMapping("/patients/init-order/{date}")
	@Transactional
	public void initOrderForDate(@PathVariable String date) {

	    LocalDate localDate = LocalDate.parse(date);

	    LocalDateTime start = localDate.atStartOfDay();
	    LocalDateTime end = localDate.atTime(23, 59, 59);

	    // 1️⃣ Reset ALL order_index
	    patientRepository.resetAllOrderIndex();

	    // 2️⃣ Fetch only selected-date patients
	    List<Patients> patients =
	        patientRepository.findByAptAt(localDate);

	    // 3️⃣ Assign order_index by ID order
	    int index = 1;
	    for (Patients p : patients) {
	        p.setOrderIndex(index++);
	    }

	    patientRepository.saveAll(patients);
	}
	
	@DeleteMapping("/patients/bulk-delete")
	@Transactional
	public ResponseEntity<Void> deletePatients(@RequestBody List<Long> ids) {
	    patientRepository.deleteAllById(ids);
	    return ResponseEntity.noContent().build();
	}






}
