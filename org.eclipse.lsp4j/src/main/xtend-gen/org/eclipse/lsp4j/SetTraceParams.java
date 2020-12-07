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
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A notification that should be used by the client to modify the trace setting of the server.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class SetTraceParams {
  /**
   * The new value that should be assigned to the trace setting.
   * For values, see {@link TraceValue}.
   */
  @NonNull
  private String value;
  
  public SetTraceParams() {
  }
  
  public SetTraceParams(@NonNull final String value) {
    this.value = Preconditions.<String>checkNotNull(value, "value");
  }
  
  /**
   * The new value that should be assigned to the trace setting.
   * For values, see {@link TraceValue}.
   */
  @Pure
  @NonNull
  public String getValue() {
    return this.value;
  }
  
  /**
   * The new value that should be assigned to the trace setting.
   * For values, see {@link TraceValue}.
   */
  public void setValue(@NonNull final String value) {
    this.value = Preconditions.checkNotNull(value, "value");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
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
    SetTraceParams other = (SetTraceParams) obj;
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
    return 31 * 1 + ((this.value== null) ? 0 : this.value.hashCode());
  }
}
