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
                    sh 'mvn sonar:sonar'
                }
            }
        }
         stage('Deploy to Nexus') {
                    steps {
                        // Étape de déploiement vers Nexus en sautant les tests
                        sh 'mvn deploy -X -DskipTests'
                    }
                }



    }

}

