package com.onepilates.email_service.infrastructure.consumer;



import com.onepilates.email_service.infrastructure.dto.EmailRequestDTO;
import com.onepilates.email_service.application.usecase.ValidarQualTipoEmailUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class OlharListaDeEmailConsumer {

    private ValidarQualTipoEmailUseCase validarQualTipoEmailUseCase;

    public OlharListaDeEmailConsumer(ValidarQualTipoEmailUseCase validarQualTipoEmailUseCase) {
        this.validarQualTipoEmailUseCase = validarQualTipoEmailUseCase;
    }


    @RabbitListener(queues = "email.queue")
    public void consumir(byte[] body) {

        try {
            String mensagem = new String(body);

            ObjectMapper objectMapper = new ObjectMapper();

            EmailRequestDTO dto = objectMapper.readValue(mensagem, EmailRequestDTO.class);

            System.out.println("📩 Tipo: " + dto.getTypeEmail());
            System.out.println("📩 Destinatário: " + dto.getDestinatario());
            System.out.println("📩 Payload: " + dto.getPayload());

            validarQualTipoEmailUseCase.executar(dto);


        } catch (Exception e) {
            System.out.println("❌ Erro ao converter mensagem");
            e.printStackTrace();
        }
    }
}
