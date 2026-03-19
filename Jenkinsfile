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
                script {
                    allure(
                            commandline: 'Allure',               // Must match the Allure CLI name in Jenkins
                            results: [[path: "${env.ALLURE_RESULTS}"]],
                            includeProperties: false,
                            jdk: '',
                            reportBuildPolicy: 'ALWAYS'
                    )
                }
            }
        }
    }

    post {
        always {
            // Inject credentials safely
            withCredentials([usernamePassword(
                    credentialsId: 'GMAIL_CREDENTIALS',
                    usernameVariable: 'GMAIL_USER',
                    passwordVariable: 'GMAIL_PASS'
            )]) {
                echo "Sending Email..."

                emailext(
                        subject: "Build ${currentBuild.currentResult}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",

                        body: """
                    <h2>Automation Test Report</h2>

                    <p><b>Project:</b> ${env.JOB_NAME}</p>
                    <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
                    <p><b>Status:</b> ${currentBuild.currentResult}</p>

                    <p><b>Allure Report:</b> 
                    <a href="${env.BUILD_URL}allure">Click Here</a></p>

                    <p><b>Console Output:</b> 
                    <a href="${env.BUILD_URL}console">View Logs</a></p>
                    """,

                        mimeType: 'text/html',
                        to: 'jagatheskmp@gmail.com'
                )
            }
        }
    }
}