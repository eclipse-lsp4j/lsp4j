This is the Release plan and TODO list for LSP4J release v0.13.0.

## Steps for Release

Items at the beginning of development

- [ ] Create an Endgame Issue to track the release. As a starting point use [documentation/releasing.md](https://github.com/eclipse/lsp4j/blob/master/documentation/releasing.md).
    - [ ] Add the [Endgame](https://github.com/eclipse/lsp4j/labels/endgame) label and Milestone for the release
    - [ ] Make sure any previous edits made to [Endgame issues](https://github.com/eclipse/lsp4j/labels/endgame) of previous releases are updated in [documentation/releasing.md](https://github.com/eclipse/lsp4j/blob/master/documentation/releasing.md)
- [ ] Ensure all previous [Endgame issues](https://github.com/eclipse/lsp4j/labels/endgame) are done.
- [ ] Create a [New milestone](https://github.com/eclipse/lsp4j/milestones/new) for the release
- [ ] Create release on [PMI](https://projects.eclipse.org/projects/technology.lsp4j)
- [ ] Check [CHANGELOG.md](https://github.com/eclipse/lsp4j/blob/master/CHANGELOG.md) is up to date. The changelog should have a version entry, release date, API Breakages and other information consistent with current entries in the changelog.
- [ ] Check [README.md](https://github.com/eclipse/lsp4j/blob/master/README.md) is up to date. In particular that the planned release and which versions of DAP and LSP are support is listed.
- [ ] Increment version of all feature.xml, pom.xml and any other place full version is used. (Easiest way is global find and replace, e.g. `s/0.12.0/0.13.0/g` and review changes.) Ensure that `-SNAPSHOT` is restored in the [gradle/versions.gradle](https://github.com/eclipse/lsp4j/blob/master/gradle/versions.gradle) and  [releng/pom.xml](https://github.com/eclipse/lsp4j/blob/master/releng/pom.xml)
- [ ] Enable `sh './releng/deploy-build.sh'` in [releng/build.Jenkinsfile](https://github.com/eclipse/lsp4j/blob/master/releng/build.Jenkinsfile) 
- [ ] Ensure [the CI build](https://ci.eclipse.org/lsp4j/job/lsp4j-multi-build/job/master/) is stable - it is always better to release a "Green Dot" build

Items in the days ahead of Release day:

- [ ] Schedule the release and if needed schedule a release review on the [PMI](https://projects.eclipse.org/projects/technology.lsp4j). A release review is needed every 12 months, not with each release.
- [ ] Check [CHANGELOG.md](https://github.com/eclipse/lsp4j/blob/master/CHANGELOG.md) is up to date. The changelog should have a version entry, release date, API Breakages and other information consistent with current entries in the changelog.
- [ ] Check [README.md](https://github.com/eclipse/lsp4j/blob/master/README.md) is up to date. In particular that the planned release and which versions of DAP and LSP are support is listed.
- [ ] Check all closed PRs and Issues to make sure their milestone is set. (*Note:* this was not until after 0.10.0 release so many old PRs and Issues have no milestone, therefore only consider items back to approx 5 Nov 2020). [This search may be useful to identify such closed issues](https://github.com/eclipse/lsp4j/issues?q=is%3Aclosed+no%3Amilestone+updated%3A%3E%3D2020-11-06)

Items on Release day:

- [ ] Prepare the repo for release by:
    - [ ] removing `-SNAPSHOT` from [gradle/versions.gradle](https://github.com/eclipse/lsp4j/blob/master/gradle/versions.gradle)
    - [ ] removing `-SNAPSHOT` from [releng/pom.xml](https://github.com/eclipse/lsp4j/blob/master/releng/pom.xml) entries in `<dependencies>` section.
    - [ ] disabling `sh './releng/deploy-build.sh'` in [releng/build.Jenkinsfile](https://github.com/eclipse/lsp4j/blob/master/releng/build.Jenkinsfile) 
    - see commit https://github.com/eclipse/lsp4j/commit/328ce8a4c89b0cd84fb62118f459b6cf79b09e90 for a past example
- [ ] Push the above change
- [ ] Run [the CI build](https://ci.eclipse.org/lsp4j/job/lsp4j-multi-build/job/master/)
- [ ] Mark the build as Keep Forever and add to the description `v0.13.0`
- [ ] Deploy the release by running [the Release CI job](https://ci.eclipse.org/lsp4j/job/lsp4j-release-eclipse) with parameters:
    - `LSP4J_PUBLISH_LOCATION` -> `updates/releases/0.13.0` ( <-- check version number)
    - `PROJECT` -> `lsp4j-multi-build/job/master`
    - `LSP4J_BUILD_NUMBER` -> the build that was just run above
- [ ] Add to the deploy job description `v0.13.0`
- [ ] Promote the staged repository to maven central
    - [Login to Nexus](https://oss.sonatype.org/#stagingRepositories)
        - To obtain permission add request to [OSSRH-26079](https://issues.sonatype.org/browse/OSSRH-26079)
    - go to *Staging Repositories*, after a short delay the staged LSP4J release should appear
    - click the staged LSP4J repo
    - press the *Close* button located in the toolbar. This runs activities, including checking various rules
    - once the rules are done (if successful), press the *Release* button (you may need to press *Refresh* to enable the *Release* button)
    - check https://search.maven.org/search?q=g:org.eclipse.lsp4j to make sure the latest release has arrived - this takes a while, 15 minutes for the files to be [on the server](https://repo1.maven.org/maven2/org/eclipse/lsp4j/) and even longer for the [search indexes](https://search.maven.org/search?q=g:org.eclipse.lsp4j) to update
- [ ] Update the meta-data on [PMI downloads page](https://projects.eclipse.org/projects/technology.lsp4j/downloads)
- [ ] Tag the release. Example: `git tag -a v0.13.0 HEAD -m"LSP4J 0.13.0" && git push origin v0.13.0`
- [ ] Contribute to Simrel. See [Simrel contribution example](https://git.eclipse.org/r/#/c/158624/)
- [ ] Create a [release page on github](https://github.com/eclipse/lsp4j/releases/new)
- [ ] Make an announcement on lsp4j-dev based on the [release page on github](https://github.com/eclipse/lsp4j/releases/tag/v0.13.0). [Example on lsp4j-dev archives](https://www.eclipse.org/lists/lsp4j-dev/msg00063.html)
- [ ] Update [documentation/releasing.md](https://github.com/eclipse/lsp4j/blob/master/documentation/releasing.md) with any changes that may have been made to the release process.
- [ ] Create the endgame for the next release right away, especially as version numbers and restoring `-SNAPSHOT` need to be done right away.
