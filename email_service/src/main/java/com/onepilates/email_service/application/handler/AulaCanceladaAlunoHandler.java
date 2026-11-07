package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.application.dispacher.EmailDispatcher;
import com.onepilates.email_service.domain.entity.EmailCancelamentoAulaAluno;
import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/** O backend já enviava este tipo, mas ele não tinha handler aqui: a mensagem era descartada. */
@Component
public class AulaCanceladaAlunoHandler implements EmailHandler {

    private final EmailDispatcher emailDispatcher;

    public AulaCanceladaAlunoHandler(EmailDispatcher emailDispatcher) {
        this.emailDispatcher = emailDispatcher;
    }

    @Override
    public TypeEmail getTipo() {
        return TypeEmail.AULA_CANCELADA_ALUNO;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void processar(EmailRequestDTO dto) {
        Map<String, Object> payload = (Map<String, Object>) dto.getPayload();

        EmailCancelamentoAulaAluno email = EmailCancelamentoAulaAluno.criar(
                dto.getDestinatario(),
                (String) payload.get("nomeAluno"),
                LocalDateTime.parse((String) payload.get("dataHoraAgendamento")),
                (String) payload.get("nomeSala"),
                (String) payload.get("nomeEspecialidade"),
                (Boolean) payload.get("canceladoPeloAluno"),
                (Boolean) payload.get("aulaDevolvidaAoPacote"));

        emailDispatcher.dispatch(email.getDestinatario(), "Aula cancelada", email.getBodyHtml());
    }
}
