/**
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import com.google.common.annotations.Beta;
import org.eclipse.lsp4j.CallHierarchyCapabilities;
import org.eclipse.lsp4j.CodeActionCapabilities;
import org.eclipse.lsp4j.CodeLensCapabilities;
import org.eclipse.lsp4j.ColorProviderCapabilities;
import org.eclipse.lsp4j.CompletionCapabilities;
import org.eclipse.lsp4j.DeclarationCapabilities;
import org.eclipse.lsp4j.DefinitionCapabilities;
import org.eclipse.lsp4j.DocumentHighlightCapabilities;
import org.eclipse.lsp4j.DocumentLinkCapabilities;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.FoldingRangeCapabilities;
import org.eclipse.lsp4j.FormattingCapabilities;
import org.eclipse.lsp4j.HoverCapabilities;
import org.eclipse.lsp4j.ImplementationCapabilities;
import org.eclipse.lsp4j.OnTypeFormattingCapabilities;
import org.eclipse.lsp4j.PublishDiagnosticsCapabilities;
import org.eclipse.lsp4j.RangeFormattingCapabilities;
import org.eclipse.lsp4j.ReferencesCapabilities;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.SelectionRangeCapabilities;
import org.eclipse.lsp4j.SemanticHighlightingCapabilities;
import org.eclipse.lsp4j.SemanticTokensCapabilities;
import org.eclipse.lsp4j.SignatureHelpCapabilities;
import org.eclipse.lsp4j.SynchronizationCapabilities;
import org.eclipse.lsp4j.TypeDefinitionCapabilities;
import org.eclipse.lsp4j.TypeHierarchyCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Text document specific client capabilities.
 */
@SuppressWarnings("all")
public class TextDocumentClientCapabilities {
  private SynchronizationCapabilities synchronization;
  
  /**
   * Capabilities specific to the `textDocument/completion`
   */
  private CompletionCapabilities completion;
  
  /**
   * Capabilities specific to the `textDocument/hover`
   */
  private HoverCapabilities hover;
  
  /**
   * Capabilities specific to the `textDocument/signatureHelp`
   */
  private SignatureHelpCapabilities signatureHelp;
  
  /**
   * Capabilities specific to the `textDocument/references`
   */
  private ReferencesCapabilities references;
  
  /**
   * Capabilities specific to the `textDocument/documentHighlight`
   */
  private DocumentHighlightCapabilities documentHighlight;
  
  /**
   * Capabilities specific to the `textDocument/documentSymbol`
   */
  private DocumentSymbolCapabilities documentSymbol;
  
  /**
   * Capabilities specific to the `textDocument/formatting`
   */
  private FormattingCapabilities formatting;
  
  /**
   * Capabilities specific to the `textDocument/rangeFormatting`
   */
  private RangeFormattingCapabilities rangeFormatting;
  
  /**
   * Capabilities specific to the `textDocument/onTypeFormatting`
   */
  private OnTypeFormattingCapabilities onTypeFormatting;
  
  /**
   * Capabilities specific to the `textDocument/declaration`
   * 
   * Since 3.14.0
   */
  private DeclarationCapabilities declaration;
  
  /**
   * Capabilities specific to the `textDocument/definition`
   * 
   * Since 3.14.0
   */
  private DefinitionCapabilities definition;
  
  /**
   * Capabilities specific to the `textDocument/typeDefinition`
   * 
   * Since 3.6.0
   */
  private TypeDefinitionCapabilities typeDefinition;
  
  /**
   * Capabilities specific to the `textDocument/implementation`
   * 
   * Since 3.6.0
   */
  private ImplementationCapabilities implementation;
  
  /**
   * Capabilities specific to the `textDocument/codeAction`
   */
  private CodeActionCapabilities codeAction;
  
  /**
   * Capabilities specific to the `textDocument/codeLens`
   */
  private CodeLensCapabilities codeLens;
  
  /**
   * Capabilities specific to the `textDocument/documentLink`
   */
  private DocumentLinkCapabilities documentLink;
  
  /**
   * Capabilities specific to the `textDocument/documentColor` and the
   * `textDocument/colorPresentation` request.
   * 
   * Since 3.6.0
   */
  private ColorProviderCapabilities colorProvider;
  
