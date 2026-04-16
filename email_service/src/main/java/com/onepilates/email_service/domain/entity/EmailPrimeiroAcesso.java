package com.onepilates.email_service.domain.entity;

public class EmailPrimeiroAcesso {

    private String destinatario;
    private String nome;
    private String senhaTemporaria;
    private String bodyHtml;

    private EmailPrimeiroAcesso(String destinatario, String nome, String senhaTemporaria, String bodyHtml) {
        this.destinatario = destinatario;
        this.nome = nome;
        this.senhaTemporaria = senhaTemporaria;
        this.bodyHtml = bodyHtml;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenhaTemporaria() {
        return senhaTemporaria;
    }

    public void setSenhaTemporaria(String senhaTemporaria) {
        this.senhaTemporaria = senhaTemporaria;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public static EmailPrimeiroAcesso criarEmailPrimeiroAcesso(String destinatario, String nomeFuncionario, String senhaTemp){

        if(destinatario == null || destinatario.isBlank() ||
                nomeFuncionario == null || nomeFuncionario.isBlank() ||
                senhaTemp == null || senhaTemp.isBlank()) {

            throw new IllegalArgumentException("Os campos não podem ser nulos ou vazios");
        }


        String bodyEmail =  """
                <!DOCTYPE html>
                        <html>
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Email OnePilates - Primeiro Acesso</title>
                            <link href="https://assets-cdn.wellhub.com/images/?su=https://images.partners.gympass.com/image/filename/250389/lg_858dc5781e84-ONE_SEM_FUNDO_COM_SLOGAN_01_01.jpg" rel="stylesheet" />
                        </head>
                        <body style="margin:0; padding:0; font-family:'Poppins', Arial, Helvetica, sans-serif;">
                            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0" style="background-color:#e5e5e5;">
                                <tr>
                                    <td align="center" style="padding:40px 20px;">
                                        <table role="presentation" width="600" cellpadding="0" cellspacing="0" border="0"
                                               style="background-color:#ffffff; border-radius:10px;">
                                            <tr>
                                                <td align="center" style="padding:40px;">
                                                    <img src="https://onepilates.com.br/site/wp-content/uploads/2018/11/logo-100px.png" alt="OnePilates"
                                                         width="160" style="display:block;">
                                                </td>
                                            </tr>
                
                                            <tr>
                                                <td style="height:4px; background-color:#FF6600;"></td>
                                            </tr>
                
                                            <tr>
                                                <td style="padding:40px;">
                                                    <h1 style="font-size:22px; font-weight:600; color:#1a1a1a;">
                                                        Suas Credenciais de Acesso
                                                    </h1>
                
                                                    <p style="font-size:15px; color:#333;">
                                                        Olá <strong>%s</strong>,
                                                    </p>
                
                                                    <p style="font-size:15px; color:#333; line-height:1.6;">
                                                        Seja bem-vindo(a) à <strong>OnePilates</strong>!<br>
                                                        Sua conta foi criada com sucesso. Abaixo estão suas credenciais para o primeiro acesso:
                                                    </p>
                
                                                    <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                                                           style="background-color:#f9f9f9; border:1px solid #e0e0e0; border-radius:6px; margin:24px 0;">
                                                        <tr>
                                                            <td style="padding:24px;">
                                                                <p style="margin:0 0 8px 0; font-size:13px; font-weight:600; color:#666; text-transform:uppercase;">
                                                                    Email de acesso (login)
                                                                </p>
                                                                <p style="margin:0; font-size:16px; font-weight:600; color:#1a1a1a;">
                                                                    %s
                                                                </p>
                
                                                                <div style="height:1px; background:#e0e0e0; margin:20px 0;"></div>
                
                                                                <p style="margin:0 0 8px 0; font-size:13px; font-weight:600; color:#666; text-transform:uppercase;">
                                                                    Senha temporária
                                                                </p>
                                                                <p style="margin:0; font-size:20px; font-weight:700; color:#FF6600;">
                                                                    %s
                                                                </p>
                
                                                                <p style="margin-top:20px; font-size:14px; color:#666;">
                                                                    Por segurança, altere sua senha assim que fizer login pela primeira vez.
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </table>
                
                                                    <p style="font-size:15px; color:#333;">
                                                        Qualquer dúvida, estamos à disposição.
                                                    </p>
                
                                                    <p style="margin-top:30px; font-size:14px; color:#666;">
                                                        Atenciosamente,<br>
                                                        <strong style="color:#FF6600;">Equipe OnePilates</strong>
                                                    </p>
                                                </td>
                                            </tr>
                
                                            <tr>
                                                <td style="background-color:#f9f9f9; text-align:center; padding:16px; font-size:12px; color:#999;">
                                                    Este é um e-mail automático. © 2025 OnePilates. Todos os direitos reservados.
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </body>
                        </html>
                """.formatted(
                nomeFuncionario,
                destinatario,
                senhaTemp);

            return new EmailPrimeiroAcesso(destinatario,nomeFuncionario,senhaTemp, bodyEmail);
    }
}
