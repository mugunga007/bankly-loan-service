FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

COPY /target/loan-0.0.1-SNAPSHOT.jar /app/loan-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java","-jar", "/app/loan-0.0.1-SNAPSHOT.jar"]