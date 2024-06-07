pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your version control system
                git 'https://github.com/utkarshhh17/Devops-Lab'
            }
        }
        
        stage('Build') {
            steps {
                // Build the Maven project
                bat 'mvn clean install'
            }
        }
    }
    
    post {
        success {
            // If the build is successful, archive the generated artifacts
            archiveArtifacts artifacts: 'target/*.jar', onlyIfSuccessful: true
        }
    }
}
