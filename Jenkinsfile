pipeline {
    agent any
    
    environment {
        // Define SonarQube server configuration
        SONARQUBE_HOME = 'sonarqube'
        SONARQUBE_URL = 'http://localhost:9000'
        SONARQUBE_PROJECT_KEY = 'squ_48235a51609579ef8fe9cab1bda747aaf3985607'
        SONARQUBE_PROJECT_NAME = 'maven'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the Git repository
                git 'https://github.com/utkarshhh17/Devops-Lab'
            }
        }
        
        stage('Build') {
            steps {
                // Build the Maven project
                bat 'mvn clean install'
            }
            
        }
        
        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis
                withSonarQubeEnv(installationName:'sonarServer') {
                      bat 'mvn sonar:sonar -Dsonar.host.url=' + env.SONARQUBE_URL + ' -Dsonar.projectKey=' + env.SONARQUBE_PROJECT_KEY + ' -Dsonar.projectName=' + env.SONARQUBE_PROJECT_NAME
                }
            }
        }
    }
    
    post {
        success {
            // If the build is successful, display a success message
            echo 'Build successful! SonarQube analysis completed.'
        }
        
        failure {
            // If the build fails, display an error message
            echo 'Build failed! SonarQube analysis could not be completed.'
        }
    }
}
