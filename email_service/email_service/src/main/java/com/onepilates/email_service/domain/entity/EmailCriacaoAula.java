package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmailCriacaoAula {

    private String nomeProfessor;
    private List<String> nomesDosAlunos;
    private String destinatario;
    private LocalDateTime dataHoraAgendamento;
    private String nomeSala;
    private String nomeEspecialidade;
    private String bodyHtml;

    private EmailCriacaoAula(
            String nomeProfessor,
            List<String> nomesDosAlunos,
            String destinatario,
            LocalDateTime dataHoraAgendamento,
            String nomeSala,
            String nomeEspecialidade,
            String bodyHtml
    ) {
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

    public List<String> getNomesDosAlunos() {
        return nomesDosAlunos;
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

    public static EmailCriacaoAula criarEmailCriacaoAula(
            String nomeProfessor,
            List<String> nomesDosAlunos,
            String destinatario,
            LocalDateTime dataHoraAgendamento,
            String nomeSala,
            String nomeEspecialidade
    ) {

        if (nomeProfessor == null || nomeProfessor.isBlank() ||
                nomesDosAlunos == null || nomesDosAlunos.isEmpty() ||
                destinatario == null || destinatario.isBlank() ||
                dataHoraAgendamento == null ||
                nomeSala == null || nomeSala.isBlank() ||
                nomeEspecialidade == null || nomeEspecialidade.isBlank()) {

            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }

        // 📅 Formatação bonita da data
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm");

        String dataHoraFormatada = dataHoraAgendamento.format(formatter);

        // 👥 Lista de alunos estilizada
        StringBuilder listaAlunosHtmlBuilder = new StringBuilder();
        for (String aluno : nomesDosAlunos) {
            listaAlunosHtmlBuilder.append("""
                <tr>
                    <td style="padding:10px 16px; background-color:#ffffff; border-left:3px solid #FF6600; border-radius:4px;">
                        <p style="margin:0; font-size:15px; color:#1a1a1a; font-weight:500;">%s</p>
                    </td>
                </tr>
            """.formatted(aluno));
        }

        String listaAlunosHtml = listaAlunosHtmlBuilder.toString();

        // 📩 HTML completo
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
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#e5e5e5;">
                <tr>
                    <td align="center" style="padding:40px 20px;">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff; max-width:600px;">
                            
                            <!-- HEADER -->
                            <tr>
                                <td align="center" style="padding:40px;">
                                    <img src="https://i.ibb.co/q39Mz6gR/logo-Original.png" width="160">
                                </td>
                            </tr>

                            <tr>
                                <td style="height:4px; background-color:#FF6600;"></td>
                            </tr>

                            <!-- CONTEÚDO -->
                            <tr>
                                <td style="padding:40px;">
                                    <h1 style="margin-bottom:24px;">Novo Agendamento Confirmado</h1>

                                    <p>Olá <strong>%s</strong>,</p>

                                    <p>Você possui um novo agendamento registrado em nosso sistema.</p>

                                    <!-- CARD -->
                                    <table width="100%%" style="background:#f9f9f9; border:1px solid #e0e0e0; border-radius:6px; margin-top:20px;">
                                        <tr>
                                            <td style="padding:24px;">

                                                <p><strong>📅 Data e Hora:</strong><br>%s</p>

                                                <p style="margin-top:16px;"><strong>🧘 Especialidade:</strong><br>%s</p>

                                                <p style="margin-top:16px;"><strong>📍 Sala:</strong><br>%s</p>

                                                <p style="margin-top:20px;"><strong>👥 Alunos Confirmados:</strong></p>

                                                <table width="100%%" style="margin-top:10px;">
                                                    %s
                                                </table>

                                            </td>
                                        </tr>
                                    </table>

                                    <p style="margin-top:24px;">Qualquer dúvida, estamos à disposição.</p>

                                    <p style="margin-top:16px;">
                                        Atenciosamente,<br>
                                        <strong style="color:#FF6600;">Equipe OnePilates</strong>
                                    </p>
                                </td>
                            </tr>

                            <!-- FOOTER -->
                            <tr>
                                <td style="padding:24px; background:#f9f9f9; text-align:center; font-size:12px; color:#999;">
                                    Este é um e-mail automático, não responda.<br>
                                    © 2025 OnePilates
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
                        nomeProfessor,
                        dataHoraFormatada,
                        nomeEspecialidade,
                        nomeSala,
                        listaAlunosHtml
                );

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