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
package org.eclipse.lsp4j

import com.google.gson.annotations.JsonAdapter
import java.util.ArrayList
import java.util.Arrays
import java.util.LinkedHashMap
import java.util.List
import java.util.Map
import org.eclipse.lsp4j.adapters.CompletionItemDefaultsEditRangeTypeAdapter
import org.eclipse.lsp4j.adapters.CompletionItemTextEditTypeAdapter
import org.eclipse.lsp4j.adapters.DocumentChangeListAdapter
import org.eclipse.lsp4j.adapters.HoverTypeAdapter
import org.eclipse.lsp4j.adapters.InitializeParamsTypeAdapter
import org.eclipse.lsp4j.adapters.ProgressNotificationAdapter
import org.eclipse.lsp4j.adapters.ResourceOperationTypeAdapter
import org.eclipse.lsp4j.adapters.SymbolInformationTypeAdapter
import org.eclipse.lsp4j.adapters.TextEditListAdapter
import org.eclipse.lsp4j.adapters.VersionedTextDocumentIdentifierTypeAdapter
import org.eclipse.lsp4j.adapters.WorkspaceDocumentDiagnosticReportListAdapter
import org.eclipse.lsp4j.adapters.WorkspaceSymbolLocationTypeAdapter
import org.eclipse.lsp4j.generator.JsonRpcData
import org.eclipse.lsp4j.jsonrpc.json.adapters.JsonElementTypeAdapter
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.messages.Either3
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode
import org.eclipse.lsp4j.jsonrpc.messages.Tuple
import org.eclipse.lsp4j.jsonrpc.util.Preconditions
import org.eclipse.lsp4j.jsonrpc.validation.NonNull
import org.eclipse.lsp4j.jsonrpc.ProtocolDeprecated
import org.eclipse.lsp4j.jsonrpc.ProtocolDraft
import org.eclipse.lsp4j.jsonrpc.ProtocolSince

@JsonRpcData
class DynamicRegistrationCapabilities {
	/**
	 * Supports dynamic registration.
	 */
	Boolean dynamicRegistration

	new() {
	}

	new(Boolean dynamicRegistration) {
		this.dynamicRegistration = dynamicRegistration
	}
}

/**
 * Capabilities specific to {@link WorkspaceEdit}s
 */
@JsonRpcData
class WorkspaceEditCapabilities {
	/**
	 * The client supports versioned document changes in {@link WorkspaceEdit}s
	 */
	Boolean documentChanges

	/**
	 * The resource operations the client supports. Clients should at least
	 * support 'create', 'rename' and 'delete' files and folders.
	 * <p>
	 * See {@link ResourceOperationKind} for allowed values.
	 */
	@ProtocolSince("3.13.0")
	List<String> resourceOperations

	/**
	 * The failure handling strategy of a client if applying the workspace edit
	 * fails.
	 * <p>
	 * See {@link FailureHandlingKind} for allowed values.
	 */
	@ProtocolSince("3.13.0")
	String failureHandling

	/**
	 * Whether the client normalizes line endings to the client specific
	 * setting.
	 * <p>
	 * If set to {@code true} the client will normalize line ending characters
	 * in a workspace edit to the client specific new line character(s).
	 */
	@ProtocolSince("3.16.0")
	Boolean normalizesLineEndings

	/**
	 * Whether the client in general supports change annotations on text edits,
	 * create file, rename file and delete file changes.
	 */
	@ProtocolSince("3.16.0")
	WorkspaceEditChangeAnnotationSupportCapabilities changeAnnotationSupport

	/**
	 * Whether the client supports snippets as text edits.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Boolean snippetEditSupport

	/**
	 * Whether the client supports {@link WorkspaceEditMetadata} in workspace edits.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Boolean metadataSupport

	new() {
	}
}

/**
 * The kind of resource operations supported by the client.
 */
@ProtocolSince("3.13.0")
final class ResourceOperationKind {
	/**
	 * Supports creating new files and folders.
	 */
	public static val Create = 'create'

	/**
	 * Supports renaming existing files and folders.
	 */
	public static val Rename = 'rename'

	/**
	 * Supports deleting existing files and folders.
	 */
	public static val Delete = 'delete'

	private new() {
	}
}

/**
 * The kind of failure handling supported by the client.
 */
@ProtocolSince("3.13.0")
final class FailureHandlingKind {
	/**
	 * Applying the workspace change is simply aborted if one of the changes
	 * provided fails. All operations executed before the failing operation stay
	 * executed.
	 */
	public static val Abort = 'abort'

	/**
	 * All operations are executed transactional. That means they either all succeed
	 * or no changes at all are applied to the workspace.
	 */
	public static val Transactional = 'transactional'

	/**
	 * If the workspace edit contains only textual file changes they are executed
	 * transactional. If resource changes (create, rename or delete file) are part
	 * of the change the failure handling strategy is abort.
	 */
	public static val TextOnlyTransactional = 'textOnlyTransactional'

	/**
	 * The client tries to undo the operations already executed. But there is no
	 * guarantee that this is succeeding.
	 */
	public static val Undo = 'undo'

	private new() {
	}
}

/**
 * Capabilities specific to the `workspace/didChangeConfiguration` notification.
 */
@JsonRpcData
class DidChangeConfigurationCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Capabilities specific to the `workspace/didChangeWatchedFiles` notification.
 */
@JsonRpcData
class DidChangeWatchedFilesCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Whether the client has support for relative patterns
	 * or not.
	 */
	@ProtocolSince("3.17.0")
	Boolean relativePatternSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * The client support partial workspace symbols. The client will send the
 * request {@code workspaceSymbol/resolve} to the server to resolve additional
 * properties.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceSymbolResolveSupportCapabilities {
	/**
	 * The properties that a client can resolve lazily. Usually
	 * {@code location.range}
	 */
	@NonNull
	List<String> properties

	new() {
		this.properties = new ArrayList
	}

	new(@NonNull List<String> properties) {
		this.properties = Preconditions.checkNotNull(properties, 'properties')
	}
}

/**
 * Capabilities specific to the {@code workspace/symbol} request.
 * <p>
 * Referred to in the spec as {@code WorkspaceSymbolClientCapabilities}.
 */
@JsonRpcData
class SymbolCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Specific capabilities for the {@link SymbolKind} in the {@code workspace/symbol} request.
	 */
	@ProtocolSince("3.4.0")
	SymbolKindCapabilities symbolKind

	/**
	 * The client supports tags on {@link SymbolInformation} and {@link WorkspaceSymbol}.
	 * Clients supporting tags have to handle unknown tags gracefully.
	 */
	@ProtocolSince("3.16.0")
	SymbolTagSupportCapabilities tagSupport

	/**
	 * The client supports partial workspace symbols. The client will send the
	 * request {@code workspaceSymbol/resolve} to the server to resolve additional
	 * properties.
	 */
	@ProtocolSince("3.17.0")
	WorkspaceSymbolResolveSupportCapabilities resolveSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(SymbolKindCapabilities symbolKind) {
		this.symbolKind = symbolKind
	}

	new(SymbolKindCapabilities symbolKind, Boolean dynamicRegistration) {
		super(dynamicRegistration)
		this.symbolKind = symbolKind
	}
}

/**
 * Capabilities specific to the `workspace/executeCommand` request.
 */
@JsonRpcData
class ExecuteCommandCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Workspace specific client capabilities.
 */
@JsonRpcData
class WorkspaceClientCapabilities {
	/**
	 * The client supports applying batch edits to the workspace by supporting
	 * the request 'workspace/applyEdit'.
	 */
	Boolean applyEdit

	/**
	 * Capabilities specific to {@link WorkspaceEdit}s
	 */
	WorkspaceEditCapabilities workspaceEdit

	/**
	 * Capabilities specific to the `workspace/didChangeConfiguration` notification.
	 */
	DidChangeConfigurationCapabilities didChangeConfiguration

	/**
	 * Capabilities specific to the `workspace/didChangeWatchedFiles` notification.
	 */
	DidChangeWatchedFilesCapabilities didChangeWatchedFiles

	/**
	 * Capabilities specific to the `workspace/symbol` request.
	 */
	SymbolCapabilities symbol

	/**
	 * Capabilities specific to the `workspace/executeCommand` request.
	 */
	ExecuteCommandCapabilities executeCommand

	/**
	 * The client has support for workspace folders.
	 */
	@ProtocolSince("3.6.0")
	Boolean workspaceFolders

	/**
	 * The client supports `workspace/configuration` requests.
	 */
	@ProtocolSince("3.6.0")
	Boolean configuration

	/**
	 * Capabilities specific to the semantic token requests scoped to the
	 * workspace.
	 */
	@ProtocolSince("3.16.0")
	SemanticTokensWorkspaceCapabilities semanticTokens

	/**
	 * Capabilities specific to the code lens requests scoped to the
	 * workspace.
	 */
	@ProtocolSince("3.16.0")
	CodeLensWorkspaceCapabilities codeLens

	/**
	 * The client has support for file requests/notifications.
	 */
	@ProtocolSince("3.16.0")
	FileOperationsWorkspaceCapabilities fileOperations

	/**
	 * Client workspace capabilities specific to inlay hints.
	 */
	@ProtocolSince("3.17.0")
	InlayHintWorkspaceCapabilities inlayHint

	/**
	 * Client workspace capabilities specific to inline values.
	 */
	@ProtocolSince("3.17.0")
	InlineValueWorkspaceCapabilities inlineValue

	/**
	 * Client workspace capabilities specific to diagnostics.
	 */
	@ProtocolSince("3.17.0")
	DiagnosticWorkspaceCapabilities diagnostics

	/**
	 * Client workspace capabilities specific to folding ranges.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	FoldingRangeWorkspaceCapabilities foldingRange

	/**
	 * Client capabilities for a text document content provider.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	TextDocumentContentCapabilities textDocumentContent

	new() {
	}
}

/**
 * Defines which synchronization capabilities the client supports.
 */
@JsonRpcData
class SynchronizationCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client supports sending will save notifications.
	 */
	Boolean willSave

	/**
	 * The client supports sending a will save request and
	 * waits for a response providing text edits which will
	 * be applied to the document before it is saved.
	 */
	Boolean willSaveWaitUntil

	/**
	 * The client supports did save notifications.
	 */
	Boolean didSave

	new() {
	}

	new(Boolean willSave, Boolean willSaveWaitUntil, Boolean didSave) {
		this.willSave = willSave
		this.willSaveWaitUntil = willSaveWaitUntil
		this.didSave = didSave
	}

	new(Boolean willSave, Boolean willSaveWaitUntil, Boolean didSave, Boolean dynamicRegistration) {
		super(dynamicRegistration)
		this.willSave = willSave
		this.willSaveWaitUntil = willSaveWaitUntil
		this.didSave = didSave
	}
}

/**
 * Defines which filters the client supports.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class FiltersCapabilities {
	/**
	 * The client supports relative patterns in document filters.
	 */
	Boolean relativePatternSupport

	new() {
	}
}

/**
 * The client supports the following {@link CompletionItem} specific capabilities.
 */
@JsonRpcData
class CompletionItemCapabilities {
	/**
	 * Client supports snippets as insert text.
	 * <p>
	 * A snippet can define tab stops and placeholders with `$1`, `$2`
	 * and `${3:foo}`. `$0` defines the final tab stop, it defaults to
	 * the end of the snippet. Placeholders with equal identifiers are linked,
	 * that is typing in one will update others too.
	 */
	Boolean snippetSupport

	/**
	 * Client supports commit characters on a completion item.
	 */
	@ProtocolSince("3.2.0")
	Boolean commitCharactersSupport

	/**
	 * Client supports the following content formats for the documentation
	 * property. The order describes the preferred format of the client.
	 * <p>
	 * See {@link MarkupKind} for allowed values.
	 */
	@ProtocolSince("3.3.0")
	List<String> documentationFormat

	/**
	 * Client supports the deprecated property on a completion item.
	 */
	@ProtocolSince("3.8.0")
	Boolean deprecatedSupport

	/**
	 * Client supports the preselect property on a completion item.
	 */
	@ProtocolSince("3.9.0")
	Boolean preselectSupport

	/**
	 * Client supports the tag property on a completion item. Clients supporting
	 * tags have to handle unknown tags gracefully. Clients especially need to
	 * preserve unknown tags when sending a completion item back to the server in
	 * a resolve call.
	 */
	@ProtocolSince("3.15.0")
	CompletionItemTagSupportCapabilities tagSupport

	/**
	 * Client support insert replace edit to control different behavior if a
	 * completion item is inserted in the text or should replace text.
	 */
	@ProtocolSince("3.16.0")
	Boolean insertReplaceSupport

	/**
	 * Indicates which properties a client can resolve lazily on a completion
	 * item. Before version 3.16.0 only the predefined properties {@link CompletionItem#documentation}
	 * and {@link CompletionItem#detail} could be resolved lazily.
	 */
	@ProtocolSince("3.16.0")
	CompletionItemResolveSupportCapabilities resolveSupport

	/**
	 * The client supports the {@link CompletionItem#insertTextMode} property on
	 * a completion item to override the whitespace handling mode
	 * as defined by the client.
	 */
	@ProtocolSince("3.16.0")
	CompletionItemInsertTextModeSupportCapabilities insertTextModeSupport

	/**
	 * The client has support for completion item label details (see also {@link CompletionItemLabelDetails}).
	 */
	@ProtocolSince("3.17.0")
	Boolean labelDetailsSupport

	new() {
	}

	new(Boolean snippetSupport) {
		this.snippetSupport = snippetSupport
	}
}

/**
 * Client supports the tag property on a completion item. Clients supporting
 * tags have to handle unknown tags gracefully. Clients especially need to
 * preserve unknown tags when sending a completion item back to the server in
 * a resolve call.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class CompletionItemTagSupportCapabilities {
	/**
	* The tags supported by the client.
	*/
	@NonNull
	List<CompletionItemTag> valueSet

	new() {
		this.valueSet = new ArrayList
	}

	new(@NonNull List<CompletionItemTag> valueSet) {
		this.valueSet = Preconditions.checkNotNull(valueSet, 'valueSet')
	}
}

/**
 * Indicates which properties a client can resolve lazily on a completion
 * item. Before version 3.16.0 only the predefined properties {@link CompletionItem#documentation}
 * and {@link CompletionItem#detail} could be resolved lazily.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CompletionItemResolveSupportCapabilities {
	/**
	 * The properties that a client can resolve lazily.
	 */
	@NonNull
	List<String> properties

	new() {
		this.properties = new ArrayList
	}

	new(@NonNull List<String> properties) {
		this.properties = Preconditions.checkNotNull(properties, 'properties')
	}
}

/**
 * The client supports the {@link CompletionItem#insertTextMode} property on
 * a completion item to override the whitespace handling mode
 * as defined by the client.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CompletionItemInsertTextModeSupportCapabilities {
	@NonNull
	List<InsertTextMode> valueSet

	new() {
		this.valueSet = new ArrayList
	}

	new(@NonNull List<InsertTextMode> valueSet) {
		this.valueSet = Preconditions.checkNotNull(valueSet, 'valueSet')
	}
}

/**
 * The client supports the following {@link CompletionItemKind} specific
 * capabilities.
 */
@ProtocolSince("3.4.0")
@JsonRpcData
class CompletionItemKindCapabilities {
	/**
	 * The completion item kind values the client supports. When this
	 * property exists the client also guarantees that it will
	 * handle values outside its set gracefully and falls back
	 * to a default value when unknown.
	 * <p>
	 * If this property is not present the client only supports
	 * the completion items kinds from {@link CompletionItemKind#Text} to
	 * {@link CompletionItemKind#Reference} as defined in the initial version of the protocol.
	 */
	List<CompletionItemKind> valueSet

	new() {
	}

	new(List<CompletionItemKind> valueSet) {
		this.valueSet = valueSet
	}
}

/**
 * The client supports the following {@link CompletionList} specific
 * capabilities.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class CompletionListCapabilities {
	/**
	 * The client supports the following itemDefaults on
	 * a completion list.
	 * <p>
	 * The value lists the supported property names of the
	 * {@link CompletionList#itemDefaults} object. If omitted,
	 * no properties are supported.
	 */
	List<String> itemDefaults

	/**
	 * Specifies whether the client supports {@link CompletionList#applyKind} to
	 * indicate how supported values from {@link CompletionList#itemDefaults}
	 * and a completion will be combined.
	 * <p>
	 * If a client supports {@link CompletionList#applyKind} it must support it
	 * for all fields that it supports that are listed in {@link CompletionApplyKind}.
	 * This means when clients add support for new/future fields in completion items
	 * they MUST also support merge for them if those fields are defined in
	 * {@link CompletionApplyKind}.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Boolean applyKindSupport

	new() {
	}

	new(List<String> itemDefaults) {
		this.itemDefaults = itemDefaults
	}
}

/**
 * Capabilities specific to the `textDocument/completion`
 */
@JsonRpcData
class CompletionCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client supports the following {@link CompletionItem} specific
	 * capabilities.
	 */
	CompletionItemCapabilities completionItem

	/**
	 * The client supports the following {@link CompletionItemKind} specific
	 * capabilities.
	 */
	@ProtocolSince("3.4.0")
	CompletionItemKindCapabilities completionItemKind

	/**
	 * The client supports sending additional context information for a
	 * `textDocument/completion` request.
	 */
	@ProtocolSince("3.3.0")
	Boolean contextSupport

	/**
	 * The client's default when the completion item doesn't provide a
	 * {@link CompletionItem#insertTextMode} property.
	 */
	@ProtocolSince("3.17.0")
	InsertTextMode insertTextMode

	/**
	 * The client supports the following {@link CompletionList} specific
	 * capabilities.
	 */
	@ProtocolSince("3.17.0")
	CompletionListCapabilities completionList

	new() {
	}

	new(CompletionItemCapabilities completionItem) {
		this.completionItem = completionItem
	}

	new(CompletionItemKindCapabilities completionItemKind) {
		this.completionItemKind = completionItemKind
	}

	new(Boolean contextSupport) {
		this.contextSupport = contextSupport
	}
}

/**
 * Capabilities specific to the `textDocument/hover`
 */
@JsonRpcData
class HoverCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Client supports the following content formats if the content
	 * property refers to {@link MarkupContent}.
	 * The order describes the preferred format of the client.
	 * <p>
	 * See {@link MarkupKind} for allowed values.
	 */
	@ProtocolSince("3.3.0")
	List<String> contentFormat

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(List<String> contentFormat, Boolean dynamicRegistration) {
		super(dynamicRegistration)
		this.contentFormat = contentFormat
	}
}

/**
 * The client supports the following {@link SignatureInformation} specific properties.
 */
@JsonRpcData
class SignatureInformationCapabilities {
	/**
	 * Client supports the following content formats for the documentation
	 * property. The order describes the preferred format of the client.
	 * <p>
	 * See {@link MarkupKind} for allowed values.
	 */
	@ProtocolSince("3.3.0")
	List<String> documentationFormat

	/**
	 * Client capabilities specific to parameter information.
	 */
	@ProtocolSince("3.14.0")
	ParameterInformationCapabilities parameterInformation

	/**
	 * The client supports the {@link SignatureInformation#activeParameter} property.
	 */
	@ProtocolSince("3.16.0")
	Boolean activeParameterSupport

	new() {
	}

	new(List<String> documentationFormat) {
		this.documentationFormat = documentationFormat
	}
}

/**
 * Client capabilities specific to parameter information.
 */
@ProtocolSince("3.14.0")
@JsonRpcData
class ParameterInformationCapabilities {
	/**
	 * The client supports processing label offsets instead of a
	 * simple label string.
	 */
	Boolean labelOffsetSupport

	new() {
	}

	new(Boolean labelOffsetSupport) {
		this.labelOffsetSupport = labelOffsetSupport
	}
}

/**
 * Capabilities specific to the `textDocument/signatureHelp`
 */
@JsonRpcData
class SignatureHelpCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client supports the following {@link SignatureInformation}
	 * specific properties.
	 */
	SignatureInformationCapabilities signatureInformation

	/**
	 * The client supports to send additional context information for a
	 * `textDocument/signatureHelp` request. A client that opts into
	 * contextSupport will also support {@link SignatureHelpOptions#retriggerCharacters}.
	 */
	@ProtocolSince("3.15.0")
	Boolean contextSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(SignatureInformationCapabilities signatureInformation, Boolean dynamicRegistration) {
		super(dynamicRegistration)
		this.signatureInformation = signatureInformation
	}
}

/**
 * Capabilities specific to the `textDocument/references`
 */
@JsonRpcData
class ReferencesCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Capabilities specific to the `textDocument/documentHighlight`
 */
@JsonRpcData
class DocumentHighlightCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Specific capabilities for the {@link SymbolKind}.
 */
@ProtocolSince("3.4.0")
@JsonRpcData
class SymbolKindCapabilities {
	/**
	 * The symbol kind values the client supports. When this
	 * property exists the client also guarantees that it will
	 * handle values outside its set gracefully and falls back
	 * to a default value when unknown.
	 * <p>
	 * If this property is not present the client only supports
	 * the symbol kinds from {@link SymbolKind#File} to
	 * {@link SymbolKind#Array} as defined in the initial version of the protocol.
	 */
	List<SymbolKind> valueSet

	new() {
	}

	new(List<SymbolKind> valueSet) {
		this.valueSet = valueSet
	}
}

/**
 * Specific capabilities for the {@link SymbolTag}.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class SymbolTagSupportCapabilities {
	/**
	 * The tags supported by the client.
	 */
	@NonNull
	List<SymbolTag> valueSet

	new() {
		this.valueSet = new ArrayList
	}

	new(@NonNull List<SymbolTag> valueSet) {
		this.valueSet = Preconditions.checkNotNull(valueSet, 'valueSet')
	}
}

/**
 * Capabilities specific to the `textDocument/documentSymbol`
 */
@JsonRpcData
class DocumentSymbolCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Specific capabilities for the {@link SymbolKind}.
	 */
	@ProtocolSince("3.4.0")
	SymbolKindCapabilities symbolKind

	/**
	 * The client support hierarchical document symbols.
	 */
	@ProtocolSince("3.10.0")
	Boolean hierarchicalDocumentSymbolSupport

	/**
	 * The client supports tags on {@link SymbolInformation}. Tags are supported on
	 * {@link DocumentSymbol} if {@link #hierarchicalDocumentSymbolSupport} is set to true.
	 * Clients supporting tags have to handle unknown tags gracefully.
	 */
	@ProtocolSince("3.16.0")
	SymbolTagSupportCapabilities tagSupport

	/**
	 * The client supports an additional label presented in the UI when
	 * registering a document symbol provider.
	 */
	@ProtocolSince("3.16.0")
	Boolean labelSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(SymbolKindCapabilities symbolKind) {
		this.symbolKind = symbolKind
	}

	new(SymbolKindCapabilities symbolKind, Boolean dynamicRegistration) {
		super(dynamicRegistration)
		this.symbolKind = symbolKind
	}

	new(SymbolKindCapabilities symbolKind, Boolean dynamicRegistration, Boolean hierarchicalDocumentSymbolSupport) {
		super(dynamicRegistration)
		this.symbolKind = symbolKind
		this.hierarchicalDocumentSymbolSupport = hierarchicalDocumentSymbolSupport
	}
}

/**
 * Capabilities specific to the `textDocument/formatting`
 */
@JsonRpcData
class FormattingCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Capabilities specific to the `textDocument/rangeFormatting`
 * and `textDocument/rangesFormatting`
 */
@JsonRpcData
class RangeFormattingCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Whether the client supports formatting multiple ranges at once.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Boolean rangesSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Capabilities specific to the `textDocument/onTypeFormatting`
 */
@JsonRpcData
class OnTypeFormattingCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Capabilities specific to the `textDocument/definition`
 */
@ProtocolSince("3.14.0")
@JsonRpcData
class DefinitionCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client supports additional metadata in the form of definition links.
	 */
	Boolean linkSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(Boolean dynamicRegistration, Boolean linkSupport) {
		super(dynamicRegistration)
		this.linkSupport = linkSupport
	}
}

/**
 * Capabilities specific to the `textDocument/declaration`
 */
@ProtocolSince("3.14.0")
@JsonRpcData
class DeclarationCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client supports additional metadata in the form of declaration links.
	 */
	Boolean linkSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(Boolean dynamicRegistration, Boolean linkSupport) {
		super(dynamicRegistration)
		this.linkSupport = linkSupport
	}
}

/**
 * Capabilities specific to the `textDocument/typeDefinition`
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class TypeDefinitionCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client supports additional metadata in the form of definition links.
	 */
	@ProtocolSince("3.14.0")
	Boolean linkSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(Boolean dynamicRegistration, Boolean linkSupport) {
		super(dynamicRegistration)
		this.linkSupport = linkSupport
	}
}

/**
 * Capabilities specific to the `textDocument/implementation`.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class ImplementationCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client supports additional metadata in the form of definition links.
	 */
	@ProtocolSince("3.14.0")
	Boolean linkSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(Boolean dynamicRegistration, Boolean linkSupport) {
		super(dynamicRegistration)
		this.linkSupport = linkSupport
	}
}

@JsonRpcData
class CodeActionKindCapabilities {
	/**
	 * The code action kind values the client supports. When this
	 * property exists the client also guarantees that it will
	 * handle values outside its set gracefully and falls back
	 * to a default value when unknown.
	 * <p>
	 * See {@link CodeActionKind} for some predefined code action kinds.
	 */
	@NonNull
	List<String> valueSet

	new() {
		this.valueSet = new ArrayList
	}

	new(@NonNull List<String> valueSet) {
		this.valueSet = Preconditions.checkNotNull(valueSet, 'valueSet')
	}
}

@JsonRpcData
class CodeActionLiteralSupportCapabilities {
	/**
	 * The code action kind is support with the following value
	 * set.
	 */
	CodeActionKindCapabilities codeActionKind

	new() {
	}

	new(CodeActionKindCapabilities codeActionKind) {
		this.codeActionKind = codeActionKind
	}
}

/**
 * Whether the client supports resolving additional code action
 * properties via a separate `codeAction/resolve` request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CodeActionResolveSupportCapabilities {
	/**
	 * The properties that a client can resolve lazily.
	 */
	@NonNull
	List<String> properties

	new() {
		this.properties = new ArrayList
	}

	new(@NonNull List<String> properties) {
		this.properties = Preconditions.checkNotNull(properties, 'properties')
	}
}

/**
 * Client supports the tag property on a code action. Clients
 * supporting tags have to handle unknown tags gracefully.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class CodeActionTagSupportCapabilities {
	/**
	* The tags supported by the client.
	*/
	@NonNull
	List<CodeActionTag> valueSet

	new() {
		this.valueSet = new ArrayList
	}

	new(@NonNull List<CodeActionTag> valueSet) {
		this.valueSet = Preconditions.checkNotNull(valueSet, 'valueSet')
	}
}

/**
 * Capabilities specific to the `textDocument/codeAction`
 */
@JsonRpcData
class CodeActionCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client support code action literals as a valid
	 * response of the `textDocument/codeAction` request.
	 */
	CodeActionLiteralSupportCapabilities codeActionLiteralSupport

	/**
	 * Whether code action supports the {@link CodeAction#isPreferred} property.
	 */
	@ProtocolSince("3.15.0")
	Boolean isPreferredSupport

	/**
	 * Whether code action supports the {@link CodeAction#disabled} property.
	 */
	@ProtocolSince("3.16.0")
	Boolean disabledSupport

	/**
	 * Whether code action supports the {@link CodeAction#data} property which is
	 * preserved between a `textDocument/codeAction` and a
	 * `codeAction/resolve` request.
	 */
	@ProtocolSince("3.16.0")
	Boolean dataSupport

	/**
	 * Whether the client supports resolving additional code action
	 * properties via a separate `codeAction/resolve` request.
	 */
	@ProtocolSince("3.16.0")
	CodeActionResolveSupportCapabilities resolveSupport

	/**
	 * Whether the client honors the change annotations in
	 * text edits and resource operations returned via the
	 * {@link CodeAction#edit} property by for example presenting
	 * the workspace edit in the user interface and asking
	 * for confirmation.
	 */
	@ProtocolSince("3.16.0")
	Boolean honorsChangeAnnotations

	/**
	 * Whether the client supports documentation for a class of code actions.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Boolean documentationSupport

	/**
	 * Client supports the tag property on a code action. Clients
	 * supporting tags have to handle unknown tags gracefully.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	CodeActionTagSupportCapabilities tagSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(CodeActionLiteralSupportCapabilities codeActionLiteralSupport, Boolean dynamicRegistration) {
		super(dynamicRegistration)
		this.codeActionLiteralSupport = codeActionLiteralSupport
	}

	new(CodeActionLiteralSupportCapabilities codeActionLiteralSupport, Boolean dynamicRegistration, Boolean isPreferredSupport) {
		this(codeActionLiteralSupport, dynamicRegistration)
		this.isPreferredSupport = isPreferredSupport
	}
}

/**
 * Whether the client supports resolving additional code lens
 * properties via a separate {@code codeLens/resolve} request.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class CodeLensResolveSupportCapabilities {
	/**
	 * The properties that a client can resolve lazily.
	 */
	@NonNull
	List<String> properties

	new() {
		this.properties = new ArrayList
	}

	new(@NonNull List<String> properties) {
		this.properties = Preconditions.checkNotNull(properties, 'properties')
	}
}

/**
 * Capabilities specific to the {@code textDocument/codeLens}
 */
@JsonRpcData
class CodeLensCapabilities extends DynamicRegistrationCapabilities {

	/**
	 * Whether the client supports resolving additional code lens
	 * properties via a separate {@code codeLens/resolve} request.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	CodeLensResolveSupportCapabilities resolveSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Capabilities specific to the code lens requests scoped to the
 * workspace.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CodeLensWorkspaceCapabilities {
	/**
	 * Whether the client implementation supports a refresh request sent from the
	 * server to the client.
	 * <p>
	 * Note that this event is global and will force the client to refresh all
	 * code lenses currently shown. It should be used with absolute care and is
	 * useful for situations where a server for example detects a project-wide
	 * change that requires such a calculation.
	 */
	Boolean refreshSupport

	new() {
	}

	new(Boolean refreshSupport) {
		this.refreshSupport = refreshSupport
	}
}

/**
 * The client has support for file requests/notifications.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileOperationsWorkspaceCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client has support for sending didCreateFiles notifications.
	 */
	Boolean didCreate

	/**
	 * The client has support for sending willCreateFiles requests.
	 */
	Boolean willCreate

	/**
	 * The client has support for sending didRenameFiles notifications.
	 */
	Boolean didRename

	/**
	 * The client has support for sending willRenameFiles requests.
	 */
	Boolean willRename

	/**
	 * The client has support for sending didDeleteFiles notifications.
	 */
	Boolean didDelete

	/**
	 * The client has support for sending willDeleteFiles requests.
	 */
	Boolean willDelete

	new() {
	}
}

/**
 * Capabilities specific to the `textDocument/documentLink`
 */
