# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application's JAR file into the container at /app
COPY target/springsecurity-0.0.1-SNAPSHOT.jar /app/springsecurity.jar

# Copy the application.properties file into the container
COPY src/main/resources/application.properties /app/application.properties

# Expose the port that your application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","springsecurity.jar"]
