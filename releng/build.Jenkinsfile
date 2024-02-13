pipeline {
  agent {
    kubernetes {
      // See comment in deplog-build.sh (gpg:sign-and-deploy-file)
      inheritFrom 'centos-7'
    }
  }
  tools {
    maven 'apache-maven-latest'
    jdk 'temurin-jdk11-latest'
  }
  options {
    timestamps()
    disableConcurrentBuilds()
  }
  stages {
    stage('Clean') {
      steps {
        timeout(activity: true, time: 20) {
          sh '''
                git status --ignored
                git clean -dfx
                git reset --hard HEAD
                git status --ignored
             '''
        }
      }
    }
    stage('Gradle') {
      steps {
        timeout(activity: true, time: 20) {
          sh "echo $JAVA_HOME"
          sh "java -version"
          sh "which java"
          sh "./gradlew \
                --no-daemon \
                -PignoreTestFailures=true \
                --refresh-dependencies \
                --continue \
                clean build signJar publish \
                "
        }
      }
    }
    stage('Maven') {
      steps {
        timeout(activity: true, time: 20) {
          sh "mvn \
                -f releng/pom.xml \
                -Dmaven.repo.local=.repository \
                --batch-mode --update-snapshots \
                clean install -Psign \
                "
        }
      }
    }
    stage('japicmp') {
      steps {
        timeout(activity: true, time: 20) {
          sh "./releng/runjapicmp.sh"
        }
      }
    }
    stage('Deploy Snapshot') {
      steps {
        timeout(activity: true, time: 20) {
          withCredentials([file(credentialsId: 'secret-subkeys.asc', variable: 'KEYRING')]) {
              sh 'gpg --batch --import "${KEYRING}"'
              sh 'for fpr in $(gpg --list-keys --with-colons  | awk -F: \'/fpr:/ {print $10}\' | sort -u); do echo -e "5\ny\n" |  gpg --batch --command-fd 0 --expert --edit-key ${fpr} trust; done'
          }
          sshagent ( ['projects-storage.eclipse.org-bot-ssh']) {
            // Skip Deploy on release builds
            // XXX: Can release vs snapshot be detected automatically so that
            // the following line does not have to be commented/uncommented
            // on each change to/from SNAPSHOT?
            // sh './releng/deploy-build.sh'
          }
        }
      }
    }
  }
  post {
    always {
      junit '**/build/test-results/test/*.xml'
      archiveArtifacts 'build/**'
    }
    cleanup {
      script {
        def curResult = currentBuild.currentResult
        def lastResult = 'NEW'
        if (currentBuild.previousBuild != null) {
          lastResult = currentBuild.previousBuild.result
        }

        if (curResult != 'SUCCESS' || lastResult != 'SUCCESS') {
          def color = ''
          switch (curResult) {
            case 'SUCCESS':
              color = '#00FF00'
              break
            case 'UNSTABLE':
              color = '#FFFF00'
              break
            case 'FAILURE':
              color = '#FF0000'
              break
            default: // e.g. ABORTED
              color = '#666666'
          }

          matrixSendMessage https: true,
            hostname: 'matrix.eclipse.org',
            accessTokenCredentialsId: "matrix-token",
            roomId: '!OVFCkkbjNkALzklVdr:matrix.eclipse.org',
            body: "${lastResult} => ${curResult} ${env.BUILD_URL} | ${env.JOB_NAME}#${env.BUILD_NUMBER}",
            formattedBody: "<div><font color='${color}'>${lastResult} => ${curResult}</font> | <a href='${env.BUILD_URL}' target='_blank'>${env.JOB_NAME}#${env.BUILD_NUMBER}</a></div>"
        }
      }
    }
  }
}
