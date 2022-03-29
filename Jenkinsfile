pipeline {
   agent any

   stages {
        stage('Build') {
         steps {
            echo "mvn clean compile"
         }
        }
        stage('Test') {
           steps {
              echo "mvn test"
           }
        }
        stage('Install') {
           steps {
              echo "mvn clean install"
           }
        }
   }
}