  /**
   * Capabilities specific to the `textDocument/rename`
   */
  private RenameCapabilities rename;
  
  /**
   * Capabilities specific to `textDocument/publishDiagnostics`.
   */
  private PublishDiagnosticsCapabilities publishDiagnostics;
  
  /**
   * Capabilities specific to `textDocument/foldingRange` requests.
   * 
   * Since 3.10.0
   */
  private FoldingRangeCapabilities foldingRange;
  
  /**
   * Capabilities specific to {@code textDocument/semanticHighlighting}.
   * 
   * @deprecated Use {@code SemanticTokens} API instead.
   */
  @Beta
  @Deprecated
  private SemanticHighlightingCapabilities semanticHighlightingCapabilities;
  
  /**
   * Capabilities specific to {@code textDocument/typeHierarchy}.
   */
  @Beta
  private TypeHierarchyCapabilities typeHierarchyCapabilities;
  
  /**
   * Capabilities specific to {@code textDocument/prepareCallHierarchy}.
   * 
   * Since 3.16.0
   */
  @Beta
  private CallHierarchyCapabilities callHierarchy;
  
  /**
   * Capabilities specific to `textDocument/selectionRange` requests
   * 
   * Since 3.15.0
   */
  private SelectionRangeCapabilities selectionRange;
  
  /**
   * Capabilities specific to {@code textDocument/semanticTokens}.
   * 
   * Since 3.16.0
   */
  @Beta
  private SemanticTokensCapabilities semanticTokens;
  
  @Pure
  public SynchronizationCapabilities getSynchronization() {
    return this.synchronization;
  }
  
  public void setSynchronization(final SynchronizationCapabilities synchronization) {
    this.synchronization = synchronization;
  }
  
  /**
   * Capabilities specific to the `textDocument/completion`
   */
  @Pure
  public CompletionCapabilities getCompletion() {
    return this.completion;
  }
  
  /**
   * Capabilities specific to the `textDocument/completion`
   */
  public void setCompletion(final CompletionCapabilities completion) {
    this.completion = completion;
  }
  
  /**
   * Capabilities specific to the `textDocument/hover`
   */
  @Pure
  public HoverCapabilities getHover() {
    return this.hover;
  }
  
  /**
   * Capabilities specific to the `textDocument/hover`
   */
  public void setHover(final HoverCapabilities hover) {
    this.hover = hover;
  }
  
  /**
   * Capabilities specific to the `textDocument/signatureHelp`
   */
  @Pure
  public SignatureHelpCapabilities getSignatureHelp() {
    return this.signatureHelp;
  }
  
  /**
   * Capabilities specific to the `textDocument/signatureHelp`
   */
  public void setSignatureHelp(final SignatureHelpCapabilities signatureHelp) {
    this.signatureHelp = signatureHelp;
  }
  
  /**
   * Capabilities specific to the `textDocument/references`
   */
  @Pure
  public ReferencesCapabilities getReferences() {
    return this.references;
  }
  
  /**
   * Capabilities specific to the `textDocument/references`
   */
  public void setReferences(final ReferencesCapabilities references) {
    this.references = references;
  }
  
  /**
   * Capabilities specific to the `textDocument/documentHighlight`
   */
  @Pure
  public DocumentHighlightCapabilities getDocumentHighlight() {
    return this.documentHighlight;
  }
  
  /**
   * Capabilities specific to the `textDocument/documentHighlight`
   */
  public void setDocumentHighlight(final DocumentHighlightCapabilities documentHighlight) {
    this.documentHighlight = documentHighlight;
  }
  
  /**
   * Capabilities specific to the `textDocument/documentSymbol`
   */
  @Pure
  public DocumentSymbolCapabilities getDocumentSymbol() {
    return this.documentSymbol;
  }
  
  /**
   * Capabilities specific to the `textDocument/documentSymbol`
   */
  public void setDocumentSymbol(final DocumentSymbolCapabilities documentSymbol) {
    this.documentSymbol = documentSymbol;
  }
  
  /**
   * Capabilities specific to the `textDocument/formatting`
   */
  @Pure
  public FormattingCapabilities getFormatting() {
    return this.formatting;
  }
  
