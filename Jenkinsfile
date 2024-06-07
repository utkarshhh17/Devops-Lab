pipeline {
    agent any
    
    environment {
        // Define SonarQube server configuration
        SONARQUBE_HOME = 'sonarqube'
        SONARQUBE_URL = 'http://localhost:9000'
        SONARQUBE_PROJECT_KEY = 'sqp_7276bea56efd650425b9785a4359eced15ea0f23'
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
                withMaven(maven: 'Maven') {
                    bat 'mvn clean install'
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis
                withSonarQubeEnv('SonarQube') {
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
