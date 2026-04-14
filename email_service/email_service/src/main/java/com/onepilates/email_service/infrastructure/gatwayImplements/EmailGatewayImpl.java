package com.onepilates.email_service.infrastructure.gatwayImplements;


import com.onepilates.email_service.domain.gatway.EmailGatway;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailGatewayImpl implements EmailGatway {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String remetente;

    public EmailGatewayImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarEmail(String destinatario, String assunto, String corpo) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);


            helper.setText(corpo, true); // true = HTML

            mailSender.send(message);

            System.out.println("✅ Email HTML enviado com sucesso para: " + destinatario);

        } catch (Exception e) {
            System.out.println("❌ Erro ao enviar email");
            e.printStackTrace();
        }
    }
}
