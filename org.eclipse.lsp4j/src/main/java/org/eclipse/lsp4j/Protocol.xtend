package org.eclipse.lsp4j

import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.List
import java.util.Map
import org.eclipse.lsp4j.generator.JsonRpcData
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

@JsonRpcData
class DynamicRegistrationCapabilities {
	/**
     * Supports dynamic registration.
     */
    Boolean dynamicRegistration
}

@JsonRpcData
class DidChangeConfigurationCapabilites extends DynamicRegistrationCapabilities {
}

@JsonRpcData
class DidChangeWatchedFilesCapabilites extends DynamicRegistrationCapabilities {
}

@JsonRpcData
class SymbolCapabilites extends DynamicRegistrationCapabilities {
}

@JsonRpcData
class ExecuteCommandCapabilites extends DynamicRegistrationCapabilities {
}

/**
 * Workspace specific client capabilities.
 */
@JsonRpcData
class WorkspaceClientCapabilites {
	/**
     * The client supports applying batch edits
     * to the workspace.
     */
    Boolean applyEdit
    
    /**
     * Capabilities specific to the `workspace/didChangeConfiguration` notification.
     */
    DidChangeConfigurationCapabilites didChangeConfiguration
    
    /**
     * Capabilities specific to the `workspace/didChangeConfiguration` notification.
     */
    DidChangeWatchedFilesCapabilites didChangeWatchedFiles
    
    /**
     * Capabilities specific to the `workspace/symbol` request.
     */
    SymbolCapabilites symbol
    
    /**
     * Capabilities specific to the `workspace/executeCommand` request.
     */
    ExecuteCommandCapabilites executeCommand
}

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
}

@JsonRpcData
class CompletionItemCapabilities {
	/**
     * Client supports snippets as insert text.
     *
     * A snippet can define tab stops and placeholders with `$1`, `$2`
     * and `${3:foo}`. `$0` defines the final tab stop, it defaults to
     * the end of the snippet. Placeholders with equal identifiers are linked,
     * that is typing in one will update others too.
     */
    Boolean snippetSupport
}

@JsonRpcData
class CompletionCapabilities extends DynamicRegistrationCapabilities {     
    /**
	 * The client supports the following `CompletionItem` specific
	 * capabilities.
	 */
	CompletionItemCapabilities completionItem
}

@JsonRpcData
class HoverCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class SignatureHelpCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class ReferencesCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class DocumentHighlightCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class DocumentSymbolCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class FormattingCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class RangeFormattingCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class OnTypeFormattingCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class DefinitionCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class CodeActionCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class CodeLensCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class DocumentLinkCapabilities extends DynamicRegistrationCapabilities {	
}

@JsonRpcData
class RenameCapabilities extends DynamicRegistrationCapabilities {	
}

/**
 * Text document specific client capabilities.
 */
@JsonRpcData
class TextDocumentClientCapabilities {
	SynchronizationCapabilities synchronization
    
    /**
     * Capabilities specific to the `textDocument/hover`
     */
    HoverCapabilities hover
    
    /**
     * Capabilities specific to the `textDocument/signatureHelp`
     */
    SignatureHelpCapabilities signatureHelp
    
    /**
     * Capabilities specific to the `textDocument/references`
     */
    ReferencesCapabilities references
    
    /**
     * Capabilities specific to the `textDocument/documentHighlight`
     */
    DocumentHighlightCapabilities documentHighlight
    
    /**
     * Capabilities specific to the `textDocument/documentSymbol`
     */
    DocumentSymbolCapabilities documentSymbol
    
    /**
     * Capabilities specific to the `textDocument/formatting`
     */
    FormattingCapabilities formatting
    
    /**
     * Capabilities specific to the `textDocument/rangeFormatting`
     */
    RangeFormattingCapabilities rangeFormatting
    
    /**
     * Capabilities specific to the `textDocument/onTypeFormatting`
     */
    OnTypeFormattingCapabilities onTypeFormatting
    
    /**
     * Capabilities specific to the `textDocument/definition`
     */
    DefinitionCapabilities definition
    
    /**
     * Capabilities specific to the `textDocument/codeAction`
     */
    CodeActionCapabilities codeAction
    
    /**
     * Capabilities specific to the `textDocument/codeLens`
     */
    CodeLensCapabilities codeLens
    
    /**
     * Capabilities specific to the `textDocument/documentLink`
     */
    DocumentLinkCapabilities documentLink
    
    /**
     * Capabilities specific to the `textDocument/rename`
     */
    RenameCapabilities rename
}

