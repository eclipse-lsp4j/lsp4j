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

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
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
  private String kind;
  
  /**
   * The content itself.
   */
  @NonNull
  private String value;
  
  public MarkupContent() {
  }
  
  public MarkupContent(@NonNull final String kind, @NonNull final String value) {
    this.kind = Preconditions.<String>checkNotNull(kind, "kind");
    this.value = Preconditions.<String>checkNotNull(value, "value");
  }
  
  /**
   * The type of the Markup.
   */
  @Pure
  @NonNull
  public String getKind() {
    return this.kind;
  }
  
  /**
   * The type of the Markup.
   */
  public void setKind(@NonNull final String kind) {
    if (kind == null) {
      throw new IllegalArgumentException("Property must not be null: kind");
    }
    this.kind = kind;
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
    if (value == null) {
      throw new IllegalArgumentException("Property must not be null: value");
    }
    this.value = value;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("kind", this.kind);
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
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
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
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    return prime * result + ((this.value== null) ? 0 : this.value.hashCode());
  }
}
