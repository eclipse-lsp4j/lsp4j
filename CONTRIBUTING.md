# Contribution Guide

The project is built with Maven and [Tycho](https://tycho.eclipseprojects.io/) and written in Java 11.

To start working on it simply clone and run `./mvnw clean install`. See the section [below](#eclipse) on building and editing with Eclipse for step-by-step instructions.

The build requires Java 21 or later (Tycho requires it), the produced artifacts are compiled for Java 11.

## Dependencies

Each module is an OSGi bundle (`eclipse-plugin`). Its dependencies are declared twice and both places have to be kept in sync:

- `META-INF/MANIFEST.MF` (`Import-Package`): used by Tycho to compile the bundle, by Eclipse/PDE and at runtime in OSGi.
- `pom.xml` (`<dependencies>`): used by the users of the artifacts that are published to Maven Central, and to resolve the bundles from Maven Central during the build.

Third party bundles that are not dependencies of a module are taken from the target platform in `releng/releng-target`.

`./mvnw clean install -Ptest-older-javas` additionally runs the tests on Java 17 and Java 11, the JDKs are taken from the [Maven toolchains](https://maven.apache.org/guides/mini/guide-using-toolchains.html) (`~/.m2/toolchains.xml`).
The Xtend compiler requires Java 17, so the tests that are written in Xtend are not run on Java 11. A new Xtend test class has to be added to the excludes of the `test-older-javas` profile in the root `pom.xml`.

# ECA

In order to contribute code, you need to sign an Eclipse Contributor Agreement, using the e-mail of your GitHub account.
See https://eclipse.org/legal/ECA.php for details

# GitHub Pull Requests

All GitHub Pull Requests run the following checks.

## eclipsefdn/eca

All Pull Requests must be by authors who have signed the ECA. The "ip-validation" status check on GitHub will check this.

## GitHub Actions

GitHub Actions are used to validate PRs. See the [workflows](https://github.com/eclipse-lsp4j/lsp4j/tree/main/.github/workflows) directory and standard [GitHub Actions documentation](https://docs.github.com/en/actions/reference/workflows-and-actions/workflow-syntax) for more details..

# Eclipse

To develop with Eclipse this is the recommended flow:

1. Using the latest [Eclipse](https://www.eclipse.org/downloads/) install a Java development environment that contains these features. These are all available in the "Install New Software" under the name of the release you are using.
- JDT The Java Development Tools
- Maven Integration for Eclipse (m2e) with the PDE integration (m2e connector for Tycho)
- Plug-in Development Environment (PDE)
- Xtend IDE
  - Much of the code and tests are written in [Xtend](https://www.eclipse.org/xtend/)

2. Fork/Clone the LSP4J repository

3. Import the root of LSP4J as Maven projects:
  - File -> Import
  - Maven -> Existing Maven Projects
  - Select the root of the cloned directory as "Root Directory"
  - Finish the wizard - this will import all the modules.

4. The projects are OSGi bundles, their manifests are in `META-INF/MANIFEST.MF`. The Java code that is generated from the Xtend sources (`src/main/xtend-gen` resp. `src/test/xtend-gen`) is created by the Xtend builder in the IDE, or by a command line build (`./mvnw clean install` in the root of the repository).

5. Refresh the lsp4j projects.
   - This step is not needed if the "Refresh using native hooks or polling" setting (in Preferences -> General -> Workspace) is enabled.

6. Clean the projects
   - Project menu -> Clean...
   - Select all the lsp4j projects

- If there are still errors in the LSP4J projects at this point, try restarting the IDE
  - This seems to happen because sometimes the Xtend builder does not remove previously created Error markers, but restarting clears them

Edit the `.xtend` files, not the files in `xtend-gen` directories. If you are in a Java file within the xtend-gen directory, right click and choose "Open Generated File".

The `org.eclipse.lsp4j.generator` project is used by the Xtend generator as an additional processor to contribute to the generated Java files. For example, the generator uses the `@JsonRpcData` annotation to convert the Xtend file and add things like `equals`, `toString`, `hashCode`.

Run the tests as you would run any normal JUnit tests (There is no need to run them as JUnit Plug-in Tests).
