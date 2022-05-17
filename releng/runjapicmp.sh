#!/bin/bash

set -u # run with unset flag error so that missing parameters cause build failure
set -e # error out on any failed commands
#set -x # echo all commands used for debugging purposes


BASELINE=0.12.0

JAPICMP_VERSION=0.15.7
WD=$(mktemp -d)
echo Working in $WD

function classpath() {
    local IFS=':'
    shift
    echo "$*"
}

curl -s https://repo1.maven.org/maven2/com/github/siom79/japicmp/japicmp/${JAPICMP_VERSION}/japicmp-${JAPICMP_VERSION}-jar-with-dependencies.jar > ${WD}/japicmp.jar


newjars=$(find ${PWD} -name org.eclipse.lsp4j*.jar -not -name \*sources.jar -not -name \*javadoc.jar)

pushd ${WD}
# Download all the oldjars first, we need them all for the classpath
for newjar in $newjars
do
    name=$(echo $newjar | sed -es,.*/,, -es,-.*\.jar,,)
    oldjar=${name}-${BASELINE}.jar
    curl -s https://repo1.maven.org/maven2/org/eclipse/lsp4j/${name}/0.12.0/${name}-${BASELINE}.jar > ${oldjar}
done

for newjar in $newjars
do
    name=$(echo $newjar | sed -es,.*/,, -es,-.*\.jar,,)
    oldjar=${name}-${BASELINE}.jar

    java -jar japicmp.jar --html-file ${name}.html --only-incompatible --ignore-missing-classes -n ${newjar} --new-classpath $(classpath $newjars) -o ${oldjar} --old-classpath $(classpath org.eclipse.*) 
    zip -u japicmp-report.zip ${name}.html
done

popd
mkdir -p build/p2-repository/
cp ${WD}/japicmp-report.zip build/p2-repository/

echo
echo Reports complete and have been saved in build/p2-repository/japicmp-report.zip
