package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.application.dispacher.EmailDispatcher;
import com.onepilates.email_service.domain.entity.EmailCriacaoAula;
import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class AulaCriadaHandler implements  EmailHandler{
    private final EmailDispatcher emailDispatcher;

    public AulaCriadaHandler(EmailDispatcher emailDispatcher) {
        this.emailDispatcher = emailDispatcher;
    }


    @Override
    public TypeEmail getTipo() {
        return TypeEmail.AULA_CRIADA;
    }

    @Override
    public void processar(EmailRequestDTO dto) {
        Map<String, Object> payload = (Map<String, Object>) dto.getPayload();

        String nomeProfessor = (String) payload.get("nomeProfessor");
        List<String> nomesAlunos = (List<String>) payload.get("nomesDosAlunos");
        String dataString = (String) payload.get("dataHoraAgendamento");

        LocalDateTime dataAgendamento = LocalDateTime.parse(dataString);
        String nomeSala = (String) payload.get("nomeSala");
        String nomeEspecialidade = (String) payload.get("nomeEspecialidade");

        EmailCriacaoAula emailCriacaoAula = EmailCriacaoAula.criarEmailCriacaoAula(nomeProfessor, nomesAlunos, dto.getDestinatario(), dataAgendamento, nomeSala, nomeEspecialidade);
        emailDispatcher.dispatch(emailCriacaoAula.getDestinatario(), "Novo Agendamento", emailCriacaoAula.getBodyHtml());

    }
}
