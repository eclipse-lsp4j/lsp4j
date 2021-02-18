#!/bin/bash

set -u # run with unset flag error so that missing parameters cause build failure
set -e # error out on any failed commands
set -x # echo all commands used for debugging purposes


SSHUSER="genie.lsp4j@projects-storage.eclipse.org"
SSH="ssh ${SSHUSER}"
SCP="scp"


DOWNLOAD=download.eclipse.org/lsp4j/builds/$BRANCH_NAME
DOWNLOAD_MOUNT=/home/data/httpd/$DOWNLOAD

# Deploying build to nightly location on download.eclipse.org
if $SSH test -e ${DOWNLOAD_MOUNT}-new; then
    $SSH rm -r ${DOWNLOAD_MOUNT}-new
fi
if $SSH test -e ${DOWNLOAD_MOUNT}-last; then
    $SSH rm -r ${DOWNLOAD_MOUNT}-last
fi
$SSH mkdir -p ${DOWNLOAD_MOUNT}-new
$SCP -rp build/p2-repository/* "${SSHUSER}:"${DOWNLOAD_MOUNT}-new
if $SSH test -e ${DOWNLOAD_MOUNT}; then
    $SSH mv ${DOWNLOAD_MOUNT} ${DOWNLOAD_MOUNT}-last
fi
$SSH mv ${DOWNLOAD_MOUNT}-new ${DOWNLOAD_MOUNT}

# Only maven deploy specific branches
case $BRANCH_NAME in
    master | release_*)
        # GPG Sign and Deploy to Maven Central snapshot
        find build/maven-repository -name '*.pom' | while read i
        do
            base="${i%.*}"
            # The centos-7 agent is used because it provides gpg 2.0.x
            # and we sign for OSSRH with gpg maven plug-in run at the command
            # line.
            # If a newer GPG version (> 2.1+) is used,
            # --pinentry-mode loopback needs to be added as gpg argument in the pom.xml.
            # If centos changes we may need to add the gpg arguments to some pom.xml
            # somewhere
            mvn \
                gpg:sign-and-deploy-file \
                -DpomFile=${base}.pom \
                -Dfile=${base}.jar \
                -Dsources=${base}-sources.jar \
                -Djavadoc=${base}-javadoc.jar \
                -Durl=https://oss.sonatype.org/content/repositories/snapshots/ \
                -DrepositoryId=ossrh
        done
        ;;
    *)
        echo "Maven deployments only done on master and release branches"
        ;;
esac
