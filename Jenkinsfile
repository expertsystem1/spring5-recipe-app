pipeline {
    agent any
   tools {
        maven 'maven_3_3_9' 
    }
    stages {
        stage('Compile Stage') {
            steps {
                   bat 'mvn clean install -DskipTests'
            }
        }
        
        stage('Test Stage') {
            steps {
                   bat 'mvn test'
            }
        }
       
    }
}