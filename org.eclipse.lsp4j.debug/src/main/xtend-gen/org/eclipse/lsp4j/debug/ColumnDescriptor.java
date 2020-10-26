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

import org.eclipse.lsp4j.debug.ColumnDescriptorType;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A ColumnDescriptor specifies what module attribute to show in a column of the ModulesView, how to format it,
 * <p>
 * and what the column's label should be.
 * <p>
 * It is only used if the underlying UI actually supports this level of customization.
 */
@SuppressWarnings("all")
public class ColumnDescriptor {
  /**
   * Name of the attribute rendered in this column.
   */
  @NonNull
  private String attributeName;
  
  /**
   * Header UI label of column.
   */
  @NonNull
  private String label;
  
  /**
   * Format to use for the rendered values in this column. TBD how the format strings looks like.
   * <p>
   * This is an optional property.
   */
  private String format;
  
  /**
   * Datatype of values in this column.  Defaults to 'string' if not specified.
   * <p>
   * This is an optional property.
   */
  private ColumnDescriptorType type;
  
  /**
   * Width of this column in characters (hint only).
   * <p>
   * This is an optional property.
   */
  private Integer width;
  
  /**
   * Name of the attribute rendered in this column.
   */
  @Pure
  @NonNull
  public String getAttributeName() {
    return this.attributeName;
  }
  
  /**
   * Name of the attribute rendered in this column.
   */
  public void setAttributeName(@NonNull final String attributeName) {
    this.attributeName = Preconditions.checkNotNull(attributeName, "attributeName");
  }
  
  /**
   * Header UI label of column.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * Header UI label of column.
   */
  public void setLabel(@NonNull final String label) {
    this.label = Preconditions.checkNotNull(label, "label");
  }
  
  /**
   * Format to use for the rendered values in this column. TBD how the format strings looks like.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getFormat() {
    return this.format;
  }
  
  /**
   * Format to use for the rendered values in this column. TBD how the format strings looks like.
   * <p>
   * This is an optional property.
   */
  public void setFormat(final String format) {
    this.format = format;
  }
  
  /**
   * Datatype of values in this column.  Defaults to 'string' if not specified.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ColumnDescriptorType getType() {
    return this.type;
  }
  
  /**
   * Datatype of values in this column.  Defaults to 'string' if not specified.
   * <p>
   * This is an optional property.
   */
  public void setType(final ColumnDescriptorType type) {
    this.type = type;
  }
  
  /**
   * Width of this column in characters (hint only).
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getWidth() {
    return this.width;
  }
  
  /**
   * Width of this column in characters (hint only).
   * <p>
   * This is an optional property.
   */
  public void setWidth(final Integer width) {
    this.width = width;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("attributeName", this.attributeName);
    b.add("label", this.label);
    b.add("format", this.format);
    b.add("type", this.type);
    b.add("width", this.width);
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
    ColumnDescriptor other = (ColumnDescriptor) obj;
    if (this.attributeName == null) {
      if (other.attributeName != null)
        return false;
    } else if (!this.attributeName.equals(other.attributeName))
      return false;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    if (this.format == null) {
      if (other.format != null)
        return false;
    } else if (!this.format.equals(other.format))
      return false;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    if (this.width == null) {
      if (other.width != null)
        return false;
    } else if (!this.width.equals(other.width))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.attributeName== null) ? 0 : this.attributeName.hashCode());
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.format== null) ? 0 : this.format.hashCode());
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    return prime * result + ((this.width== null) ? 0 : this.width.hashCode());
  }
}
