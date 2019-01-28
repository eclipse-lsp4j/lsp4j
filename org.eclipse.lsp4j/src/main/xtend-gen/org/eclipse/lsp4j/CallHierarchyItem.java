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

import com.google.common.annotations.Beta;
import com.google.gson.annotations.JsonAdapter;
import java.util.List;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.jsonrpc.json.adapters.JsonElementTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The result of a {@code textDocument/callHierarchy} request.
 */
@Beta
@SuppressWarnings("all")
public class CallHierarchyItem {
  /**
   * The name of the symbol targeted by the call hierarchy request.
   */
  @NonNull
  private String name;
  
  /**
   * More detail for this symbol, e.g the signature of a function.
   */
  private String detail;
  
  /**
   * The kind of this symbol.
   */
  @NonNull
  private SymbolKind kind;
  
  /**
   * URI of the document containing the symbol.
   */
  @NonNull
  private String uri;
  
  /**
   * {@code true} if the hierarchy item is deprecated. Otherwise, {@code false}. It is {@code false} by default.
   */
  private Boolean deprecated;
  
  /**
   * The range enclosing this symbol not including leading/trailing whitespace but everything else
   * like comments. This information is typically used to determine if the the clients cursor is
   * inside the symbol to reveal in the symbol in the UI.
   */
  @NonNull
  private Range range;
  
  /**
   * The range that should be selected and revealed when this symbol is being picked, e.g the name of a function.
   * Must be contained by the the {@code range}.
   */
  @NonNull
  private Range selectionRange;
  
  /**
   * The actual locations of incoming (or outgoing) calls to (or from) a callable identified by this item.
   * 
   * <b>Note</b>: undefined in root item.
   */
  private List<Location> callLocations;
  
  /**
   * List of incoming (or outgoing) calls to (or from) a callable identified by this item.
   * 
   * <b>Note</b>: if undefined, this item is unresolved.
   */
  private List<CallHierarchyItem> calls;
  
  /**
   * Optional data to identify an item in a resolve request.
   */
  @JsonAdapter(JsonElementTypeAdapter.Factory.class)
  private Object data;
  
  /**
   * The name of the symbol targeted by the call hierarchy request.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The name of the symbol targeted by the call hierarchy request.
   */
  public void setName(@NonNull final String name) {
    this.name = name;
  }
  
  /**
   * More detail for this symbol, e.g the signature of a function.
   */
  @Pure
  public String getDetail() {
    return this.detail;
  }
  
  /**
   * More detail for this symbol, e.g the signature of a function.
   */
  public void setDetail(final String detail) {
    this.detail = detail;
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
    this.kind = kind;
  }
  
  /**
   * URI of the document containing the symbol.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * URI of the document containing the symbol.
   */
  public void setUri(@NonNull final String uri) {
    this.uri = uri;
  }
  
  /**
   * {@code true} if the hierarchy item is deprecated. Otherwise, {@code false}. It is {@code false} by default.
   */
  @Pure
  public Boolean getDeprecated() {
    return this.deprecated;
  }
  
  /**
   * {@code true} if the hierarchy item is deprecated. Otherwise, {@code false}. It is {@code false} by default.
   */
  public void setDeprecated(final Boolean deprecated) {
    this.deprecated = deprecated;
  }
  
  /**
   * The range enclosing this symbol not including leading/trailing whitespace but everything else
   * like comments. This information is typically used to determine if the the clients cursor is
   * inside the symbol to reveal in the symbol in the UI.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range enclosing this symbol not including leading/trailing whitespace but everything else
   * like comments. This information is typically used to determine if the the clients cursor is
   * inside the symbol to reveal in the symbol in the UI.
   */
  public void setRange(@NonNull final Range range) {
    this.range = range;
  }
  
  /**
   * The range that should be selected and revealed when this symbol is being picked, e.g the name of a function.
   * Must be contained by the the {@code range}.
   */
  @Pure
  @NonNull
  public Range getSelectionRange() {
    return this.selectionRange;
  }
  
  /**
   * The range that should be selected and revealed when this symbol is being picked, e.g the name of a function.
   * Must be contained by the the {@code range}.
   */
  public void setSelectionRange(@NonNull final Range selectionRange) {
    this.selectionRange = selectionRange;
  }
  
  /**
   * The actual locations of incoming (or outgoing) calls to (or from) a callable identified by this item.
   * 
   * <b>Note</b>: undefined in root item.
   */
  @Pure
  public List<Location> getCallLocations() {
    return this.callLocations;
  }
  
  /**
   * The actual locations of incoming (or outgoing) calls to (or from) a callable identified by this item.
   * 
   * <b>Note</b>: undefined in root item.
   */
  public void setCallLocations(final List<Location> callLocations) {
    this.callLocations = callLocations;
  }
  
  /**
   * List of incoming (or outgoing) calls to (or from) a callable identified by this item.
   * 
   * <b>Note</b>: if undefined, this item is unresolved.
   */
  @Pure
  public List<CallHierarchyItem> getCalls() {
    return this.calls;
  }
  
  /**
   * List of incoming (or outgoing) calls to (or from) a callable identified by this item.
   * 
   * <b>Note</b>: if undefined, this item is unresolved.
   */
  public void setCalls(final List<CallHierarchyItem> calls) {
    this.calls = calls;
  }
  
  /**
   * Optional data to identify an item in a resolve request.
   */
  @Pure
  public Object getData() {
    return this.data;
  }
  
  /**
   * Optional data to identify an item in a resolve request.
   */
  public void setData(final Object data) {
    this.data = data;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("detail", this.detail);
    b.add("kind", this.kind);
    b.add("uri", this.uri);
    b.add("deprecated", this.deprecated);
    b.add("range", this.range);
    b.add("selectionRange", this.selectionRange);
    b.add("callLocations", this.callLocations);
    b.add("calls", this.calls);
    b.add("data", this.data);
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
    CallHierarchyItem other = (CallHierarchyItem) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.detail == null) {
      if (other.detail != null)
        return false;
    } else if (!this.detail.equals(other.detail))
      return false;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.deprecated == null) {
      if (other.deprecated != null)
        return false;
    } else if (!this.deprecated.equals(other.deprecated))
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
    if (this.callLocations == null) {
      if (other.callLocations != null)
        return false;
    } else if (!this.callLocations.equals(other.callLocations))
      return false;
    if (this.calls == null) {
      if (other.calls != null)
        return false;
    } else if (!this.calls.equals(other.calls))
      return false;
    if (this.data == null) {
      if (other.data != null)
        return false;
    } else if (!this.data.equals(other.data))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.detail== null) ? 0 : this.detail.hashCode());
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    result = prime * result + ((this.deprecated== null) ? 0 : this.deprecated.hashCode());
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.selectionRange== null) ? 0 : this.selectionRange.hashCode());
    result = prime * result + ((this.callLocations== null) ? 0 : this.callLocations.hashCode());
    result = prime * result + ((this.calls== null) ? 0 : this.calls.hashCode());
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
