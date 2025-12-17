package com.example.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PatientsDTO;
import com.example.service.PatientService;

@RestController
@RequestMapping("/api/patients")
//@CrossOrigin(origins = "*")
public class PatientsAPI {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> registerPatient(@RequestBody PatientsDTO patientsDTO){
		System.out.println("Register API hit");
		patientService.savePatient(patientsDTO);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Patient registered successfully");
		return ResponseEntity.ok(response);
	}
	
	

}
