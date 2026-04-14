package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmailAulaAtualizada {

    private String nomeProfessor;
    private List<String> nomesAlunos;
    private String destinatario;
    private LocalDateTime dataHoraAgendamento;
    private String nomeSala;
    private String nomeEspecialidade;
    private String bodyHtml;

    private EmailAulaAtualizada(String nomeProfessor,
                                List<String> nomesAlunos,
                                String destinatario,
                                LocalDateTime dataHoraAgendamento,
                                String nomeSala,
                                String nomeEspecialidade,
                                String bodyHtml) {
        this.nomeProfessor = nomeProfessor;
        this.nomesAlunos = nomesAlunos;
        this.destinatario = destinatario;
        this.dataHoraAgendamento = dataHoraAgendamento;
        this.nomeSala = nomeSala;
        this.nomeEspecialidade = nomeEspecialidade;
        this.bodyHtml = bodyHtml;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public List<String> getNomesAlunos() {
        return nomesAlunos;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public LocalDateTime getDataHoraAgendamento() {
        return dataHoraAgendamento;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public static EmailAulaAtualizada criarEmailAulaAtualizada(
            String nomeProfessor,
            List<String> listaNomesAlunos,
            String destinatario,
            LocalDateTime dataHoraAgendamento,
            String nomeSala,
            String nomeEspecialidade) {

        if (nomeProfessor == null || nomeProfessor.isBlank() ||
                listaNomesAlunos == null || listaNomesAlunos.isEmpty() ||
                destinatario == null || destinatario.isBlank() ||
                dataHoraAgendamento == null ||
                nomeSala == null || nomeSala.isBlank() ||
                nomeEspecialidade == null || nomeEspecialidade.isBlank()) {

            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }

        // Formatar data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm");
        String dataHoraFormatada = dataHoraAgendamento.format(formatter);

        // Montar lista de alunos
        StringBuilder listaAlunosHtml = new StringBuilder();
        for (String aluno : listaNomesAlunos) {
            listaAlunosHtml.append("""
                <tr>
                    <td style="padding:10px 16px; background-color:#ffffff; border-left:3px solid #FF6600; border-radius:4px;">
                        <p style="margin:0; font-size:15px; color:#1a1a1a; font-weight:500;">%s</p>
                    </td>
                </tr>
            """.formatted(escapeHtml(aluno)));
        }

        String corpoHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Email OnePilates - Aula Atualizada</title>
                    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet" />
                </head>
                <body style="margin:0; padding:0; font-family:'Poppins', Arial, Helvetica, sans-serif;">
                    <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0" style="background-color:#e5e5e5;">
                        <tr>
                            <td align="center" style="padding:40px 20px;">
                                <table role="presentation" width="600" style="background-color:#ffffff;">
                                    <tr>
                                        <td align="center" style="padding:40px;">
                                            <img src="https://i.ibb.co/q39Mz6gR/logo-Original.png" width="160">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="height:4px; background-color:#FF6600;"></td>
                                    </tr>
                                    <tr>
                                        <td style="padding:40px;">
                                            <h1>Aula Atualizada</h1>
                                            <p>Olá <strong>%s</strong>,</p>
                                            <p>Uma aula foi atualizada.</p>

                                            <p><strong>Data:</strong> %s</p>
                                            <p><strong>Especialidade:</strong> %s</p>
                                            <p><strong>Sala:</strong> %s</p>

                                            <p><strong>Alunos:</strong></p>
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
                .formatted(
                        escapeHtml(nomeProfessor),
                        dataHoraFormatada,
                        escapeHtml(nomeEspecialidade),
                        escapeHtml(nomeSala),
                        listaAlunosHtml.toString()
                );

        return new EmailAulaAtualizada(
                nomeProfessor,
                listaNomesAlunos,
                destinatario,
                dataHoraAgendamento,
                nomeSala,
                nomeEspecialidade,
                corpoHtml
        );
    }

    private static String escapeHtml(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}