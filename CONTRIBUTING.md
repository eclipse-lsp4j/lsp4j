# Contribution Guide

The project is build with Gradle and written in Java 11.

To start working on it simply clone and run `./gradlew build`. See the section [below](#Eclipse) on building and editing with Eclipse for step-by-step instructions.

# ECA

In order to contribute code, you need to sign an Eclipse Contributor Agreement, using the e-mail used at Github.
See https://eclipse.org/legal/ECA.php for details

# GitHub Pull Requests

All GitHub Pull Requests run the following checks.

## eclipsefdn/eca

All Pull Requests must be by authors who have signed the ECA. The "ip-validation" status check on GitHub will check this.

## GitHub Actions

GitHub Actions are used to validate PRs. See the GitHub [workflows](https://github.com/eclipse/lsp4j/tree/main/.github/workflows) directory and standard [GitHub Actions documentation](https://github.com/features/actions) for more details..

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
  - Finish the wizard - this will import all the projects but there will be lots of errors. Complete the following steps before trying to resolve them.

4. Run the "eclipse assemble" gradle targets.
  - There is already a launch configuration in the root of the repository called "lsp4j-build-gradle", run it.
  - Doing this will generate the missing files and update the project configurations
  - There still may be errors in your workspace at this point. Complete the following steps before trying to resolve them.

5. Refresh the lsp4j projects.
  - This step is not needed if you have the "Refresh using native hooks or polling" setting (in Preferences -> General -> Workspace) enabled.
  - There still may be errors in your workspace at this point. Complete the following steps before trying to resolve them.

6. Clean the projects
  - Project menu -> Clean...
  - Select all the lsp4j projects

- If there are still errors in the LSP4J projects at this point, try the following:
  - try cleaning the LSP4J projects again, except the `org.eclipse.lsp4j.generator` project
    - This seems to happen because sometimes it looks like there may be a race condtion with the generator being built and the projects that depend on them
  - try restarting the IDE
    - This seems to happen because sometimes Buildship plug-ins don't remove previously created Error markers, but restarting clears them
  - If you can reliably reproduce one of the above cases, then it would be great to file a bug report with the projects that provide the corresponding Eclipse plug-ins (e.g. Xtend, Buildship, JDT)

Edit the `.xtend` files, not the files in `xtend-gen` directories. If you are in a Java file within the xtend-gen directory, right click and choose "Open Generated File".

The `org.eclipse.lsp4j.generator` project is used by the Xtend generator as an additional processor to contribute to the generated Java files. For example, the generator uses the `@JsonRpcData` annotation to convert the Xtend file and add things like `equals`, `toString`, `hashCode`.

Run the tests as you would run any normal JUnit tests (There is no need to run them as JUnit Plug-in Tests).
