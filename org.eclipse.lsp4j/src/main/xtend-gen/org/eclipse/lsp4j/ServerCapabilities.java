package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.CodeLensOptions;
import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.DocumentOnTypeFormattingOptions;
import org.eclipse.lsp4j.SignatureHelpOptions;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class ServerCapabilities extends WrappedJsonObject {
  private static WrappedJsonProperty<TextDocumentSyncKind> textDocumentSyncProperty = new WrappedJsonProperty<>("textDocumentSync", WrappedJsonConverter.enumConverter(TextDocumentSyncKind.class));
  
  /**
   * Defines how text documents are synced.
   */
  @Pure
  public TextDocumentSyncKind getTextDocumentSync() {
    return textDocumentSyncProperty.get(jsonObject);
  }
  
  /**
   * Defines how text documents are synced.
   */
  public void setTextDocumentSync(final TextDocumentSyncKind textDocumentSync) {
    textDocumentSyncProperty.set(jsonObject, textDocumentSync);
  }
  
  /**
   * Removes the property textDocumentSync from the underlying JSON object.
   */
  public TextDocumentSyncKind removeTextDocumentSync() {
    return textDocumentSyncProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> hoverProviderProperty = new WrappedJsonProperty<>("hoverProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides hover support.
   */
  @Pure
  public Boolean getHoverProvider() {
    return hoverProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides hover support.
   */
  public void setHoverProvider(final Boolean hoverProvider) {
    hoverProviderProperty.set(jsonObject, hoverProvider);
  }
  
  /**
   * Removes the property hoverProvider from the underlying JSON object.
   */
  public Boolean removeHoverProvider() {
    return hoverProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<CompletionOptions> completionProviderProperty = new WrappedJsonProperty<>("completionProvider", WrappedJsonConverter.objectConverter(CompletionOptions.class));
  
  /**
   * The server provides completion support.
   */
  @Pure
  public CompletionOptions getCompletionProvider() {
    return completionProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides completion support.
   */
  public void setCompletionProvider(final CompletionOptions completionProvider) {
    completionProviderProperty.set(jsonObject, completionProvider);
  }
  
  /**
   * Removes the property completionProvider from the underlying JSON object.
   */
  public CompletionOptions removeCompletionProvider() {
    return completionProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<SignatureHelpOptions> signatureHelpProviderProperty = new WrappedJsonProperty<>("signatureHelpProvider", WrappedJsonConverter.objectConverter(SignatureHelpOptions.class));
  
  /**
   * The server provides signature help support.
   */
  @Pure
  public SignatureHelpOptions getSignatureHelpProvider() {
    return signatureHelpProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides signature help support.
   */
  public void setSignatureHelpProvider(final SignatureHelpOptions signatureHelpProvider) {
    signatureHelpProviderProperty.set(jsonObject, signatureHelpProvider);
  }
  
  /**
   * Removes the property signatureHelpProvider from the underlying JSON object.
   */
  public SignatureHelpOptions removeSignatureHelpProvider() {
    return signatureHelpProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> definitionProviderProperty = new WrappedJsonProperty<>("definitionProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides goto definition support.
   */
  @Pure
  public Boolean getDefinitionProvider() {
    return definitionProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides goto definition support.
   */
  public void setDefinitionProvider(final Boolean definitionProvider) {
    definitionProviderProperty.set(jsonObject, definitionProvider);
  }
  
  /**
   * Removes the property definitionProvider from the underlying JSON object.
   */
  public Boolean removeDefinitionProvider() {
    return definitionProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> referencesProviderProperty = new WrappedJsonProperty<>("referencesProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides find references support.
   */
  @Pure
  public Boolean getReferencesProvider() {
    return referencesProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides find references support.
   */
  public void setReferencesProvider(final Boolean referencesProvider) {
    referencesProviderProperty.set(jsonObject, referencesProvider);
  }
  
  /**
   * Removes the property referencesProvider from the underlying JSON object.
   */
  public Boolean removeReferencesProvider() {
    return referencesProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> documentHighlightProviderProperty = new WrappedJsonProperty<>("documentHighlightProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides document highlight support.
   */
  @Pure
  public Boolean getDocumentHighlightProvider() {
    return documentHighlightProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides document highlight support.
   */
  public void setDocumentHighlightProvider(final Boolean documentHighlightProvider) {
    documentHighlightProviderProperty.set(jsonObject, documentHighlightProvider);
  }
  
  /**
   * Removes the property documentHighlightProvider from the underlying JSON object.
   */
  public Boolean removeDocumentHighlightProvider() {
    return documentHighlightProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> documentSymbolProviderProperty = new WrappedJsonProperty<>("documentSymbolProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides document symbol support.
   */
  @Pure
  public Boolean getDocumentSymbolProvider() {
    return documentSymbolProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides document symbol support.
   */
  public void setDocumentSymbolProvider(final Boolean documentSymbolProvider) {
    documentSymbolProviderProperty.set(jsonObject, documentSymbolProvider);
  }
  
  /**
   * Removes the property documentSymbolProvider from the underlying JSON object.
   */
  public Boolean removeDocumentSymbolProvider() {
    return documentSymbolProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> workspaceSymbolProviderProperty = new WrappedJsonProperty<>("workspaceSymbolProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides workspace symbol support.
   */
  @Pure
  public Boolean getWorkspaceSymbolProvider() {
    return workspaceSymbolProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides workspace symbol support.
   */
  public void setWorkspaceSymbolProvider(final Boolean workspaceSymbolProvider) {
    workspaceSymbolProviderProperty.set(jsonObject, workspaceSymbolProvider);
  }
  
  /**
   * Removes the property workspaceSymbolProvider from the underlying JSON object.
   */
  public Boolean removeWorkspaceSymbolProvider() {
    return workspaceSymbolProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> codeActionProviderProperty = new WrappedJsonProperty<>("codeActionProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides code actions.
   */
  @Pure
  public Boolean getCodeActionProvider() {
    return codeActionProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides code actions.
   */
  public void setCodeActionProvider(final Boolean codeActionProvider) {
    codeActionProviderProperty.set(jsonObject, codeActionProvider);
  }
  
  /**
   * Removes the property codeActionProvider from the underlying JSON object.
   */
  public Boolean removeCodeActionProvider() {
    return codeActionProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<CodeLensOptions> codeLensProviderProperty = new WrappedJsonProperty<>("codeLensProvider", WrappedJsonConverter.objectConverter(CodeLensOptions.class));
  
  /**
   * The server provides code lens.
   */
  @Pure
  public CodeLensOptions getCodeLensProvider() {
    return codeLensProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides code lens.
   */
  public void setCodeLensProvider(final CodeLensOptions codeLensProvider) {
    codeLensProviderProperty.set(jsonObject, codeLensProvider);
  }
  
  /**
   * Removes the property codeLensProvider from the underlying JSON object.
   */
  public CodeLensOptions removeCodeLensProvider() {
    return codeLensProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> documentFormattingProviderProperty = new WrappedJsonProperty<>("documentFormattingProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides document formatting.
   */
  @Pure
  public Boolean getDocumentFormattingProvider() {
    return documentFormattingProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides document formatting.
   */
  public void setDocumentFormattingProvider(final Boolean documentFormattingProvider) {
    documentFormattingProviderProperty.set(jsonObject, documentFormattingProvider);
  }
  
  /**
   * Removes the property documentFormattingProvider from the underlying JSON object.
   */
  public Boolean removeDocumentFormattingProvider() {
    return documentFormattingProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> documentRangeFormattingProviderProperty = new WrappedJsonProperty<>("documentRangeFormattingProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides document range formatting.
   */
  @Pure
  public Boolean getDocumentRangeFormattingProvider() {
    return documentRangeFormattingProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides document range formatting.
   */
  public void setDocumentRangeFormattingProvider(final Boolean documentRangeFormattingProvider) {
    documentRangeFormattingProviderProperty.set(jsonObject, documentRangeFormattingProvider);
  }
  
  /**
   * Removes the property documentRangeFormattingProvider from the underlying JSON object.
   */
  public Boolean removeDocumentRangeFormattingProvider() {
    return documentRangeFormattingProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<DocumentOnTypeFormattingOptions> documentOnTypeFormattingProviderProperty = new WrappedJsonProperty<>("documentOnTypeFormattingProvider", WrappedJsonConverter.objectConverter(DocumentOnTypeFormattingOptions.class));
  
  /**
   * The server provides document formatting on typing.
   */
  @Pure
  public DocumentOnTypeFormattingOptions getDocumentOnTypeFormattingProvider() {
    return documentOnTypeFormattingProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides document formatting on typing.
   */
  public void setDocumentOnTypeFormattingProvider(final DocumentOnTypeFormattingOptions documentOnTypeFormattingProvider) {
    documentOnTypeFormattingProviderProperty.set(jsonObject, documentOnTypeFormattingProvider);
  }
  
  /**
   * Removes the property documentOnTypeFormattingProvider from the underlying JSON object.
   */
  public DocumentOnTypeFormattingOptions removeDocumentOnTypeFormattingProvider() {
    return documentOnTypeFormattingProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> renameProviderProperty = new WrappedJsonProperty<>("renameProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides rename support.
   */
  @Pure
  public Boolean getRenameProvider() {
    return renameProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides rename support.
   */
  public void setRenameProvider(final Boolean renameProvider) {
    renameProviderProperty.set(jsonObject, renameProvider);
  }
  
  /**
   * Removes the property renameProvider from the underlying JSON object.
   */
  public Boolean removeRenameProvider() {
    return renameProviderProperty.remove(jsonObject);
  }
  
  public ServerCapabilities() {
    super();
  }
  
  public ServerCapabilities(final JsonObject jsonObject) {
    super(jsonObject);
  }
}
