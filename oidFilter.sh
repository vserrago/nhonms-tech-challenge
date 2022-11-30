#!/usr/bin/env bash
# This is a simple wrapper script to make the execution of the command less unwieldy.
# Some usage notes:
#   JAVA_HOME needs to be set to run.
#   OID_FILTER_JAR can be provided as an env var, but must be resolvable from the shell's current
#     location.
set -eo pipefail

if [[ -z ${JAVA_HOME+x} ]]; then
  echo "JAVA_HOME is unset. Please set it to continue." && exit 1
fi

JAVA_PROG="${JAVA_HOME}/bin/java"

# Assume we're executing the currently versioned jar from the project root for simplicity's sake.
OID_FILTER_JAR="${OID_FILTER_JAR:-build/libs/nhonms-tech-challenge-0.1.0-SNAPSHOT-all.jar}"

"${JAVA_PROG}" -jar "${OID_FILTER_JAR}" "$@"
