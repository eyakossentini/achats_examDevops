FROM openjdk:17-jdk-alpine
RUN apk add openjdk17
EXPOSE 8082
ADD target/achat-1.0.jar achat-1.0.jar
ENTRYPOINT ["java","-jar","/tachat-1.0.jar"]
