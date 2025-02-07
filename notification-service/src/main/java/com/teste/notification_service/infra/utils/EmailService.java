package com.teste.notification_service.infra.utils;

import com.teste.notification_service.infra.kafka.OrderMessageConsumer;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String email, OrderMessageConsumer messageConsumer) throws MessagingException, FileNotFoundException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        String subject = "Notification of your order has arrived";

        String body = String.format("<p><strong>Order ID:</strong> %s</p>" +
                        "<p><strong>Status:</strong> %s</p>" +
                        "<p><strong>Products:</strong> %s</p>",
                messageConsumer.getId(), messageConsumer.getStatus(), messageConsumer.getProductsId());

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body, true);

        String nameOrderPdf = String.format("order-%s-%s.pdf", messageConsumer.getStatus(), messageConsumer.getId());
        Path filePath = Paths.get("/orders", nameOrderPdf);
        File file = filePath.toFile();
        if (!file.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado: " + file.getAbsolutePath());
        }
        FileSystemResource fileResource = new FileSystemResource(file);
        helper.addAttachment(nameOrderPdf, fileResource);

        javaMailSender.send(mimeMessage);
    }
}
