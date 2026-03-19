pipeline {
    agent any

    tools {
       git 'Default'             // Must match the Git tool name in Jenkins Global Tool Configuration
       maven 'Maven_3.8.1'      // Must match Maven installation name
       jdk 'JDK'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select Browser')
    }

    environment {
        ALLURE_RESULTS = "target/allure-results"
        ALLURE_REPORT  = "target/site/allure-maven"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/JagatheswaranSelvakumar/TPCS_FASHION_Automation.git'
            }
        }

        stage('Clean & Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                sh "mvn test -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh "mvn allure:report"
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            // Publish Allure Report in Jenkins
            allure results: [[path: "${env.ALLURE_RESULTS}"]], reportBuildPolicy: 'ALWAYS'
        }

        success {
            echo '✅ Tests Passed!'
        }

        failure {
            echo '❌ Tests Failed!'
        }
    }
}