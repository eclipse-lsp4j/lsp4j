/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Color;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The color presentation request is sent from the client to the server to obtain a list of presentations
 * for a color value at a given location.
 * 
 * Since 3.6.0
 */
@SuppressWarnings("all")
public class ColorPresentationParams {
  /**
   * The text document.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * The color information to request presentations for.
   */
  @NonNull
  private Color colorInfo;
  
  /**
   * The range where the color would be inserted. Serves as a context.
   */
  @NonNull
  private Range range;
  
  public ColorPresentationParams() {
  }
  
  public ColorPresentationParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Color colorInfo, @NonNull final Range range) {
    this.textDocument = textDocument;
    this.colorInfo = colorInfo;
    this.range = range;
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
    this.textDocument = textDocument;
  }
  
  /**
   * The color information to request presentations for.
   */
  @Pure
  @NonNull
  public Color getColorInfo() {
    return this.colorInfo;
  }
  
  /**
   * The color information to request presentations for.
   */
  public void setColorInfo(@NonNull final Color colorInfo) {
    this.colorInfo = colorInfo;
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
    this.range = range;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("colorInfo", this.colorInfo);
    b.add("range", this.range);
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
    ColorPresentationParams other = (ColorPresentationParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.colorInfo == null) {
      if (other.colorInfo != null)
        return false;
    } else if (!this.colorInfo.equals(other.colorInfo))
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
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.colorInfo== null) ? 0 : this.colorInfo.hashCode());
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    return result;
  }
}
