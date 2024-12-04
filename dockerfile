FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/api-1.1.jar api.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "api.jar"]