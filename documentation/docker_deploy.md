# Steps to follow to create a Docker Image

## Create a `Dockerfile` file

```dockerfile
# Use a Java base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file to the image
COPY target/tu-archivo-api.jar app.jar

# Expose the port
EXPOSE 9090

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Build the JAR file

Using Maven, run the command `mvn clean package`.

## Build the Docker image

On the same directory that the `dockerfile` is, run the command `docker build -t username/repository:latest .`

## Upload the image to Docker Hub

If you haven't logged in, use `docker login`, then run `docker push user/repository:latest`.
