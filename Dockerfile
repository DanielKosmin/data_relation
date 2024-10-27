# Use a base image with the JDK
FROM openjdk:17-jdk-slim

# Set up working directory
WORKDIR /app

# Copy the JAR file built by Gradle
COPY build/libs/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run your app
ENTRYPOINT ["java", "-jar", "app.jar"]
