package com.example.pidevfinal.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendApplicationEmail(String to, String jobTitle, String status) {
        System.out.println("Sending email to " + to + " for job: " + jobTitle + ", status: " + status);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("rayenfarhani9@gmail.com", "PidevFinal Team");
            helper.setTo(to);

            String subject;
            String body;

            // Base HTML template with inline CSS
            String baseTemplate = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>%s</title>
                </head>
                <body style="margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4;">
                    <table role="presentation" align="center" width="100%%" style="max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                        <tr>
                            <td style="padding: 20px 0; text-align: center; background-color: #3b82f6; border-top-left-radius: 8px; border-top-right-radius: 8px;">
                                <img src="https://via.placeholder.com/150x50?text=PidevFinal+Logo" alt="PidevFinal Logo" style="max-width: 150px;">
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 30px;">
                                <h2 style="color: #1f2937; margin: 0 0 20px; font-size: 24px;">%s</h2>
                                <p style="color: #4b5563; font-size: 16px; line-height: 1.5; margin: 0 0 20px;">Dear Candidate,</p>
                                <p style="color: #4b5563; font-size: 16px; line-height: 1.5; margin: 0 0 20px;">%s</p>
                                <p style="color: #4b5563; font-size: 16px; line-height: 1.5; margin: 0 0 20px;">Position: <strong>%s</strong></p>
                                <table role="presentation" width="100%%" style="margin: 20px 0;">
                                    <tr>
                                        <td style="text-align: center;">
                                            <a href="mailto:rayenfarhani9@gmail.com" style="display: inline-block; padding: 12px 24px; background-color: #3b82f6; color: #ffffff; text-decoration: none; border-radius: 4px; font-size: 16px;">Contact Us</a>
                                        </td>
                                    </tr>
                                </table>
                                <p style="color: #4b5563; font-size: 14px; line-height: 1.5; margin: 20px 0 0;">Best regards,<br><strong>PidevFinal Team</strong></p>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 20px; text-align: center; background-color: #f4f4f4; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;">
                                <p style="color: #6b7280; font-size: 12px; margin: 0;">&copy; 2025 PidevFinal. All rights reserved.</p>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """;

            switch (status.toLowerCase()) {
                case "applied":
                    subject = "Application Received for " + jobTitle;
                    body = String.format(baseTemplate,
                            subject,
                            "Thank You for Applying!",
                            "We have received your application for the position of <strong>" + jobTitle + "</strong>. Our team is reviewing your submission, and we’ll be in touch soon with next steps.",
                            jobTitle
                    );
                    break;
                case "reviewed":
                    subject = "Application Under Review for " + jobTitle;
                    body = String.format(baseTemplate,
                            subject,
                            "Your Application is Being Reviewed",
                            "Great news! Your application for the position of <strong>" + jobTitle + "</strong> is currently under review. We’re excited about your profile and will contact you soon with updates.",
                            jobTitle
                    );
                    break;
                case "interviewed":
                    subject = "Interview Scheduled for " + jobTitle;
                    body = String.format(baseTemplate,
                            subject,
                            "You’re Invited to Interview!",
                            "Congratulations! We’re thrilled to invite you to interview for the position of <strong>" + jobTitle + "</strong>. Please check your inbox (and spam folder) for scheduling details.",
                            jobTitle
                    );
                    break;
                case "hired":
                    subject = "Congratulations! You’re Hired for " + jobTitle;
                    body = String.format(baseTemplate,
                            subject,
                            "Welcome to the Team!",
                            "We’re overjoyed to offer you the position of <strong>" + jobTitle + "</strong>! Look out for an email with onboarding details and next steps.",
                            jobTitle
                    );
                    break;
                case "rejected":
                    subject = "Application Update for " + jobTitle;
                    body = String.format(baseTemplate,
                            subject,
                            "Thank You for Applying",
                            "We appreciate your interest in the position of <strong>" + jobTitle + "</strong>. After careful consideration, we’ve selected another candidate. We wish you the best in your job search!",
                            jobTitle
                    );
                    break;
                default:
                    System.err.println("Unknown status: " + status + " for " + to);
                    return; // Skip invalid status
            }

            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
            System.out.println("Email sent to " + to + " for status: " + status);
        } catch (MessagingException e) {
            System.err.println("MessagingException for " + to + ": " + e.getMessage());
        } catch (MailException e) {
            System.err.println("MailException for " + to + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error for " + to + ": " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}