package com.onepilates.email_service.infrastructure.dto;

import com.onepilates.email_service.domain.enums.TypeEmail;

public class EmailRequestDTO {
    private TypeEmail typeEmail;
    private String destinatario;
    private Object payload;

    public EmailRequestDTO(TypeEmail typeEmail, String destinatario, Object payload) {
        this.typeEmail = typeEmail;
        this.destinatario = destinatario;
        this.payload = payload;
    }

    public TypeEmail getTypeEmail() {
        return typeEmail;
    }

    public void setTypeEmail(TypeEmail typeEmail) {
        this.typeEmail = typeEmail;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "EmailRequestDTO{" +
                "typeEmail=" + typeEmail +
                ", destinatario='" + destinatario + '\'' +
                ", payload=" + payload +
                '}';
    }
}
