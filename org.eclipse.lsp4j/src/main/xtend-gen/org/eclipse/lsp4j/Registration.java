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

import com.google.gson.annotations.JsonAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.JsonElementTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * General parameters to register for a capability.
 */
@SuppressWarnings("all")
public class Registration {
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again.
   */
  @NonNull
  private String id;
  
  /**
   * The method / capability to register for.
   */
  @NonNull
  private String method;
  
  /**
   * Options necessary for the registration.
   */
  @JsonAdapter(JsonElementTypeAdapter.Factory.class)
  private Object registerOptions;
  
  public Registration() {
  }
  
  public Registration(@NonNull final String id, @NonNull final String method) {
    this.id = Preconditions.<String>checkNotNull(id, "id");
    this.method = Preconditions.<String>checkNotNull(method, "method");
  }
  
  public Registration(@NonNull final String id, @NonNull final String method, final Object registerOptions) {
    this(id, method);
    this.registerOptions = registerOptions;
  }
  
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again.
   */
  @Pure
  @NonNull
  public String getId() {
    return this.id;
  }
  
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again.
   */
  public void setId(@NonNull final String id) {
    if (id == null) {
      throw new IllegalArgumentException("Property must not be null: id");
    }
    this.id = id;
  }
  
  /**
   * The method / capability to register for.
   */
  @Pure
  @NonNull
  public String getMethod() {
    return this.method;
  }
  
  /**
   * The method / capability to register for.
   */
  public void setMethod(@NonNull final String method) {
    if (method == null) {
      throw new IllegalArgumentException("Property must not be null: method");
    }
    this.method = method;
  }
  
  /**
   * Options necessary for the registration.
   */
  @Pure
  public Object getRegisterOptions() {
    return this.registerOptions;
  }
  
  /**
   * Options necessary for the registration.
   */
  public void setRegisterOptions(final Object registerOptions) {
    this.registerOptions = registerOptions;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("method", this.method);
    b.add("registerOptions", this.registerOptions);
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
    Registration other = (Registration) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.method == null) {
      if (other.method != null)
        return false;
    } else if (!this.method.equals(other.method))
      return false;
    if (this.registerOptions == null) {
      if (other.registerOptions != null)
        return false;
    } else if (!this.registerOptions.equals(other.registerOptions))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.method== null) ? 0 : this.method.hashCode());
    return prime * result + ((this.registerOptions== null) ? 0 : this.registerOptions.hashCode());
  }
}
