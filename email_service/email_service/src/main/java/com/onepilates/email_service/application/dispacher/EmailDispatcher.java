package com.onepilates.email_service.application.dispacher;

import com.onepilates.email_service.domain.gatway.EmailGatway;
import org.springframework.stereotype.Component;

@Component
public class EmailDispatcher {

    private final EmailGatway emailGatway;

    public EmailDispatcher(EmailGatway emailGatway) {
        this.emailGatway = emailGatway;
    }

    public void dispatch(String destinatario, String assunto, String corpo) {

        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("Destinatário obrigatório");
        }

        if (assunto == null || assunto.isBlank()) {
            throw new IllegalArgumentException("Assunto obrigatório");
        }

        if (corpo == null || corpo.isBlank()) {
            throw new IllegalArgumentException("Corpo do e-mail obrigatório");
        }


        emailGatway.enviarEmail(destinatario, assunto, corpo);
    }
}
