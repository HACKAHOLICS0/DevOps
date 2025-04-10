pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'http://192.168.50.4:9000'  // Nom du serveur configuré dans Jenkins
        SONARQUBE_TOKEN = credentials('sonar-token') // Stocké dans Jenkins Credentials
        NEXUS_URL = 'http://localhost:8081'
        NEXUS_REPO = 'maven-releases'
        NEXUS_CREDENTIALS_ID = 'nexus-credentials' // ID Jenkins des credentials Nexus
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
        DOCKER_IMAGE = 'firaszn/tp-foyer:5.0.0'
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "📥 Clonage du projet depuis GitHub..."
                git branch: 'firas', url: 'https://github.com/HACKAHOLICS0/DevOps.git'
                echo "✅ Code récupéré avec succès !"
            }
        }

        stage('Maven Clean') {
            steps {
                echo "🧹 Nettoyage du projet..."
                sh 'mvn clean'
                echo "✅ Nettoyage terminé !"
            }
        }

        stage('Maven Compile') {
            steps {
                echo "⚙️ Compilation du projet..."
                sh 'mvn compile'
                echo "✅ Compilation réussie !"
            }
        }

        stage('Run Tests') {
            steps {
                echo "🧪 Exécution des tests unitaires..."
                sh 'mvn test'
                echo "✅ Tests terminés !"
            }
            post {
                always {
                    // Publier les résultats des tests JUnit
                    junit '**/target/surefire-reports/*.xml'
                    
                    // Publier la couverture de code avec JaCoCo
                    jacoco(
                        execPattern: 'target/*.exec',
                        classPattern: 'target/classes',
                        sourcePattern: 'src/main/java',
                        exclusionPattern: 'src/test*'
                    )
                }
                success {
                    echo "✅ Tous les tests ont réussi !"
                }
                failure {
                    echo "❌ Certains tests ont échoué !"
                }
            }
        }

        stage('MVN SONARQUBE') {
            steps {
                echo "Exécution de l'analyse SonarQube..."
                sh '''
                   mvn sonar:sonar \
                   -Dsonar.projectKey=tpFoyeer \
                   -Dsonar.host.url=http://192.168.50.4:9000 \
                   -Dsonar.token=sqp_046aff1a5f29a22e4224f5a7d8c86bc35781365e \
                   -X
                '''
            }
        }
        
        stage('Deploy to Nexus') {
            steps {
                script {
                    echo '📦 Déploiement sur Nexus...'
                    withCredentials([usernamePassword(credentialsId: NEXUS_CREDENTIALS_ID, usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        sh """
                            mvn deploy
                        """
                    }
                }
            }
        }
        
        stage('Deploy Image') {
            steps {
                script {
                    echo "🐳 Construction de l'image Docker..."
                    sh "docker images | grep firaszn || echo 'Aucune image existante trouvée'"

                    sh "docker build -t $DOCKER_IMAGE ."

                    echo "✅ Vérification de l'image créée..."
                    sh "docker images | grep firaszn"

                    echo "📤 Connexion à Docker Hub et push de l'image..."
                    withDockerRegistry([credentialsId: DOCKER_CREDENTIALS_ID]) {
                        sh "docker push $DOCKER_IMAGE"
                    }

                    echo "✅ Image Docker publiée avec succès !"
                }
            }
        }
        
        stage('Run Docker Compose') {
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
