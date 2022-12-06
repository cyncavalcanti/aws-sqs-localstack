package com.aws.javasqssender.controller;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.aws.javasqssender.Service.ServiceSender;
import com.aws.javasqssender.config.SqsConfigLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.eventstream.MessageBuilder;


import java.util.List;
@RestController
public class Controller {

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    //Rota para testar se o projeto tá mandando mensagem
    @GetMapping("/TesteSend")
    public ResponseEntity<?> TesteSend() {

        LOG.info("Teste Rota Controler");

        try {
            ServiceSender service = new ServiceSender();
            service.SendMessage();
            LOG.info("Mensagem Após Publicação do SQS com Sucesso!!!");
        } catch (Exception e) {
            LOG.error("Falha ao publicar a mensagem no SQS!!! Erro: " + e);
        }

        return ResponseEntity.ok("TesteRotaOk");

    }
    @GetMapping("/vermensagem")
    public ResponseEntity<?> VerMensagem(){
        LOG.info("Vendo mensagem");
        AmazonSQS sqsClient = new SqsConfigLocal().sqsClient();
        String queue_name = "teste";
        String queue = sqsClient.getQueueUrl(queue_name).getQueueUrl();
        List<Message> messages = sqsClient.receiveMessage(queue).getMessages();
        System.out.println(messages);
        return ResponseEntity.ok(messages);
    }

}
