package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;

public class AulaCanceladaHandler implements EmailHandler {
    @Override
    public TypeEmail getTipo() {
        return TypeEmail.AULA_CANCELADA;
    }

    @Override
    public void processar(EmailRequestDTO dto) {
        System.out.println("Chegou no processar da aula cancelada, falta implementar a logica a partir daqui");

    }


}
