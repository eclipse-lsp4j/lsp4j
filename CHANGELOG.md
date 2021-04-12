## LSP4J Change Log

### v0.13.0 (Date TBD)

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/20?closed=1>

Breaking API changes:

 * Method `LanguageClient.setTrace` moved to `LanguageServer`, where it should
   have been according to the specification

### v0.12.0 (Apr. 2021)

 * Restored `org.eclipse.lsp4j.websocket` which will be included along with `org.eclipse.lsp4j.websocket.jakarta`. This will allow use of LSP4J
   over Jakarta websockets and pre-Jakarta websockets. See <https://github.com/eclipse/lsp4j/issues/536> for more details.

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/19?closed=1>

Breaking API changes:

 * Type of `CodeLensOptions.resolveProvider` changed from `boolean` to `Boolean`

Breaking changes in behavior:

 * In case a type argument for `Either` is declared as `Object`,
   `EitherTypeAdapter` now uses `JsonElementTypeAdapter` for parsing/serializing
   the corresponding value. Notably, this can affect parsing of properties
   `SemanticTokensClientCapabilitiesRequests.range` and `SemanticTokensWithRegistrationOptions.range`;
   their right-hand side, declared as `Object`, will be parsed into a `JsonElement` now.

### v0.11.0 (Mar. 2021)

 * Implemented LSP version 3.16.0 (to finalized specification)
 * Implemented LSP version 3.15.0's Progress specification
 * Implemented DAP version 1.43.0
 * Implemented DAP version 1.44.0
 * Replaced `org.eclipse.lsp4j.websocket` by `org.eclipse.lsp4j.websocket.jakarta` for using LSP4J over Jakarta websockets

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/18?closed=1>

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

### v0.10.0 (Nov. 2020)

 * Implemented LSP version 3.16.0 (specification is not finalized yet)
 * Implemented DAP version 1.37.0's CancelRequest
 * Implemented DAP version 1.38.0
 * Implemented DAP version 1.39.0
 * Implemented DAP version 1.40.0
 * Implemented DAP version 1.41.0
 * Implemented DAP version 1.42.0

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/17?closed=1>

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

### v0.9.0 (Feb. 2020)

 * Implemented DAP version 1.36.0
 * Implemented DAP version 1.37.0 (except CancelRequest)
 * Implemented LSP version 3.15.0 (except Progress)
 * Improved compatibility with prior versions of `vscode-languageclient` (<https://github.com/eclipse/lsp4j/issues/407>)

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/15?closed=1>

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

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/16?closed=1>

### v0.8.0 (Aug. 2019)

 * Implemented DAP version 1.35.0
 * Added new module `org.eclipse.lsp4j.websocket` for using LSP4J over websockets

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/12?closed=1>

-----

### v0.7.2 (May 2019)

 * Updated `textDocument/callHierarchy` according to latest proposal (note: changes
   to classes and methods marked with `@Beta` are _not_ regarded as API-breaking).

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/14?closed=1>

### v0.7.1 (Apr. 2019)

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/13?closed=1>

### v0.7.0 (Feb. 2019)

 * Implemented LSP version 3.14.0
 * Support tuple of two types with the `Tuple.Two` class

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/11?closed=1>

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

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/10?closed=1>

Breaking API changes:

 * Type of `WorkspaceEdit.documentChanges` changed from `List<TextDocumentEdit>`
   to `List<Either<TextDocumentEdit, ResourceOperation>>`

-----

### v0.5.0 (Sep. 2018)

 * Updated primary license to EPL v2.0 (the secondary license EDL v1.0 remains)
 * Implemented LSP version 3.10.0
 * Implemented DAP version 1.31.0

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/3?closed=1>

Breaking API changes:

 * Return type of `textDocument/documentSymbol` changed from `List<? extends SymbolInformation>`
   to `List<Either<SymbolInformation, DocumentSymbol>>`
 * Return type of `textDocument/codeAction` changed from `List<? extends Command>`
   to `List<Either<Command, CodeAction>>`

-----

### v0.4.1 (May 2018)

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/4?closed=1>

Breaking API changes:

 * Type of `ServerCapabilities.colorProvider` changed from `ColorProviderOptions`
   to `Either<Boolean, ColorProviderOptions>`
 * Renamed `ColorPresentationParams.colorInfo` property to `color`

### v0.4.0 (Apr. 2018)

 * Implemented the VS Code Debug Protocol
 * Implemented LSP version 3.7.0
 * Support multiple local and remote services
 * Improved error handling

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/2?closed=1>

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

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/9?closed=1>

### v0.3.0 (Sep. 2017)

 * Support union of three types with the `Either3` class

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/1?closed=1>

-----

### v0.2.1 (Jul. 2017)

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/8?closed=1>

### v0.2.0 (May 2017)

 * Implemented LSP version 3.0
 * Support union of two types with the `Either` class
 * Support multiple parameters in protocol methods
 * Support custom error codes

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/7?closed=1>

Breaking API changes:

 * Type of `ServerCapabilities.textDocumentSync` changed from `TextDocumentSyncKind`
   to `Either<TextDocumentSyncKind, TextDocumentSyncOptions>`
 * Type of `Hover.contents` changed from `List<String>` to `List<Either<String, MarkedString>>`

-----

### v0.1.2 (Mar. 2017)

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/6?closed=1>

### v0.1.1 (Jan. 2017)

Fixed issues: <https://github.com/eclipse/lsp4j/milestone/5?closed=1>
