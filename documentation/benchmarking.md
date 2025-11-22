# JSON-RPC Benchmarking

The org.eclipse.lsp4j.jsonrpc project comes with some [JMH](https://github.com/openjdk/jmh) based benchmarks.
This are useful to perform analysis when doing micro-optimization of the JSON-RPC implementation.

## Running Benchmarks

At the command line run `./gradlew jmh` to run the benchmarks.
A lot of output, including results and caveats from JMH, is provided.
Additionally, the result summaries are written to `org.eclipse.lsp4j.jsonrpc/build/results/jmh/results.txt`

See the [JMH Gradle Plug-in readme](https://github.com/melix/jmh-gradle-plugin?tab=readme-ov-file#readme) and the [OpenJDK JMH readme](https://github.com/openjdk/jmh) for more details on how best to use JMH.

## Running a single benchmark

To run a single benchmark, add `-PjmhIncludes=NameOfBenchmark` to the `./gradlew` command line.

## Building and running a jmh jar

A typical way of using jmh is to build a self-contained jar.
Do this by running `./gradlew jmhJar`, the resulting jar is `org.eclipse.lsp4j.jsonrpc/build/libs/org.eclipse.lsp4j.jsonrpc-1.0.0-SNAPSHOT-jmh.jar` (with `1.0.0-SNAPSHOT` replaced with current version of LSP4J).
This jar can then be used with the normal command line options of jmh, run with `-h` to see what they are:

```sh
java -jar org.eclipse.lsp4j.jsonrpc/build/libs/org.eclipse.lsp4j.jsonrpc-1.0.0-SNAPSHOT-jmh.jar -h
```

## Running jmh and gradle is doing nothing?

Gradle's incremental build system may prevent subsequent runs of the `jmh` task from doing anything.
This happens because gradle does not think anything has changed, so there is nothing to do.
Delete the results file (`org.eclipse.lsp4j.jsonrpc/build/results/jmh/results.txt`) or do a clean build (`./gradlew clean jmh`).

## Running jmh within Eclipse

At the time of writing there is no plug-in available for Eclipse to simplify running JMH within the IDE.
LSP4J provides a Buildship launch configuration called `lsp4j-jmh` which can be used to run within the IDE.
This task can be used once the project is imported in to your Eclipse workspace as explained in the [contribution guide](https://github.com/eclipse-lsp4j/lsp4j/blob/main/CONTRIBUTING.md#eclipse).
