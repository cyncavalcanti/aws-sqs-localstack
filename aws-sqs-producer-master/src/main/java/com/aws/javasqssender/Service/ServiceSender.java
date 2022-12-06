package com.aws.javasqssender.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import com.aws.javasqssender.config.SqsConfigLocal;
import com.aws.javasqssender.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceSender {

    AmazonSQS sqsClient = new SqsConfigLocal().sqsClient();

    Product product = new Product();

    ObjectMapper mapper = new ObjectMapper();

    String queue = sqsClient.getQueueUrl("teste").getQueueUrl();

    String json;

    private SetQueueAttributesRequest logRequest ;

    private SendMessageRequest sendMessageRequest = new SendMessageRequest();

    public ServiceSender(){
        this.logRequest = new SetQueueAttributesRequest().withQueueUrl(queue)
                .addAttributesEntry(QueueAttributeName.VisibilityTimeout.toString(), "5000");
        sqsClient.setQueueAttributes(logRequest);
    }

    public void SendMessage(){
        product.setCode("0001");
        product.setDescription("Teste Publish");

        try {
            json = mapper.writeValueAsString(product);
            System.out.println("ResultingJSONstring = " + json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sendMessageRequest.withMessageBody(json);
        sendMessageRequest.withQueueUrl(queue);
        //sendMessageRequest.withMessageAttributes(messageAttributes);
        sqsClient.sendMessage(sendMessageRequest);
    }

}