@JsonRpcData
class DocumentLinkCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Whether the client supports the {@link DocumentLink#tooltip} property.
	 */
	@ProtocolSince("3.15.0")
	Boolean tooltipSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(Boolean dynamicRegistration, Boolean tooltipSupport) {
		super(dynamicRegistration)
		this.tooltipSupport = tooltipSupport
	}
}

/**
 * Capabilities specific to the `textDocument/documentColor` and the
 * `textDocument/colorPresentation` request.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class ColorProviderCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Capabilities specific to the `textDocument/rename`
 */
@JsonRpcData
class RenameCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Client supports testing for validity of rename operations
	 * before execution.
	 */
	@ProtocolSince("3.12.0")
	Boolean prepareSupport

	/**
	 * Client supports the default behavior result ({@code &#123; defaultBehavior: boolean &#125;}).
	 * <p>
	 * The value indicates the default behavior used by the client.
	 */
	@ProtocolSince("3.16.0")
	PrepareSupportDefaultBehavior prepareSupportDefaultBehavior

	/**
	 * Whether the client honors the change annotations in
	 * text edits and resource operations returned via the
	 * rename request's workspace edit by for example presenting
	 * the workspace edit in the user interface and asking
	 * for confirmation.
	 */
	@ProtocolSince("3.16.0")
	Boolean honorsChangeAnnotations

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(Boolean prepareSupport, Boolean dynamicRegistration) {
		super(dynamicRegistration)
		this.prepareSupport = prepareSupport
	}
}

/**
 * Capabilities specific to `textDocument/publishDiagnostics`.
 */
@JsonRpcData
class PublishDiagnosticsCapabilities {
	/**
	 * Whether the client accepts diagnostics with related information.
	 */
	Boolean relatedInformation

	/**
	 * Client supports the tag property to provide meta data about a diagnostic.
	 * Clients supporting tags have to handle unknown tags gracefully.
	 * <p>
	 * This property had been added and implemented as boolean before it was
	 * added to the specification as {@link DiagnosticsTagSupport}. In order to
	 * keep this implementation compatible with intermediate clients (including
	 * vscode-language-client &lt; 6.0.0) we add an either type here.
	 */
	@ProtocolSince("3.15.0")
	Either<Boolean, DiagnosticsTagSupport> tagSupport

	/**
	 * Whether the client interprets the version property of the
	 * `textDocument/publishDiagnostics` notification's parameter.
	 */
	@ProtocolSince("3.15.0")
	Boolean versionSupport

	/**
	 * Client supports a codeDescription property
	 */
	@ProtocolSince("3.16.0")
	Boolean codeDescriptionSupport

	/**
	 * Whether code action supports the {@link Diagnostic#data} property which is
	 * preserved between a `textDocument/publishDiagnostics` and
	 * `textDocument/codeAction` request.
	 */
	@ProtocolSince("3.16.0")
	Boolean dataSupport

	new() {
	}

	new(Boolean relatedInformation) {
		this.relatedInformation = relatedInformation
	}

	new(Boolean relatedInformation, DiagnosticsTagSupport tagSupport) {
		this(relatedInformation)
		this.tagSupport = tagSupport
	}

	new(Boolean relatedInformation, DiagnosticsTagSupport tagSupport, Boolean versionSupport) {
		this(relatedInformation, tagSupport)
		this.versionSupport = versionSupport
	}
}

@JsonRpcData
class DiagnosticsTagSupport {
	/**
	* The tags supported by the client.
	*/
	@NonNull
	List<DiagnosticTag> valueSet

	new() {
		this.valueSet = new ArrayList
	}

	new(@NonNull List<DiagnosticTag> valueSet) {
		this.valueSet = Preconditions.checkNotNull(valueSet, 'valueSet')
	}
}

/**
 * Specific options for the folding range kind.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class FoldingRangeKindSupportCapabilities {
	/**
	 * The folding range kind values the client supports. When this
	 * property exists the client also guarantees that it will
	 * handle values outside its set gracefully and falls back
	 * to a default value when unknown.
	 * <p>
	 * See {@link FoldingRangeKind} for some predefined folding range kinds.
	 */
	List<String> valueSet

	new() {
	}

	new(List<String> valueSet) {
		this.valueSet = valueSet
	}
}

/**
 * Specific options for the folding range.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class FoldingRangeSupportCapabilities {
	/**
	 * If set, the client signals that it supports setting {@link FoldingRange#collapsedText} on
	 * folding ranges to display custom labels instead of the default text.
	 */
	Boolean collapsedText

	new() {
	}

	new(Boolean collapsedText) {
		this.collapsedText = collapsedText
	}
}

/**
 * Capabilities specific to `textDocument/foldingRange` requests.
 */
@ProtocolSince("3.10.0")
@JsonRpcData
class FoldingRangeCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The maximum number of folding ranges that the client prefers to receive per document. The value serves as a
	 * hint, servers are free to follow the limit.
	 */
	Integer rangeLimit

	/**
	 * If set, the client signals that it only supports folding complete lines. If set, client will
	 * ignore specified {@link FoldingRange#startCharacter} and {@link FoldingRange#endCharacter} properties.
	 */
	Boolean lineFoldingOnly

	/**
	 * Specific options for the folding range kind.
	 */
	@ProtocolSince("3.17.0")
	FoldingRangeKindSupportCapabilities foldingRangeKind

	/**
	 * Specific options for the folding range.
	 */
	@ProtocolSince("3.17.0")
	FoldingRangeSupportCapabilities foldingRange

	new() {
	}
}

/**
 * Client workspace capabilities specific to folding ranges.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class FoldingRangeWorkspaceCapabilities {
	/**
	 * Whether the client implementation supports a refresh request sent from the
	 * server to the client.
	 * <p>
	 * Note that this event is global and will force the client to refresh all
	 * folding ranges currently shown. It should be used with absolute care and is
	 * useful for situations where a server, for example, detects a project-wide
	 * change that requires such a calculation.
	 */
	Boolean refreshSupport

	new() {
	}

	new(Boolean refreshSupport) {
		this.refreshSupport = refreshSupport
	}
}

/**
 * Capabilities specific to the {@code textDocument/prepareTypeHierarchy}.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class TypeHierarchyCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Type hierarchy registration options.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class TypeHierarchyRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * Capabilities specific to the {@code textDocument/prepareCallHierarchy}.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyOptions extends AbstractWorkDoneProgressOptions {
}

@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * Capabilities specific to `textDocument/selectionRange` requests
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class SelectionRangeCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensClientCapabilitiesRequestsFull {
	/**
	* The client will send the `textDocument/semanticTokens/full/delta` request if
	* the server provides a corresponding handler.
	*/
	Boolean delta

	new() {
	}

	new(Boolean delta) {
		this.delta = delta
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensClientCapabilitiesRequests {
	/**
	* The client will send the `textDocument/semanticTokens/range` request if
	* the server provides a corresponding handler.
	*/
	Either<Boolean, Object> range

	/**
	* The client will send the `textDocument/semanticTokens/full` request if
	* the server provides a corresponding handler.
	*/
	Either<Boolean, SemanticTokensClientCapabilitiesRequestsFull> full

	new() {
	}

	new(Boolean full) {
		this.full = full
	}

	new(SemanticTokensClientCapabilitiesRequestsFull full) {
		this.full = full
	}

	new(Boolean full, Boolean range) {
		this.full = full
		this.range = range
	}

	new(SemanticTokensClientCapabilitiesRequestsFull full, Boolean range) {
		this.full = full
		this.range = range
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Which requests the client supports and might send to the server.
	 */
	@NonNull
	SemanticTokensClientCapabilitiesRequests requests

	/**
	 * The token types that the client supports.
	 * <p>
	 * See {@link SemanticTokenTypes} for allowed values.
	 */
	@NonNull
	List<String> tokenTypes

	/**
	 * The token modifiers that the client supports.
	 * <p>
	 * See {@link SemanticTokenModifiers} for allowed values.
	 */
	@NonNull
	List<String> tokenModifiers

	/**
	 * The formats the client supports.
	 * <p>
	 * See {@link TokenFormat} for allowed values.
	 */
	@NonNull
	List<String> formats

	/**
	 * Whether the client supports tokens that can overlap each other.
	 */
	Boolean overlappingTokenSupport

	/**
	 * Whether the client supports tokens that can span multiple lines.
	 */
	Boolean multilineTokenSupport

	/**
	 * Whether the client allows the server to actively cancel a
	 * semantic token request, e.g. supports returning
	 * {@link ResponseErrorCode#ServerCancelled}.
	 * If a server does, the client needs to retrigger the request.
	 */
	@ProtocolSince("3.17.0")
	Boolean serverCancelSupport

	/**
	 * Whether the client uses semantic tokens to augment existing
	 * syntax tokens. If set to {@code true} client side created syntax
	 * tokens and semantic tokens are both used for colorization. If
	 * set to {@code false} the client only uses the returned semantic tokens
	 * for colorization.
	 * <p>
	 * If the value is omitted then the client behavior is not
	 * specified.
	 */
	@ProtocolSince("3.17.0")
	Boolean augmentsSyntaxTokens

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(@NonNull SemanticTokensClientCapabilitiesRequests requests, @NonNull List<String> tokenTypes, @NonNull List<String> tokenModifiers, @NonNull List<String> formats) {
		this.requests = Preconditions.checkNotNull(requests, 'requests')
		this.tokenTypes = Preconditions.checkNotNull(tokenTypes, 'tokenTypes')
		this.tokenModifiers = Preconditions.checkNotNull(tokenModifiers, 'tokenModifiers')
		this.formats = Preconditions.checkNotNull(formats, 'formats')
	}

	new(Boolean dynamicRegistration, @NonNull SemanticTokensClientCapabilitiesRequests requests, @NonNull List<String> tokenTypes, @NonNull List<String> tokenModifiers, @NonNull List<String> formats) {
		super(dynamicRegistration)
		this.requests = Preconditions.checkNotNull(requests, 'requests')
		this.tokenTypes = Preconditions.checkNotNull(tokenTypes, 'tokenTypes')
		this.tokenModifiers = Preconditions.checkNotNull(tokenModifiers, 'tokenModifiers')
		this.formats = Preconditions.checkNotNull(formats, 'formats')
	}
}

/**
 * Capabilities specific to the {@code textDocument/linkedEditingRange} request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class LinkedEditingRangeCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Capabilities specific to the semantic token requests scoped to the
 * workspace.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensWorkspaceCapabilities {
	/**
	 * Whether the client implementation supports a refresh request sent from the
	 * server to the client.
	 * <p>
	 * Note that this event is global and will force the client to refresh all
	 * semantic tokens currently shown. It should be used with absolute care and is
	 * useful for situations where a server for example detects a project-wide
	 * change that requires such a calculation.
	 */
	Boolean refreshSupport

	new() {
	}

	new(Boolean refreshSupport) {
		this.refreshSupport = refreshSupport
	}
}

/**
 * Capabilities specific to the {@code textDocument/moniker} request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class MonikerCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Show message request client capabilities
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class WindowShowMessageRequestCapabilities {
	/**
	 * Capabilities specific to the {@link MessageActionItem} type.
	 */
	WindowShowMessageRequestActionItemCapabilities messageActionItem

	new() {
	}
}

/**
 * Client capabilities for the show document request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class ShowDocumentCapabilities {
	/**
	 * The client has support for the show document
	 * request.
	 */
	boolean support

	new() {
	}

	new(boolean support) {
		this.support = support
	}
}

/**
 * Capabilities specific to the {@link MessageActionItem} type of show message request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class WindowShowMessageRequestActionItemCapabilities {
	/**
	 * Whether the client supports additional attributes which
	 * are preserved and sent back to the server in the
	 * request's response.
	 */
	Boolean additionalPropertiesSupport

	new() {
	}

	new(Boolean additionalPropertiesSupport) {
		this.additionalPropertiesSupport = additionalPropertiesSupport
	}
}

/**
 * Client capabilities specific to regular expressions.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class RegularExpressionsCapabilities {
	/**
	 * The engine's name.
	 */
	@NonNull
	String engine

	/**
	 * The engine's version.
	 */
	String version

	new() {
	}

	new(@NonNull String engine) {
		this.engine = Preconditions.checkNotNull(engine, 'engine')
	}

	new(@NonNull String engine, String version) {
		this(engine)
		this.version = version
	}
}

/**
 * Client capabilities specific to the used markdown parser.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class MarkdownCapabilities {
	/**
	 * The name of the parser.
	 */
	@NonNull
	String parser

	/**
	 * The version of the parser.
	 */
	String version

	/**
	 * A list of HTML tags that the client allows / supports in Markdown.
	 */
	@ProtocolSince("3.17.0")
	List<String> allowedTags

	new() {
	}

	new(@NonNull String parser) {
		this.parser = Preconditions.checkNotNull(parser, 'parser')
	}

	new(@NonNull String parser, String version) {
		this(parser)
		this.version = version
	}
}

/**
 * Client capability that signals how the client handles stale requests
 * (e.g. a request for which the client will not process the response
 * anymore since the information is outdated).
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class StaleRequestCapabilities {
	/**
	 * The client will actively cancel the request.
	 */
	boolean cancel

	/**
	 * The list of requests for which the client will retry the request if it receives
	 * a response with error code {@code ContentModified}
	 */
	@NonNull
	List<String> retryOnContentModified

	new() {
		this.retryOnContentModified = new ArrayList
	}

	new(boolean cancel, @NonNull List<String> retryOnContentModified) {
		this.cancel = cancel
		this.retryOnContentModified = Preconditions.checkNotNull(retryOnContentModified, 'retryOnContentModified')
	}
}

/**
 * Whether the client in general supports change annotations on text edits,
 * create file, rename file and delete file changes.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class WorkspaceEditChangeAnnotationSupportCapabilities {
	/**
	 * Whether the client groups edits with equal labels into tree nodes,
	 * for instance all edits labelled with "Changes in Strings" would
	 * be a tree node.
	 */
	Boolean groupsOnLabel

	new() {
	}

	new(Boolean groupsOnLabel) {
		this.groupsOnLabel = groupsOnLabel
	}
}


/**
 * Text document specific client capabilities.
 */
@JsonRpcData
class TextDocumentClientCapabilities {
	/**
	 * Defines which synchronization capabilities the client supports.
	 */
	SynchronizationCapabilities synchronization

	/**
	 * Defines which filters the client supports.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	FiltersCapabilities filters

	/**
	 * Capabilities specific to the {@code textDocument/completion}
	 */
	CompletionCapabilities completion

	/**
	 * Capabilities specific to the {@code textDocument/hover}
	 */
	HoverCapabilities hover

	/**
	 * Capabilities specific to the {@code textDocument/signatureHelp}
	 */
	SignatureHelpCapabilities signatureHelp

	/**
	 * Capabilities specific to the {@code textDocument/references}
	 */
	ReferencesCapabilities references

	/**
	 * Capabilities specific to the {@code textDocument/documentHighlight}
	 */
	DocumentHighlightCapabilities documentHighlight

	/**
	 * Capabilities specific to the {@code textDocument/documentSymbol}
	 */
	DocumentSymbolCapabilities documentSymbol

	/**
	 * Capabilities specific to the {@code textDocument/formatting}
	 */
	FormattingCapabilities formatting

	/**
	 * Capabilities specific to the {@code textDocument/rangeFormatting}
	 * and {@code textDocument/rangesFormatting}
	 */
	RangeFormattingCapabilities rangeFormatting

	/**
	 * Capabilities specific to the {@code textDocument/onTypeFormatting}
	 */
	OnTypeFormattingCapabilities onTypeFormatting

	/**
	 * Capabilities specific to the {@code textDocument/declaration}
	 */
	@ProtocolSince("3.14.0")
	DeclarationCapabilities declaration

	/**
	 * Capabilities specific to the {@code textDocument/definition}
	 */
	@ProtocolSince("3.14.0")
	DefinitionCapabilities definition

	/**
	 * Capabilities specific to the {@code textDocument/typeDefinition}
	 */
	@ProtocolSince("3.6.0")
	TypeDefinitionCapabilities typeDefinition

	/**
	 * Capabilities specific to the {@code textDocument/implementation}
	 */
	@ProtocolSince("3.6.0")
	ImplementationCapabilities implementation

	/**
	 * Capabilities specific to the {@code textDocument/codeAction}
	 */
	CodeActionCapabilities codeAction

	/**
	 * Capabilities specific to the {@code textDocument/codeLens}
	 */
	CodeLensCapabilities codeLens

	/**
	 * Capabilities specific to the {@code textDocument/documentLink}
	 */
	DocumentLinkCapabilities documentLink

	/**
	 * Capabilities specific to the {@code textDocument/documentColor} and the
	 * {@code textDocument/colorPresentation} request.
	 */
	@ProtocolSince("3.6.0")
	ColorProviderCapabilities colorProvider

	/**
	 * Capabilities specific to the {@code textDocument/rename}
	 */
	RenameCapabilities rename

	/**
	 * Capabilities specific to {@code textDocument/publishDiagnostics}.
	 */
	PublishDiagnosticsCapabilities publishDiagnostics

	/**
	 * Capabilities specific to {@code textDocument/foldingRange} requests.
	 */
	@ProtocolSince("3.10.0")
	FoldingRangeCapabilities foldingRange

	/**
	 * Capabilities specific to {@code textDocument/prepareTypeHierarchy}.
	 */
	@ProtocolSince("3.17.0")
	TypeHierarchyCapabilities typeHierarchy

	/**
	 * Capabilities specific to {@code textDocument/prepareCallHierarchy}.
	 */
	@ProtocolSince("3.16.0")
	CallHierarchyCapabilities callHierarchy

	/**
	 * Capabilities specific to `textDocument/selectionRange` requests
	 */
	@ProtocolSince("3.15.0")
	SelectionRangeCapabilities selectionRange

	/**
	 * Capabilities specific to {@code textDocument/semanticTokens}.
	 */
	@ProtocolSince("3.16.0")
	SemanticTokensCapabilities semanticTokens

	/**
	 * Capabilities specific to the {@code textDocument/moniker} request.
	 */
	@ProtocolSince("3.16.0")
	MonikerCapabilities moniker

	/**
	 * Capabilities specific to the {@code textDocument/linkedEditingRange} request.
	 */
	@ProtocolSince("3.16.0")
	LinkedEditingRangeCapabilities linkedEditingRange

	/**
	 * Capabilities specific to the {@code textDocument/inlayHint} request.
	 */
	@ProtocolSince("3.17.0")
	InlayHintCapabilities inlayHint

	/**
	 * Client capabilities specific to inline values.
	 */
	@ProtocolSince("3.17.0")
	InlineValueCapabilities inlineValue

	/**
	 * Capabilities specific to the diagnostic pull model.
	 */
	@ProtocolSince("3.17.0")
	DiagnosticCapabilities diagnostic

	/**
	 * Capabilities specific to the {@code textDocument/inlineCompletion} request.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	InlineCompletionCapabilities inlineCompletion

	new() {
	}
}

/**
 * Capabilities specific to the notebook document support.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentClientCapabilities {
	/**
	 * Capabilities specific to notebook document synchronization
	 */
	@NonNull
	NotebookDocumentSyncClientCapabilities synchronization

	new() {
	}

	new(@NonNull NotebookDocumentSyncClientCapabilities synchronization) {
		this.synchronization = Preconditions.checkNotNull(synchronization, 'synchronization')
	}
}

/**
 * Window specific client capabilities.
 */
@JsonRpcData
class WindowClientCapabilities {
	/**
	 * Whether client supports handling progress notifications. If set servers are allowed to
	 * report in `workDoneProgress` property in the request specific server capabilities.
	 */
	@ProtocolSince("3.15.0")
	Boolean workDoneProgress

	/**
	 * Capabilities specific to the showMessage request
	 */
	@ProtocolSince("3.16.0")
	WindowShowMessageRequestCapabilities showMessage

	/**
	 * Client capabilities for the show document request.
	 */
	@ProtocolSince("3.16.0")
	ShowDocumentCapabilities showDocument

	new() {
	}
}

/**
 * General client capabilities.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class GeneralClientCapabilities {
	/**
	 * Client capabilities specific to regular expressions.
	 */
	RegularExpressionsCapabilities regularExpressions

	/**
	 * Client capabilities specific to the client's markdown parser.
	 */
	MarkdownCapabilities markdown

	/**
	 * Client capability that signals how the client handles stale requests
	 * (e.g. a request for which the client will not process the response
	 * anymore since the information is outdated).
	 */
	@ProtocolSince("3.17.0")
	StaleRequestCapabilities staleRequestSupport

	/**
	 * The position encodings supported by the client. Client and server
	 * have to agree on the same position encoding to ensure that offsets
	 * (e.g. character position in a line) are interpreted the same on both
	 * side.
	 * <p>
	 * To keep the protocol backwards compatible the following applies: if
	 * the value 'utf-16' is missing from the array of position encodings
	 * servers can assume that the client supports UTF-16. UTF-16 is
	 * therefore a mandatory encoding.
	 * <p>
	 * If omitted it defaults to [{@link PositionEncodingKind#UTF16}].
	 * <p>
	 * Implementation considerations: since the conversion from one encoding
	 * into another requires the content of the file / line the conversion
	 * is best done where the file is read which is usually on the server
	 * side.
	 * <p>
	 * See {@link PositionEncodingKind} for some predefined position encoding kinds.
	 */
	@ProtocolSince("3.17.0")
	List<String> positionEncodings

	new() {
	}
}

/**
 * `ClientCapabilities` now define capabilities for dynamic registration, workspace and text document features the client supports.
 * The {@link #experimental} can be used to pass experimental capabilities under development.
 * For future compatibility a `ClientCapabilities` object literal can have more properties set than currently defined.
 * Servers receiving a `ClientCapabilities` object literal with unknown properties should ignore these properties.
 * A missing property should be interpreted as an absence of the capability.
 * If a property is missing that defines sub properties all sub properties should be interpreted as an absence of the capability.
 * <p>
 * Client capabilities got introduced with the version 3.0 of the protocol. They therefore only describe capabilities that got introduced in 3.x or later.
 * Capabilities that existed in the 2.x version of the protocol are still mandatory for clients. Clients cannot opt out of providing them.
 * So even if a client omits the {@link TextDocumentClientCapabilities#synchronization}
 * it is still required that the client provides text document synchronization (e.g. open, changed and close notifications).
 */
@JsonRpcData
class ClientCapabilities {
	/**
	 * Workspace specific client capabilities.
	 */
	WorkspaceClientCapabilities workspace

	/**
	 * Text document specific client capabilities.
	 */
	TextDocumentClientCapabilities textDocument

	/**
	 * Capabilities specific to the notebook document support.
	 */
	@ProtocolSince("3.17.0")
	NotebookDocumentClientCapabilities notebookDocument

	/**
	 * Window specific client capabilities.
	 */
	WindowClientCapabilities window

	/**
	 * General client capabilities.
	 */
	@ProtocolSince("3.16.0")
	GeneralClientCapabilities general

	/**
	 * Experimental client capabilities.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object experimental

	new() {
	}

	new(WorkspaceClientCapabilities workspace, TextDocumentClientCapabilities textDocument, Object experimental) {
		this.workspace = workspace
		this.textDocument = textDocument
		this.experimental = experimental
	}

	new(WorkspaceClientCapabilities workspace, TextDocumentClientCapabilities textDocument, WindowClientCapabilities window, Object experimental) {
		this.workspace = workspace
		this.textDocument = textDocument
		this.window = window
		this.experimental = experimental
	}
}

/**
 * The kind of a code action.
 * <p>
 * Kinds are a hierarchical list of identifiers separated by {@code .},
 * e.g. {@code "refactor.extract.function"}.
 * <p>
 * The set of kinds is open and client needs to announce the kinds it supports
 * to the server during initialization.
 */
final class CodeActionKind {
	/**
	 * Empty kind.
	 */
	public static val Empty = ''

	/**
	 * Base kind for quickfix actions: "quickfix"
	 */
	public static val QuickFix = 'quickfix'

	/**
	 * Base kind for refactoring actions: "refactor"
	 */
	public static val Refactor = 'refactor'

	/**
	 * Base kind for refactoring extraction actions: "refactor.extract"
	 * <p>
	 * Example extract actions:
	 * <p><ul>
	 * <li>Extract method
	 * <li>Extract function
	 * <li>Extract variable
	 * <li>Extract interface from class
	 * <li>...
	 * </ul>
	 */
	public static val RefactorExtract = 'refactor.extract'

	/**
	 * Base kind for refactoring inline actions: "refactor.inline"
	 * <p>
	 * Example inline actions:
	 * <p><ul>
	 * <li>Inline function
	 * <li>Inline variable
	 * <li>Inline constant
	 * <li>...
	 * </ul>
	 */
	public static val RefactorInline = 'refactor.inline'

	/**
	 * Base kind for refactoring move actions: "refactor.move"
	 * <p>
	 * Example move actions:
	 * <ul>
	 * <li>Move a function to a new file
	 * <li>Move a property between classes
	 * <li>Move method to base class
	 * <li>...
	 * </ul>
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	public static val RefactorMove = 'refactor.move'

	/**
	 * Base kind for refactoring rewrite actions: "refactor.rewrite"
	 * <p>
	 * Example rewrite actions:
	 * <p><ul>
	 * <li>Convert function to class
	 * <li>Add or remove parameter
	 * <li>Encapsulate field
	 * <li>Make method static
	 * <li>Move method to base class
	 * <li>...
	 * </ul>
	 */
	public static val RefactorRewrite = 'refactor.rewrite'

	/**
	 * Base kind for source actions: "source"
	 * <p>
	 * Source code actions apply to the entire file.
	 */
	public static val Source = 'source'

	/**
	 * Base kind for an organize imports source action: "source.organizeImports"
	 */
	public static val SourceOrganizeImports = 'source.organizeImports'

	/**
	 * Base kind for a 'fix all' source action: "source.fixAll".
	 * <p>
	 * 'Fix all' actions automatically fix errors that have a clear fix that
	 * do not require user input. They should not suppress errors or perform
	 * unsafe fixes such as generating new types or classes.
	 */
	public static val SourceFixAll = 'source.fixAll'

	/**
	 * Base kind for all code actions applying to the entire notebook's scope.
	 * Code action kinds using this should always begin with "notebook."
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	public static val Notebook = 'notebook'

	private new() {}
}

/**
 * A code action represents a change that can be performed in code, e.g. to fix a problem or
 * to refactor code.
 * <p>
 * A CodeAction must set either {@link #edit} and/or a {@link #command}.
 * If both are supplied, the {@link #edit} is applied first, then the {@link #command} is executed.
 */
@JsonRpcData
class CodeAction {
	/**
	 * A short, human-readable, title for this code action.
	 */
	@NonNull
	String title

	/**
	 * The kind of the code action.
	 * <p>
	 * Used to filter code actions.
	 * <p>
	 * See {@link CodeActionKind} for some predefined code action kinds.
	 */
	String kind

	/**
	 * The diagnostics that this code action resolves.
	 */
	List<Diagnostic> diagnostics

	/**
	 * Marks this as a preferred action. Preferred actions are used by the `auto fix` command and can be targeted
	 * by keybindings.
	 * <p>
	 * A quick fix should be marked preferred if it properly addresses the underlying error.
	 * A refactoring should be marked preferred if it is the most reasonable choice of actions to take.
	 */
	@ProtocolSince("3.15.0")
	Boolean isPreferred

	/**
	 * Marks that the code action cannot currently be applied.
	 * <p>
	 * Clients should follow the following guidelines regarding disabled code actions:
	 * <ul>
	 * <li>Disabled code actions are not shown in automatic <a href="https://code.visualstudio.com/docs/editor/editingevolved#_code-action">lightbulb</a>
	 * code action menu.
	 * <li>Disabled actions are shown as faded out in the code action menu when the user request a more specific type
	 * of code action, such as refactorings.
	 * <li>If the user has a <a href="https://code.visualstudio.com/docs/editor/refactoring#_keybindings-for-code-actions">keybinding</a>
	 * that auto applies a code action and only a disabled code actions are returned, the client should show the user an
	 * error message with {@link CodeActionDisabled#reason} in the editor.
	 * </ul>
	 */
	@ProtocolSince("3.16.0")
	CodeActionDisabled disabled

	/**
	 * The workspace edit this code action performs.
	 */
	WorkspaceEdit edit

	/**
	 * A command this code action executes. If a code action
	 * provides a edit and a command, first the edit is
	 * executed and then the command.
	 */
	Command command

	/**
	 * A data entry field that is preserved on a code action between
	 * a `textDocument/codeAction` and a `codeAction/resolve` request.
	 */
	@ProtocolSince("3.16.0")
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	/**
	 * Tags for this code action.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	List<CodeActionTag> tags

	new() {
	}

	new(@NonNull String title) {
		this.title = Preconditions.checkNotNull(title, 'title')
	}
}

/**
 * Marks that the code action cannot currently be applied.
 * <p>
 * Clients should follow the following guidelines regarding disabled code actions:
 * <ul>
 * <li>Disabled code actions are not shown in automatic <a href="https://code.visualstudio.com/docs/editor/editingevolved#_code-action">lightbulb</a>
 * code action menu.
 * <li>Disabled actions are shown as faded out in the code action menu when the user request a more specific type
 * of code action, such as refactorings.
 * <li>If the user has a <a href="https://code.visualstudio.com/docs/editor/refactoring#_keybindings-for-code-actions">keybinding</a>
 * that auto applies a code action and only a disabled code actions are returned, the client should show the user an
 * error message with {@link #reason} in the editor.
 * </ul>
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CodeActionDisabled {
	/**
	 * Human readable description of why the code action is currently disabled.
	 * <p>
	 * This is displayed in the code actions UI.
	 */
	@NonNull
	String reason

	new() {
	}

	new(@NonNull String reason) {
		this.reason = Preconditions.checkNotNull(reason, 'reason')
	}
}

/**
 * Contains additional diagnostic information about the context in which a code action is run.
 */
@JsonRpcData
class CodeActionContext {
	/**
	 * An array of diagnostics known on the client side overlapping the range
	 * provided to the {@code textDocument/codeAction} request. They are provided
	 * so that the server knows which errors are currently presented to the user
	 * for the given range. There is no guarantee that these accurately reflect the
	 * error state of the resource. The primary parameter to compute code actions
	 * is the provided range.
	 * <p>
	 * Note that the client should check the {@link DiagnosticServerCapabilities#markupMessageSupport}
	 * server capability before sending diagnostics with markup messages to a server.
	 * Diagnostics with markup messages should be excluded for servers that do not
	 * support them.
	 */
	@NonNull
	List<Diagnostic> diagnostics

	/**
	 * Requested kind of actions to return.
	 * <p>
	 * Actions not of this kind are filtered out by the client before being shown. So servers
	 * can omit computing them.
	 * <p>
	 * See {@link CodeActionKind} for some predefined code action kinds.
	 */
	List<String> only