  /**
   * Capabilities specific to the `textDocument/formatting`
   */
  public void setFormatting(final FormattingCapabilities formatting) {
    this.formatting = formatting;
  }
  
  /**
   * Capabilities specific to the `textDocument/rangeFormatting`
   */
  @Pure
  public RangeFormattingCapabilities getRangeFormatting() {
    return this.rangeFormatting;
  }
  
  /**
   * Capabilities specific to the `textDocument/rangeFormatting`
   */
  public void setRangeFormatting(final RangeFormattingCapabilities rangeFormatting) {
    this.rangeFormatting = rangeFormatting;
  }
  
  /**
   * Capabilities specific to the `textDocument/onTypeFormatting`
   */
  @Pure
  public OnTypeFormattingCapabilities getOnTypeFormatting() {
    return this.onTypeFormatting;
  }
  
  /**
   * Capabilities specific to the `textDocument/onTypeFormatting`
   */
  public void setOnTypeFormatting(final OnTypeFormattingCapabilities onTypeFormatting) {
    this.onTypeFormatting = onTypeFormatting;
  }
  
  /**
   * Capabilities specific to the `textDocument/declaration`
   * 
   * Since 3.14.0
   */
  @Pure
  public DeclarationCapabilities getDeclaration() {
    return this.declaration;
  }
  
  /**
   * Capabilities specific to the `textDocument/declaration`
   * 
   * Since 3.14.0
   */
  public void setDeclaration(final DeclarationCapabilities declaration) {
    this.declaration = declaration;
  }
  
  /**
   * Capabilities specific to the `textDocument/definition`
   * 
   * Since 3.14.0
   */
  @Pure
  public DefinitionCapabilities getDefinition() {
    return this.definition;
  }
  
  /**
   * Capabilities specific to the `textDocument/definition`
   * 
   * Since 3.14.0
   */
  public void setDefinition(final DefinitionCapabilities definition) {
    this.definition = definition;
  }
  
  /**
   * Capabilities specific to the `textDocument/typeDefinition`
   * 
   * Since 3.6.0
   */
  @Pure
  public TypeDefinitionCapabilities getTypeDefinition() {
    return this.typeDefinition;
  }
  
  /**
   * Capabilities specific to the `textDocument/typeDefinition`
   * 
   * Since 3.6.0
   */
  public void setTypeDefinition(final TypeDefinitionCapabilities typeDefinition) {
    this.typeDefinition = typeDefinition;
  }
  
  /**
   * Capabilities specific to the `textDocument/implementation`
   * 
   * Since 3.6.0
   */
  @Pure
  public ImplementationCapabilities getImplementation() {
    return this.implementation;
  }
  
  /**
   * Capabilities specific to the `textDocument/implementation`
   * 
   * Since 3.6.0
   */
  public void setImplementation(final ImplementationCapabilities implementation) {
    this.implementation = implementation;
  }
  
  /**
   * Capabilities specific to the `textDocument/codeAction`
   */
  @Pure
  public CodeActionCapabilities getCodeAction() {
    return this.codeAction;
  }
  
  /**
   * Capabilities specific to the `textDocument/codeAction`
   */
  public void setCodeAction(final CodeActionCapabilities codeAction) {
    this.codeAction = codeAction;
  }
  
  /**
   * Capabilities specific to the `textDocument/codeLens`
   */
  @Pure
  public CodeLensCapabilities getCodeLens() {
    return this.codeLens;
  }
  
  /**
   * Capabilities specific to the `textDocument/codeLens`
   */
  public void setCodeLens(final CodeLensCapabilities codeLens) {
    this.codeLens = codeLens;
  }
  
  /**
   * Capabilities specific to the `textDocument/documentLink`
   */
  @Pure
  public DocumentLinkCapabilities getDocumentLink() {
    return this.documentLink;
  }
  
  /**
   * Capabilities specific to the `textDocument/documentLink`
   */
  public void setDocumentLink(final DocumentLinkCapabilities documentLink) {
    this.documentLink = documentLink;
  }
  
  /**
   * Capabilities specific to the `textDocument/documentColor` and the
   * `textDocument/colorPresentation` request.
   * 
   * Since 3.6.0
   */
  @Pure
  public ColorProviderCapabilities getColorProvider() {
    return this.colorProvider;
  }
  
