## LSP4J Change Log

### v0.9.0 (Feb. 2020)

 * Implemented DAP version 1.36.0
 * Implemented DAP version 1.37.0 (except CancelRequest)
 * Implemented LSP version 3.15.0 (except Progress)
 * Improved compatibility with prior versions of `vscode-languageclient` (https://github.com/eclipse/lsp4j/issues/407)

Fixed issues: https://github.com/eclipse/lsp4j/milestone/15?closed=1

Breaking API changes:
 * In DebugProtocol fields which were `@NonNull Long` are now `int` as they
   are required. This brings the DAP implementation in LSP4J in line with
   the LSP implementation under the same conditions.

 * In DebugProtocol fields which were `@NonNull Boolean` are now `boolean`
   as they are required. This brings the DAP implementation in LSP4J in
   line with the LSP implementation under the same conditions.

 * In DebugProtocol fields which used to be defined as `Long` are now `Integer`.
   This is due to changes in DAP 1.36 and clarification sought from
   https://github.com/microsoft/debug-adapter-protocol/issues/90

 * The DebugProtocol's `EvaluateResponse`'s field `memoryReference` was previously incorrectly
   declared as `Long`, it is now correctly declared as `String`

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

  * The LSP's Protocol `Diagnostic` field `code` was previouslyt incorrectly declared as `String`,
    it is now correctly declared as `Either<String, Number>`

### v0.8.1 (Sep. 2019)

Fixed issues: https://github.com/eclipse/lsp4j/milestone/16?closed=1

### v0.8.0 (Aug. 2019)

 * Implemented DAP version 1.35.0
 * Added new module `org.eclipse.lsp4j.websocket` for using LSP4J over websockets

Fixed issues: https://github.com/eclipse/lsp4j/milestone/12?closed=1

-----

### v0.7.2 (May 2019)

 * Updated `textDocument/callHierarchy` according to latest proposal (note: changes to classes and methods marked with `@Beta` are _not_ regarded as API-breaking).

Fixed issues: https://github.com/eclipse/lsp4j/milestone/14?closed=1

### v0.7.1 (Apr. 2019)

Fixed issues: https://github.com/eclipse/lsp4j/milestone/13?closed=1

### v0.7.0 (Feb. 2019)

 * Implemented LSP version 3.14.0
 * Support tuple of two types with the `Tuple.Two` class

Fixed issues: https://github.com/eclipse/lsp4j/milestone/11?closed=1

Breaking API changes:
 * Return type of `textDocument/definition`, `textDocument/typeDefinition` and `textDocument/implementation` changed from `List<? extends Location>` to `Either<List<? extends Location>, List<? extends LocationLink>>`
 * Type of `ParameterInformation.label` changed from `String` to `Either<String, Tuple.Two<Integer, Integer>>`
 * Setters and constructors of protocol classes throw `IllegalArgumentException` if given `null` for properties marked with `@NonNull`

-----

### v0.6.0 (Nov. 2018)

 * Implemented LSP version 3.13.0

Fixed issues: https://github.com/eclipse/lsp4j/milestone/10?closed=1

Breaking API changes:
 * Type of `WorkspaceEdit.documentChanges` changed from `List<TextDocumentEdit>` to `List<Either<TextDocumentEdit, ResourceOperation>>`

-----

### v0.5.0 (Sep. 2018)

 * Updated primary license to EPL v2.0 (the secondary license EDL v1.0 remains)
 * Implemented LSP version 3.10.0
 * Implemented DAP version 1.31.0

Fixed issues: https://github.com/eclipse/lsp4j/milestone/3?closed=1

Breaking API changes:
 * Return type of `textDocument/documentSymbol` changed from `List<? extends SymbolInformation>` to `List<Either<SymbolInformation, DocumentSymbol>>`
 * Return type of `textDocument/codeAction` changed from `List<? extends Command>` to `List<Either<Command, CodeAction>>`

-----

### v0.4.1 (May 2018)

Fixed issues: https://github.com/eclipse/lsp4j/milestone/4?closed=1

Breaking API changes:
 * Type of `ServerCapabilities.colorProvider` changed from `ColorProviderOptions` to `Either<Boolean, ColorProviderOptions>`
 * Renamed `ColorPresentationParams.colorInfo` property to `color`

### v0.4.0 (Apr. 2018)

 * Implemented the VS Code Debug Protocol
 * Implemented LSP version 3.7.0
 * Support multiple local and remote services
 * Improved error handling

Fixed issues: https://github.com/eclipse/lsp4j/milestone/2?closed=1

Breaking API changes:
 * Parameter of `completion` requests changed from `TextDocumentPositionParams` to `CompletionParams` (this change **breaks all LanguageServer implementations**, but the migration is trivial)
 * Type of `CompletionItem.documentation`, `ParameterInformation.documentation` and `SignatureInformation.documentation` changed from `String` to `Either<String, MarkupContent>`
 * Type of `Hover.contents` changed from `List<Either<String, MarkedString>>` to `Either<List<Either<String, MarkedString>>, MarkupContent>`
 * All protocol properties with type `Object` are parsed to `JsonElement` (previously `Map` for objects and `List` for arrays)
 * Corrected `DocumentFilter.schema` property name to `scheme`
 * Removed `DocumentSelector` interface
 * `VersionedTextDocumentIdentifier.version` changed from type `int` to `Integer`, thus can be `null` now.
 
-----

### v0.3.1 (Apr. 2018)

Fixed issues: https://github.com/eclipse/lsp4j/milestone/9?closed=1

### v0.3.0 (Sep. 2017)

 * Support union of three types with the `Either3` class

Fixed issues: https://github.com/eclipse/lsp4j/milestone/1?closed=1

-----

### v0.2.1 (Jul. 2017)

Fixed issues: https://github.com/eclipse/lsp4j/milestone/8?closed=1

### v0.2.0 (May 2017)

 * Implemented LSP version 3.0
 * Support union of two types with the `Either` class
 * Support multiple parameters in protocol methods
 * Support custom error codes

Fixed issues: https://github.com/eclipse/lsp4j/milestone/7?closed=1

Breaking API changes:
 * Type of `ServerCapabilities.textDocumentSync` changed from `TextDocumentSyncKind` to `Either<TextDocumentSyncKind, TextDocumentSyncOptions>`
 * Type of `Hover.contents` changed from `List<String>` to `List<Either<String, MarkedString>>`

-----

### v0.1.2 (Mar. 2017)

Fixed issues: https://github.com/eclipse/lsp4j/milestone/6?closed=1

### v0.1.1 (Jan. 2017)

Fixed issues: https://github.com/eclipse/lsp4j/milestone/5?closed=1