	/**
	 * The reason why code actions were requested.
	 */
	@ProtocolSince("3.17.0")
	CodeActionTriggerKind triggerKind

	new() {
	}

	new(@NonNull List<Diagnostic> diagnostics) {
		this.diagnostics = Preconditions.checkNotNull(diagnostics, 'diagnostics')
	}

	new(@NonNull List<Diagnostic> diagnostics, List<String> only) {
		this(diagnostics)
		this.only = only
	}
}

/**
 * The code action request is sent from the client to the server to compute commands for a given text document and range.
 * These commands are typically code fixes to either fix problems or to beautify/refactor code.
 */
@JsonRpcData
class CodeActionParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The document in which the command was invoked.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The range for which the command was invoked.
	 */
	@NonNull
	Range range

	/**
	 * Context carrying additional information.
	 */
	@NonNull
	CodeActionContext context

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Range range, @NonNull CodeActionContext context) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.range = Preconditions.checkNotNull(range, 'range')
		this.context = Preconditions.checkNotNull(context, 'context')
	}
}

/**
 * A code lens represents a command that should be shown along with source text, like the number of references,
 * a way to run tests, etc.
 * <p>
 * A code lens is <em>unresolved</em> when no command is associated to it. For performance reasons the creation of a
 * code lens and resolving should be done to two stages.
 */
@JsonRpcData
class CodeLens {
	/**
	 * The range in which this code lens is valid. Should only span a single line.
	 */
	@NonNull
	Range range

	/**
	 * The command this code lens represents.
	 */
	Command command

	/**
	 * A data entry field that is preserved on a code lens item between a code lens and a code lens resolve request.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new() {
	}

	new(@NonNull Range range) {
		this.range = Preconditions.checkNotNull(range, 'range')
	}

	new(@NonNull Range range, Command command, Object data) {
		this(range)
		this.command = command
		this.data = data
	}
}

/**
 * Documentation for a class of code actions.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class CodeActionKindDocumentation {
	/**
	 * The kind of the code action being documented.
	 * <p>
	 * If the kind is generic, such as {@link CodeActionKind#Refactor}, the
	 * documentation will be shown whenever any refactorings are returned. If
	 * the kind is more specific, such as {@link CodeActionKind#RefactorExtract},
	 * the documentation will only be shown when extract refactoring code actions
	 * are returned.
	 */
	@NonNull
	String kind

	/**
	 * Command that is used to display the documentation to the user.
	 * <p>
	 * The title of this documentation code action is taken
	 * from {@link Command#title}.
	 */
	@NonNull
	Command command
}

/**
 * Code Action options.
 */
@JsonRpcData
class CodeActionOptions extends AbstractWorkDoneProgressOptions {
	/**
	 * CodeActionKinds that this server may return.
	 * <p>
	 * The list of kinds may be generic, such as {@link CodeActionKind#Refactor}, or the server
	 * may list out every specific kind they provide.
	 */
	@ProtocolSince("3.11.0")
	List<String> codeActionKinds

	/**
	 * Static documentation for a class of code actions.
	 * <p>
	 * Documentation from the provider should be shown in the code actions
	 * menu if either:
	 * <ul>
	 * <li>Code actions of {@code kind} are requested by the editor. In this case,
	 * the editor will show the documentation that most closely matches the
	 * requested code action kind. For example, if a provider has
	 * documentation for both {@link CodeActionKind#Refactor} and
	 * {@link CodeActionKind#RefactorExtract}, when the user requests
	 * code actions for {@code RefactorExtract}, the editor will use
	 * the documentation for {@code RefactorExtract} instead of the documentation
	 * for {@code Refactor}.
	 * <li>Any code actions of {@code kind} are returned by the provider.
	 * </ul>
	 * At most one documentation entry should be shown per provider.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	List<CodeActionKindDocumentation> documentation

	/**
	 * The server provides support to resolve additional
	 * information for a code action.
	 */
	@ProtocolSince("3.16.0")
	Boolean resolveProvider

	new() {
	}

	new(List<String> codeActionKinds) {
		this.codeActionKinds = codeActionKinds
	}
}

/**
 * Code Action registration options.
 */
@JsonRpcData
class CodeActionRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * CodeActionKinds that this server may return.
	 * <p>
	 * The list of kinds may be generic, such as {@link CodeActionKind#Refactor}, or the server
	 * may list out every specific kind they provide.
	 */
	@ProtocolSince("3.11.0")
	List<String> codeActionKinds

	/**
	 * The server provides support to resolve additional
	 * information for a code action.
	 */
	@ProtocolSince("3.16.0")
	Boolean resolveProvider

	new() {
	}

	new(List<String> codeActionKinds) {
		this.codeActionKinds = codeActionKinds
	}
}

/**
 * Code Lens options.
 */
@JsonRpcData
class CodeLensOptions extends AbstractWorkDoneProgressOptions {
	/**
	 * Code lens has a resolve provider as well.
	 */
	Boolean resolveProvider

	new() {
	}

	new(Boolean resolveProvider) {
		this.resolveProvider = resolveProvider
	}
}

/**
 * The code lens request is sent from the client to the server to compute code lenses for a given text document.
 */
@JsonRpcData
class CodeLensParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The document to request code lens for.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

/**
 * Represents a reference to a command. Provides a title which will be used to represent a command in the UI and,
 * optionally, an array of arguments which will be passed to the command handler function when invoked.
 */
@JsonRpcData
class Command {
	/**
	 * Title of the command, like `save`.
	 */
	@NonNull
	String title

	/**
	 * An optional tooltip.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	String tooltip

	/**
	 * The identifier of the actual command handler.
	 */
	@NonNull
	String command

	/**
	 * Arguments that the command handler should be invoked with.
	 */
	List<Object> arguments

	new() {
	}

	new(@NonNull String title, @NonNull String command) {
		this.title = Preconditions.checkNotNull(title, 'title')
		this.command = Preconditions.checkNotNull(command, 'command')
	}

	new(@NonNull String title, @NonNull String command, List<Object> arguments) {
		this(title, command)
		this.arguments = arguments
	}
}

/**
 * Additional details for a completion item label.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class CompletionItemLabelDetails {
	/**
	 * An optional string which is rendered less prominently directly after
	 * {@link CompletionItem#label}, without any spacing. Should be
	 * used for function signatures or type annotations.
	 */
	String detail

	/**
	 * An optional string which is rendered less prominently after
	 * {@link #detail}. Should be used for fully qualified
	 * names or file path.
	 */
	String description

	new() {
	}
}

/**
 * The Completion request is sent from the client to the server to compute completion items at a given cursor position.
 * Completion items are presented in the IntelliSense user interface. If computing complete completion items is expensive,
 * servers can additionally provide a handler for the resolve completion item request. This request is sent when a
 * completion item is selected in the user interface.
 */
@JsonRpcData
class CompletionItem {
	/**
	 * The label of this completion item. By default also the text that is inserted when selecting this completion.
	 * <p>
	 * If label details are provided, the label itself should be an unqualified name of the completion item.
	 */
	@NonNull
	String label

	/**
	 * Additional details for the label
	 */
	@ProtocolSince("3.17.0")
	CompletionItemLabelDetails labelDetails

	/**
	 * The kind of this completion item. Based of the kind an icon is chosen by the editor.
	 */
	CompletionItemKind kind

	/**
	 * Tags for this completion item.
	 */
	@ProtocolSince("3.15.0")
	List<CompletionItemTag> tags

	/**
	 * A human-readable string with additional information about this item, like type or symbol information.
	 */
	String detail

	/**
	 * A human-readable string that represents a doc-comment.
	 */
	Either<String, MarkupContent> documentation

	/**
	 * Indicates if this item is deprecated.
	 * <p>
	 * Deprecated in LSP: Use {@link #tags} instead if supported.
	 */
	@ProtocolSince("3.8.0")
	@ProtocolDeprecated
	Boolean deprecated

	/**
	 * Select this item when showing.
	 * <p>
	 * <em>Note</em> that only one completion item can be selected and that the
	 * tool / client decides which item that is. The rule is that the <em>first</em>
	 * item of those that match best is selected.
	 */
	@ProtocolSince("3.9.0")
	Boolean preselect

	/**
	 * A string that should be used when comparing this item with other items. When omitted or empty,
	 * the {@link #label} is used as the sort text for this item.
	 */
	String sortText

	/**
	 * A string that should be used when filtering a set of completion items. When omitted or empty,
	 * the {@link #label} is used as the filter text for this item.
	 */
	String filterText

	/**
	 * A string that should be inserted a document when selecting this completion. When omitted or empty,
	 * the {@link #label} is used as the insert text for this item.
	 */
	String insertText

	/**
	 * The format of the insert text. The format applies to both the {@link #insertText} property
	 * and the {@code newText} property of a provided {@link #textEdit}. If omitted, defaults to
	 * {@link InsertTextFormat#PlainText}.
	 * <p>
	 * Please note that this doesn't apply to {@link #additionalTextEdits}.
	 */
	InsertTextFormat insertTextFormat

	/**
	 * How whitespace and indentation is handled during completion item
	 * insertion. If not provided, the client's default value depends on
	 * the {@link CompletionCapabilities#insertTextMode} client capability.
	 */
	@ProtocolSince("3.16.0")
	InsertTextMode insertTextMode

	/**
	 * An edit which is applied to a document when selecting this completion.
	 * When an edit is provided the value of {@link #insertText} is ignored.
	 * <p>
	 * <em>Note:</em> The range of the edit must be a single line range and it must
	 * contain the position at which completion has been requested.
	 * <p>
	 * Most editors support two different operations when accepting a completion
	 * item. One is to insert a completion text and the other is to replace an
	 * existing text with a completion text. Since this can usually not be
	 * predetermined by a server it can report both ranges. Clients need to
	 * signal support for {@link InsertReplaceEdit}s via the
	 * {@link CompletionItemCapabilities#insertReplaceSupport} client capability
	 * property.
	 * <p>
	 * <em>Note 1:</em> The text edit's range as well as both ranges from an insert
	 * replace edit must be a [single line] and they must contain the position
	 * at which completion has been requested.
	 * <p>
	 * <em>Note 2:</em> If an {@link InsertReplaceEdit} is returned the edit's insert range
	 * must be a prefix of the edit's replace range, that means it must be
	 * contained and starting at the same position.
	 * <p>
	 * Since 3.16.0 additional type {@link InsertReplaceEdit}
	 */
	@JsonAdapter(CompletionItemTextEditTypeAdapter)
	Either<TextEdit, InsertReplaceEdit> textEdit

	/**
	 * The edit text used if the completion item is part of a CompletionList and
	 * CompletionList defines an item default for the text edit range.
	 * <p>
	 * Clients will only honor this property if they opt into completion list
	 * item defaults using the capability {@link CompletionListCapabilities#itemDefaults}.
	 * <p>
	 * If not provided and a list's default range is provided the label
	 * property is used as a text.
	 */
	@ProtocolSince("3.17.0")
	String textEditText

	/**
	 * An optional array of additional text edits that are applied when
	 * selecting this completion. Edits must not overlap (including the same insert position)
	 * with the main edit nor with themselves.
	 * <p>
	 * Additional text edits should be used to change text unrelated to the current cursor position
	 * (for example adding an import statement at the top of the file if the completion item will
	 * insert an unqualified type).
	 */
	List<TextEdit> additionalTextEdits

	/**
	 * An optional set of characters that when pressed while this completion is active will accept it first and
	 * then type that character. <em>Note</em> that all commit characters should have {@code length=1} and that superfluous
	 * characters will be ignored.
	 */
	@ProtocolSince("3.2.0")
	List<String> commitCharacters

	/**
	 * An optional command that is executed <em>after</em> inserting this completion. <em>Note</em> that
	 * additional modifications to the current document should be described with the
	 * {@link #additionalTextEdits} property.
	 */
	Command command

	/**
	 * A data entry field that is preserved on a completion item between a completion and a completion resolve request.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new() {
	}

	new(@NonNull String label) {
		this.label = Preconditions.checkNotNull(label, 'label')
	}
}

@ProtocolSince("3.17.0")
@JsonRpcData
class InsertReplaceRange {
	/**
	 * The range if the insert is requested
	 */
	@NonNull
	Range insert

	/**
	 * The range if the replace is requested.
	 */
	@NonNull
	Range replace

	new() {
	}

	new(@NonNull Range insert, @NonNull Range replace) {
		this.insert = Preconditions.checkNotNull(insert, 'insert')
		this.replace = Preconditions.checkNotNull(replace, 'replace')
	}
}

/**
 * In many cases, the items of an actual completion result share the same
 * value for properties like {@link CompletionItem#commitCharacters} or the range of a text
 * edit. A completion list can therefore define item defaults which will
 * be used if a completion item itself doesn't specify the value.
 * <p>
 * If a completion list specifies a default value and a completion item
 * also specifies a corresponding value, the rules for combining these are
 * defined by {@link CompletionList#applyKind} (if the client supports it),
 * defaulting to {@link ApplyKind#Replace}.
 * <p>
 * Servers are only allowed to return default values if the client
 * signals support for this via the {@link CompletionListCapabilities#itemDefaults}
 * capability.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class CompletionItemDefaults {
	/**
	 * A default commit character set.
	 */
	List<String> commitCharacters

	/**
	 * A default edit range
	 */
	@JsonAdapter(CompletionItemDefaultsEditRangeTypeAdapter)
	Either<Range, InsertReplaceRange> editRange

	/**
	 * A default insert text format
	 */
	InsertTextFormat insertTextFormat

	/**
	 * A default insert text mode
	 */
	InsertTextMode insertTextMode

	/**
	 * A default data value.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new() {
	}
}

/**
 * Specifies how fields from a completion item should be combined with those
 * from {@link CompletionList#itemDefaults}.
 * <p>
 * If unspecified, all fields will be treated as {@link ApplyKind#Replace}.
 * <p>
 * If a field's value is {@link ApplyKind#Replace}, the value from a completion item
 * (if provided and not {@code null}) will always be used instead of the value
 * from {@link CompletionList#itemDefaults}.
 * <p>
 * If a field's value is {@link ApplyKind#Merge}, the values will be merged using
 * the rules defined against each field in {@link CompletionApplyKind}.
 * <p>
 * Servers are only allowed to return {@link CompletionList#applyKind} if the client
 * signals support for this via the {@link CompletionListCapabilities#applyKindSupport}
 * capability.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class CompletionApplyKind {
	/**
	 * Specifies whether {@link CompletionItem#commitCharacters commitCharacters}
	 * on a completion will replace or be merged with those in
	 * {@link CompletionItemDefaults#commitCharacters}.
	 * <p>
	 * If {@link ApplyKind#Replace}, the commit characters from the completion item
	 * will always be used unless not provided, in which case those from
	 * {@link CompletionItemDefaults#commitCharacters} will be used. An
	 * empty list can be used if a completion item does not have any commit
	 * characters and also should not use those from
	 * {@link CompletionItemDefaults#commitCharacters}.
	 * <p>
	 * If {@link ApplyKind#Merge}, the commit characters for the completion will be
	 * the union of all values in both {@link CompletionItemDefaults#commitCharacters}
	 * and the completion's own {@link CompletionItem#commitCharacters commitCharacters}.
	 */
	ApplyKind commitCharacters

	/**
	 * Specifies whether the {@link CompletionItem#data data} field on a completion
	 * will replace or be merged with data from {@link CompletionItemDefaults#data}.
	 * <p>
	 * If {@link ApplyKind#Replace}, the data from the completion item will be used
	 * if provided (and not {@code null}), otherwise {@link CompletionItemDefaults#data}
	 * will be used. An empty object can be used if a completion item does not have
	 * any data but also should not use the value from {@link CompletionItemDefaults#data}.
	 * <p>
	 * If {@link ApplyKind#Merge}, a shallow merge will be performed between
	 * {@link CompletionItemDefaults#data} and the completion's own data
	 * using the following rules:
	 * <ul>
	 * <li>If a completion's {@link CompletionItem#data data} field is not provided
	 * (or {@code null}), the entire {@link CompletionItemDefaults#data} field will be
	 * used as-is.
	 * <li>If a completion's {@link CompletionItem#data data} field is provided,
	 * each field will overwrite the field of the same name in
	 * {@link CompletionItemDefaults#data}, but no merging of nested fields
	 * within that value will occur.
	 * </ul>
	 */
	ApplyKind data
}

/**
 * Represents a collection of completion items to be presented in the editor.
 */
@JsonRpcData
class CompletionList {
	/**
	 * This list is not complete. Further typing should result in recomputing this list.
	 */
	boolean isIncomplete

	/**
	 * The completion items.
	 */
	@NonNull
	List<CompletionItem> items

	/**
	 * In many cases, the items of an actual completion result share the same
	 * value for properties like {@link CompletionItem#commitCharacters} or the range of a text
	 * edit. A completion list can therefore define item defaults which will
	 * be used if a completion item itself doesn't specify the value.
	 * <p>
	 * If a completion list specifies a default value and a completion item
	 * also specifies a corresponding value, the rules for combining these are
	 * defined by {@link CompletionList#applyKind} (if the client supports it),
	 * defaulting to {@link ApplyKind#Replace}.
	 * <p>
	 * Servers are only allowed to return default values if the client
	 * signals support for this via the {@link CompletionListCapabilities#itemDefaults}
	 * capability.
	 */
	@ProtocolSince("3.17.0")
	CompletionItemDefaults itemDefaults

	/**
	 * Specifies how fields from a completion item should be combined with those
	 * from {@link CompletionList#itemDefaults}.
	 * <p>
	 * If unspecified, all fields will be treated as {@link ApplyKind#Replace}.
	 * <p>
	 * If a field's value is {@link ApplyKind#Replace}, the value from a completion item
	 * (if provided and not {@code null}) will always be used instead of the value
	 * from {@link CompletionList#itemDefaults}.
	 * <p>
	 * If a field's value is {@link ApplyKind#Merge}, the values will be merged using
	 * the rules defined against each field in {@link CompletionApplyKind}.
	 * <p>
	 * Servers are only allowed to return {@link CompletionList#applyKind} if the client
	 * signals support for this via the {@link CompletionListCapabilities#applyKindSupport}
	 * capability.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	CompletionApplyKind applyKind

	new() {
		this(new ArrayList)
	}

	new(@NonNull List<CompletionItem> items) {
		this.items = Preconditions.checkNotNull(items, 'items')
	}

	new(boolean isIncomplete, @NonNull List<CompletionItem> items) {
		this(items)
		this.isIncomplete = isIncomplete
	}

	new(boolean isIncomplete, @NonNull List<CompletionItem> items, CompletionItemDefaults itemDefaults) {
		this(isIncomplete, items)
		this.itemDefaults = itemDefaults
	}
}

/**
 * Completion options.
 */
@JsonRpcData
class CompletionOptions extends AbstractWorkDoneProgressOptions {

	/**
	 * The server provides support to resolve additional information for a completion item.
	 */
	Boolean resolveProvider

	/**
	 * The characters that trigger completion automatically.
	 */
	List<String> triggerCharacters

	/**
	 * The list of all possible characters that commit a completion. This field
	 * can be used if clients don't support individual commit characters per
	 * completion item. See client capability
	 * {@link CompletionItemCapabilities#commitCharactersSupport}.
	 * <p>
	 * If a server provides both {@code allCommitCharacters} and commit characters on
	 * an individual completion item the ones on the completion item win.
	 */
	@ProtocolSince("3.2.0")
	List<String> allCommitCharacters

	/**
	 * The server supports the following {@link CompletionItem} specific capabilities.
	 */
	@ProtocolSince("3.17.0")
	CompletionItemOptions completionItem

	new() {
	}

	new(Boolean resolveProvider, List<String> triggerCharacters) {
		this.resolveProvider = resolveProvider
		this.triggerCharacters = triggerCharacters
	}
}

/**
 * The server supports the following {@link CompletionItem} specific capabilities.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class CompletionItemOptions {
	/**
	 * The server has support for completion item label details (see also {@link CompletionItemLabelDetails}) when receiving
	 * a completion item in a resolve call.
	 */
	Boolean labelDetailsSupport

	new() {
	}

	new(Boolean labelDetailsSupport) {
		this.labelDetailsSupport = labelDetailsSupport
	}
}

/**
 * Represents a diagnostic, such as a compiler error or warning. Diagnostic objects are only valid in the scope of a resource.
 */
@JsonRpcData
class Diagnostic {
	/**
	 * The range at which the message applies
	 */
	@NonNull
	Range range

	/**
	 * The diagnostic's severity. Can be omitted. If omitted it is up to the client to interpret diagnostics as error,
	 * warning, info or hint.
	 */
	DiagnosticSeverity severity

	/**
	 * The diagnostic's code. Can be omitted.
	 */
	Either<String, Integer> code

	/**
	 * An optional property to describe the error code.
	 */
	@ProtocolSince("3.16.0")
	DiagnosticCodeDescription codeDescription

	/**
	 * A human-readable string describing the source of this diagnostic, e.g. 'typescript' or 'super lint'.
	 */
	String source

	/**
	 * The diagnostic's message.
	 * <p>
	 * Since 3.18.0 - support for {@link MarkupContent}. This is guarded by the client
	 * capability {@link DiagnosticCapabilities#markupMessageSupport}. If a client does not
	 * signal this capability, servers should not send {@link MarkupContent} diagnostic messages
	 * back to the client.
	 */
	@NonNull
	Either<String, MarkupContent> message

	/**
	 * Additional metadata about the diagnostic.
	 */
	@ProtocolSince("3.15.0")
	List<DiagnosticTag> tags

	/**
	 * An array of related diagnostic information, e.g. when symbol-names within a scope collide
	 * all definitions can be marked via this property.
	 */
	@ProtocolSince("3.7.0")
	List<DiagnosticRelatedInformation> relatedInformation

	/**
	 * A data entry field that is preserved between a `textDocument/publishDiagnostics`
	 * notification and `textDocument/codeAction` request.
	 */
	@ProtocolSince("3.16.0")
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new() {
	}

	new(@NonNull Range range, @NonNull String message) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.message = Preconditions.checkNotNull(message, 'message')
	}

	new(@NonNull Range range, @NonNull String message, DiagnosticSeverity severity, String source) {
		this(range, message)
		this.severity = severity
		this.source = source
	}

	new(@NonNull Range range, @NonNull String message, DiagnosticSeverity severity, String source, String code) {
		this(range, message, severity, source)
		this.code = code
	}
}

/**
 * Represents a related message and source code location for a diagnostic. This should be
 * used to point to code locations that cause or related to a diagnostics, e.g when duplicating
 * a symbol in a scope.
 */
@ProtocolSince("3.7.0")
@JsonRpcData
class DiagnosticRelatedInformation {
	/**
	 * The location of this related diagnostic information.
	 */
	@NonNull
	Location location

	/**
	 * The message of this related diagnostic information.
	 */
	@NonNull
	String message

	new() {
	}

	new(@NonNull Location location, @NonNull String message) {
		this.location = Preconditions.checkNotNull(location, 'location')
		this.message = Preconditions.checkNotNull(message, 'message')
	}
}

/**
 * Structure to capture a description for an error code.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class DiagnosticCodeDescription {
	/**
	 * A URI to open with more information about the diagnostic error.
	 */
	@NonNull
	String href

	new() {
	}

	new(@NonNull String href) {
		this.href = Preconditions.checkNotNull(href, 'href')
	}
}

/**
 * A notification sent from the client to the server to signal the change of configuration settings.
 */
@JsonRpcData
class DidChangeConfigurationParams {
	/**
	 * The actual changed settings.
	 */
	@NonNull
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object settings

	new() {
	}

	new(@NonNull Object settings) {
		this.settings = Preconditions.checkNotNull(settings, 'settings')
	}
}

/**
 * The document change notification is sent from the client to the server to signal changes to a text document.
 */
@JsonRpcData
class DidChangeTextDocumentParams {
	/**
	 * The document that did change. The version number points to the version after all provided content changes have
	 * been applied.
	 */
	@NonNull
	VersionedTextDocumentIdentifier textDocument

	/**
	 * The actual content changes.
	 */
	@NonNull
	List<TextDocumentContentChangeEvent> contentChanges = new ArrayList

	new() {
	}

	new(@NonNull VersionedTextDocumentIdentifier textDocument,
		@NonNull List<TextDocumentContentChangeEvent> contentChanges) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.contentChanges = Preconditions.checkNotNull(contentChanges, 'contentChanges')
	}
}

/**
 * The watched files notification is sent from the client to the server when the client detects changes
 * to file watched by the language client.
 */
@JsonRpcData
class DidChangeWatchedFilesParams {
	/**
	 * The actual file events.
	 */
	@NonNull
	List<FileEvent> changes

	new() {
		this(new ArrayList)
	}

	new(@NonNull List<FileEvent> changes) {
		this.changes = Preconditions.checkNotNull(changes, 'changes')
	}
}

@JsonRpcData
class DidChangeWatchedFilesRegistrationOptions {
	/**
	 * The watchers to register.
	 */
	@NonNull
	List<FileSystemWatcher> watchers

	new() {
	}

	new(@NonNull List<FileSystemWatcher> watchers) {
		this.watchers = Preconditions.checkNotNull(watchers, 'watchers')
	}
}

@JsonRpcData
class FileSystemWatcher {
	/**
	 * The glob pattern to watch. Either a string pattern relative to the base path or a relative pattern.
	 */
	@NonNull
	Either<String, RelativePattern> globPattern

	/**
	 * The kind of events of interest. If omitted it defaults
	 * to {@link WatchKind#Create} | {@link WatchKind#Change} | {@link WatchKind#Delete}
	 * which is {@code 7}.
	 */
	Integer kind

	new() {
	}

	new(@NonNull Either<String, RelativePattern> globPattern) {
		this.globPattern = Preconditions.checkNotNull(globPattern, 'globPattern')
	}

	new(@NonNull Either<String, RelativePattern> globPattern, Integer kind) {
		this(globPattern)
		this.kind = kind
	}
}

final class WatchKind {
	/**
	 * Interested in create events.
	 */
	public static val Create = 1

	/**
	 * Interested in change events
	 */
	public static val Change = 2

	/**
	 * Interested in delete events
	 */
	public static val Delete = 4

	private new() {
	}
}

/**
 * A relative pattern is a helper to construct glob patterns that are matched
 * relatively to a base URI. The common value for a {@link #baseUri} is a workspace
 * folder root, but it can be another absolute URI as well.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class RelativePattern {
	/**
	 * A workspace folder or a base URI as a string to which this pattern will be matched
	 * against relatively.
	 */
	@NonNull
	Either<WorkspaceFolder, String> baseUri

	/**
	 * The actual glob pattern.
	 */
	@NonNull
	String pattern

	new() {
	}

	new(@NonNull Either<WorkspaceFolder, String> baseUri, @NonNull String pattern) {
		this.baseUri = Preconditions.checkNotNull(baseUri, 'baseUri')
		this.pattern = Preconditions.checkNotNull(pattern, 'pattern')
	}
}

/**
 * The document close notification is sent from the client to the server when the document got closed in the client.
 * The document's truth now exists where the document's uri points to (e.g. if the document's uri is a file uri the
 * truth now exists on disk).
 */
@JsonRpcData
class DidCloseTextDocumentParams {
	/**
	 * The document that was closed.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

/**
 * The document open notification is sent from the client to the server to signal newly opened text documents.
 * The document's truth is now managed by the client and the server must not try to read the document's truth using
 * the document's uri.
 */
@JsonRpcData
class DidOpenTextDocumentParams {
	/**
	 * The document that was opened.
	 */
	@NonNull
	TextDocumentItem textDocument

	new() {
	}

	new(@NonNull TextDocumentItem textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

/**
 * The document save notification is sent from the client to the server when the document was saved in the client.
 */
@JsonRpcData
class DidSaveTextDocumentParams {
	/**
	 * The document that was closed.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * Optional the content when saved. Depends on the includeText value
	 * when the save notification was requested.
	 */
	String text

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}

	new(@NonNull TextDocumentIdentifier textDocument, String text) {
		this(textDocument)
		this.text = text
	}
}

@JsonRpcData
class WillSaveTextDocumentParams {
	/**
	 * The document that will be saved.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * A reason why a text document is saved.
	 */
	@NonNull
	TextDocumentSaveReason reason

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull TextDocumentSaveReason reason) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.reason = Preconditions.checkNotNull(reason, 'reason')
	}
}

/**
 * The document formatting request is sent from the server to the client to format a whole document.
 */
@JsonRpcData
class DocumentFormattingParams implements WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	/**
	 * The document to format.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The format options
	 */
	@NonNull
	FormattingOptions options

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull FormattingOptions options) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.options = Preconditions.checkNotNull(options, 'options')
	}
}

/**
 * Document formatting options.
 */
@JsonRpcData
class DocumentFormattingOptions extends AbstractWorkDoneProgressOptions {
}

/**
 * Document formatting registration options.
 */
@JsonRpcData
class DocumentFormattingRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
}

/**
 * A document highlight is a range inside a text document which deserves special attention. Usually a document highlight
 * is visualized by changing the background color of its range.
 */
@JsonRpcData
class DocumentHighlight {
	/**
	 * The range this highlight applies to.
	 */
	@NonNull
	Range range

	/**
	 * The highlight kind, default is {@link DocumentHighlightKind#Text}.
	 */
	DocumentHighlightKind kind

	new() {
	}

	new(@NonNull Range range) {
		this.range = Preconditions.checkNotNull(range, 'range')
	}

	new(@NonNull Range range, DocumentHighlightKind kind) {
		this(range)
		this.kind = kind
	}
}

/**
 * A document link is a range in a text document that links to an internal or external resource, like another
 * text document or a web site.
 */
@JsonRpcData
class DocumentLink {
	/**
	 * The range this link applies to.
	 */
	@NonNull
	Range range

	/**
	 * The uri this link points to. If missing a resolve request is sent later.
	 */
	String target

	/**
	 * The tooltip text when you hover over this link.
	 * <p>
	 * If a tooltip is provided, is will be displayed in a string that includes instructions on how to
	 * trigger the link, such as `{0} (ctrl + click)`. The specific instructions vary depending on OS,
	 * user settings, and localization.
	 */
	@ProtocolSince("3.15.0")
	String tooltip

	/**
	 * A data entry field that is preserved on a document link between a
	 * DocumentLinkRequest and a DocumentLinkResolveRequest.
	 */
	@ProtocolSince("3.8.0")
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new() {
	}

	new(@NonNull Range range) {
		this.range = Preconditions.checkNotNull(range, 'range')
	}

	new(@NonNull Range range, String target) {
		this(range)
		this.target = target
	}

	new(@NonNull Range range, String target, Object data) {
		this(range, target)
		this.data = data
	}

	new(@NonNull Range range, String target, Object data, String tooltip) {
		this(range, target, data)
		this.tooltip = tooltip
	}
}

/**
 * The document links request is sent from the client to the server to request the location of links in a document.
 */
@JsonRpcData
class DocumentLinkParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The document to provide document links for.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

/**
 * Document link options
 */
