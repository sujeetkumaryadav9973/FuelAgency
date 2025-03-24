package com.faos.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.faos.model.Customer;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

 
	public void sendRegistrationEmail(Customer customer) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Welcome to Fuel Agency!");

        // Create a formatted email template
        String emailContent = String.format(
            "Dear %s %s,\n\n"
            + "Thank you for registering with Fuel Agency.\n"
            + "Here are your registration details:\n\n"
            + "📌 **Customer ID:** %d\n"
            + "📌 **Email:** %s\n"
            + "📌 **Phone:** %s\n"
            + "📌 **Address:** %s\n"
            + "📌 **Registration Date:** %s\n"
            + "📌 **Connection Type:** %s\n"
            + "📌 **Connection Status:** %s\n\n"
            + "If you have any questions, feel free to contact us.\n\n"
            + "Best Regards,\n"
            + "Fuel Agency Team",
            customer.getFirstName(),
            customer.getLastName(),
            customer.getCustomerId(),
            customer.getEmail(),
            customer.getPhone(),
            customer.getAddress(),
            customer.getRegistrationDate(),
            customer.getConnectionType(),
            customer.getConnectionStatus()
        );

        message.setText(emailContent);
        mailSender.send(message);
	}


	public void sendDeactivationEmail(Customer customer) {
		// TODO Auto-generated method stub public void sendDeactivationEmail(Customer customer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Account Deactivation");
        message.setText("Dear " + customer.getFirstName() + ",\n\nYour account has been deactivated. Please contact support if you need assistance.");
        mailSender.send(message);
    }

	 public void sendRegistrationEmail(Long CustomerId,String email, String rawPassword) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(email);
	        message.setSubject("Welcome to Our Service - Your Login Credentials");

	        String emailContent = String.format(
	            "Dear Customer,\n\n"
	            + "Your account has been successfully registered.\n"
	            + "Here are your login details:\n\n"
	            + "📌 **CustomerId:** %d\n"
	            + "📌 **Email:** %s\n"
	            + "📌 **Password:** %s\n\n"
	            + "Please change your password after logging in.\n\n"
	            + "Regards,\nSupport Team",
	            CustomerId,email, rawPassword
	        );

	        message.setText(emailContent);
	        mailSender.send(message);
	    }

	
	
	}

