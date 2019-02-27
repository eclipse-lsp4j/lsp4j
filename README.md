## Eclipse LSP4J

Java binding for the [Language Server Protocol](https://github.com/Microsoft/language-server-protocol) and the [Debug Adapter Protocol](https://microsoft.github.io/debug-adapter-protocol/).

### How To Use

A brief overview of how to use LSP4J to implement a server or a client can be found here:

 * [Getting Started](documentation/README.md)
 * [Core Concepts](documentation/jsonrpc.md)

#### Maven Repositories

 * [Maven Central](https://repo.maven.apache.org/maven2/org/eclipse/lsp4j/): https://repo.maven.apache.org/maven2/
 * [Bintray](https://bintray.com/bintray/jcenter/org.eclipse.lsp4j%3Aorg.eclipse.lsp4j/): https://jcenter.bintray.com/

#### p2 Update Sites

 * Releases: http://download.eclipse.org/lsp4j/updates/releases/
 * Milestones: http://download.eclipse.org/lsp4j/updates/milestones/
 * Nightly: http://services.typefox.io/open-source/jenkins/job/lsp4j/job/master/lastStableBuild/artifact/build/p2-repository/

#### Snapshots

The snapshots are regularly deployed to:
 * https://oss.sonatype.org/content/repositories/snapshots/org/eclipse/lsp4j/

Also, you can get them directly from the TypeFox Jenkins server:
 * http://services.typefox.io/open-source/jenkins/job/lsp4j/job/master/lastSuccessfulBuild/artifact/build/maven-repository/

### Supported LSP Versions

 * LSP4J 0.7.x &rarr; LSP 3.14.0
 * LSP4J 0.6.x &rarr; LSP 3.13.0
 * LSP4J 0.5.x &rarr; LSP 3.10.0
 * LSP4J 0.4.x &rarr; LSP 3.7.0
 * LSP4J 0.2.x, 0.3.x &rarr; LSP 3.0
 * LSP4J 0.1.x &rarr; LSP 2.1

### Supported DAP Versions

 * LSP4J 0.5.x &rarr; DAP 1.31.0
 * LSP4J 0.4.x &rarr; DAP 1.25.0
 * LSP4J before 0.4.x did not support DAP

### Licenses

LSP4J is published under two licenses:

 * [Eclipse Public License v. 2.0](https://www.eclipse.org/legal/epl-2.0)
 * [Eclipse Distribution License v. 1.0](https://www.eclipse.org/org/documents/edl-v10.php), a [BSD-3-clause license](https://opensource.org/licenses/BSD-3-Clause)

SPDX-License-Identifier: `EPL-2.0 OR BSD-3-Clause`
