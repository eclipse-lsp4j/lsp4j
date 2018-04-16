/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A MarkupContent literal represents a string value which content is interpreted based on its
 * kind flag. Currently the protocol supports `plaintext` and `markdown` as markup kinds.
 * 
 * If the kind is `markdown` then the value can contain fenced code blocks like in GitHub issues.
 * See https://help.github.com/articles/creating-and-highlighting-code-blocks/#syntax-highlighting
 * 
 * Please Note that clients might sanitize the return markdown. A client could decide to
 * remove HTML from the markdown to avoid script execution.
 */
@SuppressWarnings("all")
public class MarkupContent {
  /**
   * The type of the Markup.
   */
  @NonNull
  private String MarkupKind;
  
  /**
   * The content itself.
   */
  @NonNull
  private String value;
  
  /**
   * The type of the Markup.
   */
  @Pure
  @NonNull
  public String getMarkupKind() {
    return this.MarkupKind;
  }
  
  /**
   * The type of the Markup.
   */
  public void setMarkupKind(@NonNull final String MarkupKind) {
    this.MarkupKind = MarkupKind;
  }
  
  /**
   * The content itself.
   */
  @Pure
  @NonNull
  public String getValue() {
    return this.value;
  }
  
  /**
   * The content itself.
   */
  public void setValue(@NonNull final String value) {
    this.value = value;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("MarkupKind", this.MarkupKind);
    b.add("value", this.value);
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
    MarkupContent other = (MarkupContent) obj;
    if (this.MarkupKind == null) {
      if (other.MarkupKind != null)
        return false;
    } else if (!this.MarkupKind.equals(other.MarkupKind))
      return false;
    if (this.value == null) {
      if (other.value != null)
        return false;
    } else if (!this.value.equals(other.value))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.MarkupKind== null) ? 0 : this.MarkupKind.hashCode());
    result = prime * result + ((this.value== null) ? 0 : this.value.hashCode());
    return result;
  }
}