/**
 * Defines capabilities for dynamic registration, workspace and text document features the client supports
 * The experimental can be used to pass experimential capabilities under development.
 * For future compatibility a ClientCapabilities object literal can have more properties set than currently defined.
 * Servers receiving a ClientCapabilities object literal with unknown properties should ignore these properties.
 * A missing property should be interpreted as an absence of the capability.
 */
@JsonRpcData
class ClientCapabilities {
	/**
     * Workspace specific client capabilities.
     */
    WorkspaceClientCapabilites workspace
    
    /**
     * Text document specific client capabilities.
     */
    TextDocumentClientCapabilities textDocument
    
    /**
     * Experimental client capabilities.
     */
    Object experimental
}

/**
 * Contains additional diagnostic information about the context in which a code action is run.
 */
@JsonRpcData
class CodeActionContext {
	/**
	 * An array of diagnostics.
	 */
	@NonNull
	List<Diagnostic> diagnostics = newArrayList
}

/**
 * The code action request is sent from the client to the server to compute commands for a given text document and range.
 * The request is triggered when the user moves the cursor into an problem marker in the editor or presses the lightbulb
 * associated with a marker.
 */
@JsonRpcData
class CodeActionParams {
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
}

/**
 * A code lens represents a command that should be shown along with source text, like the number of references,
 * a way to run tests, etc.
 * 
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
	 * An data entry field that is preserved on a code lens item between a code lens and a code lens resolve request.
	 */
	Object data
}

/**
 * Code Lens options.
 */
@JsonRpcData
class CodeLensOptions {
	/**
	 * Code lens has a resolve provider as well.
	 */
	boolean resolveProvider
}

/**
 * The code lens request is sent from the client to the server to compute code lenses for a given text document.
 */
@JsonRpcData
class CodeLensParams {
	/**
	 * The document to request code lens for.
	 */
	@NonNull
	TextDocumentIdentifier textDocument
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
	 * The identifier of the actual command handler.
	 */
	@NonNull
	String command

	/**
	 * Arguments that the command handler should be invoked with.
	 */
	List<Object> arguments
}

/**
 * The Completion request is sent from the client to the server to compute completion items at a given cursor position.
 * Completion items are presented in the IntelliSense user class. If computing complete completion items is expensive
 * servers can additional provide a handler for the resolve completion item request. This request is send when a
 * completion item is selected in the user class.
 */
@JsonRpcData
class CompletionItem {
	/**
	 * The label of this completion item. By default also the text that is inserted when selecting this completion.
	 */
	@NonNull
	String label

	/**
	 * The kind of this completion item. Based of the kind an icon is chosen by the editor.
	 */
	CompletionItemKind kind

	/**
	 * A human-readable string with additional information about this item, like type or symbol information.
	 */
	String detail

	/**
	 * A human-readable string that represents a doc-comment.
	 */
	String documentation

	/**
	 * A string that shoud be used when comparing this item with other items. When `falsy` the label is used.
	 */
	String sortText

	/**
	 * A string that should be used when filtering a set of completion items. When `falsy` the label is used.
	 */
	String filterText

	/**
	 * A string that should be inserted a document when selecting this completion. When `falsy` the label is used.
	 */
	String insertText
	
	/**
     * The format of the insert text. The format applies to both the `insertText` property
     * and the `newText` property of a provided `textEdit`.
     */
    InsertTextFormat insertTextFormat

    /**
     * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
     * `insertText` and `range` is ignored.
     *
     * *Note:* The range of the edit must be a single line range and it must contain the position at which completion
     * has been requested.
     */
    TextEdit textEdit

	/**
	 * An optional array of additional text edits that are applied when
	 * selecting this completion. Edits must not overlap with the main edit
	 * nor with themselves.
	 */
	List<TextEdit> additionalTextEdits

	/**
	 * An optional command that is executed *after* inserting this completion. *Note* that
	 * additional modifications to the current document should be described with the
	 * additionalTextEdits-property.
	 */
	Command command

	/**
	 * An data entry field that is preserved on a completion item between a completion and a completion resolve request.
	 */
	Object data
}

/**
 * Represents a collection of completion items to be presented in the editor.
 */
@JsonRpcData
class CompletionList {
	/**
	 * This list it not complete. Further typing should result in recomputing this list.
	 */
	boolean isIncomplete

	/**
	 * The completion items.
	 */
	@NonNull
	List<CompletionItem> items = newArrayList
}

/**
 * Completion options.
 */
@JsonRpcData
class CompletionOptions {
	/**
	 * The server provides support to resolve additional information for a completion item.
	 */
	Boolean resolveProvider

