## Eclipse LSP4J

Java binding for the [Language Server Protocol](https://github.com/Microsoft/language-server-protocol) and the [Debug Adapter Protocol](https://microsoft.github.io/debug-adapter-protocol/).

### How To Use

A brief overview of how to use LSP4J to implement a server or a client can be found here:

 * [Getting Started](documentation/README.md)
 * [Core Concepts](documentation/jsonrpc.md)

#### Maven Repositories

 * [Maven Central](https://repo.maven.apache.org/maven2/org/eclipse/lsp4j/): <https://repo.maven.apache.org/maven2/>

#### p2 Update Sites

 * Releases: <http://download.eclipse.org/lsp4j/updates/releases/>
 * Milestones: <http://download.eclipse.org/lsp4j/updates/milestones/>
 * Nightly: <https://download.eclipse.org/lsp4j/builds/master/>

#### Snapshots

The snapshots are deployed during the build to:

 * <https://oss.sonatype.org/content/repositories/snapshots/org/eclipse/lsp4j/>

### Supported LSP Versions

 * LSP4J 0.13.&ast; &rarr; LSP 3.16.0
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

 * LSP4J 0.13.&ast; &rarr; DAP 1.44.0
 * LSP4J 0.12.&ast; &rarr; DAP 1.44.0
 * LSP4J 0.11.&ast; &rarr; DAP 1.44.0
 * LSP4J 0.10.&ast; &rarr; DAP 1.42.0
 * LSP4J 0.9.&ast; &rarr; DAP 1.37.0 (except CancelRequest)
 * LSP4J 0.8.&ast; &rarr; DAP 1.35.0
 * LSP4J 0.5.&ast; &ndash; 0.7.&ast; &rarr; DAP 1.31.0
 * LSP4J 0.4.&ast; &rarr; DAP 1.25.0
 * LSP4J before 0.4.0 did not support DAP

### Building and Contributing

To build and contribute to LSP4J please consult the [Contribution Guide](https://github.com/eclipse/lsp4j/blob/master/Contributing.md).

### Licenses

LSP4J is published under two licenses:

 * [Eclipse Public License v. 2.0](https://www.eclipse.org/legal/epl-2.0)
 * [Eclipse Distribution License v. 1.0](https://www.eclipse.org/org/documents/edl-v10.php), a [BSD-3-clause license](https://opensource.org/licenses/BSD-3-Clause)

SPDX-License-Identifier: `EPL-2.0 OR BSD-3-Clause`