@JsonRpcData
class DocumentLinkOptions extends AbstractWorkDoneProgressOptions {
	/**
	 * Document links have a resolve provider as well.
	 */
	Boolean resolveProvider

	new() {
	}

	new(Boolean resolveProvider) {
		this.resolveProvider = resolveProvider
	}
}

/**
 * Execute command options.
 */
@JsonRpcData
class ExecuteCommandOptions extends AbstractWorkDoneProgressOptions {
	/**
	 * The commands to be executed on the server
	 */
	@NonNull
	List<String> commands

	new() {
		this(new ArrayList)
	}

	new(@NonNull List<String> commands) {
		this.commands = Preconditions.checkNotNull(commands, 'commands')
	}
}

/**
 * Save options.
 */
@JsonRpcData
class SaveOptions {
	/**
	 * The client is supposed to include the content on save.
	 */
	Boolean includeText

	new() {
	}

	new(Boolean includeText) {
		this.includeText = includeText
	}
}

/**
 * Rename options.
 * <p>
 * Referred to as {@code RenameRegistrationOptions} in the LSP spec.
 */
@JsonRpcData
class RenameOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * Renames should be checked and tested before being executed.
	 */
	Boolean prepareProvider

	new() {
	}

	new(Boolean prepareProvider) {
		this.prepareProvider = prepareProvider
	}
}

/**
 * Document color options.
 * <p>
 * Referred to as {@code DocumentColorRegistrationOptions} in the LSP spec.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class ColorProviderOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * Folding range options.
 * <p>
 * Referred to as {@code FoldingRangeRegistrationOptions} in the LSP spec.
 */
@ProtocolSince("3.10.0")
@JsonRpcData
class FoldingRangeProviderOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

@JsonRpcData
class TextDocumentSyncOptions {
	/**
	 * Open and close notifications are sent to the server. If omitted open
	 * close notifications should not be sent.
	 */
	Boolean openClose

	/**
	 * Change notifications are sent to the server. If omitted it defaults to
	 * {@link TextDocumentSyncKind#None}.
	 */
	TextDocumentSyncKind change

	/**
	 * Will save notifications are sent to the server.
	 */
	Boolean willSave

	/**
	 * Will save wait until requests are sent to the server.
	 */
	Boolean willSaveWaitUntil

	/**
	 * Save notifications are sent to the server.
	 */
	Either<Boolean, SaveOptions> save

	new() {
	}
}

/**
 * Static registration options to be returned in the initialize request.
 */
@JsonRpcData
class StaticRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also Registration#id.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * Format document on type options
 */
@JsonRpcData
class DocumentOnTypeFormattingOptions {
	/**
	 * A character on which formatting should be triggered, like `}`.
	 */
	@NonNull
	String firstTriggerCharacter

	/**
	 * More trigger characters.
	 */
	List<String> moreTriggerCharacter

	new() {
	}

	new(@NonNull String firstTriggerCharacter) {
		this.firstTriggerCharacter = firstTriggerCharacter
	}

	new(@NonNull String firstTriggerCharacter, List<String> moreTriggerCharacter) {
		this.firstTriggerCharacter = Preconditions.checkNotNull(firstTriggerCharacter, 'firstTriggerCharacter')
		this.moreTriggerCharacter = moreTriggerCharacter
	}
}

/**
 * The document on type formatting request is sent from the client to the server to format parts of the document during typing.
 */
@JsonRpcData
class DocumentOnTypeFormattingParams extends TextDocumentPositionParams {
	/**
	 * The format options
	 */
	@NonNull
	FormattingOptions options

	/**
	 * The character that has been typed.
	 */
	@NonNull
	String ch

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull FormattingOptions options, @NonNull Position position, @NonNull String ch) {
		super(textDocument, position)
		this.options = Preconditions.checkNotNull(options, 'options')
		this.ch = Preconditions.checkNotNull(ch, 'ch')
	}
}

/**
 * The document range formatting request is sent from the client to the server to format a given range in a document.
 */
@JsonRpcData
class DocumentRangeFormattingParams implements WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	/**
	 * The document to format.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The format options
	 */
	@NonNull
	FormattingOptions options

	/**
	 * The range to format
	 */
	@NonNull
	Range range

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull FormattingOptions options, @NonNull Range range) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.options = Preconditions.checkNotNull(options, 'options')
		this.range = Preconditions.checkNotNull(range, 'range')
	}
}

/**
 * The document ranges formatting request is sent from the client to the server to format
 * multiple ranges at once in a document.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class DocumentRangesFormattingParams implements WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	/**
	 * The document to format.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The format options.
	 */
	@NonNull
	FormattingOptions options

	/**
	 * The ranges to format.
	 */
	@NonNull
	List<Range> ranges

	new() {
		this.ranges = new ArrayList
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull FormattingOptions options, @NonNull List<Range> ranges) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.options = Preconditions.checkNotNull(options, 'options')
		this.ranges = Preconditions.checkNotNull(ranges, 'ranges')
	}
}

/**
 * Document range formatting options.
 */
@JsonRpcData
class DocumentRangeFormattingOptions extends AbstractWorkDoneProgressOptions {
	/**
	 * Whether the server supports formatting multiple ranges at once.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Boolean rangesSupport
}

/**
 * Document range formatting registration options.
 */
@JsonRpcData
class DocumentRangeFormattingRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * Whether the server supports formatting multiple ranges at once.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Boolean rangesSupport
}

/**
 * The type hierarchy request is sent from the client to the server to return a type hierarchy for
 * the language element of given text document positions. Will return {@code null} if the server
 * couldn't infer a valid type from the position.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class TypeHierarchyPrepareParams extends TextDocumentPositionAndWorkDoneProgressParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

/**
 * The request is sent from the client to the server to resolve the supertypes for
 * a given type hierarchy item. Will return {@code null} if the server couldn't infer
 * a valid type from {@link #item}. The request doesn't define
 * its own client and server capabilities. It is only issued if a server registers for the
 * {@code textDocument/prepareTypeHierarchy} request.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class TypeHierarchySupertypesParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * Representation of an item that carries type information.
	 */
	@NonNull
	TypeHierarchyItem item

	new() {
	}

	new(@NonNull TypeHierarchyItem item) {
		this.item = Preconditions.checkNotNull(item, 'item')
	}
}

/**
 * The request is sent from the client to the server to resolve the subtypes for
 * a given type hierarchy item. Will return {@code null} if the server couldn't infer
 * a valid type from {@link #item}. The request doesn't define
 * its own client and server capabilities. It is only issued if a server registers for the
 * {@code textDocument/prepareTypeHierarchy} request.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class TypeHierarchySubtypesParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * Representation of an item that carries type information.
	 */
	@NonNull
	TypeHierarchyItem item

	new() {
	}

	new(@NonNull TypeHierarchyItem item) {
		this.item = Preconditions.checkNotNull(item, 'item')
	}
}

@JsonRpcData
class DocumentSymbolOptions extends AbstractWorkDoneProgressOptions {
	/**
	 * A human-readable string that is shown when multiple outlines trees
	 * are shown for the same document.
	 */
	@ProtocolSince("3.16.0")
	String label

	new() {
	}

	new(String label) {
		this.label = label
	}
}

@JsonRpcData
class DocumentSymbolRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * A human-readable string that is shown when multiple outlines trees
	 * are shown for the same document.
	 */
	@ProtocolSince("3.16.0")
	String label

	new() {
	}

	new(String label) {
		this.label = label
	}
}

/**
 * The document symbol request is sent from the client to the server to list all symbols found in a given text document.
 */
@JsonRpcData
class DocumentSymbolParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

/**
 * An event describing a file change.
 */
@JsonRpcData
class FileEvent {
	/**
	 * The file's uri.
	 */
	@NonNull
	String uri

	/**
	 * The change type.
	 */
	@NonNull
	FileChangeType type

	new() {
	}

	new(@NonNull String uri, @NonNull FileChangeType type) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.type = Preconditions.checkNotNull(type, 'type')
	}
}

/**
 * Value-object describing what options formatting should use.
 */
class FormattingOptions extends LinkedHashMap<String, Either3<String, Number, Boolean>> {
	static val TAB_SIZE = 'tabSize'
	static val INSERT_SPACES = 'insertSpaces'
	static val TRIM_TRAILING_WHITESPACE = 'trimTrailingWhitespace'
	static val INSERT_FINAL_NEWLINE = 'insertFinalNewline'
	static val TRIM_FINAL_NEWLINES = 'trimFinalNewlines'

	new() {
	}

	new(int tabSize, boolean insertSpaces) {
		this.tabSize = tabSize
		this.insertSpaces = insertSpaces
	}

	def String getString(String key) {
		get(key)?.getFirst
	}

	def void putString(String key, String value) {
		put(key, Either3.forFirst(value))
	}

	def Number getNumber(String key) {
		get(key)?.getSecond
	}

	def void putNumber(String key, Number value) {
		put(key, Either3.forSecond(value))
	}

	def Boolean getBoolean(String key) {
		get(key)?.getThird
	}

	def void putBoolean(String key, Boolean value) {
		put(key, Either3.forThird(value))
	}

	/**
	 * Size of a tab in spaces.
	 */
	def int getTabSize() {
		val value = getNumber(TAB_SIZE)
		if (value !== null)
			return value.intValue
		else
			return 0
	}

	def void setTabSize(int tabSize) {
		putNumber(TAB_SIZE, tabSize)
	}

	/**
	 * Prefer spaces over tabs.
	 */
	def boolean isInsertSpaces() {
		val value = getBoolean(INSERT_SPACES)
		if (value !== null)
			return value
		else
			return false
	}

	def void setInsertSpaces(boolean insertSpaces) {
		putBoolean(INSERT_SPACES, insertSpaces)
	}

	/**
	 * Trim trailing whitespace on a line.
	 */
	@ProtocolSince("3.15.0")
	def boolean isTrimTrailingWhitespace() {
		val value = getBoolean(TRIM_TRAILING_WHITESPACE)
		if (value !== null)
			return value
		else
			return false
	}

	def void setTrimTrailingWhitespace(boolean trimTrailingWhitespace) {
		putBoolean(TRIM_TRAILING_WHITESPACE, trimTrailingWhitespace)
	}

	/**
	 * Insert a newline character at the end of the file if one does not exist.
	 */
	@ProtocolSince("3.15.0")
	def boolean isInsertFinalNewline() {
		val value = getBoolean(INSERT_FINAL_NEWLINE)
		if (value !== null)
			return value
		else
			return false
	}

	def void setInsertFinalNewline(boolean insertFinalNewline) {
		putBoolean(INSERT_FINAL_NEWLINE, insertFinalNewline)
	}

	/**
	 * Trim all newlines after the final newline at the end of the file.
	 */
	@ProtocolSince("3.15.0")
	def boolean isTrimFinalNewlines() {
		val value = getBoolean(TRIM_FINAL_NEWLINES)
		if (value !== null)
			return value
		else
			return false
	}

	def void setTrimFinalNewlines(boolean trimFinalNewlines) {
		putBoolean(TRIM_FINAL_NEWLINES, trimFinalNewlines)
	}
}

/**
 * Describes the content type that a client supports in various
 * result literals like {@link Hover}, {@link ParameterInformation} or {@link CompletionItem}.
 * <p>
 * Please note that {@code MarkupKind}s must not start with a {@code $}. These kinds
 * are reserved for internal usage.
 */
@ProtocolSince("3.3.0")
final class MarkupKind {
	/**
	 * Plain text is supported as a content format.
	 */
	public static val PLAINTEXT = 'plaintext'

	/**
	 * Markdown is supported as a content format.
	 */
	public static val MARKDOWN = 'markdown'

	private new() {
	}
}

/**
 * A MarkupContent literal represents a string value which content is interpreted based on its
 * kind flag. Currently the protocol supports {@link MarkupKind#PLAINTEXT plaintext} and
 * {@link MarkupKind#MARKDOWN markdown} as markup kinds.
 * <p>
 * If the kind is {@link MarkupKind#MARKDOWN markdown} then the value can contain fenced code blocks like in GitHub issues.
 * See https://help.github.com/articles/creating-and-highlighting-code-blocks/#syntax-highlighting
 * <p>
 * Please Note that clients might sanitize the return markdown. A client could decide to
 * remove HTML from the markdown to avoid script execution.
 */
@ProtocolSince("3.3.0")
@JsonRpcData
class MarkupContent {
	/**
	 * The type of the Markup.
	 * <p>
	 * See {@link MarkupKind} for allowed values.
	 */
	@NonNull
	String kind

	/**
	 * The content itself.
	 */
	@NonNull
	String value

	new() {
	}

	new(@NonNull String kind, @NonNull String value) {
		this.kind = Preconditions.checkNotNull(kind, 'kind')
		this.value = Preconditions.checkNotNull(value, 'value')
	}
}

/**
 * The result of a hover request.
 */
@JsonRpcData
@JsonAdapter(HoverTypeAdapter.Factory)
class Hover {
	/**
	 * The hover's content as markdown
	 */
	@NonNull
	Either<List<Either<String, MarkedString>>, MarkupContent> contents

	/**
	 * An optional range
	 */
	Range range

	new() {
	}

	new(@NonNull List<Either<String, MarkedString>> contents) {
		this.contents = Preconditions.checkNotNull(contents, 'contents')
	}

	new(@NonNull List<Either<String, MarkedString>> contents, Range range) {
		this.contents = Preconditions.checkNotNull(contents, 'contents')
		this.range = range
	}

	new(@NonNull MarkupContent contents) {
		this.contents = Preconditions.checkNotNull(contents, 'contents')
	}

	new(@NonNull MarkupContent contents, Range range) {
		this.contents = Preconditions.checkNotNull(contents, 'contents')
		this.range = range
	}

	new(@NonNull Either<String, MarkedString> contents) {
		this.contents = Arrays.asList(Preconditions.checkNotNull(contents, 'contents'))
	}
}

/**
 * MarkedString can be used to render human readable text. It is either a markdown string
 * or a code-block that provides a language and a code snippet. The language identifier
 * is semantically equal to the optional language identifier in fenced code blocks in GitHub
 * issues. See https://help.github.com/articles/creating-and-highlighting-code-blocks/#syntax-highlighting
 * <p>
 * The pair of a language and a value is an equivalent to markdown:
 * <pre>
 * ```${language}
 * ${value}
 * ```
 * </pre>
 * <p>
 * Note that markdown strings will be sanitized - that means html will be escaped.
 * <p>
 * Deprecated in LSP:  Use {@link MarkupContent} instead.
 */
@JsonRpcData
@ProtocolDeprecated
class MarkedString {
	@NonNull
	String language

	@NonNull
	String value

	new() {
	}

	new(@NonNull String language, @NonNull String value) {
		this.language = Preconditions.checkNotNull(language, 'language')
		this.value = Preconditions.checkNotNull(value, 'value')
	}
}

/**
 * The $/progress notification payload interface.
 */
@ProtocolSince("3.15.0")
interface WorkDoneProgressNotification {
	def WorkDoneProgressKind getKind()
}

/**
 * The $/progress notification payload to start progress reporting.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class WorkDoneProgressBegin implements WorkDoneProgressNotification {

	/**
	 * Always return begin
	 */
	override WorkDoneProgressKind getKind() {
		WorkDoneProgressKind.begin
	}

	/**
	 * Mandatory title of the progress operation. Used to briefly inform about
	 * the kind of operation being performed.
	 * <p>
	 * Examples: "Indexing" or "Linking dependencies".
	 */
	@NonNull
	String title

	/**
	 * Controls if a cancel button should show to allow the user to cancel the
	 * long running operation. Clients that don't support cancellation are allowed
	 * to ignore the setting.
	 */
	Boolean cancellable

	/**
	 * Optional, more detailed associated progress message. Contains
	 * complementary information to the {@link #title}.
	 * <p>
	 * Examples: "3/25 files", "project/src/module2", "node_modules/some_dep".
	 * If unset, the previous progress message (if any) is still valid.
	 */
	String message

	/**
	 * Optional progress percentage to display (value 100 is considered 100%).
	 * If not provided infinite progress is assumed and clients are allowed
	 * to ignore the `percentage` value in subsequent in report notifications.
	 * <p>
	 * The value should be steadily rising. Clients are free to ignore values
	 * that are not following this rule.
	 */
	Integer percentage
}

/**
 * The notification payload about progress reporting.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class WorkDoneProgressReport implements WorkDoneProgressNotification {

	/**
	 * Always return report
	 */
	override WorkDoneProgressKind getKind() {
		WorkDoneProgressKind.report
	}

	/**
	 * Controls enablement state of a cancel button. This property is only valid if a cancel
	 * button got requested in the {@link WorkDoneProgressBegin} payload.
	 * <p>
	 * Clients that don't support cancellation or don't support control the button's
	 * enablement state are allowed to ignore the setting.
	 */
	Boolean cancellable

	/**
	 * Optional, more detailed associated progress message. Contains
	 * complementary information to the {@link WorkDoneProgressBegin#title}.
	 * <p>
	 * Examples: "3/25 files", "project/src/module2", "node_modules/some_dep".
	 * If unset, the previous progress message (if any) is still valid.
	 */
	String message

	/**
	 * Optional progress percentage to display (value 100 is considered 100%).
	 * If not provided infinite progress is assumed and clients are allowed
	 * to ignore the `percentage` value in subsequent in report notifications.
	 * <p>
	 * The value should be steadily rising. Clients are free to ignore values
	 * that are not following this rule.
	 */
	Integer percentage
}

/**
 * The notification payload about progress reporting.
 * Signaling the end of a progress reporting is done using the following payload:
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class WorkDoneProgressEnd implements WorkDoneProgressNotification {

	/**
	 * Always return end
	 */
	override WorkDoneProgressKind getKind() {
		WorkDoneProgressKind.end
	}

	/**
	 * Optional, a final message indicating to for example indicate the outcome
	 * of the operation.
	 */
	String message
}

/**
 * The base protocol offers also support to report progress in a generic fashion.
 * This mechanism can be used to report any kind of progress including work done progress
 * (usually used to report progress in the user interface using a progress bar)
 * and partial result progress to support streaming of results.
 * A progress notification has the following properties:
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class ProgressParams {
	/**
	 * The progress token provided by the client or server.
	 */
	@NonNull
	Either<String, Integer> token

	/**
	 * The progress data.
	 */
	@NonNull
	@JsonAdapter(ProgressNotificationAdapter)
	Either<WorkDoneProgressNotification, Object> value

	new() {
	}

	new(@NonNull Either<String, Integer> token, @NonNull Either<WorkDoneProgressNotification, Object> value) {
		this.token = Preconditions.checkNotNull(token, 'token')
		this.value = Preconditions.checkNotNull(value, 'value')
	}
}


/**
 * A parameter literal used to pass a work done progress token.
 */
@ProtocolSince("3.15.0")
interface WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	def Either<String, Integer> getWorkDoneToken()

	/**
	 * An optional token that a server can use to report work done progress.
	 */
	def void setWorkDoneToken(Either<String, Integer> token)
}

/**
 * Options to signal work done progress support in server capabilities.
 */
@ProtocolSince("3.15.0")
interface WorkDoneProgressOptions {

	def Boolean getWorkDoneProgress()

	def void setWorkDoneProgress(Boolean workDoneProgress)

}

/**
 * Options to signal work done progress support in server capabilities.
 * It is not present in protocol specification, so it's just "dry" class.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
abstract class AbstractWorkDoneProgressOptions implements WorkDoneProgressOptions {
	Boolean workDoneProgress
}

/**
 * Options to signal work done progress support in server capabilities and TextDocumentRegistrationOptions.
 * It is not present in protocol specification, so it's just "dry" class.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
abstract class AbstractTextDocumentRegistrationAndWorkDoneProgressOptions extends TextDocumentRegistrationOptions implements WorkDoneProgressOptions {
	Boolean workDoneProgress

	new() {
	}

	new(List<DocumentFilter> documentSelector) {
		super(documentSelector)
	}
}

/**
 * A parameter literal used to pass a partial result token.
 */
@ProtocolSince("3.15.0")
interface PartialResultParams {
	/**
	 * An optional token that a server can use to report partial results (e.g. streaming) to
	 * the client.
	 */
	def Either<String, Integer> getPartialResultToken()

	/**
	 * An optional token that a server can use to report partial results (e.g. streaming) to
	 * the client.
	 */
	def void setPartialResultToken(Either<String, Integer> token)
}

/**
 * Abstract class which implements work done progress and partial result request parameter.
 * It is not present in protocol specification, so it's just "dry" class.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
abstract class WorkDoneProgressAndPartialResultParams implements WorkDoneProgressParams, PartialResultParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	/**
	 * An optional token that a server can use to report partial results (e.g. streaming) to
	 * the client.
	 */
	Either<String, Integer> partialResultToken
}

/**
 * Abstract class which extends TextDocumentPosition and implements work done progress request parameter.
 * It is not present in protocol specification, so it's just "dry" class.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
abstract class TextDocumentPositionAndWorkDoneProgressParams extends TextDocumentPositionParams implements WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

/**
 * Abstract class which extends TextDocumentPosition and implements work done progress and partial result request parameter.
 * It is not present in protocol specification, so it's just "dry" class.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
abstract class TextDocumentPositionAndWorkDoneProgressAndPartialResultParams extends TextDocumentPositionAndWorkDoneProgressParams implements PartialResultParams {
	/**
	 * An optional token that a server can use to report partial results (e.g. streaming) to
	 * the client.
	 */
	Either<String, Integer> partialResultToken

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

@JsonRpcData
class InitializeError {
	/**
	 * Indicates whether the client executes the following retry logic:
	 * <ol>
	 * <li>Show the message provided by the ResponseError to the user.
	 * <li>User selects retry or cancel.
	 * <li>If user selected retry the initialize method is sent again.
	 * </ol>
	 */
	boolean retry

	new() {
	}

	new(boolean retry) {
		this.retry = retry
	}
}

/**
 * Known error codes for an {@link InitializeError}
 */
interface InitializeErrorCode {
	/**
	 * If the protocol version provided by the client can't be handled by the server.
	 * <p>
	 * Deprecated in LSP:  This initialize error got replaced by client capabilities.
	 * There is no version handshake in version 3.0x
	 */
	@ProtocolDeprecated
	val unknownProtocolVersion = 1
}

/**
 * The initialize request is sent as the first request from the client to the server.
 */
@JsonRpcData
@JsonAdapter(InitializeParamsTypeAdapter.Factory)
class InitializeParams implements WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	/**
	 * The process Id of the parent process that started the server.
	 */
	Integer processId

	/**
	 * The rootPath of the workspace. Is null if no folder is open.
	 * <p>
	 * Deprecated in LSP:  Use {@link #workspaceFolders} instead.
	 */
	@ProtocolDeprecated
	String rootPath

	/**
	 * The rootUri of the workspace. Is null if no folder is open.
	 * If both {@link #rootPath} and `rootUri` are set, `rootUri` wins.
	 * <p>
	 * Deprecated in LSP:  Use {@link #workspaceFolders} instead.
	 */
	@ProtocolDeprecated
	String rootUri

	/**
	 * User provided initialization options.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object initializationOptions

	/**
	 * The capabilities provided by the client (editor or tool)
	 */
	@NonNull
	ClientCapabilities capabilities

	/**
	 * Information about the client
	 */
	@ProtocolSince("3.15.0")
	ClientInfo clientInfo

	/**
	 * The locale the client is currently showing the user interface
	 * in. This must not necessarily be the locale of the operating
	 * system.
	 * <p>
	 * Uses IETF language tags as the value's syntax
	 * (See https://en.wikipedia.org/wiki/IETF_language_tag)
	 */
	@ProtocolSince("3.16.0")
	String locale

	/**
	 * The initial trace setting.
	 * For values, see {@link TraceValue}. If omitted trace is disabled ({@link TraceValue#Off}).
	 */
	String trace

	/**
	 * The workspace folders configured in the client when the server starts.
	 * This property is only available if the client supports workspace folders.
	 * It can be `null` if the client supports workspace folders but none are
	 * configured.
	 */
	@ProtocolSince("3.6.0")
	List<WorkspaceFolder> workspaceFolders

	new() {
	}
}

@JsonRpcData
class InitializeResult {
	/**
	 * The capabilities the language server provides.
	 */
	@NonNull
	ServerCapabilities capabilities

	/**
	 * Information about the server.
	 */
	@ProtocolSince("3.15.0")
	ServerInfo serverInfo

	new() {
	}

	new(@NonNull ServerCapabilities capabilities) {
		this.capabilities = Preconditions.checkNotNull(capabilities, 'capabilities')
	}

	new(@NonNull ServerCapabilities capabilities, ServerInfo serverInfo) {
		this(capabilities)
		this.serverInfo = serverInfo
	}
}

@JsonRpcData
class InitializedParams {
}

/**
 * Information about the client
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class ClientInfo {
	/**
	 * The name of the client as defined by the client.
	 */
	@NonNull
	String name

	/**
	 * The client's version as defined by the client.
	 */
	String version

	new() {
	}

	new(@NonNull String name) {
		this.name = Preconditions.checkNotNull(name, 'name')
	}

	new(@NonNull String name, String version) {
		this(name)
		this.version = version
	}
}

/**
 * Information about the server.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class ServerInfo {
	/**
	 * The name of the server as defined by the server.
	 */
	@NonNull
	String name

	/**
	 * The server's version as defined by the server.
	 */
	String version

	new() {
	}

	new(@NonNull String name) {
		this.name = Preconditions.checkNotNull(name, 'name')
	}

	new(@NonNull String name, String version) {
		this(name)
		this.version = version
	}
}

/**
 * Represents a location inside a resource, such as a line inside a text file.
 */
@JsonRpcData
class Location {
	@NonNull
	String uri

	@NonNull
	Range range

	new() {
	}

	new(@NonNull String uri, @NonNull Range range) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.range = Preconditions.checkNotNull(range, 'range')
	}
}

/**
 * Represents a link between a source and a target location.
 */
@ProtocolSince("3.14.0")
@JsonRpcData
class LocationLink {
	/**
	 * Span of the origin of this link.
	 * <p>
	 * Used as the underlined span for mouse interaction. Defaults to the word range at
	 * the mouse position.
	 */
	Range originSelectionRange

	/**
	 * The target resource identifier of this link.
	 */
	@NonNull
	String targetUri

	/**
	 * The full target range of this link. If the target for example is a symbol then target range is the
	 * range enclosing this symbol not including leading/trailing whitespace but everything else
	 * like comments. This information is typically used to highlight the range in the editor.
	 */
	@NonNull
	Range targetRange

	/**
	 * The range that should be selected and revealed when this link is being followed, e.g the name of a function.
	 * Must be contained by the {@link #targetRange}. See also {@link DocumentSymbol#range}
	 */
	@NonNull
	Range targetSelectionRange

	new() {
	}

	new(@NonNull String targetUri, @NonNull Range targetRange, @NonNull Range targetSelectionRange) {
		this.targetUri = Preconditions.checkNotNull(targetUri, 'targetUri')
		this.targetRange = Preconditions.checkNotNull(targetRange, 'targetRange')
		this.targetSelectionRange = Preconditions.checkNotNull(targetSelectionRange, 'targetSelectionRange')
	}

	new(@NonNull String targetUri, @NonNull Range targetRange, @NonNull Range targetSelectionRange, Range originSelectionRange) {
		this(targetUri, targetRange, targetSelectionRange)
		this.originSelectionRange = originSelectionRange
	}
}

/**
 * The show message request is sent from a server to a client to ask the client to display a particular message in the
 * user class. In addition to the show message notification the request allows to pass actions and to wait for an
 * answer from the client.
 */
@JsonRpcData
class MessageActionItem {
	/**
	 * A short title like 'Retry', 'Open Log' etc.
	 */
	@NonNull
	String title

	new() {
	}

	new(@NonNull String title) {
		this.title = Preconditions.checkNotNull(title, 'title')
	}
}

/**
 * The show message notification is sent from a server to a client to ask the client to display a particular message
 * in the user class.
 * <p>
 * The log message notification is sent from the server to the client to ask the client to log a particular message.
 */
@JsonRpcData
class MessageParams {
	/**
	 * The message type.
	 */
	@NonNull
	MessageType type

	/**
	 * The actual message.
	 */
	@NonNull
	String message

	new() {
	}

	new(@NonNull MessageType type, @NonNull String message) {
		this.type = Preconditions.checkNotNull(type, 'type')
		this.message = Preconditions.checkNotNull(message, 'message')
	}
}

/**
 * A notification to log the trace of the server's execution. The amount and content of these notifications
 * depends on the current trace configuration. If trace is 'off', the server should not send any logTrace
 * notification. If trace is 'message', the server should not add the 'verbose' field.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class LogTraceParams {
	/**
	 * The message to be logged.
	 */
	@NonNull
	String message

	/**
	 * Additional information that can be computed if the {@code trace} configuration
	 * is set to {@link TraceValue#Verbose}
	 */
	String verbose

	new() {
	}

	new(@NonNull String message) {
		this.message = Preconditions.checkNotNull(message, 'message')
	}

	new(@NonNull String message, String verbose) {
		this.message = Preconditions.checkNotNull(message, 'message')
		this.verbose = verbose
	}
}

/**
 * A TraceValue represents the level of verbosity with which the server systematically reports its execution
 * trace using {@code $/logTrace} notifications. The initial trace value is set by the client at initialization and
 * can be modified later using the {@code $/setTrace} notification.
 */
final class TraceValue {
	public static val Off = 'off'

	public static val Messages = 'messages'

	public static val Verbose = 'verbose'

	private new() {
	}
}

/**
 * A notification that should be used by the client to modify the trace setting of the server.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class SetTraceParams {
	/**
	 * The new value that should be assigned to the trace setting.
	 * For values, see {@link TraceValue}.
	 */
	@NonNull
	String value

	new() {
	}

	new(@NonNull String value) {
		this.value = Preconditions.checkNotNull(value, 'value')
	}
}

/**
 * Represents a parameter of a callable-signature. A parameter can have a label and a doc-comment.
 */
@JsonRpcData
class ParameterInformation {
	/**
	 * The label of this parameter information.
	 * <p>
	 * Either a string or an inclusive start and exclusive end offsets within its containing
	 * signature label (see {@link SignatureInformation#label}). The offsets are based on a UTF-16
	 * string representation as {@link Position} and {@link Range} does.
	 * <p>
	 * <em>Note</em>: a label of type string should be a substring of its containing signature label.
	 * Its intended use case is to highlight the parameter label part in the {@link SignatureInformation#label}.
	 */
	@NonNull
	Either<String, Tuple.Two<Integer, Integer>> label

	/**
	 * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
	 */
	Either<String, MarkupContent> documentation

	new() {
	}

	new(@NonNull String label) {
		this.label = Preconditions.checkNotNull(label, 'label')
	}

	new(@NonNull String label, String documentation) {
		this(label)
		this.documentation = documentation
	}

	new(@NonNull String label, MarkupContent documentation) {
		this(label)
		this.documentation = documentation
	}
}

