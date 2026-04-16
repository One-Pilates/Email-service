package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.application.dispacher.EmailDispatcher;
import com.onepilates.email_service.domain.entity.EmailPrimeiroAcesso;
import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PrimeiroAcessoHandler implements EmailHandler {

    private final EmailDispatcher emailDispatcher;

    public PrimeiroAcessoHandler(EmailDispatcher emailDispatcher) {
        this.emailDispatcher = emailDispatcher;
    }


    @Override
    public TypeEmail getTipo() {
        return TypeEmail.PRIMEIRO_ACESSO;
    }

    @Override
    public void processar(EmailRequestDTO dto) {
        Map<String, Object> payload = (Map<String, Object>) dto.getPayload();

        String nome = (String) payload.get("nomeFuncionario");
        String senha = (String) payload.get("senhaTemporaria");

        if (nome == null || senha == null) {
            throw new IllegalArgumentException("Payload inválido para PRIMEIRO_ACESSO");
        }

        EmailPrimeiroAcesso emailPrimeiroAcesso = EmailPrimeiroAcesso.criarEmailPrimeiroAcesso(dto.getDestinatario(),nome,senha);
        emailDispatcher.dispatch(dto.getDestinatario(), "Primeiro Acesso", emailPrimeiroAcesso.getBodyHtml());


    }
}