	/**
	 * The characters that trigger completion automatically.
	 */
	List<String> triggerCharacters
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
	String code

	/**
	 * A human-readable string describing the source of this diagnostic, e.g. 'typescript' or 'super lint'.
	 */
	String source

	/**
	 * The diagnostic's message.
	 */
	@NonNull
	String message
}

/**
 * A notification sent from the client to the server to signal the change of configuration settings.
 */
@JsonRpcData
class DidChangeConfigurationParams {
	@NonNull
	Object settings
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
	 * Legacy property to support protocol version 1.0 requests.
	 */
	@Deprecated
	String uri

	/**
	 * The actual content changes.
	 */
	@NonNull
	List<TextDocumentContentChangeEvent> contentChanges = new ArrayList
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
	List<FileEvent> changes = new ArrayList
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

	/**
	 * Legacy property to support protocol version 1.0 requests.
	 */
	@Deprecated
	String text
}

/**
 * The document save notification is sent from the client to the server when the document for saved in the clinet.
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
}

/**
 * The document formatting request is sent from the server to the client to format a whole document.
 */
@JsonRpcData
class DocumentFormattingParams {
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
    @NonNull Range range
    
    /**
     * The uri this link points to.
     */
    @NonNull String target
}

@JsonRpcData
class DocumentLinkParams {
    /**
     * The document to provide document links for.
     */
    TextDocumentIdentifier textDocument
}

/**
 * Document link options
 */
@JsonRpcData
class DocumentLinkOptions {
	/**
     * Document links have a resolve provider as well.
     */
    Boolean resolveProvider
}

/**
 * Execute command options.
 */
@JsonRpcData
class ExecuteCommandOptions {
	/**
     * The commands to be executed on the server
     */
    @NonNull
    List<String> commands = newArrayList
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
}

@JsonRpcData
class TextDocumentSyncOptions {
	/**
     * Open and close notifications are sent to the server.
     */
    Boolean openClose
    /**
     * Change notifications are sent to the server. See TextDocumentSyncKind.None, TextDocumentSyncKind.Full
     * and TextDocumentSyncKind.Incremental.
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
    SaveOptions save
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
}

/**
 * The document on type formatting request is sent from the client to the server to format parts of the document during typing.
 */
@JsonRpcData
class DocumentOnTypeFormattingParams extends DocumentFormattingParams {
	/**
	 * The position at which this request was send.
	 */
	@NonNull
	Position position

	/**
	 * The character that has been typed.
	 */
	@NonNull
	String ch
}

/**
 * The document range formatting request is sent from the client to the server to format a given range in a document.
 */
@JsonRpcData
class DocumentRangeFormattingParams extends DocumentFormattingParams {
	/**
	 * The range to format
	 */
	@NonNull
	Range range
}

/**
 * The document symbol request is sent from the client to the server to list all symbols found in a given text document.
 */
@JsonRpcData
class DocumentSymbolParams {
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument
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
}

/**
 * Value-object describing what options formatting should use.
 */
@JsonRpcData
class FormattingOptions {
	/**
	 * Size of a tab in spaces.
	 */
	int tabSize

	/**
	 * Prefer spaces over tabs.
	 */
	boolean insertSpaces

	/**
	 * Signature for further properties.
	 */
	Map<String, String> properties
}

/**
 * The result of a hover request.
 */
@JsonRpcData
class Hover {
	/**
	 * The hover's content as markdown
	 */
	@NonNull
	List<Either<String, MarkedString>> contents

	/**
	 * An optional range
	 */
	Range range
}

/**
 * MarkedString can be used to render human readable text. It is either a markdown string
 * or a code-block that provides a language and a code snippet. The language identifier
 * is sematically equal to the optional language identifier in fenced code blocks in GitHub
 * issues. See https://help.github.com/articles/creating-and-highlighting-code-blocks/#syntax-highlighting
 *
 * The pair of a language and a value is an equivalent to markdown:
 * ```${language}
 * ${value}
 * ```
 *
 * Note that markdown strings will be sanitized - that means html will be escaped.
 */
@JsonRpcData
class MarkedString {
	@NonNull
	String language
	@NonNull
	String value
}

@JsonRpcData
class InitializeError {
	/**
	 * Indicates whether the client should retry to send the initialize request after showing the message provided
	 * in the ResponseError.
	 */
	boolean retry
}

/**
 * Known error codes for an `InitializeError`
 */
interface InitializeErrorCode {
	/**
     * If the protocol version provided by the client can't be handled by the server.
     */
    val unknownProtocolVersion = 1
}

/**
 * The initialize request is sent as the first request from the client to the server.
 */
@JsonRpcData
class InitializeParams {
	/**
	 * The process Id of the parent process that started the server.
	 */
	Integer processId