/**
 * Position in a text document expressed as zero-based line and character offset.
 */
@JsonRpcData
class Position {
	/**
	 * Line position in a document (zero-based).
	 */
	int line

	/**
	 * Character offset on a line in a document (zero-based).
	 */
	int character

	new() {
	}

	new(int line, int character) {
		this.line = line
		this.character = character
	}
}

/**
 * Diagnostics notification are sent from the server to the client to signal results of validation runs.
 */
@JsonRpcData
class PublishDiagnosticsParams {
	/**
	 * The URI for which diagnostic information is reported.
	 */
	@NonNull
	String uri

	/**
	 * An array of diagnostic information items.
	 */
	@NonNull
	List<Diagnostic> diagnostics

	/**
	 * Optional the version number of the document the diagnostics are published for.
	 */
	@ProtocolSince("3.15.0")
	Integer version

	new() {
		this.diagnostics = new ArrayList
	}

	new(@NonNull String uri, @NonNull List<Diagnostic> diagnostics) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.diagnostics = Preconditions.checkNotNull(diagnostics, 'diagnostics')
	}

	new(@NonNull String uri, @NonNull List<Diagnostic> diagnostics, Integer version) {
		this(uri, diagnostics)
		this.version = version
	}
}

/**
 * A range in a text document expressed as (zero-based) start and end positions.
 */
@JsonRpcData
class Range {
	/**
	 * The range's start position
	 */
	@NonNull
	Position start

	/**
	 * The range's end position
	 */
	@NonNull
	Position end

	new() {
	}

	new(@NonNull Position start, @NonNull Position end) {
		this.start = Preconditions.checkNotNull(start, 'start')
		this.end = Preconditions.checkNotNull(end, 'end')
	}
}

/**
 * The references request is sent from the client to the server to resolve project-wide references for the symbol
 * denoted by the given text document position.
 */
@JsonRpcData
class ReferenceContext {
	/**
	 * Include the declaration of the current symbol.
	 */
	boolean includeDeclaration

	new() {
	}

	new(boolean includeDeclaration) {
		this.includeDeclaration = includeDeclaration
	}
}

@JsonRpcData
class ReferenceOptions extends AbstractWorkDoneProgressOptions {
}

@JsonRpcData
class ReferenceRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
}

/**
 * The references request is sent from the client to the server to resolve project-wide references for the symbol
 * denoted by the given text document position.
 */
@JsonRpcData
class ReferenceParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
	@NonNull
	ReferenceContext context

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position, @NonNull ReferenceContext context) {
		super(textDocument, position)
		this.context = Preconditions.checkNotNull(context, 'context')
	}
}

/**
 * The prepare rename request is sent from the client to the server to setup and test the validity of a
 * rename operation at a given location.
 */
@ProtocolSince("3.12.0")
@JsonRpcData
class PrepareRenameParams extends TextDocumentPositionAndWorkDoneProgressParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

/**
 * One of the result types of the `textDocument/prepareRename` request.
 * Provides the range of the string to rename and a placeholder text of the string content to be renamed.
 */
@ProtocolSince("3.12.0")
@JsonRpcData
class PrepareRenameResult {
	/**
	 * The range of the string to rename
	 */
	@NonNull
	Range range

	/*
	 * A placeholder text of the string content to be renamed.
	 */
	@NonNull
	String placeholder

	new() {
	}

	new(@NonNull Range range, @NonNull String placeholder) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.placeholder = Preconditions.checkNotNull(placeholder, 'placeholder')
	}
}

/**
 * One of the result types of the `textDocument/prepareRename` request.
 * Indicates that the client should use its default behavior to compute the rename range.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class PrepareRenameDefaultBehavior {
	/**
	 * Indicates that the client should use its default behavior to compute the rename range.
	 */
	boolean defaultBehavior

	new() {
	}

	new(boolean defaultBehavior) {
		this.defaultBehavior = defaultBehavior
	}
}

/**
 * The rename request is sent from the client to the server to do a workspace wide rename of a symbol.
 */
@JsonRpcData
class RenameParams extends TextDocumentPositionAndWorkDoneProgressParams {
	/**
	 * The new name of the symbol. If the given name is not valid the request must return a
	 * ResponseError with an appropriate message set.
	 */
	@NonNull
	String newName

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position, @NonNull String newName) {
		super(textDocument, position)
		this.newName = Preconditions.checkNotNull(newName, 'newName')
	}
}

/**
 * The linked editing range request is sent from the client to the server to return for a given position
 * in a document the range of the symbol at the position and all ranges that have the same content.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class LinkedEditingRangeParams extends TextDocumentPositionAndWorkDoneProgressParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

/**
 * Linked editing range options.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class LinkedEditingRangeOptions extends AbstractWorkDoneProgressOptions {
}

/**
 * Linked editing range registration options.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class LinkedEditingRangeRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * The linked editing range response is sent from the server to the client to return the range of the symbol
 * at the given position and all ranges that have the same content.
 * <p>
 * Optionally a word pattern can be returned to describe valid contents.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class LinkedEditingRanges {
	/**
	 * A list of ranges that can be renamed together. The ranges must have
	 * identical length and contain identical text content. The ranges cannot overlap.
	 */
	@NonNull
	List<Range> ranges

	/**
	 * An optional word pattern (regular expression) that describes valid contents for
	 * the given ranges. If no pattern is provided, the client configuration's word
	 * pattern will be used.
	 */
	String wordPattern

	new() {
		this.ranges = new ArrayList
	}

	new(@NonNull List<Range> ranges) {
		this.ranges = Preconditions.checkNotNull(ranges, 'ranges')
	}

	new(@NonNull List<Range> ranges, String wordPattern) {
		this(ranges)
		this.wordPattern = wordPattern
	}
}

@ProtocolSince("3.16.0")
final class SemanticTokenTypes {
	public static val Namespace = 'namespace'

	/**
	 * Represents a generic type. Acts as a fallback for types which
	 * can't be mapped to a specific type like class or enum.
	 */
	public static val Type = 'type'

	public static val Class = 'class'

	public static val Enum = 'enum'

	public static val Interface = 'interface'

	public static val Struct = 'struct'

	public static val TypeParameter = 'typeParameter'

	public static val Parameter = 'parameter'

	public static val Variable = 'variable'

	public static val Property = 'property'

	public static val EnumMember = 'enumMember'

	public static val Event = 'event'

	public static val Function = 'function'

	public static val Method = 'method'

	public static val Macro = 'macro'

	public static val Keyword = 'keyword'

	public static val Modifier = 'modifier'

	public static val Comment = 'comment'

	public static val String = 'string'

	public static val Number = 'number'

	public static val Regexp = 'regexp'

	public static val Operator = 'operator'

	@ProtocolSince("3.17.0")
	public static val Decorator = 'decorator'

	@ProtocolDraft
	@ProtocolSince("3.18.0")
	public static val Label = 'label'

	private new() {
	}
}

@ProtocolSince("3.16.0")
final class SemanticTokenModifiers {
	public static val Declaration = 'declaration'

	public static val Definition = 'definition'

	public static val Readonly = 'readonly'

	public static val Static = 'static'

	public static val Deprecated = 'deprecated'

	public static val Abstract = 'abstract'

	public static val Async = 'async'

	public static val Modification = 'modification'

	public static val Documentation = 'documentation'

	public static val DefaultLibrary = 'defaultLibrary'

	private new() {
	}
}

@ProtocolSince("3.16.0")
final class TokenFormat {
	public static val Relative = 'relative'

	private new() {
	}
}

/**
 * The legend used by the server
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensLegend {
	/**
	 * The token types that the client supports.
	 * <p>
	 * See {@link SemanticTokenTypes} for allowed values.
	 */
	@NonNull
	List<String> tokenTypes

	/**
	 * The token modifiers that the client supports.
	 * <p>
	 * See {@link SemanticTokenModifiers} for allowed values.
	 */
	@NonNull
	List<String> tokenModifiers

	new() {
	}

	new(@NonNull List<String> tokenTypes, @NonNull List<String> tokenModifiers) {
		this.tokenTypes = Preconditions.checkNotNull(tokenTypes, 'tokenTypes')
		this.tokenModifiers = Preconditions.checkNotNull(tokenModifiers, 'tokenModifiers')
	}
}

/**
 * Server supports providing semantic tokens for a full document.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensServerFull {
	/**
	* The server supports deltas for full documents.
	*/
	Boolean delta

	new() {
	}

	new(Boolean delta) {
		this.delta = delta
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensWithRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The legend used by the server
	 */
	@NonNull
	SemanticTokensLegend legend

	/**
	 * Server supports providing semantic tokens for a specific range
	 * of a document.
	 */
	Either<Boolean, Object> range

	/**
	 * Server supports providing semantic tokens for a full document.
	 */
	Either<Boolean, SemanticTokensServerFull> full

	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also Registration#id.
	 */
	String id

	new() {
	}

	new(@NonNull SemanticTokensLegend legend) {
		this.legend = Preconditions.checkNotNull(legend, 'legend')
	}

	new(@NonNull SemanticTokensLegend legend, Boolean full) {
		this(legend)
		this.full = full
	}

	new(@NonNull SemanticTokensLegend legend, SemanticTokensServerFull full) {
		this(legend)
		this.full = full
	}

	new(@NonNull SemanticTokensLegend legend, Boolean full, Boolean range) {
		this(legend)
		this.full = full
		this.range = range
	}

	new(@NonNull SemanticTokensLegend legend, SemanticTokensServerFull full, Boolean range) {
		this(legend)
		this.full = full
		this.range = range
	}

	new(@NonNull SemanticTokensLegend legend, SemanticTokensServerFull full, Boolean range, List<DocumentFilter> documentSelector) {
		this(legend)
		this.full = full
		this.range = range
		this.documentSelector = documentSelector
	}
}

/**
 * The server can signal these capabilities
 */
@JsonRpcData
class ServerCapabilities {
	/**
	 * The position encoding the server picked from the encodings offered
	 * by the client via the client capability {@link GeneralClientCapabilities#positionEncodings}.
	 * <p>
	 * If the client didn't provide any position encodings the only valid
	 * value that a server can return is {@link PositionEncodingKind#UTF16}.
	 * <p>
	 * If omitted it defaults to {@link PositionEncodingKind#UTF16}.
	 * <p>
	 * See {@link PositionEncodingKind} for some predefined position encoding kinds.
	 */
	@ProtocolSince("3.17.0")
	String positionEncoding

	/**
	 * Defines how text documents are synced. Is either a detailed structure defining each notification or
	 * for backwards compatibility the TextDocumentSyncKind number. If omitted it defaults to
	 * {@link TextDocumentSyncKind#None}.
	 */
	Either<TextDocumentSyncKind, TextDocumentSyncOptions> textDocumentSync

	/**
	 * Defines how notebook documents are synced.
	 */
	@ProtocolSince("3.17.0")
	NotebookDocumentSyncRegistrationOptions notebookDocumentSync

	/**
	 * The server provides hover support.
	 */
	Either<Boolean, HoverOptions> hoverProvider

	/**
	 * The server provides completion support.
	 */
	CompletionOptions completionProvider

	/**
	 * The server provides signature help support.
	 */
	SignatureHelpOptions signatureHelpProvider

	/**
	 * The server provides goto definition support.
	 */
	Either<Boolean, DefinitionOptions> definitionProvider

	/**
	 * The server provides Goto Type Definition support.
	 */
	@ProtocolSince("3.6.0")
	Either<Boolean, TypeDefinitionRegistrationOptions> typeDefinitionProvider

	/**
	 * The server provides Goto Implementation support.
	 */
	@ProtocolSince("3.6.0")
	Either<Boolean, ImplementationRegistrationOptions> implementationProvider

	/**
	 * The server provides find references support.
	 */
	Either<Boolean, ReferenceOptions> referencesProvider

	/**
	 * The server provides document highlight support.
	 */
	Either<Boolean, DocumentHighlightOptions> documentHighlightProvider

	/**
	 * The server provides document symbol support.
	 */
	Either<Boolean, DocumentSymbolOptions> documentSymbolProvider

	/**
	 * The server provides workspace symbol support.
	 */
	Either<Boolean, WorkspaceSymbolOptions> workspaceSymbolProvider

	/**
	 * The server provides code actions. The {@link CodeActionOptions} return type is only
	 * valid if the client signals code action literal support via the property
	 * {@link CodeActionCapabilities#codeActionLiteralSupport}.
	 */
	Either<Boolean, CodeActionOptions> codeActionProvider

	/**
	 * The server provides code lens.
	 */
	CodeLensOptions codeLensProvider

	/**
	 * The server provides document formatting.
	 */
	Either<Boolean, DocumentFormattingOptions> documentFormattingProvider

	/**
	 * The server provides document range formatting.
	 */
	Either<Boolean, DocumentRangeFormattingOptions> documentRangeFormattingProvider

	/**
	 * The server provides document formatting on typing.
	 */
	DocumentOnTypeFormattingOptions documentOnTypeFormattingProvider

	/**
	 * The server provides rename support.
	 */
	Either<Boolean, RenameOptions> renameProvider

	/**
	 * The server provides document link support.
	 */
	DocumentLinkOptions documentLinkProvider

	/**
	 * The server provides color provider support.
	 */
	@ProtocolSince("3.6.0")
	Either<Boolean, ColorProviderOptions> colorProvider

	/**
	 * The server provides folding provider support.
	 */
	@ProtocolSince("3.10.0")
	Either<Boolean, FoldingRangeProviderOptions> foldingRangeProvider

	/**
	 * The server provides go to declaration support.
	 */
	@ProtocolSince("3.14.0")
	Either<Boolean, DeclarationRegistrationOptions> declarationProvider

	/**
	 * The server provides execute command support.
	 */
	ExecuteCommandOptions executeCommandProvider

	/**
	 * Workspace specific server capabilities
	 */
	WorkspaceServerCapabilities workspace

	/**
	 * The server provides Type Hierarchy support.
	 */
	@ProtocolSince("3.17.0")
	Either<Boolean, TypeHierarchyRegistrationOptions> typeHierarchyProvider

	/**
	 * The server provides Call Hierarchy support.
	 */
	@ProtocolSince("3.16.0")
	Either<Boolean, CallHierarchyRegistrationOptions> callHierarchyProvider

	/**
	 * The server provides selection range support.
	 */
	@ProtocolSince("3.15.0")
	Either<Boolean, SelectionRangeRegistrationOptions> selectionRangeProvider

	/**
	 * The server provides linked editing range support.
	 */
	@ProtocolSince("3.16.0")
	Either<Boolean, LinkedEditingRangeRegistrationOptions> linkedEditingRangeProvider

	/**
	 * The server provides semantic tokens support.
	 */
	@ProtocolSince("3.16.0")
	SemanticTokensWithRegistrationOptions semanticTokensProvider

	/**
	 * Whether server provides moniker support.
	 */
	@ProtocolSince("3.16.0")
	Either<Boolean, MonikerRegistrationOptions> monikerProvider

	/**
	 * The server provides inlay hints.
	 */
	@ProtocolSince("3.17.0")
	Either<Boolean, InlayHintRegistrationOptions> inlayHintProvider

	/**
	 * The server provides inline values.
	 */
	@ProtocolSince("3.17.0")
	Either<Boolean, InlineValueRegistrationOptions> inlineValueProvider

	/**
	 * The server has support for pull model diagnostics.
	 */
	@ProtocolSince("3.17.0")
	DiagnosticRegistrationOptions diagnosticProvider

	/**
	 * The server provides inline completions.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Either<Boolean, InlineCompletionRegistrationOptions> inlineCompletionProvider

	/**
	 * Text document specific server capabilities.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	TextDocumentServerCapabilities textDocument

	/**
	 * Experimental server capabilities.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object experimental
}

/**
 * Workspace specific server capabilities
 */
@JsonRpcData
class WorkspaceServerCapabilities {
	/**
	 * The server supports workspace folder.
	 */
	@ProtocolSince("3.6.0")
	WorkspaceFoldersOptions workspaceFolders

	/**
	 * The server is interested in file notifications/requests.
	 */
	@ProtocolSince("3.16.0")
	FileOperationsServerCapabilities fileOperations

	/**
	 * Text document content provider options.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	TextDocumentContentRegistrationOptions textDocumentContent

	new() {
	}

	new(WorkspaceFoldersOptions workspaceFolders) {
		this.workspaceFolders = workspaceFolders
	}
}

/**
 * Text document specific server capabilities.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class TextDocumentServerCapabilities {

	/**
	 * Capabilities specific to the diagnostic pull model.
	 */
	DiagnosticServerCapabilities diagnostic

	new() {
	}
}

/**
 * The show message request is sent from a server to a client to ask the client to display a particular message in the
 * user class. In addition to the show message notification the request allows to pass actions and to wait for an
 * answer from the client.
 */
@JsonRpcData
class ShowMessageRequestParams extends MessageParams {
	/**
	 * The message action items to present.
	 */
	List<MessageActionItem> actions

	new() {
	}

	new(List<MessageActionItem> actions) {
		this.actions = actions
	}
}

/**
 * The signature help request is sent from the client to the server to request signature information at a given cursor position.
 */
@JsonRpcData
class SignatureHelpParams extends TextDocumentPositionAndWorkDoneProgressParams {
	/**
	 * The signature help context. This is only available if the client specifies
	 * to send this using the client capability {@link SignatureHelpCapabilities#contextSupport} as true.
	 */
	@ProtocolSince("3.15.0")
	SignatureHelpContext context

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position, SignatureHelpContext context) {
		this(textDocument, position)
		this.context = context
	}
}

/**
 * The request is sent from the client to the server to resolve semantic tokens for a given whole file.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokens {
	/**
	 * An optional result id. If provided and clients support delta updating
	 * the client will include the result id in the next semantic token request.
	 * A server can then instead of computing all semantic tokens again simply
	 * send a delta.
	 */
	String resultId

	/**
	 * The actual tokens.
	 */
	@NonNull
	List<Integer> data

	new() {
		this.data = new ArrayList()
	}

	new(@NonNull List<Integer> data) {
		this.data = Preconditions.checkNotNull(data, 'data')
	}

	new(String resultId, @NonNull List<Integer> data) {
		this(data)
		this.resultId = resultId
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensPartialResult {
	@NonNull
	List<Integer> data

	new() {
	}

	new(@NonNull List<Integer> data) {
		this.data = Preconditions.checkNotNull(data, 'data')
	}
}

/**
 * The request is sent from the client to the server to resolve semantic token deltas for a given whole file.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensDeltaParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The result id of a previous response. The result Id can either point to a full response
	 * or a delta response depending on what was received last.
	 */
	@NonNull
	String previousResultId

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull String previousResultId) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.previousResultId = Preconditions.checkNotNull(previousResultId, 'previousResultId')
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensEdit {
	/**
	 * The start offset of the edit.
	 */
	int start

	/**
	 * The count of elements to remove.
	 */
	int deleteCount

	/**
	 * The elements to insert.
	 */
	List<Integer> data

	new() {
	}

	new(int start, int deleteCount, List<Integer> data) {
		this.start = start
		this.deleteCount = deleteCount
		this.data = data
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensDelta {
	String resultId

	/**
	 * The semantic token edits to transform a previous result into a new result.
	 */
	@NonNull
	List<SemanticTokensEdit> edits

	new() {
	}

	new(@NonNull List<SemanticTokensEdit> edits) {
		this.edits = Preconditions.checkNotNull(edits, 'edits')
	}

	new(@NonNull List<SemanticTokensEdit> edits, String resultId) {
		this.edits = Preconditions.checkNotNull(edits, 'edits')
		this.resultId = resultId
	}
}

@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensDeltaPartialResult {
	@NonNull
	List<SemanticTokensEdit> edits

	new() {
	}

	new(@NonNull List<SemanticTokensEdit> edits) {
		this.edits = Preconditions.checkNotNull(edits, 'edits')
	}
}

/**
 * The request is sent from the client to the server to resolve semantic tokens for a range in a given file.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class SemanticTokensRangeParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The range the semantic tokens are requested for.
	 */
	@NonNull
	Range range

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Range range) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.range = Preconditions.checkNotNull(range, 'range')
	}
}

/**
 * Additional information about the context in which a signature help request was triggered.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class SignatureHelpContext {
	/**
	 * Action that caused signature help to be triggered.
	 */
	@NonNull
	SignatureHelpTriggerKind triggerKind

	/**
	 * Character that caused signature help to be triggered.
	 * <p>
	 * This is undefined when {@link #triggerKind} is not {@link SignatureHelpTriggerKind#TriggerCharacter}
	 */
	String triggerCharacter

	/**
	 * {@code true} if signature help was already showing when it was triggered.
	 * <p>
	 * Retriggers occur when the signature help is already active and can be caused by actions such as
	 * typing a trigger character, a cursor move, or document content changes.
	 */
	boolean isRetrigger

	/**
	 * The currently active {@link SignatureHelp}.
	 * <p>
	 * The `activeSignatureHelp` has its {@link SignatureHelp#activeSignature} field updated based on
	 * the user navigating through available signatures.
	 */
	SignatureHelp activeSignatureHelp

	new() {
	}

	new(@NonNull SignatureHelpTriggerKind triggerKind, boolean isRetrigger) {
		this.triggerKind = Preconditions.checkNotNull(triggerKind, 'triggerKind')
		this.isRetrigger = isRetrigger
	}
}

/**
 * Signature help represents the signature of something callable. There can be multiple signature but only one
 * active and only one active parameter.
 */
@JsonRpcData
class SignatureHelp {
	/**
	 * One or more signatures.
	 */
	@NonNull
	List<SignatureInformation> signatures

	/**
	 * The active signature. If omitted or the value lies outside the
	 * range of {@link #signatures} the value defaults to zero or is ignored if
	 * the {@code SignatureHelp} has no signatures. Whenever possible implementors should
	 * make an active decision about the active signature and shouldn't
	 * rely on a default value.
	 * <p>
	 * In future version of the protocol this property might become
	 * mandatory to better express this.
	 */
	Integer activeSignature

	/**
	 * The active parameter of the active signature. If omitted or the value
	 * lies outside the range of {@code signatures[activeSignature].parameters}
	 * defaults to 0 if the active signature has parameters. If
	 * the active signature has no parameters it is ignored.
	 * <p>
	 * In future version of the protocol this property might become
	 * mandatory to better express the active parameter if the
	 * active signature does have any.
	 */
	Integer activeParameter

	new() {
		this.signatures = new ArrayList
	}

	new(@NonNull List<SignatureInformation> signatures, Integer activeSignature, Integer activeParameter) {
		this.signatures = Preconditions.checkNotNull(signatures, 'signatures')
		this.activeSignature = activeSignature
		this.activeParameter = activeParameter
	}
}

/**
 * Signature help options.
 */
@JsonRpcData
class SignatureHelpOptions extends AbstractWorkDoneProgressOptions {

	/**
	 * The characters that trigger signature help automatically.
	 */
	List<String> triggerCharacters

	/**
	 * List of characters that re-trigger signature help.
	 * <p>
	 * These trigger characters are only active when signature help is already showing. All trigger characters
	 * are also counted as re-trigger characters.
	 */
	@ProtocolSince("3.15.0")
	List<String> retriggerCharacters

	new() {
	}

	new(List<String> triggerCharacters) {
		this.triggerCharacters = triggerCharacters
	}

	new(List<String> triggerCharacters, List<String> retriggerCharacters) {
		this(triggerCharacters)
		this.retriggerCharacters = retriggerCharacters
	}
}

/**
 * Represents the signature of something callable. A signature can have a label, like a function-name, a doc-comment, and
 * a set of parameters.
 */
@JsonRpcData
class SignatureInformation {
	/**
	 * The label of this signature. Will be shown in the UI.
	 */
	@NonNull
	String label

	/**
	 * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
	 */
	Either<String, MarkupContent> documentation

	/**
	 * The parameters of this signature.
	 */
	List<ParameterInformation> parameters

	/**
	 * The index of the active parameter.
	 * <p>
	 * If provided, this is used in place of {@link SignatureHelp#activeParameter}.
	 */
	@ProtocolSince("3.16.0")
	Integer activeParameter

	new() {
	}

	new(@NonNull String label) {
		this.label = Preconditions.checkNotNull(label, 'label')
	}

	new(@NonNull String label, String documentation, List<ParameterInformation> parameters) {
		this(label)
		this.documentation = documentation
		this.parameters = parameters
	}

	new(@NonNull String label, MarkupContent documentation, List<ParameterInformation> parameters) {
		this(label)
		this.documentation = documentation
		this.parameters = parameters
	}
}

/**
 * Representation of an item that carries type information.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class TypeHierarchyItem {
	/**
	 * The name of this item.
	 */
	@NonNull
	String name

	/**
	 * More detail for this item, e.g. the signature of a function.
	 */
	String detail

	/**
	 * The kind of this item.
	 */
	@NonNull
	SymbolKind kind

	/**
	 * Tags for this item.
	 */
	List<SymbolTag> tags

	/**
	 * The resource identifier of this item.
	 */
	@NonNull
	String uri

	/**
	 * The range enclosing this symbol not including leading/trailing whitespace
	 * but everything else, e.g. comments and code.
	 */
	@NonNull
	Range range

	/**
	 * The range that should be selected and revealed when this symbol is being
	 * picked, e.g. the name of a function. Must be contained by the {@link #range}.
	 */
	@NonNull
	Range selectionRange

	/**
	 * A data entry field that is preserved between a type hierarchy prepare and
	 * supertypes or subtypes requests. It could also be used to identify the
	 * type hierarchy in the server, helping improve the performance on
	 * resolving supertypes and subtypes.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull String uri, @NonNull Range range, @NonNull Range selectionRange) {
		this.name = Preconditions.checkNotNull(name, 'name')
		this.kind = Preconditions.checkNotNull(kind, 'kind')
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.range = Preconditions.checkNotNull(range, 'range')
		this.selectionRange = Preconditions.checkNotNull(selectionRange, 'selectionRange')
	}

	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull String uri, @NonNull Range range, @NonNull Range selectionRange, String detail) {
		this(name, kind, uri, range, selectionRange)
		this.detail = detail
	}
}

/**
 * Represents programming constructs like variables, classes, interfaces etc. that appear in a document. Document symbols can be
 * hierarchical and they have two ranges: one that encloses its definition and one that points to its most interesting range,
 * e.g. the range of an identifier.
 */
@ProtocolSince("3.10.0")
@JsonRpcData
class DocumentSymbol {
	/**
	 * The name of this symbol.
	 */
	@NonNull
	String name

	/**
	 * The kind of this symbol.
	 */
	@NonNull
	SymbolKind kind

	/**
	 * The range enclosing this symbol not including leading/trailing whitespace but everything else
	 * like comments. This information is typically used to determine if the client's cursor is
	 * inside the symbol to reveal the symbol in the UI.
	 */
	@NonNull
	Range range

	/**
	 * The range that should be selected and revealed when this symbol is being picked, e.g the name of a function.
	 * Must be contained by the {@link #range}.
	 */
	@NonNull
	Range selectionRange

	/**
	 * More detail for this symbol, e.g the signature of a function. If not provided the
	 * name is used.
	 */
	String detail

	/**
	 * Tags for this document symbol.
	 */
	@ProtocolSince("3.16.0")
	List<SymbolTag> tags

	/**
	 * Indicates if this symbol is deprecated.
	 * <p>
	 * Deprecated in LSP:  Use {@link #tags} instead if supported.
	 */
	@ProtocolDeprecated
	Boolean deprecated

	/**
	 * Children of this symbol, e.g. properties of a class.
	 */
	List<DocumentSymbol> children

	new() {
	}

	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull Range range, @NonNull Range selectionRange) {
		this.name = Preconditions.checkNotNull(name, 'name')
		this.kind = Preconditions.checkNotNull(kind, 'kind')
		this.range = Preconditions.checkNotNull(range, 'range')
		this.selectionRange = Preconditions.checkNotNull(selectionRange, 'selectionRange')
	}

	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull Range range, @NonNull Range selectionRange,
		String detail) {
		this(name, kind, range, selectionRange)
		this.detail = detail
	}

	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull Range range, @NonNull Range selectionRange,
		String detail, List<DocumentSymbol> children) {
		this(name, kind, range, selectionRange)
		this.detail = detail
		this.children = children
	}
}

/**
 * Represents information about programming constructs like variables, classes, interfaces etc.
 * <p>
 * Deprecated in LSP: Use {@link DocumentSymbol} or {@link WorkspaceSymbol} instead if supported.
 */
@JsonRpcData
@JsonAdapter(SymbolInformationTypeAdapter.Factory)
@ProtocolDeprecated
class SymbolInformation {
	/**
	 * The name of this symbol.
	 */
	@NonNull
	@ProtocolDeprecated
	String name

	/**
	 * The kind of this symbol.
	 */
	@NonNull
	@ProtocolDeprecated
	SymbolKind kind

	/**
	 * Tags for this symbol.
	 */
	@ProtocolSince("3.16.0")
	@ProtocolDeprecated
	List<SymbolTag> tags

	/**
	 * Indicates if this symbol is deprecated.
	 * <p>
	 * Deprecated in LSP:  Use {@link #tags} instead if supported.
	 */
	@ProtocolSince("3.8.0")
	@ProtocolDeprecated
	Boolean deprecated

	/**
	 * The location of this symbol. The location's range is used by a tool
	 * to reveal the location in the editor. If the symbol is selected in the
	 * tool the range's start information is used to position the cursor. So
	 * the range usually spans more then the actual symbol's name and does
	 * normally include things like visibility modifiers.
	 * <p>
	 * The range doesn't have to denote a node range in the sense of a abstract
	 * syntax tree. It can therefore not be used to re-construct a hierarchy of
	 * the symbols.
	 */
	@NonNull
	@ProtocolDeprecated
	Location location

	/**
	 * The name of the symbol containing this symbol. This information is for
	 * user interface purposes (e.g. to render a qualifier in the user interface
	 * if necessary). It can't be used to re-infer a hierarchy for the document
	 * symbols.
	 */
	@ProtocolDeprecated
	String containerName

	@ProtocolDeprecated
	new() {
	}

	@ProtocolDeprecated
	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull Location location) {
		this.name = Preconditions.checkNotNull(name, 'name')
		this.kind = Preconditions.checkNotNull(kind, 'kind')
		this.location = Preconditions.checkNotNull(location, 'location')
	}

	@ProtocolDeprecated
	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull Location location, String containerName) {
		this(name, kind, location)
		this.containerName = containerName
	}
}

/**
 * A special workspace symbol that supports locations without a range
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceSymbolLocation {
	/**
	 * The DocumentUri of this symbol.
	 */
	@NonNull
	String uri

	new() {
	}

	new(@NonNull String uri) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}

