pipeline {
    agent any

    tools {
        jdk 'JDK_21'          // Make sure this matches your Global Tool Config
        maven 'Maven_3.8.1'   // Make sure this matches your Maven tool name
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