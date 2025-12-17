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

    private static final String BREVO_URL =
            "https://api.brevo.com/v3/smtp/email";

    public void sendRegistrationMail(String toEmail,
                                     String name,
                                     PatientsDTO dto) {

        try {
            HttpURLConnection conn =
                    (HttpURLConnection) new URL(BREVO_URL).openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("api-key", apiKey);
            conn.setDoOutput(true);

            //SECOND EMAIL IS HARD-CODED HERE
            String body = """
            {
              "sender": {
                "name": "Venkatesha Clinic",
                "email": "venkateshaclinic@gmail.com"
              },
              "to": [
                {
                  "email": "%s",
                  "name": "%s"
                },
                {
                  "email": "chandrakantha628@gmail.com",
                  "name": "Clinic Admin"
                }
              ],
              "subject": "Appointment Registered Successfully",
              "htmlContent": "<p>Hello %s,<br>DOB : %s<br>Your appointment is registered successfully.</p>"
            }
            """.formatted(
                    toEmail,
                    name,
                    name,
                    dto.getBirthDate()
            );

            conn.getOutputStream().write(body.getBytes("UTF-8"));

            int status = conn.getResponseCode();

            if (status >= 200 && status < 300) {
                System.out.println("Email sent to patient + admin");
            } else {
                System.err.println("Email failed. HTTP " + status);
                System.err.println(
                        new String(conn.getErrorStream().readAllBytes())
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


