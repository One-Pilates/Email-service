package com.onepilates.email_service.application.handler;

import com.onepilates.email_service.application.dispacher.EmailDispatcher;
import com.onepilates.email_service.domain.entity.EmailCancelamentoAula;
import com.onepilates.email_service.domain.entity.EmailCancelamentoAulaAluno;
import com.onepilates.email_service.domain.enums.TypeEmail;
import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

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
    public void processar(EmailRequestDTO dto) {

        Map<String, Object> payload = (Map<String, Object>) dto.getPayload();

        String dataString = (String) payload.get("dataHoraAgendamento");
        LocalDateTime dataAgendamento = LocalDateTime.parse(dataString);
        String nomeSala = (String) payload.get("nomeSala");
        String nomeEspecialidade = (String) payload.get("nomeEspecialidade");

        if (dto.getTypeEmail() == TypeEmail.AULA_CANCELADA_ALUNO) {
            String nomeAluno = (String) payload.get("nomeAluno");

            EmailCancelamentoAulaAluno emailAluno = EmailCancelamentoAulaAluno.criarEmailCancelamentoAulaAluno(
                    nomeAluno,
                    dto.getDestinatario(),
                    dataAgendamento,
                    nomeSala,
                    nomeEspecialidade
            );

            emailDispatcher.dispatch(dto.getDestinatario(), "Sua aula foi cancelada", emailAluno.getBodyHtml());

        } else {
            String nomeProfessor = (String) payload.get("nomeProfessor");

            EmailCancelamentoAula emailProfessor = EmailCancelamentoAula.criarEmailCancelamentoAula(
                    nomeProfessor,
                    dto.getDestinatario(),
                    dataAgendamento,
                    nomeSala,
                    nomeEspecialidade
            );

            emailDispatcher.dispatch(dto.getDestinatario(), "Aula cancelada", emailProfessor.getBodyHtml());
        }
    }
}