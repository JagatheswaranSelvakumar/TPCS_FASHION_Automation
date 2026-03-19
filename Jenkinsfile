pipeline {
    agent any

    tools {
        git 'Default'             // Must match the Git tool name in Jenkins Global Tool Configuration
        maven 'Maven_3.8.1'      // Must match Maven installation name
        jdk 'JDK'                 // Must match JDK installation name
    }

    environment {
        REPORT_PATH = "test-output/ExtentReport.html" // Where Extent generates report
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
            script {
                // 🔹 Compute test summary (if using TestNG/JUnit)
                def testResult = currentBuild.rawBuild.getAction(hudson.tasks.junit.TestResultAction)
                def total  = testResult?.totalCount ?: 0
                def failed = testResult?.failCount ?: 0
                def passed = total - failed

                // 🔹 Construct Extent report URL
                def reportUrl = "${env.BUILD_URL}artifact/${REPORT_PATH}"
                def statusColor = currentBuild.currentResult == 'SUCCESS' ? '#28a745' : '#dc3545'
                def statusIcon  = currentBuild.currentResult == 'SUCCESS' ? '✅' : '❌'

                // 🔹 Send HTML Email
                emailext(
                    subject: "${statusIcon} Automation Report - ${currentBuild.currentResult}",
                    mimeType: 'text/html',
                    to: "jagatheskmp@gmail.com", // Replace with your recipients

                    body: """
                    <html>
                    <body style="font-family: Arial, sans-serif; background:#f4f6f7; padding:20px;">

                        <div style="max-width:650px; margin:auto; background:#ffffff; border-radius:12px; padding:25px; box-shadow:0 4px 10px rgba(0,0,0,0.1);">

                            <h2 style="text-align:center; color:#2E86C1;">🚀 Automation Execution Report</h2>

                            <p><b>Project:</b> ${env.JOB_NAME}</p>
                            <p><b>Build:</b> #${env.BUILD_NUMBER}</p>

                            <p>
                                <b>Status:</b> 
                                <span style="color:${statusColor}; font-weight:bold;">
                                    ${currentBuild.currentResult}
                                </span>
                            </p>

                            <hr>

                            <h3>📊 Test Summary</h3>

                            <table style="width:100%; border-collapse: collapse; text-align:center;">
                                <tr style="background:#eee;">
                                    <th style="padding:10px; border:1px solid #ddd;">Total</th>
                                    <th style="padding:10px; border:1px solid #ddd; color:green;">Passed</th>
                                    <th style="padding:10px; border:1px solid #ddd; color:red;">Failed</th>
                                </tr>
                                <tr>
                                    <td style="padding:10px; border:1px solid #ddd;">${total}</td>
                                    <td style="padding:10px; border:1px solid #ddd; color:green;">${passed}</td>
                                    <td style="padding:10px; border:1px solid #ddd; color:red;">${failed}</td>
                                </tr>
                            </table>

                            <br>

                            <div style="text-align:center;">
                                <a href="${reportUrl}"
                                   style="background:${statusColor};
                                          color:#ffffff;
                                          padding:12px 20px;
                                          text-decoration:none;
                                          border-radius:6px;
                                          font-weight:bold;">
                                   🔍 View Extent Report
                                </a>
                            </div>

                            <br>

                            <p style="font-size:13px; color:#7f8c8d; text-align:center;">
                                🔗 Build URL: 
                                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a>
                            </p>

                        </div>

                    </body>
                    </html>
                    """
                )
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
