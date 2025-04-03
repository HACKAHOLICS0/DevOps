FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/tp-foyer-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]