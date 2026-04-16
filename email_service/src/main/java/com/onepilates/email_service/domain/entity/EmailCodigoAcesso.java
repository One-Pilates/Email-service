package com.onepilates.email_service.domain.entity;

public class EmailCodigoAcesso {

    private String nomeFuncionario;
    private String destinatario;
    private String codigo;
    private String bodyHtml;

    private EmailCodigoAcesso(String nomeFuncionario,
                              String destinatario,
                              String codigo,
                              String bodyHtml) {
        this.nomeFuncionario = nomeFuncionario;
        this.destinatario = destinatario;
        this.codigo = codigo;
        this.bodyHtml = bodyHtml;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public static EmailCodigoAcesso criarEmailCodigoAcesso(
            String nomeFuncionario,
            String destinatario,
            String codigo) {

        if (nomeFuncionario == null || nomeFuncionario.isBlank() ||
                destinatario == null || destinatario.isBlank() ||
                codigo == null || codigo.isBlank()) {

            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }

        String corpoHtml = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Email OnePilates - Redefinição de Senha</title>
                <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet" />
            </head>
            <body style="margin:0; padding:0; font-family:'Poppins', Arial, Helvetica, sans-serif;">
                <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0" style="background-color:#e5e5e5;">
                    <tr>
                        <td align="center" style="padding:40px 20px;">
                            <table role="presentation" width="600" cellpadding="0" cellspacing="0" border="0" style="background-color:#ffffff; border-radius:10px;">
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
                                        <h1 style="font-size:22px;">Redefinição de senha</h1>
                                        <p>Olá <strong>%s</strong>,</p>
                                        <p>
                                            Recebemos uma solicitação para redefinir sua senha na <strong>OnePilates</strong>.<br>
                                            Utilize o código abaixo:
                                        </p>

                                        <div style="margin:30px auto; display:inline-block; background-color:#FF6600; color:#ffffff; padding:16px 36px; border-radius:8px; font-size:24px; font-weight:600; letter-spacing:3px;">
                                            %s
                                        </div>

                                        <p style="font-size:14px; color:#666;">
                                            Este código é válido por 5 minutos.
                                        </p>

                                        <p style="margin-top:30px;">
                                            Atenciosamente,<br>
                                            <strong>Equipe OnePilates</strong>
                                        </p>
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
                .formatted(nomeFuncionario, codigo);

        return new EmailCodigoAcesso(
                nomeFuncionario,
                destinatario,
                codigo,
                corpoHtml
        );
    }
}
