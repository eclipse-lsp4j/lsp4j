/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import com.google.gson.annotations.SerializedName;
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
  private String filter;
  
  /**
   * The name of the filter. This will be shown in the UI.
   */
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
  public String getFilter() {
    return this.filter;
  }
  
  /**
   * The internal ID of the filter. This value is passed to the setExceptionBreakpoints request.
   */
  public void setFilter(final String filter) {
    this.filter = filter;
  }
  
  /**
   * The name of the filter. This will be shown in the UI.
   */
  @Pure
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The name of the filter. This will be shown in the UI.
   */
  public void setLabel(final String label) {
    this.label = label;
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
    result = prime * result + ((this.default_== null) ? 0 : this.default_.hashCode());
    return result;
  }
}
