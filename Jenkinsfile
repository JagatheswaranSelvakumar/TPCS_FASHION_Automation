pipeline {
    agent any

    tools {
        git 'Default'             // Must match the Git tool name in Jenkins Global Tool Configuration
        maven 'Maven_3.8.1'      // Must match Maven installation name
        jdk 'JDK'                 // Must match JDK installation name
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
                echo "Checking Git version and path"
                sh 'which git'
                sh 'git --version'
                git branch: 'main', url: 'https://github.com/JagatheswaranSelvakumar/TPCS_FASHION_Automation.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building project using Maven"
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Executing tests on browser: ${params.BROWSER}"
                sh "mvn test -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Archive Reports & Screenshots') {
            steps {
                echo "Archiving test-output artifacts"
                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            }
        }

    } // end stages

    post {
        always {
            echo "Publishing Extent HTML Report"
            publishHTML([
                reportDir: 'test-output',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report',
                allowMissing: false,
                keepAll: true,
                alwaysLinkToLastBuild: true
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
