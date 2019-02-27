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
@SuppressWarnings("all")
public class MarkedString {
  @NonNull
  private String language;
  
  @NonNull
  private String value;
  
  public MarkedString() {
  }
  
  public MarkedString(@NonNull final String language, @NonNull final String value) {
    this.language = Preconditions.<String>checkNotNull(language, "language");
    this.value = Preconditions.<String>checkNotNull(value, "value");
  }
  
  @Pure
  @NonNull
  public String getLanguage() {
    return this.language;
  }
  
  public void setLanguage(@NonNull final String language) {
    if (language == null) {
      throw new IllegalArgumentException("Property must not be null: language");
    }
    this.language = language;
  }
  
  @Pure
  @NonNull
  public String getValue() {
    return this.value;
  }
  
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
    b.add("language", this.language);
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
    MarkedString other = (MarkedString) obj;
    if (this.language == null) {
      if (other.language != null)
        return false;
    } else if (!this.language.equals(other.language))
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
    result = prime * result + ((this.language== null) ? 0 : this.language.hashCode());
    return prime * result + ((this.value== null) ? 0 : this.value.hashCode());
  }
}
