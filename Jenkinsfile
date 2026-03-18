pipeline {
    agent any

    tools {
        git 'Default'         // Name of your Git installation in Jenkins Global Tool Config
        maven 'Maven_3.8.1'  // Name of your Maven installation
        jdk 'JDK'             // Name of your JDK installation
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Select Browser for Test Execution'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out code from Git"
                git branch: 'main', 
                    url: 'https://github.com/JagatheswaranSelvakumar/TPCS_FASHION_Automation.git'
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
                echo "Running TestNG tests on ${params.BROWSER}"
                sh "mvn test -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo "Archiving test-output folder including ExtentReport assets"
                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            }
        }

    }

    post {
        always {
            echo "Publishing Extent HTML report"
            publishHTML([
                reportDir: 'test-output',          // folder containing HTML + CSS/JS/images
                reportFiles: 'ExtentReport.html',  // main HTML file
                reportName: 'Extent Report',
                allowMissing: false,
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
