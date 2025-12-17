package com.example.api;

import java.time.LocalDate;
import java.util.List;
import com.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Patients;
import com.example.service.GetPatients;

@RestController
@RequestMapping("/api/patients")
//@CrossOrigin(origins = "http://localhost:4200")  // allow Angular frontend
public class GetPatientsAPI {

    private final PatientService patientService;

    @Autowired
    private GetPatients getPatients;

    GetPatientsAPI(PatientService patientService) {
        this.patientService = patientService;
    }
//-------------------------------------------------------------------------------------------
    @GetMapping
    public List<Patients> getAllPatients() {
        return getPatients.getAllPatients();
    }
    
    @GetMapping("/patients")
    public List<Patients> getPatients(
        @RequestParam(required = false) LocalDate date
    ) {
        return getPatients.handleQueueByDate(date);
    }

}
