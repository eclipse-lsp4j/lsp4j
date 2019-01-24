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
   * The actual location of the call.
   * 
   * <b>Must be defined</b> in resolved callers/callees.
   */
  private Location callLocation;
  
  /**
   * List of incoming calls.
   * 
   * <i>Note</i>: The items is <em>unresolved</em> if {@code callers} and {@code callees} is not defined.
   */
  private List<CallHierarchyItem> callers;
  
  /**
   * List of outgoing calls.
   * 
   * *Note*: The items is <em>unresolved</em> if {@code callers} and {@code callees} is not defined.
   */
  private List<CallHierarchyItem> callees;
  
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
   * The actual location of the call.
   * 
   * <b>Must be defined</b> in resolved callers/callees.
   */
  @Pure
  public Location getCallLocation() {
    return this.callLocation;
  }
  
  /**
   * The actual location of the call.
   * 
   * <b>Must be defined</b> in resolved callers/callees.
   */
  public void setCallLocation(final Location callLocation) {
    this.callLocation = callLocation;
  }
  
  /**
   * List of incoming calls.
   * 
   * <i>Note</i>: The items is <em>unresolved</em> if {@code callers} and {@code callees} is not defined.
   */
  @Pure
  public List<CallHierarchyItem> getCallers() {
    return this.callers;
  }
  
  /**
   * List of incoming calls.
   * 
   * <i>Note</i>: The items is <em>unresolved</em> if {@code callers} and {@code callees} is not defined.
   */
  public void setCallers(final List<CallHierarchyItem> callers) {
    this.callers = callers;
  }
  
  /**
   * List of outgoing calls.
   * 
   * *Note*: The items is <em>unresolved</em> if {@code callers} and {@code callees} is not defined.
   */
  @Pure
  public List<CallHierarchyItem> getCallees() {
    return this.callees;
  }
  
  /**
   * List of outgoing calls.
   * 
   * *Note*: The items is <em>unresolved</em> if {@code callers} and {@code callees} is not defined.
   */
  public void setCallees(final List<CallHierarchyItem> callees) {
    this.callees = callees;
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
    b.add("range", this.range);
    b.add("selectionRange", this.selectionRange);
    b.add("callLocation", this.callLocation);
    b.add("callers", this.callers);
    b.add("callees", this.callees);
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
    if (this.callLocation == null) {
      if (other.callLocation != null)
        return false;
    } else if (!this.callLocation.equals(other.callLocation))
      return false;
    if (this.callers == null) {
      if (other.callers != null)
        return false;
    } else if (!this.callers.equals(other.callers))
      return false;
    if (this.callees == null) {
      if (other.callees != null)
        return false;
    } else if (!this.callees.equals(other.callees))
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
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.selectionRange== null) ? 0 : this.selectionRange.hashCode());
    result = prime * result + ((this.callLocation== null) ? 0 : this.callLocation.hashCode());
    result = prime * result + ((this.callers== null) ? 0 : this.callers.hashCode());
    result = prime * result + ((this.callees== null) ? 0 : this.callees.hashCode());
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