	/**
	 * The rootPath of the workspace. Is null if no folder is open.
	 * 
	 * @deprecared in favour of rootUri.
	 */
	@Deprecated
	String rootPath
	
	/**
     * The rootUri of the workspace. Is null if no
     * folder is open.
     */
	String rootUri

	/**
	 * User provided initialization options.
	 */
	Object initializationOptions

	/**
	 * The capabilities provided by the client (editor)
	 */
	ClientCapabilities capabilities

	/**
	 * An optional extension to the protocol.
	 * To tell the server what client (editor) is talking to it.
	 */
	@Deprecated
	String clientName
	
	/**
     * The initial trace setting. If omitted trace is disabled ('off').
     * 
     * Legal values: 'off' | 'messages' | 'verbose'
     */
    String trace
}

@JsonRpcData
class InitializeResult {
	/**
	 * The capabilities the language server provides.
	 */
	@NonNull
	ServerCapabilities capabilities
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
}

/**
 * The show message notification is sent from a server to a client to ask the client to display a particular message
 * in the user class.
 * 
 * The log message notification is send from the server to the client to ask the client to log a particular message.
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
}

/**
 * Represents a parameter of a callable-signature. A parameter can have a label and a doc-comment.
 */
@JsonRpcData
class ParameterInformation {
	/**
	 * The label of this signature. Will be shown in the UI.
	 */
	@NonNull
	String label

	/**
	 * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
	 */
	String documentation
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
	List<Diagnostic> diagnostics = new ArrayList
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
}

/**
 * The references request is sent from the client to the server to resolve project-wide references for the symbol
 * denoted by the given text document position.
 */
@JsonRpcData
class ReferenceParams extends TextDocumentPositionParams {
	@NonNull
	ReferenceContext context
}

/**
 * The rename request is sent from the client to the server to do a workspace wide rename of a symbol.
 */
@JsonRpcData
class RenameParams {
	/**
	 * The document in which to find the symbol.
	 */
	@NonNull
	TextDocumentIdentifier textDocument

	/**
	 * The position at which this request was send.
	 */
	@NonNull
	Position position

	/**
	 * The new name of the symbol. If the given name is not valid the request must return a
	 * ResponseError with an appropriate message set.
	 */
	@NonNull
	String newName
}

@JsonRpcData
class ServerCapabilities {
	/**
	 * Defines how text documents are synced. Is either a detailed structure defining each notification or
     * for backwards compatibility the TextDocumentSyncKind number.
	 */
	Either<TextDocumentSyncKind, TextDocumentSyncOptions> textDocumentSync

	/**
	 * The server provides hover support.
	 */
	Boolean hoverProvider

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
	Boolean definitionProvider

	/**
	 * The server provides find references support.
	 */
	Boolean referencesProvider

	/**
	 * The server provides document highlight support.
	 */
	Boolean documentHighlightProvider

	/**
	 * The server provides document symbol support.
	 */
	Boolean documentSymbolProvider

	/**
	 * The server provides workspace symbol support.
	 */
	Boolean workspaceSymbolProvider

	/**
	 * The server provides code actions.
	 */
	Boolean codeActionProvider

	/**
	 * The server provides code lens.
	 */
	CodeLensOptions codeLensProvider

	/**
	 * The server provides document formatting.
	 */
	Boolean documentFormattingProvider

	/**
	 * The server provides document range formatting.
	 */
	Boolean documentRangeFormattingProvider

	/**
	 * The server provides document formatting on typing.
	 */
	DocumentOnTypeFormattingOptions documentOnTypeFormattingProvider

	/**
	 * The server provides rename support.
	 */
	Boolean renameProvider
	
	/**
     * The server provides document link support.
     */
    DocumentLinkOptions documentLinkProvider
    
    /**
     * The server provides execute command support.
     */
    ExecuteCommandOptions executeCommandProvider
    
    /**
     * Experimental server capabilities.
     */
    Object experimental
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
	List<SignatureInformation> signatures = new ArrayList

	/**
	 * The active signature.
	 */
	Integer activeSignature

	/**
	 * The active parameter of the active signature.
	 */
	Integer activeParameter
}

/**
 * Signature help options.
 */
@JsonRpcData
class SignatureHelpOptions {
	/**
	 * The characters that trigger signature help automatically.
	 */
	List<String> triggerCharacters
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
	String documentation

