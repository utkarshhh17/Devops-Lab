pipeline {
    agent any
    
    environment {
        // Define SonarQube server configuration
        SONARQUBE_HOME = 'sonarqube'
        SONARQUBE_URL = 'http://localhost:9000'
        SONARQUBE_PROJECT_KEY = 'squ_48235a51609579ef8fe9cab1bda747aaf3985607'
        SONARQUBE_PROJECT_NAME = 'maven'

        DOCKER_IMAGE_BACKEND = 'utkarshhh17/maven-image'
        DOCKER_IMAGE_FRONTEND = 'utkarshhh17/frontend-image'
        
        LOCAL_IMAGE_NAME_BACKEND = 'maven-image'
        LOCAL_IMAGE_NAME_FRONTEND = 'frontend-image'

        KUBE_NAMESPACE = 'default'

        DEPLOYMENT_NAME_BACKEND = 'maven-container'
        DEPLOYMENT_NAME_FRONTEND = 'frontend-container'
        
        CONTAINER_NAME_BACKEND = 'maven-container' 
        CONTAINER_NAME_FRONTEND = 'frontend-container' 
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the Git repository
                git 'https://github.com/utkarshhh17/Devops-Lab'
            }
        }
        
        stage('Build and Test Backend') {
            steps {
                dir('backend') {
                    // Build and test the Maven project
                    bat 'mvn clean install'
                }
            }
        }

        stage('Build Backend Docker Image') {
            steps {
                dir('backend') {
                    // Build Docker image for backend
                    bat "docker build -t ${LOCAL_IMAGE_NAME_BACKEND} ."
                }
            }
        }

        stage('Tag and Push Backend Docker Image') {
            steps {
                dir('backend') {
                    // Tag and push Docker image for backend
                    bat "docker tag ${LOCAL_IMAGE_NAME_BACKEND} ${DOCKER_IMAGE_BACKEND}:latest"
                    bat "docker push ${DOCKER_IMAGE_BACKEND}"
                }
            }
        }

        stage('Build and Test Frontend') {
            steps {
                dir('client') {
                    // Build and test frontend (if applicable)
                    bat 'npm install'
                    // bat 'npm test'
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                dir('client') {
                    // Build Docker image for frontend
                    bat "docker build -t ${LOCAL_IMAGE_NAME_FRONTEND} ."
                }
            }
        }

        stage('Tag and Push Frontend Docker Image') {
            steps {
                dir('client') {
                    // Tag and push Docker image for frontend
                    bat "docker tag ${LOCAL_IMAGE_NAME_FRONTEND} ${DOCKER_IMAGE_FRONTEND}:latest"
                    bat "docker push ${DOCKER_IMAGE_FRONTEND}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig-credentials-id']) {
                    dir('backend') {
                        bat """
                        kubectl apply -f deployment.yaml -n ${KUBE_NAMESPACE}
                        kubectl set image deployment/${DEPLOYMENT_NAME_BACKEND} ${CONTAINER_NAME}=${DOCKER_IMAGE_NAME}:latest -n ${KUBE_NAMESPACE}
                        kubectl rollout status deployment/${DEPLOYMENT_NAME_BACKEND} -n ${KUBE_NAMESPACE}
                        """
                    }
                   
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(installationName:'sonarServer') {
                    dir('backend') {
                        bat 'mvn sonar:sonar -Dsonar.host.url=' + env.SONARQUBE_URL + ' -Dsonar.projectKey=' + env.SONARQUBE_PROJECT_KEY + ' -Dsonar.projectName=' + env.SONARQUBE_PROJECT_NAME
                    }
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