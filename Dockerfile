FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/tp-foyer-*.jar
ARG BUILD_DATE
ARG VERSION
ARG COMMIT

LABEL org.opencontainers.image.created=$BUILD_DATE \
      org.opencontainers.image.version=$VERSION \
      org.opencontainers.image.revision=$COMMIT

WORKDIR /app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]