	/**
	 * The parameters of this signature.
	 */
	List<ParameterInformation> parameters
}

/**
 * Represents information about programming constructs like variables, classes, classs etc.
 */
@JsonRpcData
class SymbolInformation {
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
	 * The location of this symbol.
	 */
	@NonNull
	Location location

	/**
	 * The name of the symbol containing this symbol.
	 */
	String containerName
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
	 */
	Integer rangeLength

	/**
	 * The new text of the document.
	 */
	@NonNull
	String text
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
	 * The content of the opened  text document.
	 */
	@NonNull
	String text
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
	 * Legacy property to support protocol version 1.0 requests.
	 */
	@Deprecated
	String uri

	/**
	 * The position inside the text document.
	 */
	@NonNull
	Position position
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
}

/**
 * An identifier to denote a specific version of a text document.
 */
@JsonRpcData
class VersionedTextDocumentIdentifier extends TextDocumentIdentifier {
	/**
	 * The version number of this document.
	 */
	int version
}

/**
 * A workspace edit represents changes to many resources managed in the workspace.
 */
@JsonRpcData
class WorkspaceEdit {
	/**
	 * Holds changes to existing resources.
	 */
	@NonNull
	Map<String, List<TextEdit>> changes = new LinkedHashMap
}

/**
 * The parameters of a Workspace Symbol Request.
 */
@JsonRpcData
class WorkspaceSymbolParams {
	/**
	 * A non-empty query string
	 */
	@NonNull
	String query
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
    Object registerOptions
}

@JsonRpcData
class RegistrationParams {
	@NonNull
	List<Registration> registrations = newArrayList
}

/**
 * A document filter denotes a document through properties like language, schema or pattern.
 */
@JsonRpcData
class DocumentFilter {
	/**
     * A language id, like `typescript`.
     */
    String language
	
	/**
     * A uri scheme, like `file` or `untitled`.
     */
    String schema
	
	/**
     * A glob pattern, like `*.{ts,js}`.
     */
    String pattern
}

/**
 * A document selector is the combination of one or many document filters.
 */
interface DocumentSelector extends List<DocumentFilter> {
}

/**
 * Since most of the registration options require to specify a document selector there is a base interface that can be used.
 */
@JsonRpcData
class TextDocumentRegistrationOptions {
	/**
     * A document selector to identify the scope of the registration. If set to null
     * the document selector provided on the client side will be used.
     */
    DocumentSelector documentSelector
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
}

@JsonRpcData
class UnregistrationParams {
	@NonNull
	List<Unregistration> unregisterations = newArrayList
}

/**
 * Describe options to be used when registered for text document change events.
 */
@JsonRpcData
class TextDocumentChangeRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
     * How documents are synced to the server. See TextDocumentSyncKind.Full 
     * and TextDocumentSyncKind.Incremental.
     */
    @NonNull
	TextDocumentSyncKind syncKind	
}

@JsonRpcData
class TextDocumentSaveRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * The client is supposed to include the content on save.
	 */
	Boolean includeText
}

@JsonRpcData
class CompletionRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
     * The characters that trigger completion automatically.
     */
    List<String> triggerCharacters

    /**
     * The server provides support to resolve additional information for a completion item.
     */
    Boolean resolveProvider
}

@JsonRpcData
class SignatureHelpRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
     * The characters that trigger signature help automatically.
     */
    List<String> triggerCharacters
}

@JsonRpcData
class CodeLensRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
     * Code lens has a resolve provider as well.
     */
    Boolean resolveProvider
}

@JsonRpcData
class DocumentLinkRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
     * Document links have a resolve provider as well.
     */
    Boolean resolveProvider
}

@JsonRpcData
class DocumentOnTypeFormattingRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
     * A character on which formatting should be triggered, like `}`.
     */
    String firstTriggerCharacter
	/**
     * More trigger characters.
     */
    List<String> moreTriggerCharacter	
}

@JsonRpcData
class ExecuteCommandParams {
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
}

/**
 * Execute command registration options.
 */
@JsonRpcData
class ExecuteCommandRegistrationOptions {
	/**
     * The commands to be executed on the server
     */
    List<String> commands
}

@JsonRpcData
class ApplyWorkspaceEditParams {
	/**
     * The edits to apply.
     */
    WorkspaceEdit edit
}

@JsonRpcData
class ApplyWorkspaceEditResponse {
	/**
     * Indicates whether the edit was applied or not.
     */
    Boolean applied	
}
