pipeline {
    agent any
    parameters {
           string(name: 'GIT_BRANCH', defaultValue: 'master', description: 'Git branch to build') // Example string parameter
           //string(name: 'IMAGE_TAG', defaultValue: 'latest', description: 'Docker image tag')     // Example string parameter
           choice(name: 'BUILD_ENV', choices: ['dev', 'test', 'prod'], description: 'Build environment') // Example choice parameter
           //booleanParam(name: 'DEPLOY_APP_RUNNER', defaultValue: true, description: 'Deploy to AWS App Runner?') // Example boolean parameter
       }

    environment {
        AWS_REGION = 'us-west-2'  // Replace with your AWS region
        ECR_REPO_NAME = 'your-ecr-repo-name'
        ECR_REGISTRY = 'YOUR_ACCOUNT_ID.dkr.ecr.YOUR_REGION.amazonaws.com'
        IMAGE_TAG = 'latest'
        AWS_CREDENTIALS_ID = 'aws-credentials-id'  // Replace with your Jenkins credentials ID
        REPOSITORY_URL = ${repository_url}
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: ${params.GIT_BRANCH}, url: 'https://github.com/ReemGharib/rfid-system
            }
        }

        stage('Build Spring Boot Application') {
            steps {
                bat 'mvn clean package'
            }
        }
    }
}
