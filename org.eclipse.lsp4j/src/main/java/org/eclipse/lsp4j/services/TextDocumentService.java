/******************************************************************************
 * Copyright (c) 2016-2018 TypeFox and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.common.annotations.Beta;

import org.eclipse.lsp4j.CallHierarchyIncomingCall;
import org.eclipse.lsp4j.CallHierarchyIncomingCallsParams;
import org.eclipse.lsp4j.CallHierarchyItem;
import org.eclipse.lsp4j.CallHierarchyOutgoingCall;
import org.eclipse.lsp4j.CallHierarchyOutgoingCallsParams;
import org.eclipse.lsp4j.CallHierarchyPrepareParams;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.ColorInformation;
import org.eclipse.lsp4j.ColorPresentation;
import org.eclipse.lsp4j.ColorPresentationParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DeclarationParams;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentColorParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightParams;
import org.eclipse.lsp4j.DocumentLink;
import org.eclipse.lsp4j.DocumentLinkParams;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.FoldingRange;
import org.eclipse.lsp4j.FoldingRangeRequestParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.ImplementationParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Moniker;
import org.eclipse.lsp4j.MonikerParams;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.LinkedEditingRangeParams;
import org.eclipse.lsp4j.LinkedEditingRanges;
import org.eclipse.lsp4j.PrepareRenameParams;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.ResolveTypeHierarchyItemParams;
import org.eclipse.lsp4j.SelectionRange;
import org.eclipse.lsp4j.SelectionRangeParams;
import org.eclipse.lsp4j.SemanticTokens;
import org.eclipse.lsp4j.SemanticTokensDelta;
import org.eclipse.lsp4j.SemanticTokensDeltaParams;
import org.eclipse.lsp4j.SemanticTokensParams;
import org.eclipse.lsp4j.SemanticTokensRangeParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentRegistrationOptions;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.TypeDefinitionParams;
import org.eclipse.lsp4j.TypeHierarchyItem;
import org.eclipse.lsp4j.TypeHierarchyParams;
import org.eclipse.lsp4j.WillSaveTextDocumentParams;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.adapters.CodeActionResponseAdapter;
import org.eclipse.lsp4j.adapters.DocumentSymbolResponseAdapter;
import org.eclipse.lsp4j.adapters.LocationLinkListAdapter;
import org.eclipse.lsp4j.adapters.PrepareRenameResponseAdapter;
import org.eclipse.lsp4j.adapters.SemanticTokensFullDeltaResponseAdapter;
import org.eclipse.lsp4j.jsonrpc.json.ResponseJsonAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

@JsonSegment("textDocument")
public interface TextDocumentService {

	/**
	 * The Completion request is sent from the client to the server to compute
	 * completion items at a given cursor position. Completion items are
	 * presented in the IntelliSense user interface. If computing complete
	 * completion items is expensive servers can additional provide a handler
	 * for the resolve completion item request. This request is sent when a
	 * completion item is selected in the user interface.
	 * 
	 * Registration Options: CompletionRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams position) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request is sent from the client to the server to resolve additional
	 * information for a given completion item.
	 */
	@JsonRequest(value="completionItem/resolve", useSegment = false)
	default CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The hover request is sent from the client to the server to request hover
	 * information at a given text document position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<Hover> hover(HoverParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The signature help request is sent from the client to the server to
	 * request signature information at a given cursor position.
	 * 
	 * Registration Options: SignatureHelpRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The go to declaration request is sent from the client to the server to resolve
	 * the declaration location of a symbol at a given text document position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 * 
	 * Since 3.14.0
	 */
	@JsonRequest
	@ResponseJsonAdapter(LocationLinkListAdapter.class)
	default CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> declaration(DeclarationParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The goto definition request is sent from the client to the server to resolve
	 * the definition location of a symbol at a given text document position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	@ResponseJsonAdapter(LocationLinkListAdapter.class)
	default CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The goto type definition request is sent from the client to the server to resolve
	 * the type definition location of a symbol at a given text document position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 * 
	 * Since 3.6.0
	 */
	@JsonRequest
	@ResponseJsonAdapter(LocationLinkListAdapter.class)
	default CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> typeDefinition(TypeDefinitionParams params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The goto implementation request is sent from the client to the server to resolve
	 * the implementation location of a symbol at a given text document position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 * 
	 * Since 3.6.0
	 */
	@JsonRequest
	@ResponseJsonAdapter(LocationLinkListAdapter.class)
	default CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> implementation(ImplementationParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The references request is sent from the client to the server to resolve
	 * project-wide references for the symbol denoted by the given text document
	 * position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The document highlight request is sent from the client to the server to
	 * to resolve a document highlights for a given text document position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(DocumentHighlightParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The document symbol request is sent from the client to the server to list all
	 * symbols found in a given text document.
	 * 
	 * Registration Options: {@link TextDocumentRegistrationOptions}
	 * 
	 * <p>
	 * <b>Caveat</b>: although the return type allows mixing the
	 * {@link DocumentSymbol} and {@link SymbolInformation} instances into a list do
	 * not do it because the clients cannot accept a heterogeneous list. A list of
	 * {@code DocumentSymbol} instances is only a valid return value if the
	 * {@link DocumentSymbolCapabilities#getHierarchicalDocumentSymbolSupport()
	 * textDocument.documentSymbol.hierarchicalDocumentSymbolSupport} is
	 * {@code true}. More details on this difference between the LSP and the LSP4J
	 * can be found <a href="https://github.com/eclipse/lsp4j/issues/252">here</a>.
	 * </p>
	 * 
	 * Servers should whenever possible return {@code DocumentSymbol} since it is the richer data structure.
	 */
	@JsonRequest
	@ResponseJsonAdapter(DocumentSymbolResponseAdapter.class)
	default CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The code action request is sent from the client to the server to compute
	 * commands for a given text document and range. These commands are
	 * typically code fixes to either fix problems or to beautify/refactor code.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	@ResponseJsonAdapter(CodeActionResponseAdapter.class)
	default CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request is sent from the client to the server to resolve additional information for a given code action. This is usually used to compute
	 * the `edit` property of a code action to avoid its unnecessary computation during the `textDocument/codeAction` request.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest(value="codeAction/resolve", useSegment = false)
	default CompletableFuture<CodeAction> resolveCodeAction(CodeAction unresolved) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The code lens request is sent from the client to the server to compute
	 * code lenses for a given text document.
	 * 
	 * Registration Options: CodeLensRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The code lens resolve request is sent from the client to the server to
	 * resolve the command for a given code lens item.
	 */
	@JsonRequest(value="codeLens/resolve", useSegment = false)
	default CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The document formatting request is sent from the client to the server to
	 * format a whole document.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The document range formatting request is sent from the client to the
	 * server to format a given range in a document.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The document on type formatting request is sent from the client to the
	 * server to format parts of the document during typing.
	 * 
	 * Registration Options: DocumentOnTypeFormattingRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The rename request is sent from the client to the server to do a
	 * workspace wide rename of a symbol.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The linked editing range request is sent from the client to the server to return
	 * for a given position in a document the range of the symbol at the position
	 * and all ranges that have the same content. Optionally a word pattern can be
	 * returned to describe valid contents. A rename to one of the ranges can be
	 * applied to all other ranges if the new content is valid. If no result-specific
	 * word pattern is provided, the word pattern from the client's language configuration
	 * is used.
	 * 
	 * Registration Options: LinkedEditingRangeRegistrationOptions
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest
	default CompletableFuture<LinkedEditingRanges> linkedEditingRange(LinkedEditingRangeParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The document open notification is sent from the client to the server to
	 * signal newly opened text documents. The document's truth is now managed
	 * by the client and the server must not try to read the document's truth
	 * using the document's uri.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonNotification
	void didOpen(DidOpenTextDocumentParams params);

	/**
	 * The document change notification is sent from the client to the server to
	 * signal changes to a text document.
	 * 
	 * Registration Options: TextDocumentChangeRegistrationOptions
	 */
	@JsonNotification
	void didChange(DidChangeTextDocumentParams params);

	/**
	 * The document close notification is sent from the client to the server
	 * when the document got closed in the client. The document's truth now
	 * exists where the document's uri points to (e.g. if the document's uri is
	 * a file uri the truth now exists on disk).
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonNotification
	void didClose(DidCloseTextDocumentParams params);

	/**
	 * The document save notification is sent from the client to the server when
	 * the document for saved in the client.
	 * 
	 * Registration Options: TextDocumentSaveRegistrationOptions
	 */
	@JsonNotification
	void didSave(DidSaveTextDocumentParams params);

	/**
	 * The document will save notification is sent from the client to the server before the document is actually saved.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonNotification
	default void willSave(WillSaveTextDocumentParams params) {}
	
	/**
	 * The document will save request is sent from the client to the server before the document is actually saved.
	 * The request can return an array of TextEdits which will be applied to the text document before it is saved.
	 * Please note that clients might drop results if computing the text edits took too long or if a server constantly fails on this request.
	 * This is done to keep the save fast and reliable.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<List<TextEdit>> willSaveWaitUntil(WillSaveTextDocumentParams params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The document links request is sent from the client to the server to request the location of links in a document.
	 * 
	 * Registration Options: DocumentLinkRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<List<DocumentLink>> documentLink(DocumentLinkParams params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The document link resolve request is sent from the client to the server to resolve the target of a given document link.
	 */
	@JsonRequest(value="documentLink/resolve", useSegment = false)
	default CompletableFuture<DocumentLink> documentLinkResolve(DocumentLink params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The document color request is sent from the client to the server to list all color references found in a given text
	 * document. Along with the range, a color value in RGB is returned.
	 * 
	 * Clients can use the result to decorate color references in an editor. For example:
	 *  - Color boxes showing the actual color next to the reference
	 *  - Show a color picker when a color reference is edited
	 * 
	 * Since 3.6.0
	 */
	@JsonRequest
	default CompletableFuture<List<ColorInformation>> documentColor(DocumentColorParams params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The color presentation request is sent from the client to the server to obtain a list of presentations for a color
	 * value at a given location. Clients can use the result to
	 *  - modify a color reference.
	 *  - show in a color picker and let users pick one of the presentations
	 * 
	 * Since 3.6.0
	 */
	@JsonRequest
	default CompletableFuture<List<ColorPresentation>> colorPresentation(ColorPresentationParams params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The folding range request is sent from the client to the server to return all folding
	 * ranges found in a given text document.
	 * 
	 * Since 3.10.0
	 */
	@JsonRequest
	default CompletableFuture<List<FoldingRange>> foldingRange(FoldingRangeRequestParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The prepare rename request is sent from the client to the server to setup and test the validity of a rename
	 * operation at a given location.
	 * 
	 * Since 3.12.0
	 */
	@JsonRequest
	@ResponseJsonAdapter(PrepareRenameResponseAdapter.class)
	default CompletableFuture<Either<Range, PrepareRenameResult>> prepareRename(PrepareRenameParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The {@code textDocument/typeHierarchy} request is sent from the client to the
	 * server to retrieve a {@link TypeHierarchyItem type hierarchy item} based on
	 * the {@link TypeHierarchyParams cursor position in the text document}. This
	 * request would also allow to specify if the item should be resolved and
	 * whether sub- or supertypes are to be resolved. If no type hierarchy item can
	 * be found under the given text document position, resolves to {@code null}.
	 * 
	 * <p>
	 * <b>Note:</b> the <a href=
	 * "https://github.com/Microsoft/vscode-languageserver-node/pull/426">{@code textDocument/typeHierarchy}
	 * language feature</a> is not yet part of the official LSP specification.
	 */
	@Beta
	@JsonRequest
	default CompletableFuture<TypeHierarchyItem> typeHierarchy(TypeHierarchyParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The {@code typeHierarchy/resolve} request is sent from the client to the
	 * server to resolve an unresolved {@link TypeHierarchyItem type hierarchy
	 * item}. A type hierarchy item is unresolved if the if the
	 * {@link TypeHierarchyItem#getParents parents} or the
	 * {@link TypeHierarchyItem#getChildren children} is not defined.
	 * 
	 * <p>
	 * <b>Note:</b> the <a href=
	 * "https://github.com/Microsoft/vscode-languageserver-node/pull/426">{@code textDocument/typeHierarchy}
	 * language feature</a> is not yet part of the official LSP specification.
	 */
	@Beta
	@JsonRequest(value="typeHierarchy/resolve", useSegment = false)
	default CompletableFuture<TypeHierarchyItem> resolveTypeHierarchy(ResolveTypeHierarchyItemParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Bootstraps call hierarchy by returning the item that is denoted by the given document
	 * and position. This item will be used as entry into the call graph. Providers should
	 * return null when there is no item at the given location.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest
	default CompletableFuture<List<CallHierarchyItem>> prepareCallHierarchy(CallHierarchyPrepareParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Provide all incoming calls for an item, e.g all callers for a method. In graph terms this describes directed
	 * and annotated edges inside the call graph, e.g the given item is the starting node and the result is the nodes
	 * that can be reached.
	 * 
	 * Since 3.16.0
	*/
	@JsonRequest(value="callHierarchy/incomingCalls", useSegment = false)
	default CompletableFuture<List<CallHierarchyIncomingCall>> callHierarchyIncomingCalls(CallHierarchyIncomingCallsParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	* Provide all outgoing calls for an item, e.g call calls to functions, methods, or constructors from the given item. In
	* graph terms this describes directed and annotated edges inside the call graph, e.g the given item is the starting
	* node and the result is the nodes that can be reached.
	* 
	* Since 3.16.0
	*/
	@JsonRequest(value="callHierarchy/outgoingCalls", useSegment = false)
	default CompletableFuture<List<CallHierarchyOutgoingCall>> callHierarchyOutgoingCalls(CallHierarchyOutgoingCallsParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The {@code textDocument/selectionRange} request is sent from the client to the server to return
	 * suggested selection ranges at an array of given positions. A selection range is a range around
	 * the cursor position which the user might be interested in selecting.
	 * 
	 * Since 3.15.0
	 */
	@JsonRequest
	default CompletableFuture<List<SelectionRange>> selectionRange(SelectionRangeParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The {@code textDocument/semanticTokens/full} request is sent from the client to the server to return
	 * the semantic tokens for a whole file.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest(value="textDocument/semanticTokens/full", useSegment = false)
	default CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The {@code textDocument/semanticTokens/full/delta} request is sent from the client to the server to return
	 * the semantic tokens delta for a whole file.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest(value="textDocument/semanticTokens/full/delta", useSegment = false)
	@ResponseJsonAdapter(SemanticTokensFullDeltaResponseAdapter.class)
	default CompletableFuture<Either<SemanticTokens, SemanticTokensDelta>> semanticTokensFullDelta(SemanticTokensDeltaParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The {@code textDocument/semanticTokens/range} request is sent from the client to the server to return
	 * the semantic tokens delta for a range.
	 *
	 * When a user opens a file it can be beneficial to only compute the semantic tokens for the visible range
	 * (faster rendering of the tokens in the user interface). If a server can compute these tokens faster than
	 * for the whole file it can provide a handler for the textDocument/semanticTokens/range request to handle
	 * this case special. Please note that if a client also announces that it will send the
	 * textDocument/semanticTokens/range server should implement this request as well to allow for flicker free
	 * scrolling and semantic coloring of a minimap.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest(value="textDocument/semanticTokens/range", useSegment = false)
	default CompletableFuture<SemanticTokens> semanticTokensRange(SemanticTokensRangeParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Language Server Index Format (LSIF) introduced the concept of symbol monikers to help associate symbols across
	 * different indexes. This request adds capability for LSP server implementations to provide the same symbol moniker
	 * information given a text document position. Clients can utilize this method to get the moniker at the current
	 * location in a file user is editing and do further code navigation queries in other services that rely on LSIF indexes
	 * and link symbols together.
	 *
	 * The {@code textDocument/moniker} request is sent from the client to the server to get the symbol monikers for a given
	 * text document position. An array of Moniker types is returned as response to indicate possible monikers at the given location.
	 * If no monikers can be calculated, an empty array or null should be returned.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest
	default CompletableFuture<List<Moniker>> moniker(MonikerParams params) {
		throw new UnsupportedOperationException();
	}
}
