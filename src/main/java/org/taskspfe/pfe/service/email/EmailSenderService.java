package org.taskspfe.pfe.service.email;

public interface EmailSenderService {
    void sendEmail(final String toEmail , final String subject, String body);
    String emailTemplateContact(String title , String description , String task);
}
