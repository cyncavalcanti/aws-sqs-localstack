package com.aws.javasqssender;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.model.*;
import com.aws.javasqssender.Service.ServiceSender;
import com.aws.javasqssender.config.SqsConfigLocal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.amazonaws.services.sqs.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication(scanBasePackages = "com.aws.javasqssender")
public class JavaSqsSenderApplication {

	public static void main(String[] args) {

		SpringApplication.run(JavaSqsSenderApplication.class, args);
		//String queue = "https://sqs.us-west-2.amazonaws.com/123456/My-Sample-Standard-Queue";

		AmazonSQS sqsClient = new SqsConfigLocal().sqsClient();
		String queue_name = "teste";
		String queue = sqsClient.getQueueUrl(queue_name).getQueueUrl();

		SetQueueAttributesRequest logRequest = new SetQueueAttributesRequest().withQueueUrl(queue)
				.addAttributesEntry(QueueAttributeName.VisibilityTimeout.toString(), "5000");
		sqsClient.setQueueAttributes(logRequest);

		System.out.printf("Log Request Criada!!! URL: {}", logRequest.getSdkRequestTimeout());

		try {
			Map<String, MessageAttributeValue> messageAttributes = new HashMap<String, MessageAttributeValue>();

			messageAttributes.put("event", new MessageAttributeValue().withDataType("String"))
					.withStringValue("logfile");
		} catch (Exception e) {

		}

		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.withMessageBody("Essa mensagem foi criado por Alvin. #1");
		sendMessageRequest.withQueueUrl(queue);
		//sendMessageRequest.withMessageAttributes(messageAttributes);
		sqsClient.sendMessage(sendMessageRequest);

//		SendMessageRequest sendMessageRequest2 = new SendMessageRequest();
//		sendMessageRequest.withMessageBody("Essa mensagem foi criado por Alvin. #2");
//		sendMessageRequest.withQueueUrl(queue);
//		sqsClient.sendMessage(sendMessageRequest2);


		System.out.println("Mensagem Enviada com Sucesso");

	}

	/*public static void sendSingleMessage(AmazonSQSClient sqsClient, String queueUrl) {
		try {
			sqsClient.sendMessage(SendMessageRequest.builder()
					.queueUrl(queueUrl)
					.messageBody("Hello world from Java!")
					.delaySeconds(10)
					.build());
			System.out.println("Message has been sent successfully");
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}*/

}
