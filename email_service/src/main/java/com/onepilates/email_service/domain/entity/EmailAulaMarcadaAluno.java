package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;

/** E-mail enviado ao aluno quando ele é colocado em uma aula. */
public class EmailAulaMarcadaAluno {

    private final String destinatario;
    private final String bodyHtml;

    private EmailAulaMarcadaAluno(String destinatario, String bodyHtml) {
        this.destinatario = destinatario;
        this.bodyHtml = bodyHtml;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public static EmailAulaMarcadaAluno criar(String destinatario, String nomeAluno, String nomeProfessor,
                                              LocalDateTime dataHora, String nomeSala, String nomeEspecialidade) {
        if (vazio(destinatario) || vazio(nomeAluno) || dataHora == null || vazio(nomeSala) || vazio(nomeEspecialidade)) {
            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }
        String conteudo = "<p>Olá <strong>" + EmailLayout.escapar(nomeAluno) + "</strong>,</p>"
                + "<p>Uma aula foi <strong>marcada</strong> para você:</p>"
                + EmailLayout.detalhesDaAula(dataHora, nomeEspecialidade, nomeSala, nomeProfessor)
                + "<p>Você pode ver suas aulas e confirmar presença no aplicativo OnePilates. "
                + "Se não puder comparecer, entre em contato com a secretaria.</p>";
        return new EmailAulaMarcadaAluno(destinatario, EmailLayout.montar("Aula Marcada", "#F77433", conteudo));
    }

    private static boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