  /**
   * Capabilities specific to the `textDocument/documentColor` and the
   * `textDocument/colorPresentation` request.
   * 
   * Since 3.6.0
   */
  public void setColorProvider(final ColorProviderCapabilities colorProvider) {
    this.colorProvider = colorProvider;
  }
  
  /**
   * Capabilities specific to the `textDocument/rename`
   */
  @Pure
  public RenameCapabilities getRename() {
    return this.rename;
  }
  
  /**
   * Capabilities specific to the `textDocument/rename`
   */
  public void setRename(final RenameCapabilities rename) {
    this.rename = rename;
  }
  
  /**
   * Capabilities specific to `textDocument/publishDiagnostics`.
   */
  @Pure
  public PublishDiagnosticsCapabilities getPublishDiagnostics() {
    return this.publishDiagnostics;
  }
  
  /**
   * Capabilities specific to `textDocument/publishDiagnostics`.
   */
  public void setPublishDiagnostics(final PublishDiagnosticsCapabilities publishDiagnostics) {
    this.publishDiagnostics = publishDiagnostics;
  }
  
  /**
   * Capabilities specific to `textDocument/foldingRange` requests.
   * 
   * Since 3.10.0
   */
  @Pure
  public FoldingRangeCapabilities getFoldingRange() {
    return this.foldingRange;
  }
  
  /**
   * Capabilities specific to `textDocument/foldingRange` requests.
   * 
   * Since 3.10.0
   */
  public void setFoldingRange(final FoldingRangeCapabilities foldingRange) {
    this.foldingRange = foldingRange;
  }
  
  /**
   * Capabilities specific to {@code textDocument/semanticHighlighting}.
   * 
   * @deprecated Use {@code SemanticTokens} API instead.
   */
  @Pure
  @Deprecated
  public SemanticHighlightingCapabilities getSemanticHighlightingCapabilities() {
    return this.semanticHighlightingCapabilities;
  }
  
  /**
   * Capabilities specific to {@code textDocument/semanticHighlighting}.
   * 
   * @deprecated Use {@code SemanticTokens} API instead.
   */
  @Deprecated
  public void setSemanticHighlightingCapabilities(final SemanticHighlightingCapabilities semanticHighlightingCapabilities) {
    this.semanticHighlightingCapabilities = semanticHighlightingCapabilities;
  }
  
  /**
   * Capabilities specific to {@code textDocument/typeHierarchy}.
   */
  @Pure
  public TypeHierarchyCapabilities getTypeHierarchyCapabilities() {
    return this.typeHierarchyCapabilities;
  }
  
  /**
   * Capabilities specific to {@code textDocument/typeHierarchy}.
   */
  public void setTypeHierarchyCapabilities(final TypeHierarchyCapabilities typeHierarchyCapabilities) {
    this.typeHierarchyCapabilities = typeHierarchyCapabilities;
  }
  
  /**
   * Capabilities specific to {@code textDocument/prepareCallHierarchy}.
   * 
   * Since 3.16.0
   */
  @Pure
  public CallHierarchyCapabilities getCallHierarchy() {
    return this.callHierarchy;
  }
  
  /**
   * Capabilities specific to {@code textDocument/prepareCallHierarchy}.
   * 
   * Since 3.16.0
   */
  public void setCallHierarchy(final CallHierarchyCapabilities callHierarchy) {
    this.callHierarchy = callHierarchy;
  }
  
  /**
   * Capabilities specific to `textDocument/selectionRange` requests
   * 
   * Since 3.15.0
   */
  @Pure
  public SelectionRangeCapabilities getSelectionRange() {
    return this.selectionRange;
  }
  
  /**
   * Capabilities specific to `textDocument/selectionRange` requests
   * 
   * Since 3.15.0
   */
  public void setSelectionRange(final SelectionRangeCapabilities selectionRange) {
    this.selectionRange = selectionRange;
  }
  
  /**
   * Capabilities specific to {@code textDocument/semanticTokens}.
   * 
   * Since 3.16.0
   */
  @Pure
  public SemanticTokensCapabilities getSemanticTokens() {
    return this.semanticTokens;
  }
  
