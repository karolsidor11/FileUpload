FROM openjdk:17-jdk-alpine
LABEL authors="Karol"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} fileUpload.jar
ENTRYPOINT ["java","-jar","/fileUpload.jar"]