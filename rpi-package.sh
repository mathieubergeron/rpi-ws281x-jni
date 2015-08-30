
echo Getting version...
VERSION=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\['`

GROUP_ID=org.ws2811
ARTIFACT_ID=ws2811-jni

JAR_NAME=$ARTIFACT_ID-$VERSION.jar
SOURCE_JAR_NAME=$ARTIFACT_ID-$VERSION-sources.jar

REMOTE=rpi.lan
REMOTE_DIR=/root/rpi-ws281x-jni

if [ "$1" != "install-only" ]
then
    echo Syncing remote files
    ssh $REMOTE << EOF
        rm -rf $REMOTE_DIR
EOF
    rsync . -rav -e ssh --exclude='.git/*' --exclude='target/*' root@$REMOTE:$REMOTE_DIR

    echo Packaging jar
    ssh $REMOTE << EOF
        cd $REMOTE_DIR
        mvn install
EOF
fi

if [ "$1" == "install" -o "$1" == "install-only" ]
then
    echo Copying jar locally
    scp root@$REMOTE:$REMOTE_DIR/target/$JAR_NAME /tmp
    scp root@$REMOTE:$REMOTE_DIR/target/$SOURCE_JAR_NAME /tmp
    scp root@$REMOTE:$REMOTE_DIR/pom.xml /tmp

    echo Installing jar locally
    mvn install:install-file -Dfile=/tmp/$JAR_NAME \
                             -Dsources=/tmp/$SOURCE_JAR_NAME \
                             -DpomFile=/tmp/pom.xml \
                             -DgroupId=$GROUP_ID \
                             -DartifactId=$ARTIFACT_ID \
                             -Dversion=$VERSION \
                             -Dpackaging=jar
fi
