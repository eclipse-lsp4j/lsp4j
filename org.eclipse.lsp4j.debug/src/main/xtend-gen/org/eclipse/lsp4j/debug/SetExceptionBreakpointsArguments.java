/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.lsp4j.debug.ExceptionOptions;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'setExceptionBreakpoints' request.
 */
@SuppressWarnings("all")
public class SetExceptionBreakpointsArguments {
  /**
   * IDs of checked exception options. The set of IDs is returned via the 'exceptionBreakpointFilters' capability.
   */
  @NonNull
  private String[] filters;
  
  /**
   * Configuration options for selected exceptions.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsExceptionOptions' is true.
   * <p>
   * This is an optional property.
   */
  private ExceptionOptions[] exceptionOptions;
  
  /**
   * IDs of checked exception options. The set of IDs is returned via the 'exceptionBreakpointFilters' capability.
   */
  @Pure
  @NonNull
  public String[] getFilters() {
    return this.filters;
  }
  
  /**
   * IDs of checked exception options. The set of IDs is returned via the 'exceptionBreakpointFilters' capability.
   */
  public void setFilters(@NonNull final String[] filters) {
    this.filters = Preconditions.checkNotNull(filters, "filters");
  }
  
  /**
   * Configuration options for selected exceptions.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsExceptionOptions' is true.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ExceptionOptions[] getExceptionOptions() {
    return this.exceptionOptions;
  }
  
  /**
   * Configuration options for selected exceptions.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsExceptionOptions' is true.
   * <p>
   * This is an optional property.
   */
  public void setExceptionOptions(final ExceptionOptions[] exceptionOptions) {
    this.exceptionOptions = exceptionOptions;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("filters", this.filters);
    b.add("exceptionOptions", this.exceptionOptions);
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
    SetExceptionBreakpointsArguments other = (SetExceptionBreakpointsArguments) obj;
    if (this.filters == null) {
      if (other.filters != null)
        return false;
    } else if (!Arrays.deepEquals(this.filters, other.filters))
      return false;
    if (this.exceptionOptions == null) {
      if (other.exceptionOptions != null)
        return false;
    } else if (!Arrays.deepEquals(this.exceptionOptions, other.exceptionOptions))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.filters== null) ? 0 : Arrays.deepHashCode(this.filters));
    return prime * result + ((this.exceptionOptions== null) ? 0 : Arrays.deepHashCode(this.exceptionOptions));
  }
}
