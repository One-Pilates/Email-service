package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;

/** E-mail enviado ao aluno quando uma aula dele é cancelada (por ele ou pela equipe). */
public class EmailCancelamentoAulaAluno {

    private final String destinatario;
    private final String bodyHtml;

    private EmailCancelamentoAulaAluno(String destinatario, String bodyHtml) {
        this.destinatario = destinatario;
        this.bodyHtml = bodyHtml;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    /**
     * @param canceladoPeloAluno    true se o próprio aluno cancelou; nulo/false se foi a equipe
     * @param aulaDevolvidaAoPacote true se a aula voltou ao pacote, false se continuou descontada,
     *                              nulo se o aluno não tinha pacote debitado para essa aula
     */
    public static EmailCancelamentoAulaAluno criar(String destinatario, String nomeAluno, LocalDateTime dataHora,
                                                   String nomeSala, String nomeEspecialidade,
                                                   Boolean canceladoPeloAluno, Boolean aulaDevolvidaAoPacote) {
        if (vazio(destinatario) || vazio(nomeAluno) || dataHora == null || vazio(nomeSala) || vazio(nomeEspecialidade)) {
            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }
        boolean peloAluno = Boolean.TRUE.equals(canceladoPeloAluno);
        String abertura = peloAluno
                ? "Registramos o <strong>cancelamento</strong> da aula abaixo, feito por você:"
                : "Informamos que a aula abaixo foi <strong>cancelada</strong> pela equipe:";

        String conteudo = "<p>Olá <strong>" + EmailLayout.escapar(nomeAluno) + "</strong>,</p>"
                + "<p>" + abertura + "</p>"
                + EmailLayout.detalhesDaAula(dataHora, nomeEspecialidade, nomeSala, null)
                + "<p>" + situacaoDoPacote(peloAluno, aulaDevolvidaAoPacote) + "</p>"
                + "<p>Em caso de dúvidas, fale com a secretaria.</p>";
        return new EmailCancelamentoAulaAluno(destinatario,
                EmailLayout.montar("Aula Cancelada", "#FF0000", conteudo));
    }

    private static String situacaoDoPacote(boolean peloAluno, Boolean devolvida) {
        if (Boolean.TRUE.equals(devolvida)) {
            return "A aula <strong>voltou para o seu pacote</strong>.";
        }
        if (Boolean.FALSE.equals(devolvida)) {
            return "Como o cancelamento foi feito com <strong>menos de 24 horas de antecedência</strong>, "
                    + "a aula <strong>foi descontada do seu pacote</strong>.";
        }
        return peloAluno ? "Seu cancelamento foi registrado." : "";
    }

    private static boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
