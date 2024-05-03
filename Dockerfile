pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Fetch the code from the master branch of GitHub
                git branch: 'hadilBranch', url: 'https://github.com/eyakossentini/achats_examDevops.git'
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
                        -Dsonar.projectKey=achatProject_devops \
                        -Dsonar.host.url=http://192.168.50.4:9000 \
                        -Dsonar.login=squ_95fbe028824259f02d380b2c81faa275930ab7bc'
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
