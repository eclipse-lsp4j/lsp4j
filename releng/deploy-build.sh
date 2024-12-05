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
    main | release_*)
        # GPG Sign and Deploy to Maven Central snapshot
        find build/maven-repository -name '*.pom' | while read i
        do
            base="${i%.*}"
            # See https://wiki.eclipse.org/Jenkins#How_can_artifacts_be_deployed_to_OSSRH_.2F_Maven_Central.3F for more info
            # on the Eclipse Foundation specific settings.
            mvn -X \
                org.apache.maven.plugins:maven-gpg-plugin:1.6:sign-and-deploy-file \
                -DpomFile=${base}.pom \
                -Dfile=${base}.jar \
                -Dfiles=${base}-sources.jar,${base}-javadoc.jar \
                -Dclassifiers=sources,javadoc \
                -Dtypes=jar,jar \
                -Durl=https://oss.sonatype.org/content/repositories/snapshots/ \
                -DrepositoryId=ossrh
        done
        ;;
    *)
        echo "Maven deployments only done on main and release branches"
        ;;
esac
