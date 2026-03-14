#!/bin/sh
#
# Gradle wrapper script for POSIX systems.
#
APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`
APP_HOME=`dirname "$0"`
APP_HOME=`cd "$APP_HOME" && pwd`
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

set -e
if [ ! -f "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" ]; then
  if ! which curl > /dev/null 2>&1; then
    echo "ERROR: curl is not installed. Cannot download Gradle wrapper."
    exit 1
  fi
  curl -L "https://raw.githubusercontent.com/gradle/gradle/v8.4.0/gradle/wrapper/gradle-wrapper.jar" \
    -o "$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
fi

exec "$JAVACMD" $DEFAULT_JVM_OPTS \
  -classpath "$CLASSPATH" \
  org.gradle.wrapper.GradleWrapperMain "$@"
