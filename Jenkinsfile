pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm  // Récupère le code source du dépôt
            }
        }

        stage('Build') {
            steps {
                // Ajoute ici les étapes de build, par exemple pour Maven ou Gradle
                // Exemple pour Maven :
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Ajoute ici l'étape d'analyse SonarQube
                sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000'
            }
        }
    }
}
