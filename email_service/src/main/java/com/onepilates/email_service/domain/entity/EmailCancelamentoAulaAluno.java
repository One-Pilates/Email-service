package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EmailCancelamentoAulaAluno {

    private String nomeAluno;
    private String destinatario;
    private LocalDateTime dataHoraAgendamento;
    private String nomeSala;
    private String nomeEspecialidade;
    private String bodyHtml;

    private EmailCancelamentoAulaAluno(String nomeAluno,
                                       String destinatario,
                                       LocalDateTime dataHoraAgendamento,
                                       String nomeSala,
                                       String nomeEspecialidade,
                                       String bodyHtml) {
        this.nomeAluno = nomeAluno;
        this.destinatario = destinatario;
        this.dataHoraAgendamento = dataHoraAgendamento;
        this.nomeSala = nomeSala;
        this.nomeEspecialidade = nomeEspecialidade;
        this.bodyHtml = bodyHtml;
    }

    public String getNomeAluno() {
        return nomeAluno;
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

    public static EmailCancelamentoAulaAluno criarEmailCancelamentoAulaAluno(
            String nomeAluno,
            String destinatario,
            LocalDateTime dataHoraAgendamento,
            String nomeSala,
            String nomeEspecialidade) {

        if (nomeAluno == null || nomeAluno.isBlank() ||
                destinatario == null || destinatario.isBlank() ||
                dataHoraAgendamento == null ||
                nomeSala == null || nomeSala.isBlank() ||
                nomeEspecialidade == null || nomeEspecialidade.isBlank()) {

            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm", new Locale("pt", "BR"));
        String dataHoraFormatada = dataHoraAgendamento.format(formatter);

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

                                    <!-- Logo -->
                                    <tr>
                                        <td align="center" style="padding:40px;">
                                            <img src="https://onepilates.com.br/wp-content/uploads/2026/03/logo-50px.png" width="160">
                                        </td>
                                    </tr>

                                    <!-- Linha vermelha -->
                                    <tr>
                                        <td style="height:4px; background-color:#FF0000;"></td>
                                    </tr>

                                    <!-- Conteúdo principal -->
                                    <tr>
                                        <td style="padding:40px;">
                                            <h1 style="font-size:22px; margin-top:0;">Sua aula foi cancelada</h1>

                                            <p>Olá <strong>%s</strong>,</p>

                                            <p>Informamos que a sua aula foi <strong>cancelada</strong>. Confira os detalhes abaixo:</p>

                                            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                                                   style="background-color:#f5f5f5; border-left:4px solid #FF0000; margin:20px 0;">
                                                <tr>
                                                    <td style="padding:16px 20px;">
                                                        <p style="margin:6px 0;"><strong>📅 Data e horário:</strong> %s</p>
                                                        <p style="margin:6px 0;"><strong>🏋️ Especialidade:</strong> %s</p>
                                                        <p style="margin:6px 0;"><strong>📍 Sala:</strong> %s</p>
                                                    </td>
                                                </tr>
                                            </table>

                                            <h2 style="font-size:17px; margin-top:28px;">O que fazer agora?</h2>

                                            <p>
                                                Caso deseje <strong>reagendar ou repor sua aula</strong>, entre em contato com a nossa
                                                equipe. Estamos à disposição para encontrar o melhor horário para você!
                                            </p>

                                            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                                                   style="background-color:#fff8f8; border:1px solid #ffe0e0; border-radius:6px; margin:20px 0;">
                                                <tr>
                                                    <td style="padding:20px;">
                                                        <p style="margin:0 0 8px 0; font-weight:600;">📞 Fale com a OnePilates:</p>
                                                        <p style="margin:4px 0;">• WhatsApp ou telefone da unidade</p>
                                                        <p style="margin:4px 0;">• Recepção presencial</p>
                                                        <p style="margin:4px 0;">• E-mail: <a href="mailto:contato@onepilates.com.br" style="color:#FF0000;">contato@onepilates.com.br</a></p>
                                                    </td>
                                                </tr>
                                            </table>

                                            <p style="font-size:13px; color:#777777;">
                                                ⚠️ <em>Lembramos que a reposição de aulas está sujeita à disponibilidade de horários e às
                                                políticas vigentes da unidade. Consulte nossa equipe para mais informações.</em>
                                            </p>

                                            <p>Agradecemos a sua compreensão e esperamos te ver em breve!</p>

                                            <p>Atenciosamente,<br>
                                            <strong>Equipe OnePilates</strong></p>
                                        </td>
                                    </tr>

                                    <!-- Rodapé -->
                                    <tr>
                                        <td style="background-color:#f9f9f9; text-align:center; padding:16px; font-size:12px; color:#999999;">
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
                .formatted(nomeAluno, dataHoraFormatada, nomeEspecialidade, nomeSala);

        return new EmailCancelamentoAulaAluno(
                nomeAluno,
                destinatario,
                dataHoraAgendamento,
                nomeSala,
                nomeEspecialidade,
                corpoHtml
        );
    }
}