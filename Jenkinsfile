pipeline {
    agent any

    environment {
        AWS_REGION = 'us-west-2'  // Replace with your AWS region
        ECR_REPO_NAME = 'your-ecr-repo-name'
        ECR_REGISTRY = 'YOUR_ACCOUNT_ID.dkr.ecr.YOUR_REGION.amazonaws.com'
        IMAGE_TAG = 'latest'
        AWS_CREDENTIALS_ID = 'aws-credentials-id'  // Replace with your Jenkins credentials ID
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/ReemGharib/rfid-system'
            }
        }

        stage('Build Spring Boot Application') {
            steps {
                sh './mvnw clean package'
            }
        }
    }
}
