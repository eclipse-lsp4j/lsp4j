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

import com.google.gson.annotations.SerializedName;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An ExceptionBreakpointsFilter is shown in the UI as an option for configuring how exceptions are dealt with.
 */
@SuppressWarnings("all")
public class ExceptionBreakpointsFilter {
  /**
   * The internal ID of the filter. This value is passed to the setExceptionBreakpoints request.
   */
  @NonNull
  private String filter;
  
  /**
   * The name of the filter. This will be shown in the UI.
   */
  @NonNull
  private String label;
  
  /**
   * Initial value of the filter. If not specified a value 'false' is assumed.
   * <p>
   * This is an optional property.
   */
  @SerializedName(value = "default")
  private Boolean default_;
  
  /**
   * The internal ID of the filter. This value is passed to the setExceptionBreakpoints request.
   */
  @Pure
  @NonNull
  public String getFilter() {
    return this.filter;
  }
  
  /**
   * The internal ID of the filter. This value is passed to the setExceptionBreakpoints request.
   */
  public void setFilter(@NonNull final String filter) {
    this.filter = Preconditions.checkNotNull(filter, "filter");
  }
  
  /**
   * The name of the filter. This will be shown in the UI.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The name of the filter. This will be shown in the UI.
   */
  public void setLabel(@NonNull final String label) {
    this.label = Preconditions.checkNotNull(label, "label");
  }
  
  /**
   * Initial value of the filter. If not specified a value 'false' is assumed.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getDefault_() {
    return this.default_;
  }
  
  /**
   * Initial value of the filter. If not specified a value 'false' is assumed.
   * <p>
   * This is an optional property.
   */
  public void setDefault_(final Boolean default_) {
    this.default_ = default_;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("filter", this.filter);
    b.add("label", this.label);
    b.add("default_", this.default_);
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
    ExceptionBreakpointsFilter other = (ExceptionBreakpointsFilter) obj;
    if (this.filter == null) {
      if (other.filter != null)
        return false;
    } else if (!this.filter.equals(other.filter))
      return false;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    if (this.default_ == null) {
      if (other.default_ != null)
        return false;
    } else if (!this.default_.equals(other.default_))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.filter== null) ? 0 : this.filter.hashCode());
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    return prime * result + ((this.default_== null) ? 0 : this.default_.hashCode());
  }
}
