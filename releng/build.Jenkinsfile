pipeline {
  agent any
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
          sh "/shared/common/apache-maven-latest/bin/mvn \
                -f releng/pom.xml \
                -Dmaven.repo.local=.repository \
                --batch-mode --update-snapshots \
                clean install -Psign \
                "
        }
      }
    }
    // Skip Deploy on release builds
    // stage('Deploy Snapshot') {
    //   steps {
    //     timeout(activity: true, time: 20) {
    //       sh './releng/deploy-build.sh'
    //     }
    //   }
    // }
  }
  post {
    always {
      junit '**/build/test-results/test/*.xml'
      archiveArtifacts 'build/**'
    }
  }
}