/**
 * A special workspace symbol that supports locations without a range
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceSymbol {
	/**
	 * The name of this symbol.
	 */
	@NonNull
	String name

	/**
	 * The kind of this symbol.
	 */
	@NonNull
	SymbolKind kind

	/**
	 * Tags for this symbol.
	 */
	List<SymbolTag> tags

	/**
	 * The location of this symbol. Whether a server is allowed to
	 * return a location without a range depends on the client
	 * capability {@link SymbolCapabilities#resolveSupport}.
	 * <p>
	 * See also {@link SymbolInformation#location}.
	 */
	@NonNull
	@JsonAdapter(WorkspaceSymbolLocationTypeAdapter)
	Either<Location, WorkspaceSymbolLocation> location

	/**
	 * The name of the symbol containing this symbol. This information is for
	 * user interface purposes (e.g. to render a qualifier in the user interface
	 * if necessary). It can't be used to re-infer a hierarchy for the document
	 * symbols.
	 */
	String containerName

	/**
	 * A data entry field that is preserved on a workspace symbol between a
	 * workspace symbol request and a workspace symbol resolve request.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new() {
	}

	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull Either<Location, WorkspaceSymbolLocation> location) {
		this.name = Preconditions.checkNotNull(name, 'name')
		this.kind = Preconditions.checkNotNull(kind, 'kind')
		this.location = Preconditions.checkNotNull(location, 'location')
	}

	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull Either<Location, WorkspaceSymbolLocation> location, String containerName) {
		this(name, kind, location)
		this.containerName = containerName
	}
}

/**
 * An event describing a change to a text document. If range and rangeLength are omitted the new text is considered
 * to be the full content of the document.
 */
@JsonRpcData
class TextDocumentContentChangeEvent {
	/**
	 * The range of the document that changed.
	 */
	Range range

	/**
	 * The length of the range that got replaced.
	 * <p>
	 * Deprecated in LSP:  Use {@link #range} instead.
	 */
	@ProtocolDeprecated
	Integer rangeLength

	/**
	 * The new text of the range/document.
	 */
	@NonNull
	String text

	new() {
	}

	new(@NonNull String text) {
		this.text = Preconditions.checkNotNull(text, 'text')
	}

	new(Range range, @NonNull String text) {
		this(text)
		this.range = range
	}
}

/**
 * Text documents are identified using an URI. On the protocol level URI's are passed as strings.
 */
@JsonRpcData
class TextDocumentIdentifier {
	/**
	 * The text document's uri.
	 */
	@NonNull
	String uri

	new() {
	}

	new(@NonNull String uri) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}

/**
 * An item to transfer a text document from the client to the server.
 */
@JsonRpcData
class TextDocumentItem {
	/**
	 * The text document's uri.
	 */
	@NonNull
	String uri

	/**
	 * The text document's language identifier
	 */
	@NonNull
	String languageId

	/**
	 * The version number of this document (it will strictly increase after each change, including undo/redo).
	 */
	int version

	/**
	 * The content of the opened text document.
	 */
	@NonNull
	String text

	new() {
	}

	new(@NonNull String uri, @NonNull String languageId, int version, @NonNull String text) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.languageId = Preconditions.checkNotNull(languageId, 'languageId')
		this.version = version
		this.text = Preconditions.checkNotNull(text, 'text')
	}
}

/**
 * A parameter literal used in requests to pass a text document and a position inside that document.
 */
@JsonRpcData
class TextDocumentPositionParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The position inside the text document.
	 */
	@NonNull
	Position position

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.position = Preconditions.checkNotNull(position, 'position')
	}
}


/**
 * The Completion request is sent from the client to the server to compute completion items at a given cursor position.
 */
@JsonRpcData
class CompletionParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
	/**
	 * The completion context. This is only available if the client specifies
	 * to send this using {@link CompletionCapabilities#contextSupport} as true.
	 */
	@ProtocolSince("3.3.0")
	CompletionContext context

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position, CompletionContext context) {
		this(textDocument, position)
		this.context = context
	}
}

@ProtocolSince("3.3.0")
@JsonRpcData
class CompletionContext {
	/**
	 * How the completion was triggered.
	 */
	@NonNull
	CompletionTriggerKind triggerKind

	/**
	 * The trigger character (a single character) that has trigger code complete.
	 * Is undefined if {@link #triggerKind} is not {@link CompletionTriggerKind#TriggerCharacter}.
	 */
	String triggerCharacter

	new() {
	}

	new(@NonNull CompletionTriggerKind triggerKind) {
		this.triggerKind = Preconditions.checkNotNull(triggerKind, 'triggerKind')
	}

	new(@NonNull CompletionTriggerKind triggerKind, String triggerCharacter) {
		this(triggerKind)
		this.triggerCharacter = triggerCharacter
	}
}

/**
 * A textual edit applicable to a text document.
 */
@JsonRpcData
class TextEdit {
	/**
	 * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
	 */
	@NonNull
	Range range

	/**
	 * The string to be inserted. For delete operations use an empty string.
	 */
	@NonNull
	String newText

	new() {
	}

	new(@NonNull Range range, @NonNull String newText) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.newText = Preconditions.checkNotNull(newText, 'newText')
	}
}

/**
 * Additional information that describes document changes.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class ChangeAnnotation {
	/**
	 * A human-readable string describing the actual change. The string
	 * is rendered prominent in the user interface.
	 */
	@NonNull
	String label

	/**
	 * A flag which indicates that user confirmation is needed
	 * before applying the change.
	 */
	Boolean needsConfirmation

	/**
	 * A human-readable string which is rendered less prominent in
	 * the user interface.
	 */
	String description

	new() {
	}

	new(@NonNull String label) {
		this.label = Preconditions.checkNotNull(label, 'label')
	}
}

/**
 * A special text edit with an additional change annotation.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class AnnotatedTextEdit extends TextEdit {
	/**
	 * The actual annotation identifier
	 */
	@NonNull
	String annotationId

	new() {
	}

	new(@NonNull Range range, @NonNull String newText, @NonNull String annotationId) {
		super(range, newText)
		this.annotationId = Preconditions.checkNotNull(annotationId, 'annotationId')
	}
}

/**
 * A special text edit to provide an insert and a replace operation.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class InsertReplaceEdit {
	/**
	 * The string to be inserted.
	 */
	@NonNull
	String newText

	/**
	 * The range if the insert is requested
	 */
	@NonNull
	Range insert

	/**
	 * The range if the replace is requested.
	 */
	@NonNull
	Range replace

	new() {
	}

	new(@NonNull String newText, @NonNull Range insert, @NonNull Range replace) {
		this.newText = Preconditions.checkNotNull(newText, 'newText')
		this.insert = Preconditions.checkNotNull(insert, 'insert')
		this.replace = Preconditions.checkNotNull(replace, 'replace')
	}
}

/**
 * An identifier to denote a specific version of a text document. This information usually flows from the client to the server.
 */
@JsonRpcData
@JsonAdapter(VersionedTextDocumentIdentifierTypeAdapter.Factory)
class VersionedTextDocumentIdentifier extends TextDocumentIdentifier {
	/**
	 * The version number of this document. If a versioned text document identifier
	 * is sent from the server to the client and the file is not open in the editor
	 * (the server has not received an open notification before) the server can send
	 * `null` to indicate that the version is known and the content on disk is the
	 * truth (as specified with document content ownership).
	 */
	Integer version

	new() {
	}

	new(@NonNull String uri, Integer version) {
		super(uri)
		this.version = version
	}
}

/**
 * Describes textual changes on a single text document.
 * The text document is referred to as a {@link VersionedTextDocumentIdentifier}
 * to allow clients to check the text document version before an edit is applied.
 */
@JsonRpcData
class TextDocumentEdit {
	/**
	 * The text document to change.
	 */
	@NonNull
	VersionedTextDocumentIdentifier textDocument

	/**
	 * The edits to be applied.
	 * <p>
	 * Since 3.18 - support for {@link SnippetTextEdit}. This is guarded by the
	 * client capability {@link WorkspaceEditCapabilities#snippetEditSupport}.
	 * If a client does not signal this capability, servers should not send
	 * {@link SnippetTextEdit} snippets back to the client.
	 */
	@NonNull
	@JsonAdapter(TextEditListAdapter)
	List<Either<TextEdit, SnippetTextEdit>> edits

	new() {
	}

	new(@NonNull VersionedTextDocumentIdentifier textDocument, @NonNull List<Either<TextEdit, SnippetTextEdit>> edits) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.edits = Preconditions.checkNotNull(edits, 'edits')
	}
}

@JsonRpcData
@JsonAdapter(ResourceOperationTypeAdapter)
abstract class ResourceOperation {
	/**
	 * The kind of resource operation. For allowed values, see {@link ResourceOperationKind}
	 */
	@NonNull
	String kind

	/**
	 * An optional annotation identifier describing the operation.
	 */
	@ProtocolSince("3.16.0")
	String annotationId

	new() {
	}

	new(@NonNull String kind) {
		this.kind = Preconditions.checkNotNull(kind, 'kind')
	}
}

/**
 * Options to create a file.
 */
@ProtocolSince("3.13.0")
@JsonRpcData
class CreateFileOptions {
	/**
	 * Overwrite existing file. Overwrite wins over {@link #ignoreIfExists}
	 */
	Boolean overwrite

	/**
	 * Ignore if exists.
	 */
	Boolean ignoreIfExists

	new() {
	}

	new(Boolean overwrite, Boolean ignoreIfExists) {
		this.overwrite = overwrite
		this.ignoreIfExists = ignoreIfExists
	}
}

/**
 * Create file operation
 */
@ProtocolSince("3.13.0")
@JsonRpcData
class CreateFile extends ResourceOperation {
	/**
	 * The resource to create.
	 */
	@NonNull
	String uri

	/**
	 * Additional options
	 */
	CreateFileOptions options

	new() {
		super(ResourceOperationKind.Create)
	}

	new(@NonNull String uri) {
		super(ResourceOperationKind.Create)
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}

	new(@NonNull String uri, CreateFileOptions options) {
		this(uri)
		this.options = options
	}
}

/**
 * Rename file options
 */
@ProtocolSince("3.13.0")
@JsonRpcData
class RenameFileOptions {
	/**
	 * Overwrite target if existing. Overwrite wins over {@link #ignoreIfExists}
	 */
	Boolean overwrite

	/**
	 * Ignores if target exists.
	 */
	Boolean ignoreIfExists

	new() {
	}

	new(Boolean overwrite, Boolean ignoreIfExists) {
		this.overwrite = overwrite
		this.ignoreIfExists = ignoreIfExists
	}
}

/**
 * Rename file operation
 */
@ProtocolSince("3.13.0")
@JsonRpcData
class RenameFile extends ResourceOperation {
	/**
	 * The old (existing) location.
	 */
	@NonNull
	String oldUri

	/**
	 * The new location.
	 */
	@NonNull
	String newUri

	/**
	 * Rename options.
	 */
	RenameFileOptions options

	new() {
		super(ResourceOperationKind.Rename)
	}

	new(@NonNull String oldUri, @NonNull String newUri) {
		super(ResourceOperationKind.Rename)
		this.oldUri = Preconditions.checkNotNull(oldUri, 'oldUri')
		this.newUri = Preconditions.checkNotNull(newUri, 'newUri')
	}

	new(@NonNull String oldUri, @NonNull String newUri, RenameFileOptions options) {
		this(oldUri, newUri)
		this.options = options
	}
}

/**
 * Delete file options
 */
@ProtocolSince("3.13.0")
@JsonRpcData
class DeleteFileOptions {
	/**
	 * Delete the content recursively if a folder is denoted.
	 */
	Boolean recursive

	/**
	 * Ignore the operation if the file doesn't exist.
	 */
	Boolean ignoreIfNotExists

	new() {
	}

	new(Boolean recursive, Boolean ignoreIfNotExists) {
		this.recursive = recursive
		this.ignoreIfNotExists = ignoreIfNotExists
	}
}

/**
 * Delete file operation
 */
@ProtocolSince("3.13.0")
@JsonRpcData
class DeleteFile extends ResourceOperation {
	/**
	 * The file to delete.
	 */
	@NonNull
	String uri

	/**
	 * Delete options.
	 */
	DeleteFileOptions options

	new() {
		super(ResourceOperationKind.Delete)
	}

	new(@NonNull String uri) {
		super(ResourceOperationKind.Delete)
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}

	new(@NonNull String uri, DeleteFileOptions options) {
		this(uri)
		this.options = options
	}
}

/**
 * A workspace edit represents changes to many resources managed in the workspace.
 * The edit should either provide {@link #changes} or {@link #documentChanges}.
 * If documentChanges are present they are preferred over changes
 * if the client can handle versioned document edits.
 */
@JsonRpcData
class WorkspaceEdit {
	/**
	 * Holds changes to existing resources.
	 */
	Map<String, List<TextEdit>> changes

	/**
	 * Depending on the client capability
	 * {@link WorkspaceEditCapabilities#resourceOperations} document changes are either
	 * an array of {@link TextDocumentEdit}s to express changes to n different text
	 * documents where each text document edit addresses a specific version of
	 * a text document. Or it can contain above {@link TextDocumentEdit}s mixed with
	 * create, rename and delete file / folder operations.
	 * <p>
	 * Whether a client supports versioned document edits is expressed via
	 * {@link WorkspaceEditCapabilities#documentChanges} client capability.
	 * <p>
	 * If a client neither supports {@link WorkspaceEditCapabilities#documentChanges} nor
	 * {@link WorkspaceEditCapabilities#resourceOperations} then only plain {@link TextEdit}s
	 * using the {@link #changes} property are supported.
	 */
	@JsonAdapter(DocumentChangeListAdapter)
	List<Either<TextDocumentEdit, ResourceOperation>> documentChanges

	/**
	 * A map of change annotations that can be referenced in
	 * {@link AnnotatedTextEdit}s or {@link ResourceOperation}s.
	 * <p>
	 * Client support depends on {@link WorkspaceEditCapabilities#changeAnnotationSupport}.
	 */
	@ProtocolSince("3.16.0")
	Map<String, ChangeAnnotation> changeAnnotations

	new() {
		this.changes = new LinkedHashMap
	}

	new(Map<String, List<TextEdit>> changes) {
		this.changes = changes
	}

	new(List<Either<TextDocumentEdit, ResourceOperation>> documentChanges) {
		this.documentChanges = documentChanges
	}
}

/**
 * The options of a Workspace Symbol Request.
 */
@JsonRpcData
class WorkspaceSymbolOptions extends AbstractWorkDoneProgressOptions {
	/**
	 * The server provides support to resolve additional
	 * information for a workspace symbol.
	 */
	@ProtocolSince("3.17.0")
	Boolean resolveProvider

	new() {
	}

	new(Boolean resolveProvider) {
		this.resolveProvider = resolveProvider
	}
}

/**
 * The options of a Workspace Symbol Registration Request.
 */
@JsonRpcData
class WorkspaceSymbolRegistrationOptions extends WorkspaceSymbolOptions {
}


/**
 * The parameters of a Workspace Symbol Request.
 */
@JsonRpcData
class WorkspaceSymbolParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * A query string to filter symbols by. Clients may send an empty
	 * string here to request all symbols.
	 */
	@NonNull
	String query

	new() {
	}

	new(@NonNull String query) {
		this.query = Preconditions.checkNotNull(query, 'query')
	}
}

/**
 * General parameters to register for a capability.
 */
@JsonRpcData
class Registration {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again.
	 */
	@NonNull
	String id

	/**
	 * The method / capability to register for.
	 */
	@NonNull
	String method

	/**
	 * Options necessary for the registration.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object registerOptions

	new() {
	}

	new(@NonNull String id, @NonNull String method) {
		this.id = Preconditions.checkNotNull(id, 'id')
		this.method = Preconditions.checkNotNull(method, 'method')
	}

	new(@NonNull String id, @NonNull String method, Object registerOptions) {
		this(id, method)
		this.registerOptions = registerOptions
	}
}

/**
 * The client/registerCapability request is sent from the server to the client to register
 * for a new capability on the client side. Not all clients need to support dynamic
 * capability registration. A client opts in via the dynamicRegistration property on the
 * specific client capabilities. A client can even provide dynamic registration for
 * capability A but not for capability B (see TextDocumentClientCapabilities as an example).
 */
@JsonRpcData
class RegistrationParams {
	@NonNull
	List<Registration> registrations

	new() {
		this(new ArrayList)
	}

	new(@NonNull List<Registration> registrations) {
		this.registrations = Preconditions.checkNotNull(registrations, 'registrations')
	}
}

/**
 * A document filter denotes a document through properties like language, schema or pattern.
 * <p>
 * At least one of the properties {@link #language}, {@link #scheme}, or {@link #pattern} must be set.
 */
@JsonRpcData
class DocumentFilter {
	/**
	 * A language id, like {@code typescript}.
	 */
	String language

	/**
	 * A uri scheme, like {@code file} or {@code untitled}.
	 */
	String scheme

	/**
	 * A glob pattern, like <code>*.{ts,js}</code>.
	 * <p>
	 * Since 3.18 - support for relative patterns, which depends on the
	 * client capability {@link FiltersCapabilities#relativePatternSupport}.
	 */
	Either<String, RelativePattern> pattern

	new() {
	}

	new(String language, String scheme, Either<String, RelativePattern> pattern) {
		this.language = language
		this.scheme = scheme
		this.pattern = pattern
	}
}

/**
 * Since most of the registration options require to specify a document selector there is
 * a base interface that can be used.
 */
@JsonRpcData
class TextDocumentRegistrationOptions {
	/**
	 * A document selector to identify the scope of the registration. If set to null
	 * the document selector provided on the client side will be used.
	 */
	List<DocumentFilter> documentSelector

	new() {
	}

	new(List<DocumentFilter> documentSelector) {
		this.documentSelector = documentSelector
	}
}

/**
 * General parameters to unregister a capability.
 */
@JsonRpcData
class Unregistration {
	/**
	 * The id used to unregister the request or notification. Usually an id
	 * provided during the register request.
	 */
	@NonNull
	String id

	/**
	 * The method / capability to unregister for.
	 */
	@NonNull
	String method

	new() {
	}

	new(@NonNull String id, @NonNull String method) {
		this.id = Preconditions.checkNotNull(id, 'id')
		this.method = Preconditions.checkNotNull(method, 'method')
	}
}

/**
 * The client/unregisterCapability request is sent from the server to the client to unregister
 * a previously registered capability.
 */
@JsonRpcData
class UnregistrationParams {
	// This is a known misspelling in the spec and is intended to be fixed in LSP 4.x
	@NonNull
	List<Unregistration> unregisterations

	new() {
		this(new ArrayList)
	}

	new(@NonNull List<Unregistration> unregisterations) {
		this.unregisterations = Preconditions.checkNotNull(unregisterations, 'unregisterations')
	}
}

/**
 * Describe options to be used when registered for text document change events.
 */
@JsonRpcData
class TextDocumentChangeRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * How documents are synced to the server.
	 */
	@NonNull
	TextDocumentSyncKind syncKind

	new() {
	}

	new(@NonNull TextDocumentSyncKind syncKind) {
		this.syncKind = Preconditions.checkNotNull(syncKind, 'syncKind')
	}
}

@JsonRpcData
class TextDocumentSaveRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * The client is supposed to include the content on save.
	 */
	Boolean includeText

	new() {
	}

	new(Boolean includeText) {
		this.includeText = includeText
	}
}

@JsonRpcData
class CompletionRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * Most tools trigger completion request automatically without explicitly requesting
	 * it using a keyboard shortcut (e.g. Ctrl+Space). Typically they do so when the user
	 * starts to type an identifier. For example if the user types `c` in a JavaScript file
	 * code complete will automatically pop up present `console` besides others as a
	 * completion item. Characters that make up identifiers don't need to be listed here.
	 * <p>
	 * If code complete should automatically be trigger on characters not being valid inside
	 * an identifier (for example `.` in JavaScript) list them in `triggerCharacters`.
	 */
	List<String> triggerCharacters

	/**
	 * The server provides support to resolve additional information for a completion item.
	 */
	Boolean resolveProvider

	/**
	 * The list of all possible characters that commit a completion. This field
	 * can be used if clients don't support individual commit characters per
	 * completion item. See client capability
	 * {@link CompletionItemCapabilities#commitCharactersSupport}.
	 * <p>
	 * If a server provides both {@code allCommitCharacters} and commit characters on
	 * an individual completion item the ones on the completion item win.
	 */
	@ProtocolSince("3.2.0")
	List<String> allCommitCharacters

	/**
	 * The server supports the following {@link CompletionItem} specific capabilities.
	 */
	@ProtocolSince("3.17.0")
	CompletionItemOptions completionItem

	new() {
	}

	new(List<String> triggerCharacters, Boolean resolveProvider) {
		this.triggerCharacters = triggerCharacters
		this.resolveProvider = resolveProvider
	}
}

@JsonRpcData
class SignatureHelpRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The characters that trigger signature help automatically.
	 */
	List<String> triggerCharacters

	/**
	 * List of characters that re-trigger signature help.
	 * <p>
	 * These trigger characters are only active when signature help is already showing. All trigger characters
	 * are also counted as re-trigger characters.
	 */
	@ProtocolSince("3.15.0")
	List<String> retriggerCharacters

	new() {
	}

	new(List<String> triggerCharacters) {
		this.triggerCharacters = triggerCharacters
	}

	new(List<String> triggerCharacters, List<String> retriggerCharacters) {
		this(triggerCharacters)
		this.retriggerCharacters = retriggerCharacters
	}
}

@JsonRpcData
class CodeLensRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * Code lens has a resolve provider as well.
	 */
	Boolean resolveProvider

	new() {
	}

	new(Boolean resolveProvider) {
		this.resolveProvider = resolveProvider
	}
}

@JsonRpcData
class DocumentLinkRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * Document links have a resolve provider as well.
	 */
	Boolean resolveProvider

	new() {
	}

	new(Boolean resolveProvider) {
		this.resolveProvider = resolveProvider
	}
}

@JsonRpcData
class DocumentOnTypeFormattingRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * A character on which formatting should be triggered, like `}`.
	 */
	@NonNull
	String firstTriggerCharacter

	/**
	 * More trigger characters.
	 */
	List<String> moreTriggerCharacter

	new() {
	}

	new(@NonNull String firstTriggerCharacter) {
		this.firstTriggerCharacter = Preconditions.checkNotNull(firstTriggerCharacter, 'firstTriggerCharacter')
	}

	new(@NonNull String firstTriggerCharacter, List<String> moreTriggerCharacter) {
		this(firstTriggerCharacter)
		this.moreTriggerCharacter = moreTriggerCharacter
	}
}

/**
 * The workspace/executeCommand request is sent from the client to the server to trigger command
 * execution on the server. In most cases the server creates a WorkspaceEdit structure and applies
 * the changes to the workspace using the request workspace/applyEdit which is sent from the server
 * to the client.
 */
@JsonRpcData
class ExecuteCommandParams implements WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	/**
	 * The identifier of the actual command handler.
	 */
	@NonNull
	String command

	/**
	 * Arguments that the command should be invoked with.
	 * The arguments are typically specified when a command is returned from the server to the client.
	 * Example requests that return a command are textDocument/codeAction or textDocument/codeLens.
	 */
	List<Object> arguments

	new() {
	}

	new(@NonNull String command, List<Object> arguments) {
		this.command = Preconditions.checkNotNull(command, 'command')
		this.arguments = arguments
	}

	new(@NonNull String command, List<Object> arguments, Either<String, Integer> workDoneToken) {
		this(command, arguments)
		this.workDoneToken = workDoneToken
	}
}

/**
 * Execute command registration options.
 */
@JsonRpcData
class ExecuteCommandRegistrationOptions extends ExecuteCommandOptions {
}

/**
 * Additional data about a workspace edit.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class WorkspaceEditMetadata {
	/**
	 * Signal to the editor that this edit is a refactoring.
	 */
	Boolean isRefactoring

	new() {
	}
}

/**
 * The workspace/applyEdit request is sent from the server to the client to modify resource on the client side.
 */
@JsonRpcData
class ApplyWorkspaceEditParams {
	/**
	 * The edits to apply.
	 */
	@NonNull
	WorkspaceEdit edit

	/**
	 * An optional label of the workspace edit. This label is
	 * presented in the user interface for example on an undo
	 * stack to undo the workspace edit.
	 */
	String label

	/**
	 * Additional data about the edit.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	WorkspaceEditMetadata metadata

	new() {
	}

	new(@NonNull WorkspaceEdit edit) {
		this.edit = Preconditions.checkNotNull(edit, 'edit')
	}

	new(@NonNull WorkspaceEdit edit, String label) {
		this(edit)
		this.label = label
	}
}

/**
 * The result of the `workspace/applyEdit` request.
 * <p>
 * Referred to as {@code ApplyWorkspaceEditResult} in the LSP spec.
 */
@JsonRpcData
class ApplyWorkspaceEditResponse {
	/**
	 * Indicates whether the edit was applied or not.
	 */
	boolean applied

	/**
	 * An optional textual description for why the edit was not applied.
	 * This may be used by the server for diagnostic logging or to provide
	 * a suitable error for a request that triggered the edit.
	 */
	String failureReason

	/**
	 * Depending on the client's failure handling strategy `failedChange`
	 * might contain the index of the change that failed. This property is
	 * only available if the client signals a {@link WorkspaceEditCapabilities#failureHandling}
	 * strategy in its client capabilities.
	 */
	Integer failedChange

	new() {
	}

	new(boolean applied) {
		this.applied = applied
	}
}

/**
 * The server supports workspace folder.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class WorkspaceFoldersOptions {
	/**
	 * The server has support for workspace folders
	 */
	Boolean supported

	/**
	 * Whether the server wants to receive workspace folder
	 * change notifications.
	 * <p>
	 * If a string is provided, the string is treated as an ID
	 * under which the notification is registered on the client
	 * side. The ID can be used to unregister for these events
	 * using the `client/unregisterCapability` request.
	 */
	Either<String, Boolean> changeNotifications
}

/**
 * The workspace/workspaceFolders request is sent from the server to the client to fetch
 * the current open list of workspace folders. Returns null in the response if only a single
 * file is open in the tool. Returns an empty array if a workspace is open but no folders
 * are configured.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class WorkspaceFolder {
	/**
	 * The associated URI for this workspace folder.
	 */
	@NonNull String uri

	/**
	 * The name of the workspace folder. Used to refer to this
	 * workspace folder in the user interface.
	 */
	@NonNull String name

	new() {
	}

	new(@NonNull String uri, @NonNull String name) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.name = Preconditions.checkNotNull(name, 'name')
	}
}

/**
 * The workspace folder change event.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class WorkspaceFoldersChangeEvent {
	/**
	 * The array of added workspace folders
	 */
	@NonNull
	List<WorkspaceFolder> added = new ArrayList

	/**
	 * The array of the removed workspace folders
	 */
	@NonNull
	List<WorkspaceFolder> removed = new ArrayList

	new() {
	}

	new(@NonNull List<WorkspaceFolder> added, @NonNull List<WorkspaceFolder> removed) {
		this.added = Preconditions.checkNotNull(added, 'added')
		this.removed = Preconditions.checkNotNull(removed, 'removed')
	}
}

/**
 * The workspace/didChangeWorkspaceFolders notification is sent from the client to the server to
 * inform the server about workspace folder configuration changes. The notification is sent by
 * default if both ServerCapabilities/workspace/workspaceFolders and
 * ClientCapabilities/workspace/workspaceFolders are true; or if the server has registered to
 * receive this notification it first.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class DidChangeWorkspaceFoldersParams {
	/**
	 * The actual workspace folder change event.
	 */
	@NonNull
	WorkspaceFoldersChangeEvent event

	new() {
	}

	new(@NonNull WorkspaceFoldersChangeEvent event) {
		this.event = Preconditions.checkNotNull(event, 'event')
	}
}

/**
 * The server is interested in file notifications/requests.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileOperationsServerCapabilities {
	/**
	 * The server is interested in receiving didCreateFiles notifications.
	 */
	FileOperationOptions didCreate

	/**
	 * The server is interested in receiving willCreateFiles requests.
	 */
	FileOperationOptions willCreate

	/**
	 * The server is interested in receiving didRenameFiles notifications.
	 */
	FileOperationOptions didRename

	/**
	 * The server is interested in receiving willRenameFiles requests.
	 */
	FileOperationOptions willRename

	/**
	 * The server is interested in receiving didDeleteFiles file notifications.
	 */
	FileOperationOptions didDelete

	/**
	 * The server is interested in receiving willDeleteFiles file requests.
	 */
	FileOperationOptions willDelete

	new() {
	}
}

/**
 * The options for file operations.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileOperationOptions {
	/**
	 * The actual filters.
	 */
	@NonNull
	List<FileOperationFilter> filters = new ArrayList

	new() {
	}

	new(@NonNull List<FileOperationFilter> filters) {
		this.filters = Preconditions.checkNotNull(filters, 'filters')
	}
}

/**
 * A filter to describe in which file operation requests or notifications
 * the server is interested in.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileOperationFilter {
	/**
	 * The actual file operation pattern.
	 */
	@NonNull
	FileOperationPattern pattern

	/**
	 * A Uri like {@code file} or {@code untitled}.
	 */
	String scheme

	new() {
	}

	new(@NonNull FileOperationPattern pattern) {
		this.pattern = Preconditions.checkNotNull(pattern, 'pattern')
	}

	new(@NonNull FileOperationPattern pattern, String scheme) {
		this(pattern)
		this.scheme = scheme
	}
}

/**
 * A pattern to describe in which file operation requests or notifications
 * the server is interested in.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileOperationPattern {
	/**
	 * The glob pattern to match. Glob patterns can have the following syntax:
	 * <ul>
	 * <li>`*` to match one or more characters in a path segment
	 * <li>`?` to match on one character in a path segment
	 * <li>`**` to match any number of path segments, including none
	 * <li>`{}` to group sub patterns into an OR expression. (e.g. `**​/*.{ts,js}` matches all TypeScript and JavaScript files)
	 * <li>`[]` to declare a range of characters to match in a path segment (e.g., `example.[0-9]` to match on `example.0`, `example.1`, …)
	 * <li>`[!...]` to negate a range of characters to match in a path segment (e.g., `example.[!0-9]` to match on `example.a`, `example.b`, but not `example.0`)
	 * </ul>
	 */
	@NonNull
	String glob

	/**
	 * Whether to match files or folders with this pattern.
	 * <p>
	 * Matches both if undefined.
	 * <p>
	 * See {@link FileOperationPatternKind} for allowed values.
	 */
	String matches

	/**
	 * Additional options used during matching.
	 */
	FileOperationPatternOptions options

	new() {
	}

	new(@NonNull String glob) {
		this.glob = Preconditions.checkNotNull(glob, 'glob')
	}
}

/**
 * A pattern kind describing if a glob pattern matches a file a folder or
 * both.
 */
@ProtocolSince("3.16.0")
final class FileOperationPatternKind {
	/**
	 * The pattern matches a file only.
	 */
	public static val File = 'file'

	/**
	 * The pattern matches a folder only.
	 */
	public static val Folder = 'folder'

