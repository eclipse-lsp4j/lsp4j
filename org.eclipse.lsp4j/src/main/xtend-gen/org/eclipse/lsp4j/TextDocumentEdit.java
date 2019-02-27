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

import com.google.common.base.Preconditions;
import java.util.List;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Describes textual changes on a single text document.
 * The text document is referred to as a `VersionedTextDocumentIdentifier`
 * to allow clients to check the text document version before an edit is applied.
 */
@SuppressWarnings("all")
public class TextDocumentEdit {
  /**
   * The text document to change.
   */
  @NonNull
  private VersionedTextDocumentIdentifier textDocument;
  
  /**
   * The edits to be applied
   */
  @NonNull
  private List<TextEdit> edits;
  
  public TextDocumentEdit() {
  }
  
  public TextDocumentEdit(@NonNull final VersionedTextDocumentIdentifier textDocument, @NonNull final List<TextEdit> edits) {
    this.textDocument = Preconditions.<VersionedTextDocumentIdentifier>checkNotNull(textDocument);
    this.edits = Preconditions.<List<TextEdit>>checkNotNull(edits);
  }
  
  /**
   * The text document to change.
   */
  @Pure
  @NonNull
  public VersionedTextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The text document to change.
   */
  public void setTextDocument(@NonNull final VersionedTextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * The edits to be applied
   */
  @Pure
  @NonNull
  public List<TextEdit> getEdits() {
    return this.edits;
  }
  
  /**
   * The edits to be applied
   */
  public void setEdits(@NonNull final List<TextEdit> edits) {
    this.edits = edits;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("edits", this.edits);
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
    TextDocumentEdit other = (TextDocumentEdit) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.edits == null) {
      if (other.edits != null)
        return false;
    } else if (!this.edits.equals(other.edits))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    return prime * result + ((this.edits== null) ? 0 : this.edits.hashCode());
  }
}
