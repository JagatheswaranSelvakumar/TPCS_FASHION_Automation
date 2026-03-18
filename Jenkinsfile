pipeline {
    agent any

    tools {
        git 'Default'           // Must match the Name you set in Global Tool Configuration
        maven 'Maven_3.8.1'     // Must match installed Maven version in Jenkins
        jdk 'JDK'               // Must match installed JDK in Jenkins
    }

    parameters {
        choice(
            name: 'BROWSER', 
            choices: ['chrome', 'firefox', 'edge'], 
            description: 'Select Browser for tests'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out code from Git"
                sh 'which git'
                sh 'git --version'
                git branch: 'main', url: 'https://github.com/JagatheswaranSelvakumar/TPCS_FASHION_Automation.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building the project with Maven"
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running tests on browser: ${params.BROWSER}"
                sh "mvn test -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Archive Reports') {
            steps {
                echo "Archiving test-output artifacts"
                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            }
        }

    }

    post {
        always {
            echo "Publishing Extent HTML Report"
            publishHTML([
                reportDir: 'test-output',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report',
                allowMissing: true,
                alwaysLinkToLastBuild: true
            ])
        }

        success {
            echo '✅ All tests passed successfully!'
        }

        failure {
            echo '❌ Some tests failed. Check reports for details.'
        }
    }
}
