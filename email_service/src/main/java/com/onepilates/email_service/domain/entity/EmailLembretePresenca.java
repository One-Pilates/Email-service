package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;

/** Lembrete enviado ao aluno 48h antes da aula, pedindo que confirme a presença. */
public class EmailLembretePresenca {

    private final String destinatario;
    private final String bodyHtml;

    private EmailLembretePresenca(String destinatario, String bodyHtml) {
        this.destinatario = destinatario;
        this.bodyHtml = bodyHtml;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public static EmailLembretePresenca criar(String destinatario, String nomeAluno, String nomeProfessor,
                                              LocalDateTime dataHora, String nomeSala, String nomeEspecialidade) {
        if (vazio(destinatario) || vazio(nomeAluno) || dataHora == null || vazio(nomeSala) || vazio(nomeEspecialidade)) {
            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }
        String conteudo = "<p>Olá <strong>" + EmailLayout.escapar(nomeAluno) + "</strong>,</p>"
                + "<p>Sua aula está chegando. Confira os detalhes:</p>"
                + EmailLayout.detalhesDaAula(dataHora, nomeEspecialidade, nomeSala, nomeProfessor)
                + "<p><strong>Entre no aplicativo OnePilates e confirme a sua presença.</strong> "
                + "Se não puder comparecer, entre em contato com a secretaria o quanto antes.</p>"
                + "<p>Lembrete: cancelamentos feitos com menos de 24 horas de antecedência "
                + "descontam a aula do seu pacote.</p>";
        return new EmailLembretePresenca(destinatario, EmailLayout.montar("Confirme sua presença", "#F77433", conteudo));
    }

    private static boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
