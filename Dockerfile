FROM openjdk:17-jdk-slim

COPY target/loan-0.0.1-SNAPSHOT.jar /app/loan-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java","-jar", "/app/loan-0.0.1-SNAPSHOT.jar"]