pipeline {
    agent any
    
    environment{
        HELLO_WORLD = 'Hello World this is a custom env variable'
        
    }


   tools {
        maven 'maven_3_3_9' 
    }
    stages {
        stage('Compile Stage') {
            steps {
                   echo "Custom env var: ${HELLO_WORLD}"
                   echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL} with Java: ${JAVA_HOME}"
                   bat 'mvn clean install -DskipTests'
            }
        }
        
        stage('Test Stage') {
            steps {
                   bat 'mvn test'
            }
        }
       
    }
    
    post{
            

        always {
        //junit 'build/reports/**/*.xml'
            mail to: 'vincenzo.lucente@finconsgroup.com'
                 subject: "completed pipeline"
                 body: "This is a message from Jenkins"
                  
            echo 'One way or another, I have finished'
            deleteDir() /* clean up our workspace */
        }
        success {
            echo 'I succeeeded!'
            //slackSend channel: '#ops-room',
            //          color: 'good',
            //       message: "The pipeline ${currentBuild.fullDisplayName} completed successfully."
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    
        
        
    }

}