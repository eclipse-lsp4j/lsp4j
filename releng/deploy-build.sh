#!/bin/bash

set -u # run with unset flag error so that missing parameters cause build failure
set -e # error out on any failed commands
set -x # echo all commands used for debugging purposes

DOWNLOAD=download.eclipse.org/lsp4j/builds/$BRANCH_NAME
DOWNLOAD_MOUNT=/home/data/httpd/$DOWNLOAD

# Deploying build to nightly location on download.eclipse.org
if test -e ${DOWNLOAD_MOUNT}-new; then
    rm -r ${DOWNLOAD_MOUNT}-new
fi
if test -e ${DOWNLOAD_MOUNT}-last; then
    rm -r ${DOWNLOAD_MOUNT}-last
fi
mkdir -p ${DOWNLOAD_MOUNT}-new
cp -rpvf build/p2-repository/* ${DOWNLOAD_MOUNT}-new
if test -e ${DOWNLOAD_MOUNT}; then
    mv ${DOWNLOAD_MOUNT} ${DOWNLOAD_MOUNT}-last
fi
mv ${DOWNLOAD_MOUNT}-new ${DOWNLOAD_MOUNT}

# Only maven deploy specific branches
case $BRANCH_NAME in
    master | release_*)
        # GPG Sign and Deploy to Maven Central - either snapshot or staging
        find build/maven-repository -name '*.pom' | while read i
        do
            base="${i%.*}"
            /shared/common/apache-maven-latest/bin/mvn \
                -s /opt/public/hipp/homes/genie.lsp4j/.m2/settings-deploy-ossrh.xml \
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
