FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

COPY target/bankly-loan-service-0.0.1-SNAPSHOT.jar /app/bankly-loan-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java","-jar", "/app/bankly-loan-service-0.0.1-SNAPSHOT.jar"]