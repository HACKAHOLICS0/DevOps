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
                script {
                    echo "🚀 Démarrage des services avec Docker Compose..."
                    sh "docker compose up -d"
                    echo "✅ Services Docker Compose démarrés en arrière-plan"
                }
            }
        }
    }
    
    post {
        success {
            echo "\033[1;32m🎉 Pipeline terminé avec succès ✅\033[0m"
        }
        failure {
            echo "\033[1;31m❌ Pipeline échoué ! Vérifiez les logs. \033[0m"
        }
    }
}
