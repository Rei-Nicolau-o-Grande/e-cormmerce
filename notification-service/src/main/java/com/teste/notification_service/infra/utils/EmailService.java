package com.teste.notification_service.infra.utils;

import com.teste.notification_service.infra.kafka.OrderWithProductsMessageConsumer;
import com.teste.notification_service.infra.kafka.ProductMessageConsume;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
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

    public void sendEmail(String email, OrderWithProductsMessageConsumer messageConsumer) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String subject = "Notification of your order has arrived";

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(bodyHtml(messageConsumer), true);

            String nameOrderPdf = String.format("order-%s-%s.pdf", messageConsumer.status(), messageConsumer.id());
            Path filePath = Paths.get("/orders", nameOrderPdf);
            File file = filePath.toFile();
            if (!file.exists()) {
                throw new FileNotFoundException("Arquivo não encontrado: " + file.getAbsolutePath());
            }
            FileSystemResource fileResource = new FileSystemResource(file);
            helper.addAttachment(nameOrderPdf, fileResource);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | FileNotFoundException | MailException e) {
            throw new RuntimeException(e);
        }
    }

    private String bodyHtml(OrderWithProductsMessageConsumer messageConsumer) {
        StringBuilder productsTable = new StringBuilder();
        productsTable.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse;'>");
        productsTable.append("<tr><th>Product Name</th><th>Price</th></tr>");

        for (ProductMessageConsume product : messageConsumer.products()) {
            productsTable.append(String.format("<tr><td>%s</td><td>%.2f</td></tr>",
                    product.name(), product.price()));
        }

        productsTable.append("</table>");

        return String.format("<p><strong>Order ID:</strong> %s</p>" +
                        "<p><strong>Status:</strong> %s</p>" +
                        "<p><strong>Products:</strong> %s</p>",
                messageConsumer.id(), messageConsumer.status(), productsTable);
    }
}
