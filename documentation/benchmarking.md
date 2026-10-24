# JSON-RPC Benchmarking

The org.eclipse.lsp4j.jsonrpc project comes with some [JMH](https://github.com/openjdk/jmh) based benchmarks.
This are useful to perform analysis when doing micro-optimization of the JSON-RPC implementation.

The benchmarks are not part of the published artifacts, they are only compiled when the `jmh` profile is active.

## Running Benchmarks

At the command line run

```sh
./mvnw -pl org.eclipse.lsp4j.jsonrpc -Pjmh compile exec:exec
```

A lot of output, including results and caveats from JMH, is provided.

See the [OpenJDK JMH readme](https://github.com/openjdk/jmh) for more details on how best to use JMH.

## Running a single benchmark

To run a single benchmark, add the name of the benchmark to the `jmh.args` property, e.g.

```sh
./mvnw -pl org.eclipse.lsp4j.jsonrpc -Pjmh compile exec:exec -Djmh.args="StreamMessageProducerBenchmark"
```

All command line options of JMH can be passed that way, e.g. `-Djmh.args="-h"` shows the help, `-Djmh.args="-l"` lists the benchmarks.

## Running jmh within Eclipse

At the time of writing there is no plug-in available for Eclipse to simplify running JMH within the IDE.
The benchmarks can be run from the IDE by creating a Java Application launch configuration for
`org.openjdk.jmh.Main` with the classpath of the `org.eclipse.lsp4j.jsonrpc` project
(the folder `target/jmh-classes` has to be added to the classpath).
