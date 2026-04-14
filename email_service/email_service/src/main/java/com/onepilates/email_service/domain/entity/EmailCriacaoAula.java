package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

public class EmailCriacaoAula {
    private String nomeProfessor;
    private List<String> nomesDosAlunos;
    private String destinatario;
    private LocalDateTime dataHoraAgendamento;
    private String nomeSala;
    private String nomeEspecialidade;
    private String bodyHtml;

    private EmailCriacaoAula(String nomeProfessor, List<String> nomesDosAlunos, String destinatario, LocalDateTime dataHoraAgendamento, String nomeSala, String nomeEspecialidade, String bodyHtml) {
        this.nomeProfessor = nomeProfessor;
        this.nomesDosAlunos = nomesDosAlunos;
        this.destinatario = destinatario;
        this.dataHoraAgendamento = dataHoraAgendamento;
        this.nomeSala = nomeSala;
        this.nomeEspecialidade = nomeEspecialidade;
        this.bodyHtml = bodyHtml;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public List<String> getNomesDosAlunos() {
        return nomesDosAlunos;
    }

    public void setNomesDosAlunos(List<String> nomesDosAlunos) {
        this.nomesDosAlunos = nomesDosAlunos;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public LocalDateTime getDataHoraAgendamento() {
        return dataHoraAgendamento;
    }

    public void setDataHoraAgendamento(LocalDateTime dataHoraAgendamento) {
        this.dataHoraAgendamento = dataHoraAgendamento;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }

    public void setNomeEspecialidade(String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public static EmailCriacaoAula criarEmailCriacaoAula(
            String nomeProfessor,
            List<String> nomesDosAlunos,
            String destinatario,
            LocalDateTime dataHoraAgendamento,
            String nomeSala,
            String nomeEspecialidade) {

        if (nomeProfessor == null || nomeProfessor.isBlank() ||
                nomesDosAlunos == null || nomesDosAlunos.isEmpty() ||
                destinatario == null || destinatario.isBlank() ||
                dataHoraAgendamento == null ||
                nomeSala == null || nomeSala.isBlank() ||
                nomeEspecialidade == null || nomeEspecialidade.isBlank()) {

            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }

        // Formatar data
        String dataHoraFormatada = dataHoraAgendamento
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        // Montar lista de alunos em HTML (cada um em uma linha)
        StringBuilder listaAlunosHtmlBuilder = new StringBuilder();
        for (String aluno : nomesDosAlunos) {
            listaAlunosHtmlBuilder.append("""
            <tr>
                <td style="padding:4px 0; font-size:14px; color:#333333;">
                    • %s
                </td>
            </tr>
        """.formatted(aluno));
        }

        String listaAlunosHtml = listaAlunosHtmlBuilder.toString();


        String corpoHtml = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Email OnePilates - Novo Agendamento</title>
                <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet" />
            </head>
            <body style="margin:0; padding:0; font-family:'Poppins', Arial, Helvetica, sans-serif;">
                <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0" style="background-color:#e5e5e5;">
                    <tr>
                        <td align="center" style="padding:40px 20px;">
                            <table role="presentation" width="600" cellpadding="0" cellspacing="0" border="0" style="background-color:#ffffff; max-width:600px;">
                                <tr>
                                    <td align="center" style="padding:40px 40px 30px 40px;">
                                        <img src="https://i.ibb.co/q39Mz6gR/logo-Original.png" width="160">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="height:4px; background-color:#FF6600;"></td>
                                </tr>
                                <tr>
                                    <td style="padding:40px;">
                                        <h1 style="font-size:22px;">Novo Agendamento Confirmado</h1>
                                        <p>Olá <strong>%s</strong>,</p>
                                        <p>Informamos que você possui um novo agendamento registrado em nosso sistema.</p>

                                        <p><strong>Data e Hora:</strong> %s</p>
                                        <p><strong>Especialidade:</strong> %s</p>
                                        <p><strong>Sala:</strong> %s</p>

                                        <p><strong>Alunos Confirmados:</strong></p>
                                        <table>%s</table>

                                        <p>Atenciosamente,<br><strong>Equipe OnePilates</strong></p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """
                .formatted(nomeProfessor, dataHoraFormatada, nomeEspecialidade, nomeSala, listaAlunosHtml);

        return new EmailCriacaoAula(
                nomeProfessor,
                nomesDosAlunos,
                destinatario,
                dataHoraAgendamento,
                nomeSala,
                nomeEspecialidade,
                corpoHtml
        );
    }
}
