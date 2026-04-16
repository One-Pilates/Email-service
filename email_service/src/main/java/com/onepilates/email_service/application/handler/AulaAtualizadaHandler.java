package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.application.dispacher.EmailDispatcher;
import com.onepilates.email_service.domain.entity.EmailAulaAtualizada;
import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Component
public class AulaAtualizadaHandler implements EmailHandler{

    private final EmailDispatcher emailDispatcher;

    public AulaAtualizadaHandler(EmailDispatcher emailDispatcher) {
        this.emailDispatcher = emailDispatcher;
    }

    @Override
    public TypeEmail getTipo() {
        return TypeEmail.AULA_ATUALIZADA;
    }

    @Override
    public void processar(EmailRequestDTO dto) {
        
        Map<String, Object> payload = (Map<String, Object>) dto.getPayload();

        String nomeProfessor = (String) payload.get("nomeProfessor");
        List<String> nomesAlunos = (List<String>) payload.get("nomesAlunos");
        String dataString = (String) payload.get("dataHoraAgendamento");

        LocalDateTime dataAgendamento = LocalDateTime.parse(dataString);
        String nomeSala = (String) payload.get("nomeSala");
        String nomeEspecialidade = (String) payload.get("nomeEspecialidade");

        EmailAulaAtualizada emailAulaAtualizada = EmailAulaAtualizada.criarEmailAulaAtualizada(nomeProfessor, nomesAlunos, dto.getDestinatario(), dataAgendamento, nomeSala, nomeEspecialidade);


        emailDispatcher.dispatch(dto.getDestinatario(), "Agendamento Atualizado", emailAulaAtualizada.getBodyHtml() );



    }
}
