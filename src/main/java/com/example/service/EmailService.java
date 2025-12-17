package com.example.service;

import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.dto.PatientsDTO;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    private static final String BREVO_URL = "https://api.brevo.com/v3/smtp/email";

    public void sendRegistrationMail(String toEmail, String name, PatientsDTO dto) {
        try {
            HttpURLConnection conn =
                (HttpURLConnection) new URL(BREVO_URL).openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("api-key", apiKey);
            conn.setRequestProperty("content-type", "application/json");
            conn.setDoOutput(true);

            String body = """
            {
              "sender": {"name":"Venkatesha Clinic","email":"venkateshaclinic@gmail.com"},
              "to":[{"email":"%s","name":"%s"}],
              "subject":"Appointment Registered Successfully",
              "htmlContent":"<p>Hello %s,<br>DOB : %s <br>Your appointment is registered successfully.</p>"
            }
            """.formatted(toEmail, name, name, dto.getBirthDate().toString());

            conn.getOutputStream().write(body.getBytes());

            conn.getInputStream(); // triggers request
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

