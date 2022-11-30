# NantHeath OpenNMS Tech Challenge

This is my submission for the tech challenge. It is a gradle-based Java 17 application.

My approach to this challenge was to be more holistic than the base challenge description. While the
knowledge of algorithms and efficiency is important, I find in my day-to-day, it is also important
to be able to design, decompose, encapsulate, and test written code. While not 100% perfect or
complete (see [Where to go from here](#where-to-go-from-here)), I think it gives an
approximation of the kind of code I'd be able to write.

# Requirements

The application is relatively simple, and requirements are few. They are:

- A local installation of JDK17
- An internet connection to download dependencies with.

# Compilation, Testing, and Building

```bash
# Required to compile, test or build
export JAVA_HOME="/path/to/jdk17"
cd path/to/nhonms-tech-challenge

#To compile
./gradlew :build

#To run tests
./gradlew :tests

#To build an executable jar
./gradlew :shadowjar

#To execute all three at once
./gradlew clean :build :test :shadowjar
```

The resulting jar can be found in `build/libs/nhonms-tech-challenge-0.1.0-SNAPSHOT-all.jar`. It is a
fat jar, meaning that all dependencies are included with it.

# Running the application

The following assumes that the jar has been built and left in
`build/libs/nhonms-tech-challenge-0.1.0-SNAPSHOT-all.jar`. However, it can be moved and referenced
from its new location.

In addition, a wrapper shell script, `oidFilter.sh`, can be executed from the project root to
simplify command execution.

For more details on usage, use the `-h` or `--help` options.

```bash
# Required to run the command
export JAVA_HOME="/path/to/jdk17"
cd path/to/nhonms-tech-challenge

# Redirect a text file into stdin
$JAVA_HOME/bin/java -jar build/libs/nhonms-tech-challenge-0.1.0-SNAPSHOT-all.jar <path/to/my-oids.txt
# Pipe the contents of another command into stdin 
echo ".1.3.6.1.4.1.9.9.117" | $JAVA_HOME/bin/java -jar build/libs/nhonms-tech-challenge-0.1.0-SNAPSHOT-all.jar
# Using a custom config path 
$JAVA_HOME/bin/java -jar build/libs/nhonms-tech-challenge-0.1.0-SNAPSHOT-all.jar -c path/to/snmp.yaml <path/to/my-oids.txt
# Seeing usage
$JAVA_HOME/bin/java -jar build/libs/nhonms-tech-challenge-0.1.0-SNAPSHOT-all.jar --help

# The previous examples, using the wrapper script
./oidFilter.sh <path/to/my-oids.txt
echo ".1.3.6.1.4.1.9.9.117" | ./oidFilter.sh
./oidFilter.sh -c path/to/snmp.yaml <path/to/my-oids.txt
./oidFilter.sh --help
```

# Where to go from here

While I would love to do all the things I can think of, sometimes a line has to be drawn and an MVP
declared. If I were to spend more time developing this challenge, here are some items I would
pursue:

- Increase test coverage, particularly in `OidFilter`. `OidFilter` should be testable by ensuring
  the input and output streams are injected as dependencies.
- Add a performance test suite & gradle task to measure execution performance across filter
  implementations.
- The [picocli documentation outlines](https://picocli.info/#_graalvm_native_image) how to build a
  native standalone executable. "Everything included" executables are all the rage these days,
  because of the ease of installation and use. A natively-executed java application would be no
  exception.
- Setting up [autocompletion](https://picocli.info/autocomplete.html).
- Generating [manpage documentation](https://picocli.info/#_generate_man_page_documentation).
- A CI pipeline that can build, execute tests, and tag/publish the artifact to a release page.
- A DI framework like Spring or Guice could be nice to have, especially if the project were to
  expand past the handful of classes currently into a sprawling nested-command structure with tens
  of dependent classes or more. However, it is not as necessary as-is.