FROM openjdk:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]
