# Utilisation de OpenJDK 17 basé sur Alpine
FROM openjdk:17-jdk-alpine

# Définition du répertoire de travail
WORKDIR /app

# Ajouter le fichier JAR dans l’image Docker
ADD target/tp-foyer-1.0.0.jar app.jar

# Exposer le port de l’application (même que dans application.properties)
EXPOSE 8089

# Commande pour exécuter l’application
ENTRYPOINT ["java", "-jar", "app.jar"]