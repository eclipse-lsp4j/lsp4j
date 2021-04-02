pipeline {
  agent {
    kubernetes {
        // See comment in release-eclipse.sh (gpg:sign-and-deploy-file)
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
  parameters {
    booleanParam(defaultValue: true, description: 'Do a dry run of the build. All commands will be echoed. First run with this on, then when you are sure it is right, choose rebuild in the passing job and uncheck this box', name: 'DRY_RUN')
    string(defaultValue: 'updates/milestones/S201911261515', description: 'The relative path in LSP4J downloads area to publish promoted build to (e.g. updates/milestones/S201911261515, updates/releases/0.13.0)', name: 'LSP4J_PUBLISH_LOCATION')
    string(defaultValue: 'lsp4j-multi-build/job/master', description: 'The LSP4J project name being promoted from (e.g. lsp4j-multi-build/job/master or lsp4j-multi-build/job/release_0.13.0).', name: 'PROJECT')
    string(defaultValue: '12345', description: 'The CI build number being promoted from', name: 'LSP4J_BUILD_NUMBER')
  }
  stages {
    stage('Upload') {
      steps {
        withCredentials([file(credentialsId: 'secret-subkeys.asc', variable: 'KEYRING')]) {
            sh 'gpg --batch --import "${KEYRING}"'
            sh 'for fpr in $(gpg --list-keys --with-colons  | awk -F: \'/fpr:/ {print $10}\' | sort -u); do echo -e "5\ny\n" |  gpg --batch --command-fd 0 --expert --edit-key ${fpr} trust; done'
        }
        sshagent ( ['projects-storage.eclipse.org-bot-ssh']) {
          sh './releng/release-eclipse.sh'
        }
      }
    }
  }
}
