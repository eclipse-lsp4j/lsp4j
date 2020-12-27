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

import java.util.List;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.SymbolTag;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents programming constructs like variables, classes, interfaces etc. that appear in a document. Document symbols can be
 * hierarchical and they have two ranges: one that encloses its definition and one that points to its most interesting range,
 * e.g. the range of an identifier.
 */
@SuppressWarnings("all")
public class DocumentSymbol {
  /**
   * The name of this symbol.
   */
  @NonNull
  private String name;
  
  /**
   * The kind of this symbol.
   */
  @NonNull
  private SymbolKind kind;
  
  /**
   * The range enclosing this symbol not including leading/trailing whitespace but everything else
   * like comments. This information is typically used to determine if the clients cursor is
   * inside the symbol to reveal in the symbol in the UI.
   */
  @NonNull
  private Range range;
  
  /**
   * The range that should be selected and revealed when this symbol is being picked, e.g the name of a function.
   * Must be contained by the `range`.
   */
  @NonNull
  private Range selectionRange;
  
  /**
   * More detail for this symbol, e.g the signature of a function. If not provided the
   * name is used.
   */
  private String detail;
  
  /**
   * Tags for this document symbol.
   * 
   * Since 3.16.0
   */
  private List<SymbolTag> tags;
  
  /**
   * Indicates if this symbol is deprecated.
   * 
   * @deprecated Use `tags` instead if supported.
   */
  @Deprecated
  private Boolean deprecated;
  
  /**
   * Children of this symbol, e.g. properties of a class.
   */
  private List<DocumentSymbol> children;
  
  public DocumentSymbol() {
  }
  
  public DocumentSymbol(@NonNull final String name, @NonNull final SymbolKind kind, @NonNull final Range range, @NonNull final Range selectionRange) {
    this.name = Preconditions.<String>checkNotNull(name, "name");
    this.kind = Preconditions.<SymbolKind>checkNotNull(kind, "kind");
    this.range = Preconditions.<Range>checkNotNull(range, "range");
    this.selectionRange = Preconditions.<Range>checkNotNull(selectionRange, "selectionRange");
  }
  
  public DocumentSymbol(@NonNull final String name, @NonNull final SymbolKind kind, @NonNull final Range range, @NonNull final Range selectionRange, final String detail) {
    this(name, kind, range, selectionRange);
    this.detail = detail;
  }
  
  public DocumentSymbol(@NonNull final String name, @NonNull final SymbolKind kind, @NonNull final Range range, @NonNull final Range selectionRange, final String detail, final List<DocumentSymbol> children) {
    this(name, kind, range, selectionRange);
    this.detail = detail;
    this.children = children;
  }
  
  /**
   * The name of this symbol.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The name of this symbol.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  /**
   * The kind of this symbol.
   */
  @Pure
  @NonNull
  public SymbolKind getKind() {
    return this.kind;
  }
  
  /**
   * The kind of this symbol.
   */
  public void setKind(@NonNull final SymbolKind kind) {
    this.kind = Preconditions.checkNotNull(kind, "kind");
  }
  
  /**
   * The range enclosing this symbol not including leading/trailing whitespace but everything else
   * like comments. This information is typically used to determine if the clients cursor is
   * inside the symbol to reveal in the symbol in the UI.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range enclosing this symbol not including leading/trailing whitespace but everything else
   * like comments. This information is typically used to determine if the clients cursor is
   * inside the symbol to reveal in the symbol in the UI.
   */
  public void setRange(@NonNull final Range range) {
    this.range = Preconditions.checkNotNull(range, "range");
  }
  
  /**
   * The range that should be selected and revealed when this symbol is being picked, e.g the name of a function.
   * Must be contained by the `range`.
   */
  @Pure
  @NonNull
  public Range getSelectionRange() {
    return this.selectionRange;
  }
  
  /**
   * The range that should be selected and revealed when this symbol is being picked, e.g the name of a function.
   * Must be contained by the `range`.
   */
  public void setSelectionRange(@NonNull final Range selectionRange) {
    this.selectionRange = Preconditions.checkNotNull(selectionRange, "selectionRange");
  }
  
  /**
   * More detail for this symbol, e.g the signature of a function. If not provided the
   * name is used.
   */
  @Pure
  public String getDetail() {
    return this.detail;
  }
  
  /**
   * More detail for this symbol, e.g the signature of a function. If not provided the
   * name is used.
   */
  public void setDetail(final String detail) {
    this.detail = detail;
  }
  
  /**
   * Tags for this document symbol.
   * 
   * Since 3.16.0
   */
  @Pure
  public List<SymbolTag> getTags() {
    return this.tags;
  }
  
  /**
   * Tags for this document symbol.
   * 
   * Since 3.16.0
   */
  public void setTags(final List<SymbolTag> tags) {
    this.tags = tags;
  }
  
  /**
   * Indicates if this symbol is deprecated.
   * 
   * @deprecated Use `tags` instead if supported.
   */
  @Pure
  @Deprecated
  public Boolean getDeprecated() {
    return this.deprecated;
  }
  
  /**
   * Indicates if this symbol is deprecated.
   * 
   * @deprecated Use `tags` instead if supported.
   */
  @Deprecated
  public void setDeprecated(final Boolean deprecated) {
    this.deprecated = deprecated;
  }
  
  /**
   * Children of this symbol, e.g. properties of a class.
   */
  @Pure
  public List<DocumentSymbol> getChildren() {
    return this.children;
  }
  
  /**
   * Children of this symbol, e.g. properties of a class.
   */
  public void setChildren(final List<DocumentSymbol> children) {
    this.children = children;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("kind", this.kind);
    b.add("range", this.range);
    b.add("selectionRange", this.selectionRange);
    b.add("detail", this.detail);
    b.add("tags", this.tags);
    b.add("deprecated", this.deprecated);
    b.add("children", this.children);
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
    DocumentSymbol other = (DocumentSymbol) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.selectionRange == null) {
      if (other.selectionRange != null)
        return false;
    } else if (!this.selectionRange.equals(other.selectionRange))
      return false;
    if (this.detail == null) {
      if (other.detail != null)
        return false;
    } else if (!this.detail.equals(other.detail))
      return false;
    if (this.tags == null) {
      if (other.tags != null)
        return false;
    } else if (!this.tags.equals(other.tags))
      return false;
    if (this.deprecated == null) {
      if (other.deprecated != null)
        return false;
    } else if (!this.deprecated.equals(other.deprecated))
      return false;
    if (this.children == null) {
      if (other.children != null)
        return false;
    } else if (!this.children.equals(other.children))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.selectionRange== null) ? 0 : this.selectionRange.hashCode());
    result = prime * result + ((this.detail== null) ? 0 : this.detail.hashCode());
    result = prime * result + ((this.tags== null) ? 0 : this.tags.hashCode());
    result = prime * result + ((this.deprecated== null) ? 0 : this.deprecated.hashCode());
    return prime * result + ((this.children== null) ? 0 : this.children.hashCode());
  }
}
