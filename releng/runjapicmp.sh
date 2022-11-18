#!/bin/bash

set -u # run with unset flag error so that missing parameters cause build failure
set -e # error out on any failed commands
#set -x # echo all commands used for debugging purposes


BASELINE=0.19.0

JAPICMP_VERSION=0.15.7
WD=$(mktemp -d)
echo Working in $WD

function classpath() {
    local IFS=':'
    shift
    echo "$*"
}

curl -s https://repo1.maven.org/maven2/com/github/siom79/japicmp/japicmp/${JAPICMP_VERSION}/japicmp-${JAPICMP_VERSION}-jar-with-dependencies.jar > ${WD}/japicmp.jar


newjars=($(find ${PWD}/*/build/libs -name org.eclipse.lsp4j*.jar -not -name \*sources.jar -not -name \*javadoc.jar))
names=()
oldjars=()

pushd ${WD}
# We need to iterate twice, once to download all the oldjars first, then to run japicmp
# with the classpath containing all the downloaded files
for newjar in ${newjars[@]}
do
    name=$(echo $newjar | sed -es,.*/,, -es,-.*\.jar,,)
    names+=($name)
    oldjar=${name}-${BASELINE}.jar
    oldjars+=($oldjar)
    curl -s https://repo1.maven.org/maven2/org/eclipse/lsp4j/${name}/${BASELINE}/${name}-${BASELINE}.jar > ${oldjar}
done

for i in ${!newjars[@]}
do
    newjar=${newjars[$i]}
    oldjar=${oldjars[$i]}
    name=${names[$i]}

    mkdir -p japicmp-report/
    java -jar japicmp.jar --html-file japicmp-report/${name}.html --only-modified --ignore-missing-classes -n ${newjar} --new-classpath $(classpath ${newjars[@]}) -o ${oldjar} --old-classpath $(classpath org.eclipse.*)
    zip -u japicmp-report.zip japicmp-report/${name}.html
done

popd
mkdir -p build/p2-repository/
cp ${WD}/japicmp-report.zip build/p2-repository/
cd build/p2-repository/ && unzip japicmp-report.zip

echo
echo Reports complete and have been saved in build/p2-repository/japicmp-report.zip
