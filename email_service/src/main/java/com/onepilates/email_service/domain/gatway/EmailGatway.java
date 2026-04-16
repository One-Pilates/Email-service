package com.onepilates.email_service.domain.gatway;

public interface EmailGatway {
     void enviarEmail(String destinatario, String assunto, String corpoHtml);

}
