/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A document filter denotes a document through properties like language, schema or pattern.
 */
@SuppressWarnings("all")
public class DocumentFilter {
  /**
   * A language id, like `typescript`.
   */
  private String language;
  
  /**
   * A uri scheme, like `file` or `untitled`.
   */
  private String scheme;
  
  /**
   * A glob pattern, like `*.{ts,js}`.
   */
  private String pattern;
  
  public DocumentFilter() {
  }
  
  public DocumentFilter(final String language, final String scheme, final String pattern) {
    this.language = language;
    this.scheme = scheme;
    this.pattern = pattern;
  }
  
  /**
   * A language id, like `typescript`.
   */
  @Pure
  public String getLanguage() {
    return this.language;
  }
  
  /**
   * A language id, like `typescript`.
   */
  public void setLanguage(final String language) {
    this.language = language;
  }
  
  /**
   * A uri scheme, like `file` or `untitled`.
   */
  @Pure
  public String getScheme() {
    return this.scheme;
  }
  
  /**
   * A uri scheme, like `file` or `untitled`.
   */
  public void setScheme(final String scheme) {
    this.scheme = scheme;
  }
  
  /**
   * A glob pattern, like `*.{ts,js}`.
   */
  @Pure
  public String getPattern() {
    return this.pattern;
  }
  
  /**
   * A glob pattern, like `*.{ts,js}`.
   */
  public void setPattern(final String pattern) {
    this.pattern = pattern;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("language", this.language);
    b.add("scheme", this.scheme);
    b.add("pattern", this.pattern);
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
    DocumentFilter other = (DocumentFilter) obj;
    if (this.language == null) {
      if (other.language != null)
        return false;
    } else if (!this.language.equals(other.language))
      return false;
    if (this.scheme == null) {
      if (other.scheme != null)
        return false;
    } else if (!this.scheme.equals(other.scheme))
      return false;
    if (this.pattern == null) {
      if (other.pattern != null)
        return false;
    } else if (!this.pattern.equals(other.pattern))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.language== null) ? 0 : this.language.hashCode());
    result = prime * result + ((this.scheme== null) ? 0 : this.scheme.hashCode());
    return prime * result + ((this.pattern== null) ? 0 : this.pattern.hashCode());
  }
}
