#!/bin/bash

set -u # run with unset flag error so that missing parameters cause build failure
set -e # error out on any failed commands
set -x # echo all commands used for debugging purposes


# This script is expected to be run with CWD of ./releng so that when maven runs below
# with the -f flag the CWD of Maven is the same as the rest of ths script.

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
$SCP -rp ../build/p2-repository/* "${SSHUSER}:"${DOWNLOAD_MOUNT}-new
if $SSH test -e ${DOWNLOAD_MOUNT}; then
    $SSH mv ${DOWNLOAD_MOUNT} ${DOWNLOAD_MOUNT}-last
fi
$SSH mv ${DOWNLOAD_MOUNT}-new ${DOWNLOAD_MOUNT}

# Only maven deploy specific branches
case $BRANCH_NAME in
    main | release_*)
        # GPG Sign and Deploy to Maven Central snapshot
        # The artifacts are taken from the local Maven repository that was used by the build
        # The artifacts that are published to Maven Central: the parent pom and the
        # LSP4J bundles. The artifacts of the p2 build are not published.
        for artifact in lsp4j-parent org.eclipse.lsp4j org.eclipse.lsp4j.debug \
            org.eclipse.lsp4j.generator org.eclipse.lsp4j.jsonrpc \
            org.eclipse.lsp4j.jsonrpc.debug org.eclipse.lsp4j.websocket.jakarta; do
        find ${MAVEN_REPO_LOCAL:-../.repository}/org/eclipse/lsp4j/${artifact} -name '*.pom' | while read i
        do
            base="${i%.*}"
            # See https://wiki.eclipse.org/Jenkins#How_can_artifacts_be_deployed_to_OSSRH_.2F_Maven_Central.3F for more info
            # on the Eclipse Foundation specific settings.
            if [ -f "${base}.jar" ]; then
                mvn -f gpgparameters.pom \
                    org.apache.maven.plugins:maven-gpg-plugin:3.2.8:sign-and-deploy-file \
                    -DpomFile=${base}.pom \
                    -Dfile=${base}.jar \
                    -Dfiles=${base}-sources.jar,${base}-javadoc.jar \
                    -Dclassifiers=sources,javadoc \
                    -Dtypes=jar,jar \
                    -Durl=https://central.sonatype.com/repository/maven-snapshots/ \
                    -DrepositoryId=central
            else
                # Artifacts without a jar, e.g. the parent pom
                mvn -f gpgparameters.pom \
                    org.apache.maven.plugins:maven-gpg-plugin:3.2.8:sign-and-deploy-file \
                    -DpomFile=${base}.pom \
                    -Dfile=${base}.pom \
                    -Dpackaging=pom \
                    -Durl=https://central.sonatype.com/repository/maven-snapshots/ \
                    -DrepositoryId=central
            fi
        done
        done
        ;;
    *)
        echo "Maven deployments only done on main and release branches"
        ;;
esac
