/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import com.google.common.annotations.Beta;
import java.util.List;
import org.eclipse.lsp4j.SemanticHighlightingInformation;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Parameters for the semantic highlighting (server-side) push notification.
 */
@Beta
@SuppressWarnings("all")
public class SemanticHighlightingParams {
  /**
   * The text document that has to be decorated with the semantic highlighting information.
   */
  @NonNull
  private VersionedTextDocumentIdentifier textDocument;
  
  /**
   * An array of semantic highlighting information.
   */
  @NonNull
  private List<SemanticHighlightingInformation> lines;
  
  public SemanticHighlightingParams() {
  }
  
  public SemanticHighlightingParams(@NonNull final VersionedTextDocumentIdentifier textDocument, @NonNull final List<SemanticHighlightingInformation> lines) {
    this.textDocument = textDocument;
    this.lines = lines;
  }
  
  /**
   * The text document that has to be decorated with the semantic highlighting information.
   */
  @Pure
  @NonNull
  public VersionedTextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The text document that has to be decorated with the semantic highlighting information.
   */
  public void setTextDocument(@NonNull final VersionedTextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * An array of semantic highlighting information.
   */
  @Pure
  @NonNull
  public List<SemanticHighlightingInformation> getLines() {
    return this.lines;
  }
  
  /**
   * An array of semantic highlighting information.
   */
  public void setLines(@NonNull final List<SemanticHighlightingInformation> lines) {
    this.lines = lines;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("lines", this.lines);
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
    SemanticHighlightingParams other = (SemanticHighlightingParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.lines == null) {
      if (other.lines != null)
        return false;
    } else if (!this.lines.equals(other.lines))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    return prime * result + ((this.lines== null) ? 0 : this.lines.hashCode());
  }
}
