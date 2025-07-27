## LSP4J Change Log

### v1.0.0 (TBD)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/37?closed=1>

* Removed `@Deprecated` annotations on members deprecated in the LSP/DAP protocol [#895](https://github.com/eclipse-lsp4j/lsp4j/issues/895)

Breaking API changes:

  * Removed deprecated API [#874](https://github.com/eclipse-lsp4j/lsp4j/issues/874)
    * Remove deprecated convenience constructors in protocol files [#875](https://github.com/eclipse-lsp4j/lsp4j/issues/875)
    * Remove deprecated LanguageServerAPI annotation [#876](https://github.com/eclipse-lsp4j/lsp4j/issues/876)
    * Remove deprecated get/set properties in FormattingOptions [#880](https://github.com/eclipse-lsp4j/lsp4j/issues/880)
    * Remove deprecated static methods in the Either class, instead use TypeUtils helper methods [#877](https://github.com/eclipse-lsp4j/lsp4j/issues/877)
    * Remove deprecated factories [#878](https://github.com/eclipse-lsp4j/lsp4j/issues/878)
    * Remove deprecated ResponseErrorCode.serverNotInitialized, use ResponseErrorCode.ServerNotInitialized instead [#879](https://github.com/eclipse-lsp4j/lsp4j/issues/879)
    * Remove deprecated org.eclipse.lsp4j.websocket. Please upgrade to using Jakarta or remain with LSP4J version 0.x.x [#647](https://github.com/eclipse-lsp4j/lsp4j/issues/647)
    * Remove deprecated ConcurrentMessageProcessor.startProcessing, use ConcurrentMessageProcessor.beginProcessing instead [#922](https://github.com/eclipse-lsp4j/lsp4j/issues/922)
 * Added beta support for new features in LSP 3.18 [#893](https://github.com/eclipse-lsp4j/lsp4j/pull/893)
    * Type of `TextDocumentEdit.edits` changed from `List<TextEdit>` to `List<Either<TextEdit, SnippetTextEdit>>`
    * Type of `Diagnostic.message` changed from `String` to `Either<String, MarkupContent>`

japicmp report: <https://download.eclipse.org/lsp4j/builds/main/japicmp-report/>

### [v0.24.0 (Jan 2025)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.24.0)

* Implemented DAP version 1.69.0

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/35?closed=1>

Breaking API changes:

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.24.0/japicmp-report/>

### [v0.23.1 (May 2024)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.23.1)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/36?closed=1>

Breaking API changes:

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.23.1/japicmp-report/>

### [v0.23.0 (May 2024)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.23.0)

 * Implemented DAP version 1.65.0

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/34?closed=1>

  * The exception handling around throwing `ResponseErrorException` has been improved to ensure that it is unwrapped to the expected `ResponseError` on the receiving side.
  In addition, `@JsonDelegate`s that throw exceptions have their checked exceptions wrapped in the more narrow `IllegalStateException` instead of a `RuntimeException`.
    * See [#802](https://github.com/eclipse-lsp4j/lsp4j/issues/802) for detailed discussion.

Breaking API changes:

  * DAP version 1.61 removed unused `ModulesViewDescriptor`

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.23.0/japicmp-report/>

### [v0.22.0 (February 2024)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.22.0)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/29?closed=1>

  * The `org.eclipse.lsp4j.websocket` bundle has been deprecated in preparation for removal in the future.
    * Please migrate to `org.eclipse.lsp4j.websocket.jakarta`
    * The `org.eclipse.lsp4j.websocket` bundle is no longer included in the SDK feature nor published to Eclipse SimRel
    * See [#647](https://github.com/eclipse-lsp4j/lsp4j/issues/647) for detailed discussion.

Breaking API changes:

  * The Message class now has a new transient field, `jsonHandler`, to enable the `toString` implementation to properly format messages when custom type adapters are used.
    * For consumers that have their own custom serializers or other reflective message processors they may need to be updated to ensure that transient fields are skipped, for example by using [`Modifier.isTransient`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/reflect/Modifier.html#isTransient(int))
    * See [#768](https://github.com/eclipse-lsp4j/lsp4j/issues/768) for detailed discussion.
  * The name field in WorkspaceFolder is no longer optional according to the specification.
    * See [#741](https://github.com/eclipse-lsp4j/lsp4j/issues/741) for detailed discussion.
  * The LSP4J generator when applied to `@JsonRpcData` annotated classes generates a dependency on package `org.eclipse.lsp4j.jsonrpc.util` in the `org.eclipse.lsp4j.jsonrpc` bundle.
    * This removes the implied requirement in LSP4J 0.21.0 that there are classes called `ToStringBuilder` and `Preconditions` in a sub-package called `util`;
      instead, the generator now uses classes `ToStringBuilder` and `Preconditions` in package `org.eclipse.lsp4j.jsonrpc.util`.
    * Duplicate classes `ToStringBuilder` and `Preconditions` have been removed from `org.eclipse.lsp4j.util` and `org.eclipse.lsp4j.debug.util` packages.
    * See [#742](https://github.com/eclipse-lsp4j/lsp4j/issues/742) for detailed discussion.

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.22.0/japicmp-report/>

### [v0.21.2 (January 2024)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.21.2)

This release widens the dependency range of Guava dependency and includes in the p2 site newer versions of third party dependencies.
See [#785](https://github.com/eclipse-lsp4j/lsp4j/issues/785) for details.

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/33?closed=1>

Breaking API changes:

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.21.2/japicmp-report/>

### [v0.21.1 (August 2023)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.21.1)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/32?closed=1>

Breaking API changes:

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.21.1/japicmp-report/>


### [v0.21.0 (May 2023)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.21.0)

 * Implemented DAP version 1.60.0
 * LSP4J's implementation of JSON-RPC 2.0 specification has been fixed regarding
   de-structure the outermost array wrapper if it's a single array parameter.
   * See [#553](https://github.com/eclipse-lsp4j/lsp4j/issues/553) for detailed discussion.
 * xbase's `@Pure` and `ToStringBuilder` is no longer used by LSP4J
   * See [#494](https://github.com/eclipse-lsp4j/lsp4j/issues/494) for detailed discussion.

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/28?closed=1>

Breaking API changes:

 * LSP4J now requires Java 11 to build and run. This can be observed in the japicmp
   report which says class file format version changed from 52.0 to 55.0.
   <https://github.com/eclipse-lsp4j/lsp4j/issues/547>.

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.21.0/japicmp-report/>

### [v0.20.1 (February 2023)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.20.1)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/29?closed=1>

Breaking API changes:

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.20.1/japicmp-report/>

### [v0.20.0 (February 2023)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.20.0)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/27?closed=1>

Breaking API changes:

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.20.0/japicmp-report/>

### [v0.19.1 (April 2023)](https://github.com/eclipse/lsp4j/releases/tag/v0.19.1)

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/30?closed=1>

Breaking API changes:

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.19.1/japicmp-report/>

### [v0.19.0 (November 2022)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.19.0)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/26?closed=1>

Breaking API changes:

 * Removed `WorkspaceDocumentDiagnosticReportTypeAdapter`, which was
   effectively replaced with `WorkspaceDocumentDiagnosticReportListAdapter`
   as part of fixing issue <https://github.com/eclipse-lsp4j/lsp4j/issues/682>.

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.19.0/japicmp-report/>

### [v0.18.0 (November 2022)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.18.0)

 * Fixed issues related to reflection errors so that `--add-opens java.base/java.util.concurrent=ALL-UNNAMED` is no longer needed on Java 17.

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/25?closed=1>

Breaking API changes:

 * None.

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.18.0/japicmp-report/>

### [v0.17.0 (October 2022)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.17.0)

 * Update org.eclipse.xtend.lib dependency to 2.28.0 to address CVE-2020-8908 #672

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/24?closed=1>

Breaking API changes:

 * None.

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.17.0/japicmp-report/>

### [v0.16.0 (October 2022)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.16.0)

 * Implemented DAP version 1.58.0

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/23?closed=1>

Breaking API changes:

 * None.

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.16.0/japicmp-report/>

### [v0.15.0 (August 2022)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.15.0)

 * Added Notebook support
 * Added `WorkspaceSymbol.data`
 * Added support for client default behavior on renames
 * Implemented DAP version 1.56.0

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/22?closed=1>

Breaking API changes:

 * Return type of `textDocument/prepareRename` changed from `Either<Range, PrepareRenameResult>`
   to `Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior>`
 * Class `SemanticTokensWithRegistrationOptions` now extends
   `AbstractTextDocumentRegistrationAndWorkDoneProgressOptions`
   instead of `AbstractWorkDoneProgressOptions`
 * Removed unspecified deprecated properties `InitializeParams.clientName`
 `DidOpenTextDocumentParams.text`, `DidChangeTextDocumentParams.uri`, `TextDocumentPositionParams.uri`

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.15.0/japicmp-report/>

### [v0.14.0 (May 2022)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.14.0)

 * Added changes to the LSP 3.17 specification that were added after specification was published as done.
   * See [#630](https://github.com/eclipse-lsp4j/lsp4j/pull/630) for all changes.

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/21?closed=1>

japicmp report: <https://download.eclipse.org/lsp4j/updates/releases/0.14.0/japicmp-report/>

### [v0.13.0 (May 2022)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.13.0)

 * Implemented LSP version 3.17.0 (except Notebook support)
 * Implemented DAP versions 1.45.0 - 1.55.0

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/20?closed=1>

Breaking API changes:

 * Method `LanguageClient.setTrace` moved to `LanguageServer`, where it should
   have been according to the specification
 * Method `IDebugProtocolServer.runInTerminal` moved to `IDebugProtocolClient`, where it should
   have been according to the specification
 * Removed `RenameOptions.id` as it was already deprecated and never specified for `StaticRegistrationOptions`
 * Removed `SemanticTokenTypes.Member` as it was already deprecated and not specified
 * Removed `TraceValue.Message` as it was already deprecated and not specified
 * Changed `TraceValue` to be `final` matching similar classes
 * Removed duplicate `ResponseErrorCode` as it has been deprecated for several versions
 * Removed `ResponseErrorCode.serverErrorStart` and `ResponseErrorCode.serverErrorEnd` as they were
   already deprecated and just boundaries not actual error codes
 * Return type of `workspace/symbol` changed from `List<? extends SymbolInformation>` to
   `Either<List<? extends SymbolInformation>, List<? extends WorkspaceSymbol>>`
 * Type of `FileSystemWatcher.globPattern` changed from `String` to `Either<String, RelativePattern>`
 * In DAP, return type of `IDebugProtocolServer.setExceptionBreakpoints` changed from `Void` to `SetExceptionBreakpointsResponse`

Breaking Beta API changes:

 * Significant updates were made to the `TypeHierarchy` features to replace the non-standard implementation
 * Removed `WorkspaceEdit.resourceChanges`, `WorkspaceEditCapabilities.resourceChanges`, and `ResourceChange` as they have been deprecated
   for numerous versions and are not specified

Deprecated API changes:

 * `SymbolInformation` is deprecated in favor of `DocumentSymbol` or `WorkspaceSymbol`
 * `ResponseErrorCode.serverNotInitialized` deprecated in favor of `ResponseErrorCode.ServerNotInitialized`

### [v0.12.0 (Apr. 2021)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.12.0)

 * Restored `org.eclipse.lsp4j.websocket` which will be included along with `org.eclipse.lsp4j.websocket.jakarta`. This will allow use of LSP4J
   over Jakarta websockets and pre-Jakarta websockets. See <https://github.com/eclipse-lsp4j/lsp4j/issues/536> for more details.

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/19?closed=1>

Breaking API changes:

 * Type of `CodeLensOptions.resolveProvider` changed from `boolean` to `Boolean`

Breaking changes in behavior:

 * In case a type argument for `Either` is declared as `Object`,
   `EitherTypeAdapter` now uses `JsonElementTypeAdapter` for parsing/serializing
   the corresponding value. Notably, this can affect parsing of properties
   `SemanticTokensClientCapabilitiesRequests.range` and `SemanticTokensWithRegistrationOptions.range`;
   their right-hand side, declared as `Object`, will be parsed into a `JsonElement` now.

### [v0.11.0 (Mar. 2021)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.11.0)

 * Implemented LSP version 3.16.0 (to finalized specification)
 * Implemented LSP version 3.15.0's Progress specification
 * Implemented DAP version 1.43.0
 * Implemented DAP version 1.44.0
 * Replaced `org.eclipse.lsp4j.websocket` by `org.eclipse.lsp4j.websocket.jakarta` for using LSP4J over Jakarta websockets

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/18?closed=1>

Breaking API changes:

 * Coloring and Semantic Highlighting APIs are removed
 * Changed some types from `Number` to `Integer`
 * Type of `CompletionItem.textEdit` changed from `TextEdit` to `Either<TextEdit, InsertReplaceEdit>`
 * Type of `ServerCapabilities.hoverProvider` changed from `Boolean` to `Either<Boolean, HoverOptions>`
 * Type of `ServerCapabilities.definitionProvider` changed from `Boolean` to `Either<Boolean, DefinitionOptions>`
 * Type of `ServerCapabilities.typeDefinitionProvider` changed from `Either<Boolean, StaticRegistrationOptions>` to `Either<Boolean, TypeDefinitionRegistrationOptions>`
 * Type of `ServerCapabilities.implementationProvider` changed from `Either<Boolean, StaticRegistrationOptions>` to `Either<Boolean, ImplementationRegistrationOptions>`
 * Type of `ServerCapabilities.referencesProvider` changed from `Boolean` to `Either<Boolean, ReferenceOptions>`
 * Type of `ServerCapabilities.documentHighlightProvider` changed from `Boolean` to `Either<Boolean, DocumentHighlightOptions>`
 * Type of `ServerCapabilities.documentSymbolProvider` changed from `Boolean` to `Either<Boolean, DocumentSymbolOptions>`
 * Type of `ServerCapabilities.workspaceSymbolProvider` changed from `Boolean` to `Either<Boolean, WorkspaceSymbolOptions>`
 * Type of `ServerCapabilities.documentFormattingProvider` changed from `Boolean` to `Either<Boolean, DocumentFormattingOptions>`
 * Type of `ServerCapabilities.documentRangeFormattingProvider` changed from `Boolean` to `Either<Boolean, DocumentRangeFormattingOptions>`
 * Type of `ServerCapabilities.declarationProvider` changed from `Either<Boolean, StaticRegistrationOptions>` to `Either<Boolean, DeclarationRegistrationOptions>`
 * Type of `ServerCapabilities.callHierarchyProvider` changed from `Either<Boolean, StaticRegistrationOptions>` to `Either<Boolean, CallHierarchyRegistrationOptions>`
 * Type of `ServerCapabilities.selectionRangeProvider` changed from `Either<Boolean, StaticRegistrationOptions>` to `Either<Boolean, SelectionRangeRegistrationOptions>`
 * Type of `ServerCapabilities.linkedEditingRangeProvider` changed from `Either<Boolean, StaticRegistrationOptions>` to `Either<Boolean, LinkedEditingRangeRegistrationOptions>`
 * Type of `ProgressParams.value` changed from `WorkDoneProgressNotification` to `Either<WorkDoneProgressNotification, Object>`

Deprecated API changes:

 * In DAP, `VariablePresentationHintKind.DATA_BREAKPOINT` has been marked @Deprecated to match its deprecation in the protocol in 1.44.
 * In DAP, `SetBreakpointsArguments.lines` has been marked @Deprecated to match its deprecation in protocol many versions ago.

### [v0.10.0 (Nov. 2020)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.10.0)

 * Implemented LSP version 3.16.0 (specification is not finalized yet)
 * Implemented DAP version 1.37.0's CancelRequest
 * Implemented DAP version 1.38.0
 * Implemented DAP version 1.39.0
 * Implemented DAP version 1.40.0
 * Implemented DAP version 1.41.0
 * Implemented DAP version 1.42.0

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/17?closed=1>

Breaking API changes:

 * In DAP, `ReadMemoryArguments.count` was `Integer` however it is
   a required property and has been changed to `int`
 * In DAP, `Module.id` has been marked `@NonNull` as `id` is a required property.
 * In DAP, `Scope.presentationHint` was an enum `ScopePresentationHint`, it is
   now a `String` as `presentationHint` has possible values that include - but
   not limited to those defined in `ScopePresentationHint`. `ScopePresentationHint`
   has changed from an enum to an interface containing String constants.
 * In DAP, `SetBreakpointsArguments.lines` was changed from `Integer[]` to
   `int[]` as the individual items in the array are not optional, but the array as
   a whole is optional.
 * In DAP, `TerminateThreadsArguments.threadIds` was changed from `Integer[]`
   to `int[]` as the individual items in the array are not optional, but the array
   as a whole is optional.

Deprecated API changes:

  * Semantic Highlighting is deprecated in favor of Semantic Tokens

### [v0.9.0 (Feb. 2020)](https://github.com/eclipse-lsp4j/lsp4j/releases/tag/v0.9.0)

 * Implemented DAP version 1.36.0
 * Implemented DAP version 1.37.0 (except CancelRequest)
 * Implemented LSP version 3.15.0 (except Progress)
 * Improved compatibility with prior versions of `vscode-languageclient` (<https://github.com/eclipse-lsp4j/lsp4j/issues/407>)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/15?closed=1>

Breaking API changes:

 * In DebugProtocol fields which were `@NonNull Long` are now `int` as they
   are required. This brings the DAP implementation in LSP4J in line with
   the LSP implementation under the same conditions.

 * In DebugProtocol fields which were `@NonNull Boolean` are now `boolean`
   as they are required. This brings the DAP implementation in LSP4J in
   line with the LSP implementation under the same conditions.

 * In DebugProtocol fields which used to be defined as `Long` are now `Integer`.
   This is due to changes in DAP 1.36 and clarification sought from
   <https://github.com/microsoft/debug-adapter-protocol/issues/90>

 * The DebugProtocol's `EvaluateResponse`'s field `memoryReference` was previously
   incorrectly declared as `Long`, it is now correctly declared as `String`

 * `CodeActionKindCapabilities.valueSet` marked with `@NonNull`

 * Parameters for the following requests have been changed from `TextDocumentPositionParams`
     * `textDocument/signatureHelp` changed to `SignatureHelpParams`
     * `textDocument/hover` changed to `HoverParams`
     * `textDocument/declaration` changed to `DeclarationParams`
     * `textDocument/definition` changed to `DefinitionParams`
     * `textDocument/typeDefinition` changed to `TypeDefinitionParams`
     * `textDocument/implementation` changed to `ImplementationParams`
     * `textDocument/documentHighlight` changed to `DocumentHighlightParams`
     * `textDocument/prepareRename` changed to `PrepareRenameParams`

 * The LSP's Protocol `Diagnostic` field `code` was previously incorrectly declared
   as `String`, it is now correctly declared as `Either<String, Number>`

### v0.8.1 (Sep. 2019)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/16?closed=1>

### v0.8.0 (Aug. 2019)

 * Implemented DAP version 1.35.0
 * Added new module `org.eclipse.lsp4j.websocket` for using LSP4J over websockets

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/12?closed=1>

-----

### v0.7.2 (May 2019)

 * Updated `textDocument/callHierarchy` according to latest proposal (note: changes
   to classes and methods marked with `@Beta` are _not_ regarded as API-breaking).

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/14?closed=1>

### v0.7.1 (Apr. 2019)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/13?closed=1>

### v0.7.0 (Feb. 2019)

 * Implemented LSP version 3.14.0
 * Support tuple of two types with the `Tuple.Two` class

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/11?closed=1>

Breaking API changes:

 * Return type of `textDocument/definition`, `textDocument/typeDefinition` and
   `textDocument/implementation` changed from `List<? extends Location>` to
   `Either<List<? extends Location>, List<? extends LocationLink>>`
 * Type of `ParameterInformation.label` changed from `String` to
   `Either<String, Tuple.Two<Integer, Integer>>`
 * Setters and constructors of protocol classes throw `IllegalArgumentException`
   if given `null` for properties marked with `@NonNull`

-----

### v0.6.0 (Nov. 2018)

 * Implemented LSP version 3.13.0

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/10?closed=1>

Breaking API changes:

 * Type of `WorkspaceEdit.documentChanges` changed from `List<TextDocumentEdit>`
   to `List<Either<TextDocumentEdit, ResourceOperation>>`

-----

### v0.5.0 (Sep. 2018)

 * Updated primary license to EPL v2.0 (the secondary license EDL v1.0 remains)
 * Implemented LSP version 3.10.0
 * Implemented DAP version 1.31.0

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/3?closed=1>

Breaking API changes:

 * Return type of `textDocument/documentSymbol` changed from `List<? extends SymbolInformation>`
   to `List<Either<SymbolInformation, DocumentSymbol>>`
 * Return type of `textDocument/codeAction` changed from `List<? extends Command>`
   to `List<Either<Command, CodeAction>>`

-----

### v0.4.1 (May 2018)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/4?closed=1>

Breaking API changes:

 * Type of `ServerCapabilities.colorProvider` changed from `ColorProviderOptions`
   to `Either<Boolean, ColorProviderOptions>`
 * Renamed `ColorPresentationParams.colorInfo` property to `color`

### v0.4.0 (Apr. 2018)

 * Implemented the VS Code Debug Protocol
 * Implemented LSP version 3.7.0
 * Support multiple local and remote services
 * Improved error handling

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/2?closed=1>

Breaking API changes:

 * Parameter of `completion` requests changed from `TextDocumentPositionParams`
   to `CompletionParams` (this change **breaks all LanguageServer implementations**,
   but the migration is trivial)
 * Type of `CompletionItem.documentation`, `ParameterInformation.documentation`
   and `SignatureInformation.documentation` changed from `String` to
   `Either<String, MarkupContent>`
 * Type of `Hover.contents` changed from `List<Either<String, MarkedString>>` to
   `Either<List<Either<String, MarkedString>>, MarkupContent>`
 * All protocol properties with type `Object` are parsed to `JsonElement`
   (previously `Map` for objects and `List` for arrays)
 * Corrected `DocumentFilter.schema` property name to `scheme`
 * Removed `DocumentSelector` interface
 * `VersionedTextDocumentIdentifier.version` changed from type `int` to `Integer`,
   thus can be `null` now.

-----

### v0.3.1 (Apr. 2018)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/9?closed=1>

### v0.3.0 (Sep. 2017)

 * Support union of three types with the `Either3` class

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/1?closed=1>

-----

### v0.2.1 (Jul. 2017)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/8?closed=1>

### v0.2.0 (May 2017)

 * Implemented LSP version 3.0
 * Support union of two types with the `Either` class
 * Support multiple parameters in protocol methods
 * Support custom error codes

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/7?closed=1>

Breaking API changes:

 * Type of `ServerCapabilities.textDocumentSync` changed from `TextDocumentSyncKind`
   to `Either<TextDocumentSyncKind, TextDocumentSyncOptions>`
 * Type of `Hover.contents` changed from `List<String>` to `List<Either<String, MarkedString>>`

-----

### v0.1.2 (Mar. 2017)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/6?closed=1>

### v0.1.1 (Jan. 2017)

Fixed issues: <https://github.com/eclipse-lsp4j/lsp4j/milestone/5?closed=1>
