package com.onepilates.email_service.application.usecase;

import com.onepilates.email_service.application.handler.EmailHandler;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ValidarQualTipoEmailUseCase {

    private final List<EmailHandler> handlers;

    public ValidarQualTipoEmailUseCase(List<EmailHandler> handlers) {
        this.handlers = handlers;
    }


    public void executar(EmailRequestDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO não pode ser nulo");
        }

        if (dto.getTypeEmail() == null) {
            throw new IllegalArgumentException("Tipo de email é obrigatório");
        }

        EmailHandler handler = handlers.stream()
                .filter(h -> h.getTipo() == dto.getTypeEmail())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Nenhum handler encontrado para o tipo: " + dto.getTypeEmail())
                );

        handler.processar(dto);
    }
}

