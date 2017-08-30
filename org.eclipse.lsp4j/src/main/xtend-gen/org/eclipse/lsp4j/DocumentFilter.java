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
  private String schema;
  
  /**
   * A glob pattern, like `*.{ts,js}`.
   */
  private String pattern;
  
  public DocumentFilter() {
  }
  
  public DocumentFilter(final String language, final String schema, final String pattern) {
    this.language = language;
    this.schema = schema;
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
  public String getSchema() {
    return this.schema;
  }
  
  /**
   * A uri scheme, like `file` or `untitled`.
   */
  public void setSchema(final String schema) {
    this.schema = schema;
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
    b.add("schema", this.schema);
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
    if (this.schema == null) {
      if (other.schema != null)
        return false;
    } else if (!this.schema.equals(other.schema))
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
    result = prime * result + ((this.schema== null) ? 0 : this.schema.hashCode());
    result = prime * result + ((this.pattern== null) ? 0 : this.pattern.hashCode());
    return result;
  }
}
