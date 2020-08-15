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

import org.eclipse.lsp4j.Color;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.WorkDoneProgressAndPartialResultParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The color presentation request is sent from the client to the server to obtain a list of presentations
 * for a color value at a given location.
 * 
 * Since 3.6.0
 */
@SuppressWarnings("all")
public class ColorPresentationParams extends WorkDoneProgressAndPartialResultParams {
  /**
   * The text document.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * The color information to request presentations for.
   */
  @NonNull
  private Color color;
  
  /**
   * The range where the color would be inserted. Serves as a context.
   */
  @NonNull
  private Range range;
  
  public ColorPresentationParams() {
  }
  
  public ColorPresentationParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Color color, @NonNull final Range range) {
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument, "textDocument");
    this.color = Preconditions.<Color>checkNotNull(color, "color");
    this.range = Preconditions.<Range>checkNotNull(range, "range");
  }
  
  /**
   * The text document.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The text document.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = Preconditions.checkNotNull(textDocument, "textDocument");
  }
  
  /**
   * The color information to request presentations for.
   */
  @Pure
  @NonNull
  public Color getColor() {
    return this.color;
  }
  
  /**
   * The color information to request presentations for.
   */
  public void setColor(@NonNull final Color color) {
    this.color = Preconditions.checkNotNull(color, "color");
  }
  
  /**
   * The range where the color would be inserted. Serves as a context.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range where the color would be inserted. Serves as a context.
   */
  public void setRange(@NonNull final Range range) {
    this.range = Preconditions.checkNotNull(range, "range");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("color", this.color);
    b.add("range", this.range);
    b.add("workDoneToken", getWorkDoneToken());
    b.add("partialResultToken", getPartialResultToken());
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
    if (!super.equals(obj))
      return false;
    ColorPresentationParams other = (ColorPresentationParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.color == null) {
      if (other.color != null)
        return false;
    } else if (!this.color.equals(other.color))
      return false;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.color== null) ? 0 : this.color.hashCode());
    return prime * result + ((this.range== null) ? 0 : this.range.hashCode());
  }
}
