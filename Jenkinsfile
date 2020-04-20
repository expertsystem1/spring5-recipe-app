pipeline {
    agent any
   tools {
        maven 'maven_3_3_9' 
    }
    stages {
        stage('Compile Stage') {
            steps {
                   bat 'mvn clean compile'
            }
        }
        
        stage('Test Stage') {
            steps {
                   bat 'mvn test'
            }
        }
        
        stage('Deployment Stage') {
            steps {
                   bat 'mvn deploy'
            }
        }
    }
}