	private new() {
	}
}

/**
 * Matching options for the file operation pattern.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileOperationPatternOptions {
	/**
	 * The pattern should be matched ignoring casing.
	 */
	Boolean ignoreCase

	new() {
	}

	new(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase
	}
}

/**
 * The parameters sent in notifications/requests for user-initiated creation
 * of files.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CreateFilesParams {
	/**
	 * An array of all files/folders created in this operation.
	 */
	@NonNull
	List<FileCreate> files = new ArrayList

	new() {
	}

	new(@NonNull List<FileCreate> files) {
		this.files = Preconditions.checkNotNull(files, 'files')
	}
}

/**
 * Represents information on a file/folder create.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileCreate {
	/**
	 * A file:// URI for the location of the file/folder being created.
	 */
	@NonNull
	String uri

	new() {
	}

	new(@NonNull String uri) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}

/**
 * The parameters sent in notifications/requests for user-initiated renames
 * of files.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class RenameFilesParams {
	/**
	 * An array of all files/folders renamed in this operation. When a folder
	 * is renamed, only the folder will be included, and not its children.
	 */
	@NonNull
	List<FileRename> files = new ArrayList

	new() {
	}

	new(@NonNull List<FileRename> files) {
		this.files = Preconditions.checkNotNull(files, 'files')
	}
}

/**
 * Represents information on a file/folder rename.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileRename {
	/**
	 * A file:// URI for the original location of the file/folder being renamed.
	 */
	@NonNull
	String oldUri

	/**
	 * A file:// URI for the new location of the file/folder being renamed.
	 */
	@NonNull
	String newUri

	new() {
	}

	new(@NonNull String oldUri, @NonNull String newUri) {
		this.oldUri = Preconditions.checkNotNull(oldUri, 'oldUri')
		this.newUri = Preconditions.checkNotNull(newUri, 'newUri')
	}
}

/**
 * The parameters sent in notifications/requests for user-initiated deletes
 * of files.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class DeleteFilesParams {
	/**
	 * An array of all files/folders deleted in this operation.
	 */
	@NonNull
	List<FileDelete> files = new ArrayList

	new() {
	}

	new(@NonNull List<FileDelete> files) {
		this.files = Preconditions.checkNotNull(files, 'files')
	}
}

/**
 * Represents information on a file/folder delete.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class FileDelete {
	/**
	 * A file:// URI for the location of the file/folder being deleted.
	 */
	@NonNull
	String uri

	new() {
	}

	new(@NonNull String uri) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}

/**
 * The workspace/configuration request is sent from the server to the client to fetch configuration
 * settings from the client. The request can fetch several configuration settings in one roundtrip.
 * The order of the returned configuration settings correspond to the order of the passed
 * {@link ConfigurationItem}s (e.g. the first item in the response is the result for the first
 * configuration item in the params).
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class ConfigurationParams {
	@NonNull
	List<ConfigurationItem> items

	new() {
	}

	new(@NonNull List<ConfigurationItem> items) {
		this.items = Preconditions.checkNotNull(items, 'items')
	}
}

/**
 * A ConfigurationItem consists of the configuration section to ask for and an additional scope URI.
 * The configuration section asked for is defined by the server and doesn’t necessarily need to
 * correspond to the configuration store used by the client. So a server might ask for a configuration
 * {@code cpp.formatterOptions} but the client stores the configuration in an XML store layout differently.
 * It is up to the client to do the necessary conversion. If a scope URI is provided the client
 * should return the setting scoped to the provided resource. If the client for example uses
 * EditorConfig to manage its settings the configuration should be returned for the passed resource
 * URI. If the client can't provide a configuration setting for a given scope then null needs to be
 * present in the returned array.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class ConfigurationItem {
	/**
	 * The scope to get the configuration section for.
	 */
	String scopeUri

	/**
	 * The configuration section asked for.
	 */
	String section
}

/**
 * The document color request is sent from the client to the server to list all color references
 * found in a given text document. Along with the range, a color value in RGB is returned.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class DocumentColorParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

@ProtocolSince("3.6.0")
@JsonRpcData
class ColorInformation {
	/**
	 * The range in the document where this color appears.
	 */
	@NonNull
	Range range

	/**
	 * The actual color value for this color range.
	 */
	@NonNull
	Color color

	new() {
	}

	new(@NonNull Range range, @NonNull Color color) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.color = Preconditions.checkNotNull(color, 'color')
	}
}

/**
 * Represents a color in RGBA space.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class Color {
	/**
	 * The red component of this color in the range [0-1].
	 */
	double red

	/**
	 * The green component of this color in the range [0-1].
	 */
	double green

	/**
	 * The blue component of this color in the range [0-1].
	 */
	double blue

	/**
	 * The alpha component of this color in the range [0-1].
	 */
	double alpha

	new() {
	}

	new(double red, double green, double blue, double alpha) {
		this.red = red
		this.green = green
		this.blue = blue
		this.alpha = alpha
	}
}

/**
 * The color presentation request is sent from the client to the server to obtain a list of presentations
 * for a color value at a given location.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class ColorPresentationParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The color information to request presentations for.
	 */
	@NonNull
	Color color

	/**
	 * The range where the color would be inserted. Serves as a context.
	 */
	@NonNull
	Range range

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Color color, @NonNull Range range) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.color = Preconditions.checkNotNull(color, 'color')
		this.range = Preconditions.checkNotNull(range, 'range')
	}
}

@ProtocolSince("3.6.0")
@JsonRpcData
class ColorPresentation {
	/**
	 * The label of this color presentation. It will be shown on the color
	 * picker header. By default this is also the text that is inserted when selecting
	 * this color presentation.
	 */
	@NonNull
	String label

	/**
	 * An edit which is applied to a document when selecting
	 * this presentation for the color. When `null` the label is used.
	 */
	TextEdit textEdit

	/**
	 * An optional array of additional text edits that are applied when
	 * selecting this color presentation. Edits must not overlap with the main edit nor with themselves.
	 */
	List<TextEdit> additionalTextEdits

	new() {
	}

	new(@NonNull String label) {
		this.label = Preconditions.checkNotNull(label, 'label')
	}

	new(@NonNull String label, TextEdit textEdit) {
		this(label)
		this.textEdit = textEdit
	}

	new(@NonNull String label, TextEdit textEdit, List<TextEdit> additionalTextEdits) {
		this(label)
		this.textEdit = textEdit
		this.additionalTextEdits = additionalTextEdits
	}
}

/**
 * The folding range request is sent from the client to the server to return all folding
 * ranges found in a given text document.
 */
@ProtocolSince("3.10.0")
@JsonRpcData
class FoldingRangeRequestParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

/**
 * A set of predefined range kinds.
 */
@ProtocolSince("3.10.0")
final class FoldingRangeKind {
	/**
	 * Folding range for a comment
	 */
	public static val Comment = 'comment'

	/**
	 * Folding range for a imports or includes
	 */
	public static val Imports = 'imports'

	/**
	 * Folding range for a region
	 */
	public static val Region = 'region'

	private new() {}
}

/**
 * Represents a folding range.
 */
@ProtocolSince("3.10.0")
@JsonRpcData
class FoldingRange {
	/**
	 * The zero-based line number from where the folded range starts.
	 */
	int startLine

	/**
	 * The zero-based line number where the folded range ends.
	 */
	int endLine

	/**
	 * The zero-based character offset from where the folded range starts. If not defined, defaults
	 * to the length of the start line.
	 */
	Integer startCharacter

	/**
	 * The zero-based character offset before the folded range ends. If not defined, defaults to the
	 * length of the end line.
	 */
	Integer endCharacter

	/**
	 * Describes the kind of the folding range such as {@link FoldingRangeKind#Comment} or {@link FoldingRangeKind#Region}.
	 * The kind is used to categorize folding ranges and used by commands like 'Fold all comments'.
	 * See {@link FoldingRangeKind} for an enumeration of standardized kinds.
	 */
	String kind

	/**
	 * The text that the client should show when the specified range is
	 * collapsed. If not defined or not supported by the client, a default
	 * will be chosen by the client.
	 */
	@ProtocolSince("3.17.0")
	String collapsedText

	new() {
	}

	new(int startLine, int endLine) {
		this.startLine = startLine
		this.endLine = endLine
	}
}

/**
 * The parameter of a `textDocument/prepareCallHierarchy` request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyPrepareParams extends TextDocumentPositionAndWorkDoneProgressParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

/**
 * The parameter of a `callHierarchy/incomingCalls` request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyIncomingCallsParams extends WorkDoneProgressAndPartialResultParams {
	@NonNull
	CallHierarchyItem item

	new() {
	}

	new(@NonNull CallHierarchyItem item) {
		this.item = Preconditions.checkNotNull(item, 'item')
	}
}

/**
 * The parameter of a `callHierarchy/outgoingCalls` request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyOutgoingCallsParams extends WorkDoneProgressAndPartialResultParams {
	@NonNull
	CallHierarchyItem item

	new() {
	}

	new(@NonNull CallHierarchyItem item) {
		this.item = Preconditions.checkNotNull(item, 'item')
	}
}

/**
 * Represents an incoming call, e.g. a caller of a method or constructor.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyIncomingCall {
	/**
	 * The item that makes the call.
	 */
	@NonNull
	CallHierarchyItem from

	/**
	 * The range at which at which the calls appears. This is relative to the caller
	 * denoted by {@link #from}.
	 */
	@NonNull
	List<Range> fromRanges

	new() {
	}

	new(@NonNull CallHierarchyItem from, @NonNull List<Range> fromRanges) {
		this.from = Preconditions.checkNotNull(from, 'from')
		this.fromRanges = Preconditions.checkNotNull(fromRanges, 'fromRanges')
	}
}

/**
 * Represents an outgoing call, e.g. calling a getter from a method or a method from a constructor etc.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyOutgoingCall {
	/**
	 * The item that is called.
	 */
	@NonNull
	CallHierarchyItem to

	/**
	 * The range at which this item is called. This is the range relative to the caller, i.e. the {@link CallHierarchyOutgoingCallsParams#item}.
	 */
	@NonNull
	List<Range> fromRanges

	new() {
	}

	new(@NonNull CallHierarchyItem to, @NonNull List<Range> fromRanges) {
		this.to = Preconditions.checkNotNull(to, 'to')
		this.fromRanges = Preconditions.checkNotNull(fromRanges, 'fromRanges')
	}
}

/**
 * The result of a {@code textDocument/prepareCallHierarchy} request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class CallHierarchyItem {
	/**
	 * The name of the item targeted by the call hierarchy request.
	 */
	@NonNull
	String name

	/**
	 * More detail for this item, e.g the signature of a function.
	 */
	String detail

	/**
	 * The kind of this item.
	 */
	@NonNull
	SymbolKind kind

	/**
	 * Tags for this item.
	 */
	List<SymbolTag> tags

	/**
	 * The resource identifier of this item.
	 */
	@NonNull
	String uri

	/**
	 * The range enclosing this symbol not including leading/trailing whitespace but everything else
	 * like comments. This information is typically used to determine if the client's cursor is
	 * inside the symbol to reveal the symbol in the UI.
	 */
	@NonNull
	Range range

	/**
	 * The range that should be selected and revealed when this symbol is being picked, e.g the name of a function.
	 * Must be contained by the {@link CallHierarchyItem#getRange range}.
	 */
	@NonNull
	Range selectionRange

	/**
	 * A data entry field that is preserved between a call hierarchy prepare and
	 * incoming calls or outgoing calls requests.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new() {
	}

	new(@NonNull String name, @NonNull SymbolKind kind, @NonNull String uri, @NonNull Range range, @NonNull Range selectionRange) {
		this.name = Preconditions.checkNotNull(name, 'name')
		this.kind = Preconditions.checkNotNull(kind, 'kind')
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.range = Preconditions.checkNotNull(range, 'range')
		this.selectionRange = Preconditions.checkNotNull(selectionRange, 'selectionRange')
	}
}

/**
 * A parameter literal used in selection range requests.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class SelectionRangeParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The positions inside the text document.
	 */
	@NonNull
	List<Position> positions

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull List<Position> positions) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.positions = Preconditions.checkNotNull(positions, 'positions')
	}
}

/**
 * Selection range options.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class SelectionRangeOptions extends AbstractWorkDoneProgressOptions {
}

/**
 * Selection range registration options.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class SelectionRangeRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * A selection range represents a part of a selection hierarchy. A selection range
 * may have a parent selection range that contains it.
 */
@ProtocolSince("3.15.0")
@JsonRpcData
class SelectionRange {
	/**
	 * The range of this selection range.
	 */
	@NonNull
	Range range

	/**
	 * The parent selection range containing this range. Therefore `parent.range` must contain {@link #range}.
	 */
	SelectionRange parent

	new() {
	}

	new(@NonNull Range range, SelectionRange parent) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.parent = parent
	}
}

/**
 * Hover options.
 */
@JsonRpcData
class HoverOptions extends AbstractWorkDoneProgressOptions {
}

/**
 * Hover registration options.
 */
@JsonRpcData
class HoverRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
}

/**
 * The hover request is sent from the client to the server to request hover information at a given
 * text document position.
 */
@JsonRpcData
class HoverParams extends TextDocumentPositionAndWorkDoneProgressParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

@ProtocolSince("3.14.0")
@JsonRpcData
class DeclarationOptions extends AbstractWorkDoneProgressOptions {
}

@ProtocolSince("3.14.0")
@JsonRpcData
class DeclarationRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * The go to declaration request is sent from the client to the server to resolve the declaration
 * location of a symbol at a given text document position.
 */
@ProtocolSince("3.14.0")
@JsonRpcData
class DeclarationParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

@JsonRpcData
class DefinitionOptions extends AbstractWorkDoneProgressOptions {
}

@JsonRpcData
class DefinitionRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
}

/**
 * The go to definition request is sent from the client to the server to resolve the definition
 * location of a symbol at a given text document position.
 */
@JsonRpcData
class DefinitionParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

@ProtocolSince("3.6.0")
@JsonRpcData
class TypeDefinitionOptions extends AbstractWorkDoneProgressOptions {
}

@ProtocolSince("3.6.0")
@JsonRpcData
class TypeDefinitionRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * The go to type definition request is sent from the client to the server to resolve the type definition
 * location of a symbol at a given text document position.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class TypeDefinitionParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

@ProtocolSince("3.6.0")
@JsonRpcData
class ImplementationOptions extends AbstractWorkDoneProgressOptions {
}

@ProtocolSince("3.6.0")
@JsonRpcData
class ImplementationRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * The go to implementation request is sent from the client to the server to resolve the implementation
 * location of a symbol at a given text document position.
 */
@ProtocolSince("3.6.0")
@JsonRpcData
class ImplementationParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

@JsonRpcData
class DocumentHighlightOptions extends AbstractWorkDoneProgressOptions {
}

@JsonRpcData
class DocumentHighlightRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
}

/**
 * The document highlight request is sent from the client to the server to resolve document highlights
 * for a given text document position.
 */
@JsonRpcData
class DocumentHighlightParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

/**
 * Moniker options.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class MonikerOptions extends AbstractWorkDoneProgressOptions {
}

/**
 * Moniker registration options.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class MonikerRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
}

/**
 * The moniker request is sent from the client to the server to get the symbol monikers for a given text document position.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class MonikerParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position) {
		super(textDocument, position)
	}
}

/**
 * Moniker uniqueness level to define scope of the moniker.
 */
@ProtocolSince("3.16.0")
final class UniquenessLevel {
	/**
	 * The moniker is only unique inside a document
	 */
	public static val Document = 'document'

	/**
	 * The moniker is unique inside a project for which a dump got created
	 */
	public static val Project = 'project'

	/**
	 * The moniker is unique inside the group to which a project belongs
	 */
	public static val Group = 'group'

	/**
	 * The moniker is unique inside the moniker scheme.
	 */
	public static val Scheme = 'scheme'

	/**
	 * The moniker is globally unique
	 */
	public static val Global = 'global'

	private new() {
	}
}

/**
 * The moniker kind.
 */
@ProtocolSince("3.16.0")
final class MonikerKind {
	/**
	 * The moniker represents a symbol that is imported into a project
	 */
	public static val Import = 'import'

	/**
	 * The moniker represents a symbol that is exported from a project
	 */
	public static val Export = 'export'

	/**
	 * The moniker represents a symbol that is local to a project (e.g. a local
	 * variable of a function, a class not visible outside the project, ...)
	 */
	public static val Local = 'local'

	private new() {
	}
}

/**
 * Moniker definition to match LSIF 0.5 moniker definition.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class Moniker {
	/**
	 * The scheme of the moniker. For example tsc or .Net
	 */
	@NonNull
	String scheme

	/**
	 * The identifier of the moniker. The value is opaque in LSIF however
	 * schema owners are allowed to define the structure if they want.
	 */
	@NonNull
	String identifier

	/**
	 * The scope in which the moniker is unique. Values are taken from {@link UniquenessLevel}.
	 */
	@NonNull
	String unique

	/**
	 * The moniker kind if known. Values are taken from {@link MonikerKind}.
	 */
	String kind

	new() {
	}

	new(@NonNull String scheme, @NonNull String identifier, @NonNull String unique) {
		this.scheme = Preconditions.checkNotNull(scheme, 'scheme')
		this.identifier = Preconditions.checkNotNull(identifier, 'identifier')
		this.unique = Preconditions.checkNotNull(unique, 'unique')
	}

	new(@NonNull String scheme, @NonNull String identifier, @NonNull String unique, String kind) {
		this(scheme, identifier, unique)
		this.kind = kind
	}
}

/**
 * The {@code window/workDoneProgress/create} request is sent from the server to the client to ask the client to create a work done progress.
 */
@JsonRpcData
class WorkDoneProgressCreateParams {
	/**
	 * The token to be used to report progress.
	 */
	@NonNull
	Either<String, Integer> token

	new() {
	}

	new(@NonNull Either<String, Integer> token) {
		this.token = Preconditions.checkNotNull(token, 'token')
	}
}

/**
 * The {@code window/workDoneProgress/cancel} notification is sent from the client to the server to cancel a progress initiated on the server side using the
 * {@code window/workDoneProgress/create}.
 */
@JsonRpcData
class WorkDoneProgressCancelParams {
	/**
	 * The token to be used to report progress.
	 */
	@NonNull
	Either<String, Integer> token

	new() {
	}

	new(@NonNull Either<String, Integer> token) {
		this.token = Preconditions.checkNotNull(token, 'token')
	}
}

/**
 * Params to show a document.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class ShowDocumentParams {
	/**
	 * The document uri to show.
	 */
	@NonNull
	String uri

	/**
	 * Indicates to show the resource in an external program.
	 * To show for example <a href="https://www.eclipse.org/">
	 * https://www.eclipse.org/</a>
	 * in the default WEB browser set to {@code true}.
	 */
	Boolean external

	/**
	 * An optional property to indicate whether the editor
	 * showing the document should take focus or not.
	 * Clients might ignore this property if an external
	 * program is started.
	 */
	Boolean takeFocus

	/**
	 * An optional selection range if the document is a text
	 * document. Clients might ignore the property if an
	 * external program is started or the file is not a text
	 * file.
	 */
	Range selection

	new() {
	}

	new(@NonNull String uri) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}

/**
 * The result of an show document request.
 */
@ProtocolSince("3.16.0")
@JsonRpcData
class ShowDocumentResult {
	/**
	 * A boolean indicating if the show was successful.
	 */
	boolean success

	new() {
	}

	new(boolean success) {
		this.success = success
	}
}

/**
 * Capabilities specific to the {@code textDocument/inlayHint} request.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlayHintCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Indicates which properties a client can resolve lazily on a inlay hint.
	 */
	InlayHintResolveSupportCapabilities resolveSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Indicates which properties a client can resolve lazily on a inlay hint.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlayHintResolveSupportCapabilities {
	/**
	 * The properties that a client can resolve lazily.
	 */
	@NonNull
	List<String> properties

	new() {
		this.properties = new ArrayList
	}

	new(@NonNull List<String> properties) {
		this.properties = Preconditions.checkNotNull(properties, 'properties')
	}
}

/**
 * Inlay hint options used during static or dynamic registration.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlayHintRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	/**
	 * The server provides support to resolve additional
	 * information for an inlay hint item.
	 */
	Boolean resolveProvider

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * A parameter literal used in inlay hint requests.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlayHintParams implements WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The visible document range for which inlay hints should be computed.
	 */
	@NonNull
	Range range

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Range range) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.range = Preconditions.checkNotNull(range, 'range')
	}
}

/**
 * Inlay hint information.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlayHint {
	/**
	 * The position of this hint.
	 */
	@NonNull
	Position position

	/**
	 * The label of this hint. A human readable string or an array of
	 * {@link InlayHintLabelPart} label parts.
	 * <p>
	 * *Note* that neither the string nor the label part can be empty.
	 */
	@NonNull
	Either<String, List<InlayHintLabelPart>> label

	/**
	 * The kind of this hint. Can be omitted in which case the client
	 * should fall back to a reasonable default.
	 */
	InlayHintKind kind

	/**
	 * Optional text edits that are performed when accepting this inlay hint.
	 * <p>
	 * *Note* that edits are expected to change the document so that the inlay
	 * hint (or its nearest variant) is now part of the document and the inlay
	 * hint itself is now obsolete.
	 * <p>
	 * Depending on the client capability {@link InlayHintCapabilities#resolveSupport} clients
	 * might resolve this property late using the resolve request.
	 */
	List<TextEdit> textEdits

	/**
	 * The tooltip text when you hover over this item.
	 * <p>
	 * Depending on the client capability {@link InlayHintCapabilities#resolveSupport} clients
	 * might resolve this property late using the resolve request.
	 */
	Either<String, MarkupContent> tooltip

	/**
	 * Render padding before the hint.
	 * <p>
	 * Note: Padding should use the editor's background color, not the
	 * background color of the hint itself. That means padding can be used
	 * to visually align/separate an inlay hint.
	 */
	Boolean paddingLeft

	/**
	 * Render padding after the hint.
	 * <p>
	 * Note: Padding should use the editor's background color, not the
	 * background color of the hint itself. That means padding can be used
	 * to visually align/separate an inlay hint.
	 */
	Boolean paddingRight

	/**
	 * A data entry field that is preserved on a inlay hint between
	 * a {@code textDocument/inlayHint} and a {@code inlayHint/resolve} request.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object data

	new() {
	}

	new(@NonNull Position position, @NonNull Either<String, List<InlayHintLabelPart>> label) {
		this.position = Preconditions.checkNotNull(position, 'position')
		this.label = Preconditions.checkNotNull(label, 'label')
	}
}

/**
 * An inlay hint label part allows for interactive and composite labels
 * of inlay hints.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlayHintLabelPart {
	/**
	 * The value of this label part.
	 */
	@NonNull
	String value

	/**
	 * The tooltip text when you hover over this label part. Depending on
	 * the client capability {@link InlayHintCapabilities#resolveSupport} clients might resolve
	 * this property late using the resolve request.
	 */
	Either<String, MarkupContent> tooltip

	/**
	 * An optional source code location that represents this
	 * label part.
	 * <p>
	 * The editor will use this location for the hover and for code navigation
	 * features: This part will become a clickable link that resolves to the
	 * definition of the symbol at the given location (not necessarily the
	 * location itself), it shows the hover that shows at the given location,
	 * and it shows a context menu with further code navigation commands.
	 * <p>
	 * Depending on the client capability {@link InlayHintCapabilities#resolveSupport} clients
	 * might resolve this property late using the resolve request.
	 */
	Location location

	/**
	 * An optional command for this label part.
	 * <p>
	 * Depending on the client capability {@link InlayHintCapabilities#resolveSupport} clients
	 * might resolve this property late using the resolve request.
	 */
	Command command

	new() {
	}

	new(@NonNull String value) {
		this.value = Preconditions.checkNotNull(value, 'value')
	}
}

/**
 * Client workspace capabilities specific to inlay hints.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlayHintWorkspaceCapabilities {
	/**
	 * Whether the client implementation supports a refresh request sent from
	 * the server to the client.
	 * <p>
	 * Note that this event is global and will force the client to refresh all
	 * inlay hints currently shown. It should be used with absolute care and
	 * is useful for situations where a server for example detects a project wide
	 * change that requires such a calculation.
	 */
	Boolean refreshSupport

	new() {
	}

	new(Boolean refreshSupport) {
		this.refreshSupport = refreshSupport
	}
}

/**
 * Client capabilities specific to inline values.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValueCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Inline value options used during static or dynamic registration.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValueRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * A parameter literal used in inline value requests.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValueParams implements WorkDoneProgressParams {
	/**
	 * An optional token that a server can use to report work done progress.
	 */
	Either<String, Integer> workDoneToken

	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The visible document range for which inlay hints should be computed.
	 */
	@NonNull
	Range range

	/**
	 * Additional information about the context in which inline values were
	 * requested.
	 */
	@NonNull
	InlineValueContext context

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Range range, @NonNull InlineValueContext context) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
		this.range = Preconditions.checkNotNull(range, 'range')
		this.context = Preconditions.checkNotNull(context, 'context')
	}
}

/**
 * Additional information about the context in which inline values were
 * requested.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValueContext {
	/**
	 * The stack frame (as a DAP Id) where the execution has stopped.
	 */
	int frameId

	/**
	 * The document range where execution has stopped.
	 * Typically the end position of the range denotes the line where the
	 * inline values are shown.
	 */
	@NonNull
	Range stoppedLocation

	new() {
	}

	new(int frameId, @NonNull Range stoppedLocation) {
		this.frameId = frameId
		this.stoppedLocation = Preconditions.checkNotNull(stoppedLocation, 'stoppedLocation')
	}
}

/**
 * Provide inline value as text.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValueText {
	/**
	 * The document range for which the inline value applies.
	 */
	@NonNull
	Range range

	/**
	 * The text of the inline value.
	 */
	@NonNull
	String text

	new() {
	}

	new(@NonNull Range range, @NonNull String text) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.text = Preconditions.checkNotNull(text, 'text')
	}
}

/**
 * Provide inline value through a variable lookup.
 * <p>
 * If only a range is specified, the variable name will be extracted from
 * the underlying document.
 * <p>
 * An optional variable name can be used to override the extracted name.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValueVariableLookup {
	/**
	 * The document range for which the inline value applies.
	 * The range is used to extract the variable name from the underlying
	 * document.
	 */
	@NonNull
	Range range

	/**
	 * If specified the name of the variable to look up.
	 */
	String variableName

	/**
	 * How to perform the lookup.
	 */
	boolean caseSensitiveLookup

	new() {
	}

	new(@NonNull Range range, boolean caseSensitiveLookup) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.caseSensitiveLookup = Preconditions.checkNotNull(caseSensitiveLookup, 'caseSensitiveLookup')
	}

	new(@NonNull Range range, boolean caseSensitiveLookup, String variableName) {
		this(range, caseSensitiveLookup)
		this.variableName = Preconditions.checkNotNull(variableName, 'variableName')
	}
}

/**
 * Provide an inline value through an expression evaluation.
 * <p>
 * If only a range is specified, the expression will be extracted from the
 * underlying document.
 * <p>
 * An optional expression can be used to override the extracted expression.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValueEvaluatableExpression {
	/**
	 * The document range for which the inline value applies.
	 * The range is used to extract the evaluatable expression from the
	 * underlying document.
	 */
	@NonNull
	Range range

	/**
	 * If specified the name of the variable to look up.
	 */
	String expression

	new() {
	}

	new(@NonNull Range range) {
		this.range = Preconditions.checkNotNull(range, 'range')
	}

	new(@NonNull Range range, String expression) {
		this(range)
		this.expression = Preconditions.checkNotNull(expression, 'expression')
	}
}

/**
 * Inline value information can be provided by different means:
 * - directly as a text value ({@link InlineValueText}).
 * - as a name to use for a variable lookup ({@link InlineValueVariableLookup})
 * - as an evaluatable expression ({@link InlineValueEvaluatableExpression})
 * The InlineValue types combines all inline value types into one type.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValue extends Either3<InlineValueText, InlineValueVariableLookup, InlineValueEvaluatableExpression> {
	new(@NonNull InlineValueText inlineValueText) {
		super(Preconditions.checkNotNull(inlineValueText, 'inlineValueText'), null)
	}

	new(@NonNull InlineValueVariableLookup inlineValueVariableLookup) {
		super(null, Either.<InlineValueVariableLookup, InlineValueEvaluatableExpression>forLeft(Preconditions.checkNotNull(inlineValueVariableLookup, 'inlineValueVariableLookup')))
	}

	new(@NonNull InlineValueEvaluatableExpression inlineValueEvaluatableExpression) {
		super(null, Either.<InlineValueVariableLookup, InlineValueEvaluatableExpression>forRight(Preconditions.checkNotNull(inlineValueEvaluatableExpression, 'inlineValueEvaluatableExpression')))
	}

	def InlineValueText getInlineValueText() {
		super.getFirst
	}

	def boolean isInlineValueText() {
		super.isFirst
	}

	def InlineValueVariableLookup getInlineValueVariableLookup() {
		super.getSecond
	}

	def boolean isInlineValueVariableLookup() {
		super.isSecond
	}

	def InlineValueEvaluatableExpression getInlineValueEvaluatableExpression() {
		super.getThird
	}

	def boolean isInlineValueEvaluatableExpression() {
		super.isThird
	}
}

/**
 * Client workspace capabilities specific to inline values.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class InlineValueWorkspaceCapabilities {
	/**
	 * Whether the client implementation supports a refresh request sent from
	 * the server to the client.
	 * <p>
	 * Note that this event is global and will force the client to refresh all
	 * inline values currently shown. It should be used with absolute care and
	 * is useful for situations where a server for example detect a project wide
	 * change that requires such a calculation.
	 */
	Boolean refreshSupport

	new() {
	}

	new(Boolean refreshSupport) {
		this.refreshSupport = refreshSupport
	}
}

/**
 * A set of predefined position encoding kinds indicating how
 * positions are encoded, specifically what column offsets mean.
 */
@ProtocolSince("3.17.0")
final class PositionEncodingKind {
	/**
	 * Character offsets count UTF-8 code units.
	 */
	public static val UTF8 = 'utf-8'

	/**
	 * Character offsets count UTF-16 code units.
	 * <p>
	 * This is the default and must always be supported
	 * by servers.
	 */
	public static val UTF16 = 'utf-16'

	/**
	 * Character offsets count UTF-32 code units.
	 * <p>
	 * Implementation note: these are the same as Unicode code points,
	 * so this kind may also be used for an encoding-agnostic
	 * representation of character offsets.
	 */
	public static val UTF32 = 'utf-32'

	private new() {}
}

