package com.example.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientsDTO {
	
	private String name;
	private String email;
	private String phone;
	private Long id; 
	private LocalDate aptAt;
	private LocalDate birthDate;

}
