package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailCancelamentoAula {

    private String nomeProfessor;
    private String destinatario;
    private LocalDateTime dataHoraAgendamento;
    private String nomeSala;
    private String nomeEspecialidade;
    private String bodyHtml;

    private EmailCancelamentoAula(String nomeProfessor,
                                  String destinatario,
                                  LocalDateTime dataHoraAgendamento,
                                  String nomeSala,
                                  String nomeEspecialidade,
                                  String bodyHtml) {
        this.nomeProfessor = nomeProfessor;
        this.destinatario = destinatario;
        this.dataHoraAgendamento = dataHoraAgendamento;
        this.nomeSala = nomeSala;
        this.nomeEspecialidade = nomeEspecialidade;
        this.bodyHtml = bodyHtml;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
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

    public static EmailCancelamentoAula criarEmailCancelamentoAula(
            String nomeProfessor,
            String destinatario,
            LocalDateTime dataHoraAgendamento,
            String nomeSala,
            String nomeEspecialidade) {

        if (nomeProfessor == null || nomeProfessor.isBlank() ||
                destinatario == null || destinatario.isBlank() ||
                dataHoraAgendamento == null ||
                nomeSala == null || nomeSala.isBlank() ||
                nomeEspecialidade == null || nomeEspecialidade.isBlank()) {

            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }

        // Formatação da data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm");
        String dataHoraFormatada = dataHoraAgendamento.format(formatter);

        // HTML
        String corpoHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Email OnePilates - Cancelamento de Aula</title>
                    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet" />
                </head>
                <body style="margin:0; padding:0; font-family:'Poppins', Arial, Helvetica, sans-serif;">
                    <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0" style="background-color:#e5e5e5;">
                        <tr>
                            <td align="center" style="padding:40px 20px;">
                                <table role="presentation" width="600" cellpadding="0" cellspacing="0" border="0" style="background-color:#ffffff;">
                                    <tr>
                                        <td align="center" style="padding:40px;">
                                            <img src="https://assets-cdn.wellhub.com/images/?su=https://images.partners.gympass.com/image/filename/250389/lg_858dc5781e84-ONE_SEM_FUNDO_COM_SLOGAN_01_01.jpg" width="160">
                                        </td>
                                    </tr>

                                    <tr>
                                        <td style="height:4px; background-color:#FF0000;"></td>
                                    </tr>

                                    <tr>
                                        <td style="padding:40px;">
                                            <h1 style="font-size:22px;">Aula Cancelada</h1>

                                            <p>Olá <strong>%s</strong>,</p>

                                            <p>Informamos que a aula abaixo foi <strong>cancelada</strong>:</p>

                                            <p><strong>Data e horário:</strong> %s</p>
                                            <p><strong>Especialidade:</strong> %s</p>
                                            <p><strong>Sala:</strong> %s</p>

                                            <p>Em caso de dúvidas, estamos à disposição.</p>

                                            <p>Atenciosamente,<br>
                                            <strong>Equipe OnePilates</strong></p>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td style="background-color:#f9f9f9; text-align:center; padding:16px; font-size:12px;">
                                            Este é um e-mail automático. © 2025 OnePilates.
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """
                .formatted(nomeProfessor, dataHoraFormatada, nomeEspecialidade, nomeSala);

        return new EmailCancelamentoAula(
                nomeProfessor,
                destinatario,
                dataHoraAgendamento,
                nomeSala,
                nomeEspecialidade,
                corpoHtml
        );
    }
}