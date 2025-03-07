# Utilisation de OpenJDK 17 basé sur Alpine
FROM openjdk:17-jdk-alpine

# Exposer le port de l’application
EXPOSE 8082

# Ajouter le fichier JAR dans l’image Docker
ADD target/tp-foyer-1.0.jar tp-foyer-1.0.jar

# Commande pour exécuter l’application
ENTRYPOINT ["java", "-jar", "/tp-foyer-1.0.jar"]
