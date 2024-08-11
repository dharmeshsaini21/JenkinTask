pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat(script: 'mcn clean test', returnStatus: true)
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts 'target/surefire-reports/emailable-report.html'
      }
    }

    stage('Zipping'){
        steps {
            sh 'mkdir report'
            zip zipFile: 'reports.zip', archive: false, dir: 'target/surefire-reports'
            archiveArtifacts artifact: 'reports.zip', fingerprint: true
        }
    }

    stage('email Report') {
      steps {
        emailext(subject: 'Jenkins Multibranch Report',
        attachLog: true,
        body: 'PFA the Logs',
        from: 'dharmeshsaini9911@gmail.com',
        to: 'dharmeshsaini21@gmail.com')
      }
    }

  }
}