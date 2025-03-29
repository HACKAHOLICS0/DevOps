FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy the JAR file into the container
COPY target/tp-foyer-5.0.0.jar app.jar

# Add wait-for script for database readiness
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh

EXPOSE 8089

# Command to run the application
ENTRYPOINT ["/wait-for.sh", "db:3306", "--", "java", "-jar", "app.jar"]