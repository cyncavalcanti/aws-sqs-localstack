FROM openjdk:11
VOLUME /main-app
EXPOSE 8080
ADD target/app_sqs_sender.jar app_sqs_sender.jar
ENTRYPOINT ["java","-jar","/app_sqs_sender.jar"]
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar