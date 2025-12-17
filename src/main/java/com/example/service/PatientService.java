package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.PatientsDTO;
import com.example.entity.Patients;
import com.example.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private EmailService emailService;
	
	public void savePatient(PatientsDTO dto) {
		Patients patient=new Patients();
		patient.setName(dto.getName());
		patient.setEmail(dto.getEmail());
		patient.setPhone(dto.getPhone());
		patient.setAptAt(dto.getAptAt());
		patient.setBirthDate(dto.getBirthDate());
		
//		if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
//            emailService.sendRegistrationMail(
//                dto.getEmail(),
//                dto.getName()
//            );
//        }
		
		patientRepository.save(patient);
		
		try {
		    emailService.sendRegistrationMail(dto.getEmail(), dto.getName(), dto);
		} catch (Exception e) {
		    throw new RuntimeException("Email failed", e);
		}
	}

}
