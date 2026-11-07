package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.application.dispacher.EmailDispatcher;
import com.onepilates.email_service.domain.entity.EmailLembretePresenca;
import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class LembretePresencaAlunoHandler implements EmailHandler {

    private final EmailDispatcher emailDispatcher;

    public LembretePresencaAlunoHandler(EmailDispatcher emailDispatcher) {
        this.emailDispatcher = emailDispatcher;
    }

    @Override
    public TypeEmail getTipo() {
        return TypeEmail.LEMBRETE_PRESENCA_ALUNO;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void processar(EmailRequestDTO dto) {
        Map<String, Object> payload = (Map<String, Object>) dto.getPayload();

        EmailLembretePresenca email = EmailLembretePresenca.criar(
                dto.getDestinatario(),
                (String) payload.get("nomeAluno"),
                (String) payload.get("nomeProfessor"),
                LocalDateTime.parse((String) payload.get("dataHoraAgendamento")),
                (String) payload.get("nomeSala"),
                (String) payload.get("nomeEspecialidade"));

        emailDispatcher.dispatch(email.getDestinatario(), "Confirme sua presença na aula", email.getBodyHtml());
    }
}
