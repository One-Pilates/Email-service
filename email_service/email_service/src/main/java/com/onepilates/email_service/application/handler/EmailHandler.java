package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;

public interface EmailHandler {
    TypeEmail getTipo();

    void processar(EmailRequestDTO dto);
}
