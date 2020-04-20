pipeline {
    agent {
        label 'Windows 10'
    }
    
    environment{
        HELLO_WORLD = 'Hello World this is a custom env variable'
        
    }


   tools {
        maven 'maven_3_3_9' 
    }
    stages {
        stage('Compile Stage') {
            steps {
                   echo ' Running ${env.BUILD_ID} on ${env.JENKINS_URL} with Java: ${JAVA_HOME}'
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