  /**
   * Capabilities specific to {@code textDocument/semanticTokens}.
   * 
   * Since 3.16.0
   */
  public void setSemanticTokens(final SemanticTokensCapabilities semanticTokens) {
    this.semanticTokens = semanticTokens;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("synchronization", this.synchronization);
    b.add("completion", this.completion);
    b.add("hover", this.hover);
    b.add("signatureHelp", this.signatureHelp);
    b.add("references", this.references);
    b.add("documentHighlight", this.documentHighlight);
    b.add("documentSymbol", this.documentSymbol);
    b.add("formatting", this.formatting);
    b.add("rangeFormatting", this.rangeFormatting);
    b.add("onTypeFormatting", this.onTypeFormatting);
    b.add("declaration", this.declaration);
    b.add("definition", this.definition);
    b.add("typeDefinition", this.typeDefinition);
    b.add("implementation", this.implementation);
    b.add("codeAction", this.codeAction);
    b.add("codeLens", this.codeLens);
    b.add("documentLink", this.documentLink);
    b.add("colorProvider", this.colorProvider);
    b.add("rename", this.rename);
    b.add("publishDiagnostics", this.publishDiagnostics);
    b.add("foldingRange", this.foldingRange);
    b.add("semanticHighlightingCapabilities", this.semanticHighlightingCapabilities);
    b.add("typeHierarchyCapabilities", this.typeHierarchyCapabilities);
    b.add("callHierarchy", this.callHierarchy);
    b.add("selectionRange", this.selectionRange);
    b.add("semanticTokens", this.semanticTokens);
    return b.toString();
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TextDocumentClientCapabilities other = (TextDocumentClientCapabilities) obj;
    if (this.synchronization == null) {
      if (other.synchronization != null)
        return false;
    } else if (!this.synchronization.equals(other.synchronization))
      return false;
    if (this.completion == null) {
      if (other.completion != null)
        return false;
    } else if (!this.completion.equals(other.completion))
      return false;
    if (this.hover == null) {
      if (other.hover != null)
        return false;
    } else if (!this.hover.equals(other.hover))
      return false;
    if (this.signatureHelp == null) {
      if (other.signatureHelp != null)
        return false;
    } else if (!this.signatureHelp.equals(other.signatureHelp))
      return false;
    if (this.references == null) {
      if (other.references != null)
        return false;
    } else if (!this.references.equals(other.references))
      return false;
    if (this.documentHighlight == null) {
      if (other.documentHighlight != null)
        return false;
    } else if (!this.documentHighlight.equals(other.documentHighlight))
      return false;
    if (this.documentSymbol == null) {
      if (other.documentSymbol != null)
        return false;
    } else if (!this.documentSymbol.equals(other.documentSymbol))
      return false;
    if (this.formatting == null) {
      if (other.formatting != null)
        return false;
    } else if (!this.formatting.equals(other.formatting))
      return false;
    if (this.rangeFormatting == null) {
      if (other.rangeFormatting != null)
        return false;
    } else if (!this.rangeFormatting.equals(other.rangeFormatting))
      return false;
    if (this.onTypeFormatting == null) {
      if (other.onTypeFormatting != null)
        return false;
    } else if (!this.onTypeFormatting.equals(other.onTypeFormatting))
      return false;
    if (this.declaration == null) {
      if (other.declaration != null)
        return false;
    } else if (!this.declaration.equals(other.declaration))
      return false;
    if (this.definition == null) {
      if (other.definition != null)
        return false;
    } else if (!this.definition.equals(other.definition))
      return false;
    if (this.typeDefinition == null) {
      if (other.typeDefinition != null)
        return false;
    } else if (!this.typeDefinition.equals(other.typeDefinition))
      return false;
    if (this.implementation == null) {
      if (other.implementation != null)
        return false;
    } else if (!this.implementation.equals(other.implementation))
      return false;
    if (this.codeAction == null) {
      if (other.codeAction != null)
        return false;
    } else if (!this.codeAction.equals(other.codeAction))
      return false;
    if (this.codeLens == null) {
      if (other.codeLens != null)
        return false;
    } else if (!this.codeLens.equals(other.codeLens))
      return false;
    if (this.documentLink == null) {
      if (other.documentLink != null)
        return false;
    } else if (!this.documentLink.equals(other.documentLink))
      return false;
    if (this.colorProvider == null) {
      if (other.colorProvider != null)
        return false;
    } else if (!this.colorProvider.equals(other.colorProvider))
      return false;
    if (this.rename == null) {
      if (other.rename != null)
        return false;
    } else if (!this.rename.equals(other.rename))
      return false;
    if (this.publishDiagnostics == null) {
      if (other.publishDiagnostics != null)
        return false;
    } else if (!this.publishDiagnostics.equals(other.publishDiagnostics))
      return false;
    if (this.foldingRange == null) {
      if (other.foldingRange != null)
        return false;
    } else if (!this.foldingRange.equals(other.foldingRange))
      return false;
    if (this.semanticHighlightingCapabilities == null) {
      if (other.semanticHighlightingCapabilities != null)
        return false;
    } else if (!this.semanticHighlightingCapabilities.equals(other.semanticHighlightingCapabilities))
      return false;
    if (this.typeHierarchyCapabilities == null) {
      if (other.typeHierarchyCapabilities != null)
        return false;
    } else if (!this.typeHierarchyCapabilities.equals(other.typeHierarchyCapabilities))
      return false;
    if (this.callHierarchy == null) {
      if (other.callHierarchy != null)
        return false;
    } else if (!this.callHierarchy.equals(other.callHierarchy))
      return false;
    if (this.selectionRange == null) {
      if (other.selectionRange != null)
        return false;
    } else if (!this.selectionRange.equals(other.selectionRange))
      return false;
    if (this.semanticTokens == null) {
      if (other.semanticTokens != null)
        return false;
    } else if (!this.semanticTokens.equals(other.semanticTokens))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.synchronization== null) ? 0 : this.synchronization.hashCode());
    result = prime * result + ((this.completion== null) ? 0 : this.completion.hashCode());
    result = prime * result + ((this.hover== null) ? 0 : this.hover.hashCode());
    result = prime * result + ((this.signatureHelp== null) ? 0 : this.signatureHelp.hashCode());
    result = prime * result + ((this.references== null) ? 0 : this.references.hashCode());
    result = prime * result + ((this.documentHighlight== null) ? 0 : this.documentHighlight.hashCode());
    result = prime * result + ((this.documentSymbol== null) ? 0 : this.documentSymbol.hashCode());
    result = prime * result + ((this.formatting== null) ? 0 : this.formatting.hashCode());
    result = prime * result + ((this.rangeFormatting== null) ? 0 : this.rangeFormatting.hashCode());
    result = prime * result + ((this.onTypeFormatting== null) ? 0 : this.onTypeFormatting.hashCode());
    result = prime * result + ((this.declaration== null) ? 0 : this.declaration.hashCode());
    result = prime * result + ((this.definition== null) ? 0 : this.definition.hashCode());
    result = prime * result + ((this.typeDefinition== null) ? 0 : this.typeDefinition.hashCode());
    result = prime * result + ((this.implementation== null) ? 0 : this.implementation.hashCode());
    result = prime * result + ((this.codeAction== null) ? 0 : this.codeAction.hashCode());
    result = prime * result + ((this.codeLens== null) ? 0 : this.codeLens.hashCode());
    result = prime * result + ((this.documentLink== null) ? 0 : this.documentLink.hashCode());
    result = prime * result + ((this.colorProvider== null) ? 0 : this.colorProvider.hashCode());
    result = prime * result + ((this.rename== null) ? 0 : this.rename.hashCode());
    result = prime * result + ((this.publishDiagnostics== null) ? 0 : this.publishDiagnostics.hashCode());
    result = prime * result + ((this.foldingRange== null) ? 0 : this.foldingRange.hashCode());
    result = prime * result + ((this.semanticHighlightingCapabilities== null) ? 0 : this.semanticHighlightingCapabilities.hashCode());
    result = prime * result + ((this.typeHierarchyCapabilities== null) ? 0 : this.typeHierarchyCapabilities.hashCode());
    result = prime * result + ((this.callHierarchy== null) ? 0 : this.callHierarchy.hashCode());
    result = prime * result + ((this.selectionRange== null) ? 0 : this.selectionRange.hashCode());
    return prime * result + ((this.semanticTokens== null) ? 0 : this.semanticTokens.hashCode());
  }
}
