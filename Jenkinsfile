pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Fetch the code from the master branch of GitHub
                git branch: 'manarbranch', url: 'https://github.com/eyakossentini/achats_examDevops.git'
            }
        }

        stage('Clean') {
            steps {
                // Clean the Maven project
                sh 'mvn clean '
            }
        }

        stage('Compile') {
            steps {
                // Compile the Maven project
                sh 'mvn compile'
            }
        }

        stage('Test Junit & Mockito'){
            steps{
                sh 'mvn test'
            }
        }

        stage('SonarQube analysis') {
            steps {
                // Exécution de l'analyse de code avec SonarQube
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar \
                        -Dsonar.login=squ_1dd8d7bb678d9998d88b3819ce107099472d7a21'
                }
            }
        }

         stage('Deploy to Nexus') {
                    steps {
                        // Étape de déploiement vers Nexus en sautant les tests
                        sh 'mvn deploy'
                    }
         }

         stage('Docker Image') {
                     steps {
                         sh 'docker build -t manarkhemir/achat:1.0.0 .'
                     }
         }

                 stage('DockerHub') {
                     steps {
                         withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'manar.khemir@esprit.tn', passwordVariable: 'dockerhub')]) {
                             sh 'docker login -u manar.khemir@esprit.tn -p dockerhub'
                             sh 'docker push manarkhemir/achat:1.0.0'
                         }
                     }
                 }

               stage('Run Docker Compose') {
                           steps {
                               script {
                                   // Perform Docker login if needed
                                   sh 'docker login -u manar.khemir@esprit.tn -p dockerhub'

                                    // Pull the Docker images if needed
                                    sh 'docker-compose pull'

                                     // Run Docker Compose
                                  sh 'docker-compose up -d'
                               }
                           }
                       }
                stage('Prometheus') {
                           steps {
                               sh 'docker compose up -d prometheus-p'
                           }
                       }

                       stage('Grafana') {
                           steps {
                               sh 'docker compose up -d grafana'
                           }
                       }

   }
 }
