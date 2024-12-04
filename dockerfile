FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/api.jar api.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "api.jar"]