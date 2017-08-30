/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.CodeActionCapabilities;
import org.eclipse.lsp4j.CodeLensCapabilities;
import org.eclipse.lsp4j.CompletionCapabilities;
import org.eclipse.lsp4j.DefinitionCapabilities;
import org.eclipse.lsp4j.DocumentHighlightCapabilities;
import org.eclipse.lsp4j.DocumentLinkCapabilities;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.FormattingCapabilities;
import org.eclipse.lsp4j.HoverCapabilities;
import org.eclipse.lsp4j.OnTypeFormattingCapabilities;
import org.eclipse.lsp4j.RangeFormattingCapabilities;
import org.eclipse.lsp4j.ReferencesCapabilities;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.SignatureHelpCapabilities;
import org.eclipse.lsp4j.SynchronizationCapabilities;
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
   * Capabilities specific to the `textDocument/definition`
   */
  private DefinitionCapabilities definition;
  
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
   * Capabilities specific to the `textDocument/rename`
   */
  private RenameCapabilities rename;
  
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
   * Capabilities specific to the `textDocument/definition`
   */
  @Pure
  public DefinitionCapabilities getDefinition() {
    return this.definition;
  }
  
  /**
   * Capabilities specific to the `textDocument/definition`
   */
  public void setDefinition(final DefinitionCapabilities definition) {
    this.definition = definition;
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
    b.add("definition", this.definition);
    b.add("codeAction", this.codeAction);
    b.add("codeLens", this.codeLens);
    b.add("documentLink", this.documentLink);
    b.add("rename", this.rename);
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
    if (this.definition == null) {
      if (other.definition != null)
        return false;
    } else if (!this.definition.equals(other.definition))
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
    if (this.rename == null) {
      if (other.rename != null)
        return false;
    } else if (!this.rename.equals(other.rename))
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
    result = prime * result + ((this.definition== null) ? 0 : this.definition.hashCode());
    result = prime * result + ((this.codeAction== null) ? 0 : this.codeAction.hashCode());
    result = prime * result + ((this.codeLens== null) ? 0 : this.codeLens.hashCode());
    result = prime * result + ((this.documentLink== null) ? 0 : this.documentLink.hashCode());
    result = prime * result + ((this.rename== null) ? 0 : this.rename.hashCode());
    return result;
  }
}
