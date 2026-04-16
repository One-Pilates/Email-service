package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.application.dispacher.EmailDispatcher;
import com.onepilates.email_service.domain.entity.EmailCodigoAcesso;
import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class CodigoAcessoHandler implements EmailHandler{

    private final EmailDispatcher emailDispatcher;

    public CodigoAcessoHandler(EmailDispatcher emailDispatcher) {
        this.emailDispatcher = emailDispatcher;
    }

    @Override
    public TypeEmail getTipo() {
        return TypeEmail.CODIGO_ACESSO;
    }

    @Override
    public void processar(EmailRequestDTO dto) {

        Map<String, Object> payload = (Map<String, Object>) dto.getPayload();

        String nome = (String) payload.get("nomeFuncionario");
        String codigo = (String) payload.get("codigo");

        EmailCodigoAcesso emailCodigoAcesso = EmailCodigoAcesso.criarEmailCodigoAcesso(nome, dto.getDestinatario(), codigo);
        emailDispatcher.dispatch(dto.getDestinatario(), "Codigo para troca de senha", emailCodigoAcesso.getBodyHtml());

    }
}
