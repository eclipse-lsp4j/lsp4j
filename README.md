## Eclipse LSP4J

Java binding for the [Language Server Protocol](https://microsoft.github.io/language-server-protocol) and the [Debug Adapter Protocol](https://microsoft.github.io/debug-adapter-protocol).

### How To Use

A brief overview of how to use LSP4J to implement a server or a client can be found here:

 * [Getting Started](documentation/README.md)
 * [Core Concepts](documentation/jsonrpc.md)

#### Maven Repositories

 * [Maven Central](https://repo.maven.apache.org/maven2/org/eclipse/lsp4j/): <https://repo.maven.apache.org/maven2/>

#### p2 Update Sites

 * Releases: <https://download.eclipse.org/lsp4j/updates/releases/>
 * Milestones: <https://download.eclipse.org/lsp4j/updates/milestones/>
 * Nightly: <https://download.eclipse.org/lsp4j/builds/main/>

#### japicmp reports

 * The p2 Update sites listed above (since 0.13.0) contain a japicmp report against the last released version to make it easier to identify API changes.

#### Snapshots

The snapshots are deployed during the build to:

 * <https://oss.sonatype.org/content/repositories/snapshots/org/eclipse/lsp4j/>

##### Signed JARs

The Maven Repositories, p2 Update Sites, and the Snapshots contain _signed jars_. If you plan to simply add them to your own jar, you'll need to [remove/exclude](https://stackoverflow.com/a/6743609/14376491) or [update/override](https://stackoverflow.com/a/46014819/14376491) these signatures.

### Supported LSP Versions

 * LSP4J 1.0.&ast; _(Next release)_ &rarr; LSP 3.18.0 (specification is not finalized yet) plus [SymbolTag proposal](https://github.com/microsoft/language-server-protocol/pull/2003)
 * LSP4J 0.24.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.23.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.22.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.21.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.20.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.19.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.18.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.17.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.16.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.15.&ast; &rarr; LSP 3.17.0
 * LSP4J 0.14.&ast; &rarr; LSP 3.17.0 (except Notebook support and `WorkspaceSymbol.data`)
 * LSP4J 0.13.&ast; &rarr; LSP 3.17.0 (except Notebook support and some changes to the LSP 3.17
 specification that were added after specification was published as done)
 * LSP4J 0.12.&ast; &rarr; LSP 3.16.0
 * LSP4J 0.11.&ast; &rarr; LSP 3.16.0
 * LSP4J 0.10.&ast; &rarr; LSP 3.16.0 (specification is not finalized yet)
 * LSP4J 0.9.&ast; &rarr; LSP 3.15.0 (except Progress)
 * LSP4J 0.7.&ast;, 0.8.&ast; &rarr; LSP 3.14.0
 * LSP4J 0.6.&ast; &rarr; LSP 3.13.0
 * LSP4J 0.5.&ast; &rarr; LSP 3.10.0
 * LSP4J 0.4.&ast; &rarr; LSP 3.7.0
 * LSP4J 0.2.&ast;, 0.3.&ast; &rarr; LSP 3.0
 * LSP4J 0.1.&ast; &rarr; LSP 2.1

### Supported DAP Versions

 * LSP4J 1.0.&ast; _(Next release)_ &rarr; DAP 1.69.0
 * LSP4J 0.24.&ast; &rarr; DAP 1.69.0
 * LSP4J 0.23.&ast; &rarr; DAP 1.65.0
 * LSP4J 0.22.&ast; &rarr; DAP 1.60.0
 * LSP4J 0.21.&ast; &rarr; DAP 1.60.0
 * LSP4J 0.20.&ast; &rarr; DAP 1.58.0
 * LSP4J 0.19.&ast; &rarr; DAP 1.58.0
 * LSP4J 0.18.&ast; &rarr; DAP 1.58.0
 * LSP4J 0.17.&ast; &rarr; DAP 1.58.0
 * LSP4J 0.16.&ast; &rarr; DAP 1.58.0
 * LSP4J 0.15.&ast; &rarr; DAP 1.56.0
 * LSP4J 0.14.&ast; &rarr; DAP 1.55.0
 * LSP4J 0.13.&ast; &rarr; DAP 1.55.0
 * LSP4J 0.12.&ast; &rarr; DAP 1.44.0
 * LSP4J 0.11.&ast; &rarr; DAP 1.44.0
 * LSP4J 0.10.&ast; &rarr; DAP 1.42.0
 * LSP4J 0.9.&ast; &rarr; DAP 1.37.0 (except CancelRequest)
 * LSP4J 0.8.&ast; &rarr; DAP 1.35.0
 * LSP4J 0.5.&ast; &ndash; 0.7.&ast; &rarr; DAP 1.31.0
 * LSP4J 0.4.&ast; &rarr; DAP 1.25.0
 * LSP4J before 0.4.0 did not support DAP

### Building and Contributing

To build and contribute to LSP4J please consult the [Contribution Guide](https://github.com/eclipse-lsp4j/lsp4j/blob/main/CONTRIBUTING.md).

### Licenses

LSP4J is published under two licenses:

 * [Eclipse Public License v. 2.0](https://www.eclipse.org/legal/epl-2.0)
 * [Eclipse Distribution License v. 1.0](https://www.eclipse.org/org/documents/edl-v10.php), a [BSD-3-clause license](https://opensource.org/licenses/BSD-3-Clause)

SPDX-License-Identifier: `EPL-2.0 OR BSD-3-Clause`

### API Policy

The Eclipse LSP4J project uses Semantic Versioning to determine the version number increment the project should use, as determined by [japicmp](https://siom79.github.io/japicmp/CliTool.html).
All bundles within LSP4J share a version number to simplify version management, both for LSP4J project as well as API consumers of LSP4J.
Therefore the version increment for any LSP4J bundle will be the largest increment required by semantic versioning of any of the bundles.

#### New versions of LSP and DAP specifications

New versions of [LSP](https://microsoft.github.io/language-server-protocol/) and [DAP](https://microsoft.github.io/debug-adapter-protocol/)
specifications may lead to breaking API changes due to mapping of TypeScript types to Java types.

It is particularly difficult to maintain binary compatibility when adapting to some protocol changes that allow return values or fields to have new/additional types.
In JSON-RPC + TypeScript, an additional type is not a breaking change, but in Java with LSP4J this is often a breaking change.
For example, in LSP 3.18, `Diagnostic.message` has changed from `string` to `string | MarkupContent`, in Java LSP4J this is represented as a
change from `String` to `Either<String, MarkupContent>`, which is an API breaking change, and would necessitate a major version bump for semantic versioning.

#### API Policy for versions before 1.0

Prior to 1.0 version of LSP4J Semantic Versioning was not used.

#### Published API changes

Breaking API changes are published in the [CHANGELOG](https://github.com/eclipse-lsp4j/lsp4j/blob/main/CHANGELOG.md).
In addition, each release and continuous build of LSP4J runs the japicmp tool, wrapped with [runjapicmp.sh](https://github.com/eclipse-lsp4j/lsp4j/blob/main/releng/runjapicmp.sh).
The generated japicmp reports for releases and current development branch are linked in the [CHANGELOG](https://github.com/eclipse-lsp4j/lsp4j/blob/main/CHANGELOG.md).
The GitHub actions builds save as an artifact the same report for each pull request and commit.

#### Compare API of two arbitrary versions

Using japicmp online you can compare API between any two versions easily, here is an example of comparing 0.21.0 to 0.24.0 for each of the LSP4J bundles:

- [org.eclipse.lsp4j](https://www.japicmp.de/cmp?groupId=org.eclipse.lsp4j&artifactId=org.eclipse.lsp4j&oldVersion=0.21.0&newVersion=0.24.0)
- [org.eclipse.lsp4j.debug](https://www.japicmp.de/cmp?groupId=org.eclipse.lsp4j&artifactId=org.eclipse.lsp4j.debug&oldVersion=0.21.0&newVersion=0.24.0)
- [org.eclipse.lsp4j.generator](https://www.japicmp.de/cmp?groupId=org.eclipse.lsp4j&artifactId=org.eclipse.lsp4j.generator&oldVersion=0.21.0&newVersion=0.24.0)
- [org.eclipse.lsp4j.jsonrpc](https://www.japicmp.de/cmp?groupId=org.eclipse.lsp4j&artifactId=org.eclipse.lsp4j.jsonrpc&oldVersion=0.21.0&newVersion=0.24.0)
- [org.eclipse.lsp4j.websocket](https://www.japicmp.de/cmp?groupId=org.eclipse.lsp4j&artifactId=org.eclipse.lsp4j.websocket&oldVersion=0.21.0&newVersion=0.24.0)
- [org.eclipse.lsp4j.websocket.jakarta](https://www.japicmp.de/cmp?groupId=org.eclipse.lsp4j&artifactId=org.eclipse.lsp4j.websocket.jakarta&oldVersion=0.21.0&newVersion=0.24.0)
