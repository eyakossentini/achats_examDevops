pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Fetch the code from the master branch of GitHub
                git branch: 'amira', url: 'https://github.com/eyakossentini/achats_examDevops.git'
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
                        -Dsonar.projectKey=achats_examDevops \
                        -Dsonar.host.url=http://192.168.50.4:9000 \
                        -Dsonar.login=a8f45dc77ff97dcb8f5f928c968514a14f91912a'
                }
            }
        }
         stage('Deploy to Nexus') {
                    steps {
                        // Étape de déploiement vers Nexus en sautant les tests
                        sh 'mvn deploy -X -DskipTests'
                    }
                }
         stage('Docker Image') {
                     steps {
                         sh 'docker build -t amirachartel/achat:1.0.0 .'
                     }
         }

                 stage('DockerHub') {
                     steps {
                         withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'amirachartel', passwordVariable: 'amira2115')]) {
                             sh 'docker login -u amirachartel -p amira2115'
                             sh 'docker push amirachartel/achat:1.0.0'
                         }
                     }
                 }
                               // stage('Run Docker Compose') {
                                            //steps {
                                               // script {
                                                    // Perform Docker login if needed
                                                  //  sh 'docker login -u amirachartel -p amira2115'

                                                     // Pull the Docker images if needed
                                                    // sh 'docker compose pull'

                                                      // Run Docker Compose
                                                   //sh 'docker compose up -d'
                                                //}
                                            //}
                                      //  }



   }
 }

