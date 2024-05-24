package org.taskspfe.pfe.service.email;

import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderServiceImpl  implements  EmailSenderService{

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailSenderServiceImpl.class);
    private final JavaMailSender javaMailSender;

    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendEmail(final String toEmail , final String subject, String body)
    {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(body, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("rahma@pfe.com");
            javaMailSender.send(mimeMessage);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String emailTemplateContact(String title, String description, String task) {
        return "<div style=\"font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #e0f7fa;\">\n" +
                "    <div style=\"max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\">\n" +
                "        <div style=\"text-align: center; padding-bottom: 20px; border-bottom: 2px solid #00796b;\">\n" +
                "            <h1 style=\"margin: 0; font-size: 26px; color: #004d40;\">" + title + "</h1>\n" +
                "        </div>\n" +
                "        <div style=\"padding: 20px 0;\">\n" +
                "            <p style=\"font-size: 16px; color: #00796b; line-height: 1.6; margin: 0;\">\n" +
                "              " + description + "\n" +
                "            </p>\n" +
                "        </div>\n" +
                "        <div style=\"padding: 20px; background-color: #e0f2f1; border-radius: 4px; margin-top: 20px; border-left: 4px solid #00796b;\">\n" +
                "            <p style=\"font-size: 14px; color: #004d40;\">\n" +
                "               <strong>"+ task + "</strong>\n" +
                "            </p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
    }


}
