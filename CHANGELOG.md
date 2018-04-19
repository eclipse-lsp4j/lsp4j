## LSP4J Change Log

### v0.4.0 (Apr. 2018)

 * Implemented the VS Code Debug Protocol
 * Implemented LSP version 3.7.0
 * Support multiple local and remote services
 * Improved error handling

Fixed issues: [#54](https://github.com/eclipse/lsp4j/issues/54), [#118](https://github.com/eclipse/lsp4j/issues/118), [#122](https://github.com/eclipse/lsp4j/issues/122), [#123](https://github.com/eclipse/lsp4j/issues/123), [#126](https://github.com/eclipse/lsp4j/issues/126), [#128](https://github.com/eclipse/lsp4j/issues/128), [#130](https://github.com/eclipse/lsp4j/issues/130), [#132](https://github.com/eclipse/lsp4j/issues/132), [#136](https://github.com/eclipse/lsp4j/issues/136), [#138](https://github.com/eclipse/lsp4j/issues/138), [#139](https://github.com/eclipse/lsp4j/issues/139), [#141](https://github.com/eclipse/lsp4j/issues/141), [#142](https://github.com/eclipse/lsp4j/issues/142), [#143](https://github.com/eclipse/lsp4j/issues/143), [#144](https://github.com/eclipse/lsp4j/issues/144), [#146](https://github.com/eclipse/lsp4j/issues/146), [#147](https://github.com/eclipse/lsp4j/issues/147), [#152](https://github.com/eclipse/lsp4j/issues/152), [#153](https://github.com/eclipse/lsp4j/issues/153), [#157](https://github.com/eclipse/lsp4j/issues/157), [#167](https://github.com/eclipse/lsp4j/issues/167), [#178](https://github.com/eclipse/lsp4j/issues/178), [#181](https://github.com/eclipse/lsp4j/issues/181), [#183](https://github.com/eclipse/lsp4j/issues/183)

Breaking API changes:
 * Parameter of `completion` requests changed from `TextDocumentPositionParams` to `CompletionParams` (this change **breaks all LanguageServer implementations**, but the migration is trivial)
 * Type of `CompletionItem.documentation`, `ParameterInformation.documentation` and `SignatureInformation.documentation` changed from `String` to `Either<String, MarkupContent>`
 * Type of `Hover.contents` changed from `List<Either<String, MarkedString>>` to `Either<List<Either<String, MarkedString>>, MarkupContent>`
 * All protocol properties with type `Object` are parsed to `JsonElement` (previously `Map` for objects and `List` for arrays)
 * Corrected `DocumentFilter.schema` property name to `scheme`
 * Removed `DocumentSelector` interface

-----

### v0.3.0 (Sep. 2017)

 * Support union of three types with the `Either3` class

Fixed issues: [#99](https://github.com/eclipse/lsp4j/issues/99), [#100](https://github.com/eclipse/lsp4j/issues/100), [#103](https://github.com/eclipse/lsp4j/issues/103), [#105](https://github.com/eclipse/lsp4j/issues/105), [#106](https://github.com/eclipse/lsp4j/issues/106), [#111](https://github.com/eclipse/lsp4j/issues/111), [#119](https://github.com/eclipse/lsp4j/issues/119)

-----

### v0.2.1 (Jul. 2017)

Fixed issues: [#107](https://github.com/eclipse/lsp4j/issues/107)

### v0.2.0 (May 2017)

 * Implemented LSP version 3.0
 * Support union of two types with the `Either` class
 * Support multiple parameters in protocol methods
 * Support custom error codes

Fixed issues: [#17](https://github.com/eclipse/lsp4j/issues/17), [#56](https://github.com/eclipse/lsp4j/issues/56), [#68](https://github.com/eclipse/lsp4j/issues/68), [#69](https://github.com/eclipse/lsp4j/issues/69), [#70](https://github.com/eclipse/lsp4j/issues/70), [#71](https://github.com/eclipse/lsp4j/issues/71), [#73](https://github.com/eclipse/lsp4j/issues/73), [#77](https://github.com/eclipse/lsp4j/issues/77), [#80](https://github.com/eclipse/lsp4j/issues/80), [#82](https://github.com/eclipse/lsp4j/issues/82), [#87](https://github.com/eclipse/lsp4j/issues/87), [#88](https://github.com/eclipse/lsp4j/issues/88), [#93](https://github.com/eclipse/lsp4j/issues/93)

Breaking API changes:
 * Type of `ServerCapabilities.textDocumentSync` changed from `TextDocumentSyncKind` to `Either<TextDocumentSyncKind, TextDocumentSyncOptions>`
 * Type of `Hover.contents` changed from `List<String>` to `List<Either<String, MarkedString>>`

-----

### v0.1.2 (Mar. 2017)

Fixed issues: [#58](https://github.com/eclipse/lsp4j/issues/58), [#59](https://github.com/eclipse/lsp4j/issues/59), [#71](https://github.com/eclipse/lsp4j/issues/71), [#73](https://github.com/eclipse/lsp4j/issues/73)

### v0.1.1 (Jan. 2017)

Fixed issues: [#50](https://github.com/eclipse/lsp4j/issues/50), [#51](https://github.com/eclipse/lsp4j/issues/51), [#52](https://github.com/eclipse/lsp4j/issues/52), [#53](https://github.com/eclipse/lsp4j/issues/53)
