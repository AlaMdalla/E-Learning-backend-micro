package com.example.pidevfinal.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    public SmsService() {
    }

    public void sendSms(String to, String jobTitle, String status) {
        System.out.println("Sending SMS to " + to + " for job: " + jobTitle + ", status: " + status);
        try {
            Twilio.init(accountSid, authToken);

            String messageBody;
            switch (status.toLowerCase()) {
                case "applied":
                    messageBody = "Thank you for applying to " + jobTitle + "! We'll review your application soon. - PidevFinal";
                    break;
                case "reviewed":
                    messageBody = "Your application for " + jobTitle + " is under review. We'll update you soon! - PidevFinal";
                    break;
                case "interviewed":
                    messageBody = "Congrats! You're invited to interview for " + jobTitle + ". Check your email for details. - PidevFinal";
                    break;
                case "hired":
                    messageBody = "You're hired for " + jobTitle + "! Welcome to the team! - PidevFinal";
                    break;
                case "rejected":
                    messageBody = "Thank you for applying to " + jobTitle + ". We've selected another candidate. - PidevFinal";
                    break;
                default:
                    System.err.println("Unknown status: " + status + " for SMS to " + to);
                    return;
            }

            if (messageBody.length() > 160) {
                messageBody = messageBody.substring(0, 157) + "...";
            }

            Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(fromPhoneNumber),
                    messageBody
            ).create();

            System.out.println("SMS sent to " + to + " for status: " + status);
        } catch (Exception e) {
            System.err.println("SMS failed for " + to + ": " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}