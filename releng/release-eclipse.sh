#!/bin/bash

set -u # run with unset flag error so that missing parameters cause build failure
set -e # error out on any failed commands
set -x # echo all commands used for debugging purposes

SSHUSER="genie.lsp4j@projects-storage.eclipse.org"
SSH="ssh ${SSHUSER}"
SCP="scp"

DOWNLOAD=download.eclipse.org/lsp4j/$LSP4J_PUBLISH_LOCATION
DOWNLOAD_MOUNT=/home/data/httpd/$DOWNLOAD
ARTIFACTS=https://ci.eclipse.org/lsp4j/job/${PROJECT}/$LSP4J_BUILD_NUMBER/artifact
ARTIFACTS_REPO_TARGET=$ARTIFACTS/build

echo Using download location root of "https://$DOWNLOAD"
echo Using artifacts location root of $ARTIFACTS

echo Testing to make sure we are publishing to a new directory
$SSH "test ! -e $DOWNLOAD_MOUNT"

echo Testing to make sure artifacts location is sane
wget -q --output-document=/dev/null $ARTIFACTS_REPO_TARGET

ECHO=echo
if [ "$DRY_RUN" == "false" ]; then
    ECHO=""
else
    echo Dry run of build:
fi

$ECHO $SSH mkdir -p $DOWNLOAD_MOUNT

$ECHO $SSH "cd $DOWNLOAD_MOUNT && \
    wget -q $ARTIFACTS_REPO_TARGET/p2-repository/*zip*/p2-repository.zip  && \
    unzip -q p2-repository.zip  && \
    mv p2-repository/* .  && \
    rm -r p2-repository p2-repository.zip"

# Stop here because of https://github.com/eclipse-lsp4j/lsp4j/issues/887#issuecomment-3874528594 on EF Jenkins
# The below can be run semi-manually on dev machine to deploy what we built
exit 0

### GPG Sign and Deploy to Maven Central
# - this wget/unzip is not $ECHO so that the find/loop does something
wget -q $ARTIFACTS_REPO_TARGET/maven-repository/*zip*/maven-repository.zip
unzip -q maven-repository.zip
find maven-repository -name '*.pom' | while read i
do
    base="${i%.*}"
    $ECHO cp releng/gpgparameters.pom gpgparameters.pom
    $ECHO mvn -f gpgparameters.pom \
        org.apache.maven.plugins:maven-gpg-plugin:3.2.8:sign-and-deploy-file \
        -DpomFile=${base}.pom \
        -Dfile=${base}.jar \
        -Dfiles=${base}-sources.jar,${base}-javadoc.jar \
        -Dclassifiers=sources,javadoc \
        -Dtypes=jar,jar \
        -DrepositoryId=central \
        -Durl=https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/
done

# After upload, automatically deploy https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/
# We can't get username/password on EF Jenkins here because it is maven encrypted
CENTRAL_USER=$(echo username)
CENTRAL_PASS=$(echo password)

wget --method=POST \
  --user="$CENTRAL_USER" \
  --password="$CENTRAL_PASS" \
  --header="Content-Length: 0" \
  "https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/org.eclipse.lsp4j"

if [ "$DRY_RUN" == "false" ]; then
    echo Release uploaded to "https://$DOWNLOAD"
else
    echo Dry run completed.
fi
