# Using OpenJDK 17 based on Alpine
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Add the JAR file to Docker image (updated to release version)
ADD target/tp-foyer-5.0.0.jar app.jar

# Expose application port (same as in application.properties)
EXPOSE 8089

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
