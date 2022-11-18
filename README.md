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

 * LSP4J 0.20.&ast; _(Next release)_ &rarr; LSP 3.17.0
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

 * LSP4J 0.20.&ast; _(Next release)_ &rarr; DAP 1.58.0
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

To build and contribute to LSP4J please consult the [Contribution Guide](https://github.com/eclipse/lsp4j/blob/main/CONTRIBUTING.md).

### Licenses

LSP4J is published under two licenses:

 * [Eclipse Public License v. 2.0](https://www.eclipse.org/legal/epl-2.0)
 * [Eclipse Distribution License v. 1.0](https://www.eclipse.org/org/documents/edl-v10.php), a [BSD-3-clause license](https://opensource.org/licenses/BSD-3-Clause)

SPDX-License-Identifier: `EPL-2.0 OR BSD-3-Clause`
