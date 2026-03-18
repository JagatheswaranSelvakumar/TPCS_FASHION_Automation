pipeline {
    agent any

    tools {
        git 'Default'              // Match the Git tool name in Jenkins Global Tool Configuration
        maven 'Maven_3.8.1'       // Match your installed Maven tool
        jdk 'JDK'                  // Match your installed JDK tool
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select Browser')
    }

    stages {

        stage('Checkout') {
            steps {
                echo '🔄 Checking out source code'
                sh 'which git'
                sh 'git --version'
                git branch: 'main', url: 'https://github.com/JagatheswaranSelvakumar/TPCS_FASHION_Automation.git'
            }
        }

        stage('Build') {
            steps {
                echo '🏗 Building the project with Maven'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                echo "🧪 Running TestNG tests on ${params.BROWSER}"
                sh "mvn test -Dbrowser=${params.BROWSER}"
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo '📦 Archiving test output'
                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            script {
                echo '📊 Publishing Extent HTML report'
               publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, icon: '', keepAll: true, reportDir: 'test-output/', reportFiles: 'ExtentReport.html', reportName: 'TPCS_FASHION_Automation_Report', reportTitles: '', useWrapperFileDirectly: true])
            }
        }

        success {
            echo '✅ Tests Passed'
        }

        failure {
            echo '❌ Tests Failed'
        }
    }
}
