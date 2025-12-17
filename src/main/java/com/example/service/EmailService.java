package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.dto.PatientsDTO;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Async
    public void sendRegistrationMail(String toEmail, String patientName, PatientsDTO dto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail,"chandrakantha628@gmail.com");
        message.setSubject("Appointment Registered Successfully");
        message.setText(
            "Hello " + patientName +", DOB : "+ dto.getBirthDate().toString()+",\n\n"+
            "Your appointment has been successfully registered.\n\n" +
            "Thank you for choosing our service.\n\n" +
            "Regards,\nHospital Team"
        );

        mailSender.send(message);
    }
}
