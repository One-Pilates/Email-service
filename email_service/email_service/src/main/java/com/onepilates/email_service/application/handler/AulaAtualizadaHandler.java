package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AulaAtualizadaHandler implements EmailHandler{
    @Override
    public TypeEmail getTipo() {
        return TypeEmail.AULA_ATUALIZADA;
    }

    @Override
    public void processar(EmailRequestDTO dto) {

    }
}
