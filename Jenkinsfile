pipeline {
    agent any

    tools {
        git 'Default'                // Name of Git installation in Jenkins
        maven 'Maven_3.8.1'         // Name of Maven installation in Jenkins
        jdk 'JDK'                    // Name of JDK installation in Jenkins
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Select Browser for the tests'
        )
    }

    environment {
        ALLURE_RESULTS = "target/allure-results"
        ALLURE_REPORT  = "target/site/allure-maven"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out repository..."
                git branch: 'main', url: 'https://github.com/JagatheswaranSelvakumar/TPCS_FASHION_Automation.git'
            }
        }

        stage('Clean & Build') {
            steps {
                echo "Building project with Maven..."
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running tests on ${params.BROWSER}..."
                sh "mvn test -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Archive Test Artifacts') {
            steps {
                echo "Archiving test reports and screenshots..."
                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            }
        }

        stage('Publish Allure Report') {
            steps {
                echo "Generating Allure report..."
                allure(
                    commandline: 'Allure',               // Must match the Allure CLI name in Jenkins
                    results: [[path: "${env.ALLURE_RESULTS}"]],
                    includeProperties: false,
                    reportBuildPolicy: 'ALWAYS'
                )
            }
        }
    }

    post {


        success {
            echo "✅ Tests passed successfully!"
        }

        failure {
            echo "❌ Tests failed!"
        }
    }
}