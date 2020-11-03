#!/bin/bash

set -u # run with unset flag error so that missing parameters cause build failure
set -e # error out on any failed commands
set -x # echo all commands used for debugging purposes

DOWNLOAD=download.eclipse.org/lsp4j/$LSP4J_PUBLISH_LOCATION
DOWNLOAD_MOUNT=/home/data/httpd/$DOWNLOAD
ARTIFACTS=https://ci.eclipse.org/lsp4j/job/${PROJECT}/$LSP4J_BUILD_NUMBER/artifact
ARTIFACTS_REPO_TARGET=$ARTIFACTS/build

echo Using download location root of "https://$DOWNLOAD"
echo Using artifacts location root of $ARTIFACTS

echo Testing to make sure we are publishing to a new directory
test ! -e $DOWNLOAD_MOUNT

echo Testing to make sure artifacts location is sane
wget -q --output-document=/dev/null $ARTIFACTS_REPO_TARGET

ECHO=echo
if [ "$DRY_RUN" == "false" ]; then
    ECHO=""
else
    echo Dry run of build:
fi

$ECHO mkdir -p $DOWNLOAD_MOUNT

$ECHO cd $DOWNLOAD_MOUNT
$ECHO wget -q $ARTIFACTS_REPO_TARGET/p2-repository/*zip*/p2-repository.zip
$ECHO unzip -q p2-repository.zip
$ECHO mv p2-repository/* .
$ECHO rm -r p2-repository p2-repository.zip

### GPG Sign and Deploy to Maven Central
# - this wget/unzip is not $ECHO so that the find/loop does something
wget -q $ARTIFACTS_REPO_TARGET/maven-repository/*zip*/maven-repository.zip
unzip -q maven-repository.zip
find maven-repository -name '*.pom' | while read i
do
    base="${i%.*}"
    $ECHO /shared/common/apache-maven-latest/bin/mvn \
        gpg:sign-and-deploy-file \
        -DpomFile=${base}.pom \
        -Dfile=${base}.jar \
        -Dsources=${base}-sources.jar \
        -Djavadoc=${base}-javadoc.jar \
        -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2 \
        -DrepositoryId=ossrh
done

if [ "$DRY_RUN" == "false" ]; then
    echo Release uploaded to "https://$DOWNLOAD"
else
    echo Dry run completed.
fi
