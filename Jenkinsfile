pipeline {
    agent any
    
    
    environment {
        // Define SonarQube server configuration
        SONARQUBE_HOME = 'sonarqube'
        SONARQUBE_URL = 'http://localhost:9000'
        SONARQUBE_PROJECT_KEY = 'squ_48235a51609579ef8fe9cab1bda747aaf3985607'
        SONARQUBE_PROJECT_NAME = 'maven'

        DOCKER_IMAGE_NAME = 'utkarshhh17/maven-image'
        LOCAL_IMAGE_NAME = 'maven-image'
        
        KUBE_NAMESPACE = 'default'

        DEPLOYMENT_NAME = 'maven-container'
        CONTAINER_NAME = 'maven-container' 

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
        stage('Test') {
            steps {
                // Run tests
                bat 'mvn test'
            }
        }
        stage('Build Docker') {
            steps {
                bat "docker build -t ${LOCAL_IMAGE_NAME} ."
            }
        }

        stage('Tag Docker Image') {
            steps {
                bat "docker tag ${LOCAL_IMAGE_NAME} ${DOCKER_IMAGE_NAME}:latest"
            }
        }
        stage('Docker Login and Push') {
            steps {
                    // Perform Docker login
                    bat 'docker login -u utkarshhh17 -p roronoazoro1'
  
                    // Push the Docker image
                    bat "docker push ${DOCKER_IMAGE_NAME}"
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig-credentials-id']) {
                    bat """
                    kubectl apply -f deployment.yaml -n ${KUBE_NAMESPACE}
                    kubectl set image deployment/${DEPLOYMENT_NAME} ${CONTAINER_NAME}=${DOCKER_IMAGE_NAME}:latest -n ${KUBE_NAMESPACE}
                    kubectl rollout status deployment/${DEPLOYMENT_NAME} -n ${KUBE_NAMESPACE}
                    """
                }
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
