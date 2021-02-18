pipeline {
  agent {
    kubernetes {
      // See comment in deplog-build.sh (gpg:sign-and-deploy-file)
      label 'centos-7'
    }
  }
  tools {
    maven 'apache-maven-latest'
    jdk 'oracle-jdk8-latest'
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
                clean build signJar createLocalMavenRepo \
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
            sh './releng/deploy-build.sh'
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
  }
}
