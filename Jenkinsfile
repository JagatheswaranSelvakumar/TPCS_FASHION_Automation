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
        GMAIL_CREDENTIALS = credentials('gmail-jenkins-app-password')
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
            always {
                echo "Sending email notification..."
                emailext(
                    subject: "Jenkins Build ${currentBuild.fullDisplayName} - ${currentBuild.currentResult}",
                    body: """
                    <p>Build URL: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p>Status: ${currentBuild.currentResult}</p>
                    """,
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                    to: 'recipient@example.com',  // Replace with actual recipient
                    replyTo: "${GMAIL_CREDENTIALS_USR}",
                    from: "${GMAIL_CREDENTIALS_USR}"
                )
            }

            success {
                echo "✅ Build and tests succeeded!"
            }

            failure {
                echo "❌ Build or tests failed!"
            }
        }
}