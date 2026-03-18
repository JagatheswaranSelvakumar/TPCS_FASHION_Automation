pipeline {
    agent any

    tools {
        maven 'Maven_3.8.1'
        jdk 'JDK'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select Browser')
    }

    stages {

        stage('Checkout') {
            steps {
                sh 'which git'
                sh 'git --version'
                git 'https://github.com/JagatheswaranSelvakumar/TPCS_FASHION_Automation.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                sh "mvn test -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            publishHTML([
                reportDir: 'test-output',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }

        success {
            echo '✅ Tests Passed'
        }

        failure {
            echo '❌ Tests Failed'
        }
    }
}