/**
 * Client capabilities specific to diagnostic pull requests.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DiagnosticCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * Whether the client supports related documents for document diagnostic pulls.
	 */
	Boolean relatedDocumentSupport

	/**
	 * Whether the client accepts diagnostics with related information.
	 */
	Boolean relatedInformation

	/**
	 * Whether the client supports the {@link Diagnostic#tags} property.
	 * Clients supporting tags have to handle unknown tags gracefully.
	 */
	DiagnosticsTagSupport tagSupport

	/**
	 * Whether the client supports the {@link Diagnostic#codeDescription} property.
	 */
	Boolean codeDescriptionSupport

	/**
	 * Whether the client supports the {@link Diagnostic#data} property.
	 */
	Boolean dataSupport

	/**
	 * Whether the client supports {@link MarkupContent} in diagnostic messages.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Boolean markupMessageSupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		this.dynamicRegistration = dynamicRegistration
	}

	new(Boolean dynamicRegistration, Boolean relatedDocumentSupport) {
		this(dynamicRegistration)
		this.relatedDocumentSupport = relatedDocumentSupport
	}
}

/**
 * Server capabilities specific to the diagnostic pull model.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class DiagnosticServerCapabilities {

	/**
	 * Whether the server supports {@link MarkupContent} in diagnostic messages.
	 */
	Boolean markupMessageSupport

	new() {
	}
}

/**
 * Diagnostic registration options.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DiagnosticRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	/**
	 * An optional identifier under which the diagnostics are
	 * managed by the client.
	 */
	String identifier

	/**
	 * Whether the language has inter file dependencies meaning that
	 * editing code in one file can result in a different diagnostic
	 * set in another file. Inter file dependencies are common for
	 * most programming languages and typically uncommon for linters.
	 */
	boolean interFileDependencies

	/**
	 * The server provides support for workspace diagnostics as well.
	 */
	boolean workspaceDiagnostics

	new() {
	}

	new(String id) {
		this.id = id
	}

	new(boolean interFileDependencies, boolean workspaceDiagnostics) {
		this.interFileDependencies = interFileDependencies
		this.workspaceDiagnostics = workspaceDiagnostics
	}
}

/**
 * Parameters of the document diagnostic request.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DocumentDiagnosticParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The additional identifier provided during registration.
	 */
	String identifier

	/**
	 * The result id of a previous response if provided.
	 */
	String previousResultId

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument) {
		this.textDocument = Preconditions.checkNotNull(textDocument, 'textDocument')
	}
}

/**
 * The result of a document diagnostic pull request. A report can
 * either be a full report containing all diagnostics for the
 * requested document or a unchanged report indicating that nothing
 * has changed in terms of diagnostics in comparison to the last
 * pull request.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DocumentDiagnosticReport extends Either<RelatedFullDocumentDiagnosticReport, RelatedUnchangedDocumentDiagnosticReport> {
	new(@NonNull RelatedFullDocumentDiagnosticReport relatedFullDocumentDiagnosticReport) {
		super(Preconditions.checkNotNull(relatedFullDocumentDiagnosticReport, 'relatedFullDocumentDiagnosticReport'), null)
	}

	new(@NonNull RelatedUnchangedDocumentDiagnosticReport relatedUnchangedDocumentDiagnosticReport) {
		super(null, Preconditions.checkNotNull(relatedUnchangedDocumentDiagnosticReport, 'relatedUnchangedDocumentDiagnosticReport'))
	}

	def RelatedFullDocumentDiagnosticReport getRelatedFullDocumentDiagnosticReport() {
		super.getLeft
	}

	def boolean isRelatedFullDocumentDiagnosticReport() {
		super.isLeft
	}

	def RelatedUnchangedDocumentDiagnosticReport getRelatedUnchangedDocumentDiagnosticReport() {
		super.getRight
	}

	def boolean isRelatedUnchangedDocumentDiagnosticReport() {
		super.isRight
	}
}

/**
 * The document diagnostic report kinds.
 */
@ProtocolSince("3.17.0")
final class DocumentDiagnosticReportKind {
	/**
	 * A diagnostic report with a full
	 * set of problems.
	 */
	public static val Full = 'full'

	/**
	 * A report indicating that the last
	 * returned report is still accurate.
	 */
	public static val Unchanged = 'unchanged'

	private new() {}
}

/**
 * A diagnostic report with a full set of problems.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class FullDocumentDiagnosticReport {
	/**
	 * A full document diagnostic report.
	 */
	@NonNull
	val kind = DocumentDiagnosticReportKind.Full

	/**
	 * An optional result id. If provided it will
	 * be sent on the next diagnostic request for the
	 * same document.
	 */
	String resultId

	/**
	 * The actual items.
	 */
	@NonNull
	List<Diagnostic> items


	new() {
		this.items = new ArrayList
	}

	new(@NonNull List<Diagnostic> items) {
		this.items = Preconditions.checkNotNull(items, 'items')
	}
}

/**
 * A diagnostic report indicating that the last returned
 * report is still accurate.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class UnchangedDocumentDiagnosticReport {
	/**
	 * A document diagnostic report indicating
	 * no changes to the last result. A server can
	 * only return `unchanged` if result ids are
	 * provided.
	 */
	@NonNull
	val kind = DocumentDiagnosticReportKind.Unchanged

	/**
	 * A result id which will be sent on the next
	 * diagnostic request for the same document.
	 */
	@NonNull
	String resultId

	new() {
	}

	new(@NonNull String resultId) {
		this.resultId = Preconditions.checkNotNull(resultId, 'resultId')
	}
}

/**
 * A full diagnostic report with a set of related documents.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class RelatedFullDocumentDiagnosticReport extends FullDocumentDiagnosticReport {
	/**
	 * Diagnostics of related documents. This information is useful
	 * in programming languages where code in a file A can generate
	 * diagnostics in a file B which A depends on. An example of
	 * such a language is C/C++ where marco definitions in a file
	 * a.cpp and result in errors in a header file b.hpp.
	 */
	Map<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>> relatedDocuments

	new() {
	}

	new(@NonNull List<Diagnostic> items) {
		super(items)
	}
}

/**
 * An unchanged diagnostic report with a set of related documents.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class RelatedUnchangedDocumentDiagnosticReport extends UnchangedDocumentDiagnosticReport {
	/**
	 * Diagnostics of related documents. This information is useful
	 * in programming languages where code in a file A can generate
	 * diagnostics in a file B which A depends on. An example of
	 * such a language is C/C++ where marco definitions in a file
	 * a.cpp and result in errors in a header file b.hpp.
	 */
	Map<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>> relatedDocuments

	new() {
	}

	new(@NonNull String resultId) {
		super(resultId)
	}
}

/**
 * A partial result for a document diagnostic report.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DocumentDiagnosticReportPartialResult {
	@NonNull
	Map<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>> relatedDocuments

	new(@NonNull Map<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>> relatedDocuments) {
		this.relatedDocuments = Preconditions.checkNotNull(relatedDocuments, 'relatedDocuments')
	}
}

/**
 * Cancellation data returned from a diagnostic request.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DiagnosticServerCancellationData {
	boolean retriggerRequest

	new() {
	}

	new(boolean retriggerRequest) {
		this.retriggerRequest = retriggerRequest
	}
}

/**
 * Parameters of the workspace diagnostic request.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceDiagnosticParams extends WorkDoneProgressAndPartialResultParams {
	/**
	 * The additional identifier provided during registration.
	 */
	String identifier

	/**
	 * The currently known diagnostic reports with their
	 * previous result ids.
	 */
	@NonNull
	List<PreviousResultId> previousResultIds

	new() {
	}

	new(@NonNull List<PreviousResultId> previousResultIds) {
		this.previousResultIds = Preconditions.checkNotNull(previousResultIds, 'previousResultIds')
	}
}

/**
 * A previous result id in a workspace pull request.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class PreviousResultId {
	/**
	 * The URI for which the client knows a
	 * result id.
	 */
	@NonNull
	String uri

	/**
	 * The value of the previous result id.
	 */
	@NonNull
	String value

	new() {
	}

	new(@NonNull String uri, @NonNull String value) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.value = Preconditions.checkNotNull(value, 'value')
	}
}

/**
 * A workspace diagnostic report.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceDiagnosticReport {
	@NonNull
	@JsonAdapter(WorkspaceDocumentDiagnosticReportListAdapter)
	List<WorkspaceDocumentDiagnosticReport> items

	new() {
	}

	new(@NonNull List<WorkspaceDocumentDiagnosticReport> items) {
		this.items = Preconditions.checkNotNull(items, 'items')
	}
}

/**
 * A full document diagnostic report for a workspace diagnostic result.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceFullDocumentDiagnosticReport extends FullDocumentDiagnosticReport {
	/**
	 * The URI for which diagnostic information is reported.
	 */
	@NonNull
	String uri

	/**
	 * The version number for which the diagnostics are reported.
	 * If the document is not marked as open {@code null} can be provided.
	 */
	Integer version

	new() {
	}

	new(@NonNull List<Diagnostic> items, @NonNull String uri, Integer version) {
		super(items)
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.version = version
	}
}

/**
 * An unchanged document diagnostic report for a workspace diagnostic result.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceUnchangedDocumentDiagnosticReport extends UnchangedDocumentDiagnosticReport {
	/**
	 * The URI for which diagnostic information is reported.
	 */
	@NonNull
	String uri

	/**
	 * The version number for which the diagnostics are reported.
	 * If the document is not marked as open `null` can be provided.
	 */
	Integer version

	new() {
	}

	new(@NonNull String resultId, @NonNull String uri, Integer version) {
		super(resultId)
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.version = version
	}
}

/**
 * A workspace diagnostic document report.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceDocumentDiagnosticReport extends Either<WorkspaceFullDocumentDiagnosticReport, WorkspaceUnchangedDocumentDiagnosticReport> {
	new(@NonNull WorkspaceFullDocumentDiagnosticReport workspaceFullDocumentDiagnosticReport) {
		super(Preconditions.checkNotNull(workspaceFullDocumentDiagnosticReport, 'workspaceFullDocumentDiagnosticReport'), null)
	}

	new(@NonNull WorkspaceUnchangedDocumentDiagnosticReport workspaceUnchangedDocumentDiagnosticReport) {
		super(null, Preconditions.checkNotNull(workspaceUnchangedDocumentDiagnosticReport, 'workspaceUnchangedDocumentDiagnosticReport'))
	}

	def WorkspaceFullDocumentDiagnosticReport getWorkspaceFullDocumentDiagnosticReport() {
		super.getLeft
	}

	def boolean isWorkspaceFullDocumentDiagnosticReport() {
		super.isLeft
	}

	def WorkspaceUnchangedDocumentDiagnosticReport getWorkspaceUnchangedDocumentDiagnosticReport() {
		super.getRight
	}

	def boolean isWorkspaceUnchangedDocumentDiagnosticReport() {
		super.isRight
	}
}

/**
 * A partial result for a workspace diagnostic report.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class WorkspaceDiagnosticReportPartialResult {
	@NonNull
	@JsonAdapter(WorkspaceDocumentDiagnosticReportListAdapter)
	List<WorkspaceDocumentDiagnosticReport> items

	new() {
	}

	new(@NonNull List<WorkspaceDocumentDiagnosticReport> items) {
		this.items = Preconditions.checkNotNull(items, 'items')
	}
}

/**
 * Workspace client capabilities specific to diagnostic pull requests.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DiagnosticWorkspaceCapabilities {
	/**
	 * Whether the client implementation supports a refresh request sent from
	 * the server to the client.
	 * <p>
	 * Note that this event is global and will force the client to refresh all
	 * pulled diagnostics currently shown. It should be used with absolute care
	 * and is useful for situations where a server for example detects a project
	 * wide change that requires such a calculation.
	 */
	Boolean refreshSupport

	new() {
	}

	new(Boolean refreshSupport) {
		this.refreshSupport = refreshSupport
	}
}

/**
 * A notebook document.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocument {
	/**
	 * The notebook document's URI.
	 */
	@NonNull
	String uri

	/**
	 * The type of the notebook.
	 */
	@NonNull
	String notebookType

	/**
	 * The version number of this document (it will increase after each
	 * change, including undo/redo).
	 */
	int version

	/**
	 * Additional metadata stored with the notebook
	 * document.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object metadata

	/**
	 * The cells of a notebook.
	 */
	@NonNull
	List<NotebookCell> cells

	new() {
		this.cells = new ArrayList
	}

	new(@NonNull String uri, @NonNull String notebookType, int version, @NonNull List<NotebookCell> cells) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
		this.notebookType = Preconditions.checkNotNull(notebookType, 'notebookType')
		this.version = version
		this.cells = Preconditions.checkNotNull(cells, 'cells')
	}
}

/**
 * A notebook cell.
 * <p>
 * A cell's document URI must be unique across ALL notebook
 * cells and can therefore be used to uniquely identify a
 * notebook cell or the cell's text document.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookCell {
	/**
	 * The cell's kind
	 */
	@NonNull
	NotebookCellKind kind

	/**
	 * The URI of the cell's text document
	 * content.
	 */
	@NonNull
	String document

	/**
	 * Additional metadata stored with the cell.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object metadata

	/**
	 * Additional execution summary information
	 * if supported by the client.
	 */
	ExecutionSummary executionSummary

	new() {
	}

	new(@NonNull NotebookCellKind kind, @NonNull String document) {
		this.kind = Preconditions.checkNotNull(kind, 'kind')
		this.document = Preconditions.checkNotNull(document, 'document')
	}
}

@ProtocolSince("3.17.0")
@JsonRpcData
class ExecutionSummary {
	/**
	 * A strict monotonically increasing value
	 * indicating the execution order of a cell
	 * inside a notebook.
	 */
	int executionOrder

	/**
	 * Whether the execution was successful or
	 * not if known by the client.
	 */
	Boolean success

	new() {
	}

	new(int executionOrder) {
		this.executionOrder = executionOrder
	}

	new(int executionOrder, Boolean success) {
		this(executionOrder)
		this.success = success
	}
}

/**
 * A notebook cell text document filter denotes a cell text
 * document by different properties.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookCellTextDocumentFilter {
	/**
	 * A filter that matches against the notebook
	 * containing the notebook cell. If a string
	 * value is provided it matches against the
	 * notebook type. '*' matches every notebook.
	 */
	@NonNull
	Either<String, NotebookDocumentFilter> notebook

	/**
	 * A language id like `python`.
	 * <p>
	 * Will be matched against the language id of the
	 * notebook cell document. '*' matches every language.
	 */
	String language

	new() {
	}

	new(@NonNull Either<String, NotebookDocumentFilter> notebook) {
		this.notebook = Preconditions.checkNotNull(notebook, 'notebook')
	}

	new(@NonNull Either<String, NotebookDocumentFilter> notebook, String language) {
		this(notebook)
		this.language = language
	}
}

/**
 * A notebook document filter denotes a notebook document by
 * different properties.
 * <p>
 * At least one of either {@link #notebookType}, {@link #scheme},
 * or {@link #pattern} is required.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentFilter {
	/**
	 * The type of the enclosing notebook.
	 */
	String notebookType

	/**
	 * A Uri scheme, like `file` or `untitled`.
	 */
	String scheme

	/**
	 * A glob pattern.
	 */
	Either<String, RelativePattern> pattern

	new() {
	}
}

/**
 * Notebook specific client capabilities.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentSyncClientCapabilities extends DynamicRegistrationCapabilities {
	/**
	 * The client supports sending execution summary data per cell.
	 */
	Boolean executionSummarySupport

	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}

	new(Boolean dynamicRegistration, Boolean executionSummarySupport) {
		this(dynamicRegistration)
		this.executionSummarySupport = executionSummarySupport
	}
}

/**
 * The notebooks to be synced.
 * <p>
 * At least one of either {@link #notebook} or {@link #cells} is required.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookSelector {
	/**
	 * The notebook to be synced. If a string
	 * value is provided it matches against the
	 * notebook type. '*' matches every notebook.
	 */
	Either<String, NotebookDocumentFilter> notebook

	/**
	 * The cells of the matching notebook to be synced.
	 */
	List<NotebookSelectorCell> cells

	new() {
	}
}

/**
 * The cell of the matching notebook to be synced.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookSelectorCell {
	/**
	 * The cells of the matching notebook to be synced.
	 */
	@NonNull
	String language

	new() {
	}

	new(@NonNull String language) {
		this.language = Preconditions.checkNotNull(language, 'language')
	}
}

/**
 * Options specific to a notebook plus
 * its cells to be synced to the server.
 * <p>
 * If a selector provides a notebook document
 * filter but no cell selector all cells of a
 * matching notebook document will be synced.
 * <p>
 * If a selector provides no notebook document
 * filter but only a cell selector all notebook
 * documents that contain at least one matching
 * cell will be synced.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentSyncOptions {
	/**
	 * The notebooks to be synced
	 */
	@NonNull
	List<NotebookSelector> notebookSelector

	/**
	 * Whether save notification should be forwarded to
	 * the server. Will only be honored if mode === `notebook`.
	 */
	Boolean save

	new() {
		this.notebookSelector = new ArrayList
	}

	new(@NonNull List<NotebookSelector> notebookSelector) {
		this.notebookSelector = Preconditions.checkNotNull(notebookSelector, 'notebookSelector')
	}

	new(@NonNull List<NotebookSelector> notebookSelector, Boolean save) {
		this(notebookSelector)
		this.save = save
	}
}

/**
 * Registration options specific to a notebook.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentSyncRegistrationOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	/**
	 * The notebooks to be synced
	 */
	@NonNull
	List<NotebookSelector> notebookSelector

	/**
	 * Whether save notification should be forwarded to
	 * the server. Will only be honored if mode === `notebook`.
	 */
	Boolean save

	new() {
		this.notebookSelector = new ArrayList
	}

	new(@NonNull List<NotebookSelector> notebookSelector) {
		this.notebookSelector = Preconditions.checkNotNull(notebookSelector, 'notebookSelector')
	}

	new(@NonNull List<NotebookSelector> notebookSelector, Boolean save) {
		this(notebookSelector)
		this.save = save
	}
}

/**
 * The params sent in an open notebook document notification.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DidOpenNotebookDocumentParams {
	/**
	 * The notebook document that got opened.
	 */
	@NonNull
	NotebookDocument notebookDocument

	/**
	 * The text documents that represent the content
	 * of a notebook cell.
	 */
	@NonNull
	List<TextDocumentItem> cellTextDocuments

	new() {
		this.cellTextDocuments = new ArrayList
	}

	new(@NonNull NotebookDocument notebookDocument, @NonNull List<TextDocumentItem> cellTextDocuments) {
		this.notebookDocument = Preconditions.checkNotNull(notebookDocument, 'notebookDocument')
		this.cellTextDocuments = Preconditions.checkNotNull(cellTextDocuments, 'cellTextDocuments')
	}
}

/**
 * The params sent in a change notebook document notification.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DidChangeNotebookDocumentParams {
	/**
	 * The notebook document that did change. The version number points
	 * to the version after all provided changes have been applied.
	 */
	@NonNull
	VersionedNotebookDocumentIdentifier notebookDocument

	/**
	 * The actual changes to the notebook document.
	 * <p>
	 * The change describes single state change to the notebook document.
	 * So it moves a notebook document, its cells and its cell text document
	 * contents from state S to S'.
	 * <p>
	 * To mirror the content of a notebook using change events use the
	 * following approach:
	 * <p><ul>
	 * <li>start with the same initial content
	 * <li>apply the 'notebookDocument/didChange' notifications in the order
	 * you receive them.
	 * </ul>
	 */
	@NonNull
	NotebookDocumentChangeEvent change

	new() {
	}

	new(@NonNull VersionedNotebookDocumentIdentifier notebookDocument, @NonNull NotebookDocumentChangeEvent change) {
		this.notebookDocument = Preconditions.checkNotNull(notebookDocument, 'notebookDocument')
		this.change = Preconditions.checkNotNull(change, 'change')
	}
}

/**
 * A versioned notebook document identifier.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class VersionedNotebookDocumentIdentifier {
	/**
	 * The version number of this notebook document.
	 */
	int version

	/**
	 * The notebook document's URI.
	 */
	@NonNull
	String uri

	new() {
	}

	new(int version, @NonNull String uri) {
		this.version = version
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}

/**
 * A change event for a notebook document.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentChangeEvent {
	/**
	 * The changed meta data if any.
	 */
	@JsonAdapter(JsonElementTypeAdapter.Factory)
	Object metadata

	/**
	 * Changes to cells
	 */
	NotebookDocumentChangeEventCells cells

	new() {
	}
}

/**
 * Changes to cells
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentChangeEventCells {
	/**
	 * Changes to the cell structure to add or
	 * remove cells.
	 */
	NotebookDocumentChangeEventCellStructure structure

	/**
	 * Changes to notebook cells properties like its
	 * kind, execution summary or metadata.
	 */
	List<NotebookCell> data

	/**
	 * Changes to the text content of notebook cells.
	 */
	List<NotebookDocumentChangeEventCellTextContent> textContent

	new() {
	}
}

/**
 * Changes to the cell structure to add or
 * remove cells.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentChangeEventCellStructure {
	/**
	 * The change to the cell array.
	 */
	@NonNull
	NotebookCellArrayChange array

	/**
	 * Additional opened cell text documents.
	 */
	List<TextDocumentItem> didOpen

	/**
	 * Additional closed cell text documents.
	 */
	List<TextDocumentIdentifier> didClose

	new() {
	}

	new(@NonNull NotebookCellArrayChange array) {
		this.array = Preconditions.checkNotNull(array, 'array')
	}
}

/**
 * Changes to the text content of notebook cells.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentChangeEventCellTextContent {
	@NonNull
	VersionedTextDocumentIdentifier document

	@NonNull
	List<TextDocumentContentChangeEvent> changes

	new() {
		this.changes = new ArrayList
	}

	new(@NonNull VersionedTextDocumentIdentifier document, @NonNull List<TextDocumentContentChangeEvent> changes) {
		this.document = Preconditions.checkNotNull(document, 'document')
		this.changes = Preconditions.checkNotNull(changes, 'changes')
	}
}

/**
 * A change describing how to move a `NotebookCell`
 * array from state S to S'.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookCellArrayChange {
	/**
	 * The start offset of the cell that changed.
	 */
	int start

	/**
	 * The deleted cells
	 */
	int deleteCount

	/**
	 * The new cells, if any
	 */
	List<NotebookCell> cells

	new() {
	}

	new(int start, int deleteCount) {
		this.start = start
		this.deleteCount = deleteCount
	}

	new(int start, int deleteCount, List<NotebookCell> cells) {
		this(start, deleteCount)
		this.cells = cells
	}
}

/**
 * The params sent in a save notebook document notification.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DidSaveNotebookDocumentParams {
	/**
	 * The notebook document that got saved.
	 */
	@NonNull
	NotebookDocumentIdentifier notebookDocument

	new() {
	}

	new(@NonNull NotebookDocumentIdentifier notebookDocument) {
		this.notebookDocument = Preconditions.checkNotNull(notebookDocument, 'notebookDocument')
	}
}

/**
 * The params sent in a close notebook document notification.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class DidCloseNotebookDocumentParams {
	/**
	 * The notebook document that got closed.
	 */
	@NonNull
	NotebookDocumentIdentifier notebookDocument

	/**
	 * The text documents that represent the content
	 * of a notebook cell that got closed.
	 */
	@NonNull
	List<TextDocumentIdentifier> cellTextDocuments

	new() {
	}

	new(@NonNull NotebookDocumentIdentifier notebookDocument, @NonNull List<TextDocumentIdentifier> cellTextDocuments) {
		this.notebookDocument = Preconditions.checkNotNull(notebookDocument, 'notebookDocument')
		this.cellTextDocuments = Preconditions.checkNotNull(cellTextDocuments, 'cellTextDocuments')
	}
}

/**
 * A literal to identify a notebook document in the client.
 */
@ProtocolSince("3.17.0")
@JsonRpcData
class NotebookDocumentIdentifier {
	/**
	 * The notebook document's URI.
	 */
	@NonNull
	String uri

	new() {
	}

	new(@NonNull String uri) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}

/**
 * Describes kind of {@link StringValue}.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
final class StringValueKind {
	/**
	 * Indicates a snippet {@link StringValue}.
	 */
	public static val SNIPPET = 'snippet'

	private new() {
	}
}

/**
 * A string value used as a snippet is a template which allows to insert text
 * and to control the editor cursor when insertion happens.
 * <p>
 * A snippet can define tab stops and placeholders with `$1`, `$2`
 * and `${3:foo}`. `$0` defines the final tab stop, it defaults to
 * the end of the snippet. Variables are defined with `$name` and
 * `${name:default value}`.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class StringValue {
	/**
	 * The kind of the string value.
	 * <p>
	 * See {@link StringValueKind} for allowed values.
	 */
	@NonNull
	String kind

	/**
	 * The string value.
	 */
	@NonNull
	String value

	new() {
	}

	new(@NonNull String kind, @NonNull String value) {
		this.kind = Preconditions.checkNotNull(kind, 'kind')
		this.value = Preconditions.checkNotNull(value, 'value')
	}
}

/**
 * Client capabilities specific to inline completions.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class InlineCompletionCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Inline completion options used during static or dynamic registration.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class InlineCompletionRegistrationOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	new() {
	}

	new(String id) {
		this.id = id
	}
}

/**
 * A parameter literal used in inline completion requests.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class InlineCompletionParams extends TextDocumentPositionAndWorkDoneProgressParams {
	/**
	 * Additional information about the context in which inline completions
	 * were requested.
	 */
	@NonNull
	InlineCompletionContext context

	new() {
	}

	new(@NonNull TextDocumentIdentifier textDocument, @NonNull Position position, @NonNull InlineCompletionContext context) {
		super(textDocument, position)
		this.context = Preconditions.checkNotNull(context, 'context')
	}
}

/**
 * Provides information about the context in which an inline completion was
 * requested.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class InlineCompletionContext {
	/**
	 * Describes how the inline completion was triggered.
	 */
	@NonNull
	InlineCompletionTriggerKind triggerKind

	/**
	 * Provides information about the currently selected item in the
	 * autocomplete widget if it is visible.
	 *<p>
	 * If set, provided inline completions must extend the text of the
	 * selected item and use the same range, otherwise they are not shown as
	 * preview.
	 * As an example, if the document text is `console.` and the selected item
	 * is `.log` replacing the `.` in the document, the inline completion must
	 * also replace `.` and start with `.log`, for example `.log()`.
	 *<p>
	 * Inline completion providers are requested again whenever the selected
	 * item changes.
	 */
	SelectedCompletionInfo selectedCompletionInfo

	new() {
	}

	new(@NonNull InlineCompletionTriggerKind triggerKind) {
		this.triggerKind = Preconditions.checkNotNull(triggerKind, 'triggerKind')
	}

	new(@NonNull InlineCompletionTriggerKind triggerKind, SelectedCompletionInfo selectedCompletionInfo) {
		this.triggerKind = Preconditions.checkNotNull(triggerKind, 'triggerKind')
		this.selectedCompletionInfo = selectedCompletionInfo
	}
}

/**
 * Describes the currently selected completion item.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class SelectedCompletionInfo {
	/**
	 * The range that will be replaced if this completion item is accepted.
	 */
	@NonNull
	Range range

	/**
	 * The text the range will be replaced with if this completion is
	 * accepted.
	 */
	@NonNull
	String text

	new() {
	}

	new(@NonNull Range range, @NonNull String text) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.text = Preconditions.checkNotNull(text, 'text')
	}
}

/**
 * Represents a collection of {@link InlineCompletionItem} to be presented in the editor.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class InlineCompletionList {
	/**
	 * The inline completion items.
	 */
	@NonNull
	List<InlineCompletionItem> items

	new() {
	}

	new(@NonNull List<InlineCompletionItem> items) {
		this.items = Preconditions.checkNotNull(items, 'items')
	}
}

/**
 * An inline completion item represents a text snippet that is proposed inline
 * to complete text that is being typed.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class InlineCompletionItem {
	/**
	 * The text to replace the range with. Must be set.
	 * Is used both for the preview and the accept operation.
	 */
	@NonNull
	Either<String, StringValue> insertText

	/**
	 * A text that is used to decide if this inline completion should be
	 * shown. When `falsy`, the {@link InlineCompletionItem#insertText} is
	 * used.
	 * <p>
	 * An inline completion is shown if the text to replace is a prefix of the
	 * filter text.
	 */
	String filterText

	/**
	 * The range to replace.
	 * Must begin and end on the same line.
	 * <p>
	 * Prefer replacements over insertions to provide a better experience when
	 * the user deletes typed text.
	 */
	Range range

	/**
	 * An optional {@link Command} that is executed *after* inserting this
	 * completion.
	 */
	Command command

	new() {
	}

	new(@NonNull Either<String, StringValue> insertText) {
		this.insertText = Preconditions.checkNotNull(insertText, 'insertText')
	}
}

/**
 * An interactive text edit.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class SnippetTextEdit {

	/**
	 * The range of the text document to be manipulated.
	 */
	@NonNull
	Range range

	/**
	 * The snippet to be inserted.
	 */
	@NonNull
	StringValue snippet

	/**
	 * The actual identifier of the snippet edit.
	 */
	String annotationId

	new() {
	}

	new(@NonNull Range range, @NonNull StringValue snippet) {
		this.range = Preconditions.checkNotNull(range, 'range')
		this.snippet = Preconditions.checkNotNull(snippet, 'snippet')
	}

	new(@NonNull Range range, @NonNull StringValue snippet, String annotationId) {
		this(range, snippet)
		this.annotationId = annotationId
	}
}

/**
 * Client capabilities for a text document content provider.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class TextDocumentContentCapabilities extends DynamicRegistrationCapabilities {
	new() {
	}

	new(Boolean dynamicRegistration) {
		super(dynamicRegistration)
	}
}

/**
 * Text document content provider registration options.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class TextDocumentContentRegistrationOptions {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again. See also {@link Registration#id}.
	 */
	String id

	@NonNull
	List<String> schemes

	new() {
		this.schemes = new ArrayList
	}

	new(@NonNull List<String> schemes) {
		this.schemes = Preconditions.checkNotNull(schemes, 'schemes')
	}
}

/**
 * Parameters for the {@code workspace/textDocumentContent} request.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class TextDocumentContentParams {
	/**
	 * The uri of the text document.
	 */
	@NonNull
	String uri

	new() {
	}

	new(@NonNull String uri) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}

/**
 * Result of the {@code workspace/textDocumentContent} request.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class TextDocumentContentResult {
	/**
	 * The text content of the text document. Please note, that the content of
	 * any subsequent open notifications for the text document might differ
	 * from the returned content due to whitespace and line ending
	 * normalizations done on the client.
	 */
	@NonNull
	String text

	new() {
	}

	new(@NonNull String text) {
		this.text = Preconditions.checkNotNull(text, 'text')
	}
}

/**
 * Parameters for the {@code workspace/textDocumentContent/refresh} request.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
@JsonRpcData
class TextDocumentContentRefreshParams {
	/**
	 * The uri of the text document to refresh.
	 */
	@NonNull
	String uri

	new() {
	}

	new(@NonNull String uri) {
		this.uri = Preconditions.checkNotNull(uri, 'uri')
	}
}
