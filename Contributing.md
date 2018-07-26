# Contribution Guide

The project is build with Gradle and written in Java 8.

To start working on it simply clone and run `./gradlew build`.

# CLA

In order to contribute code, you need to sign an Eclipse Contributor Agreement, using the e-mail used at Github.
See https://eclipse.org/legal/ECA.php for details

# Eclipse

To develop with Eclipse this is the recommended flow:

1. Using the latest [Eclipse](https://www.eclipse.org/downloads/) install a Java development environment that contains these features. These are all available in the "Install New Software" under the name of the release you are using (e.g. Oxygen)
- JDT The Java Development Tools
- The latest Buildship plugin  (>= 2.\*) 
  - The LSP4J build is gradle based
- Xtend IDE
  - Much of the code and tests are written in [Xtend](https://www.eclipse.org/xtend/)

2. Fork/Clone the LSP4J repository

3. Import the root of LSP4J as a Gradle project:
  - File -> Import
  - Existing Gradle Project
  - Select the root of the cloned directory as "Project root directory"
  - Finish the wizard - this will import all the projects but there will be lots of errors

4. Run the "eclipse assemble" gradle targets.
  - There is already a launch configuration in the root of the repository called "lsp4j-build-gradle", run it.
  - Doing this will generate the missing files and update the project configurations

5. Clean the projects
  - Project menu -> Clean...
  - Select all the lsp4j projects

Edit the `.xtend` files, not the files in `xtend-gen` directories. If you are in a Java file within the xtend-gen directory, right click and choose "Open Generated File".

The `org.eclipse.lsp4j.generator` project is used by the Xtend generator as an additional processor to contribute to the generated Java files. For example, the generator uses the `@JsonRpcData` annotation to convert the Xtend file and add things like `equals`, `toString`, `hashCode`.

Run the tests as you would run any normal JUnit tests (There is no need to run them as JUnit Plug-in Tests).
