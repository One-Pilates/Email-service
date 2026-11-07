package com.onepilates.email_service.domain.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Moldura HTML comum dos e-mails enviados ao aluno (mesmo visual dos demais e-mails da OnePilates). */
final class EmailLayout {

    private static final DateTimeFormatter DATA_HORA =
            DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"));

    private EmailLayout() {
    }

    static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora.format(DATA_HORA);
    }

    /** Evita que um nome com {@code <} ou {@code &} quebre ou injete HTML no e-mail. */
    static String escapar(String texto) {
        return texto == null ? "" : texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    /**
     * @param titulo      título exibido no topo do conteúdo
     * @param corDestaque cor da faixa abaixo do logo (hex)
     * @param conteudo    HTML do miolo (já escapado pelo chamador)
     */
    static String montar(String titulo, String corDestaque, String conteudo) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Email OnePilates - %1$s</title>
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
                                        <td style="height:4px; background-color:%2$s;"></td>
                                    </tr>
                                    <tr>
                                        <td style="padding:40px;">
                                            <h1 style="font-size:22px;">%1$s</h1>
                                            %3$s
                                            <p>Equipe <strong>OnePilates</strong></p>
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
                """.formatted(titulo, corDestaque, conteudo);
    }

    static String detalhesDaAula(LocalDateTime dataHora, String especialidade, String sala, String professor) {
        StringBuilder html = new StringBuilder();
        html.append("<p><strong>Data e horário:</strong> ").append(escapar(formatarDataHora(dataHora))).append("</p>");
        html.append("<p><strong>Especialidade:</strong> ").append(escapar(especialidade)).append("</p>");
        html.append("<p><strong>Sala:</strong> ").append(escapar(sala)).append("</p>");
        if (professor != null && !professor.isBlank()) {
            html.append("<p><strong>Professor(a):</strong> ").append(escapar(professor)).append("</p>");
        }
        return html.toString();
    }
}
