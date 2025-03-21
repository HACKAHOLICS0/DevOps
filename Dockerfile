# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/tp-foyer-5.0.0.jar tp-foyer.jar

# Expose the application port
EXPOSE 8089

# Command to run the application
ENTRYPOINT ["java", "-jar", "tp-foyer.jar"]