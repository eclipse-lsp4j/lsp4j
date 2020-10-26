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
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Optional properties of a variable that can be used to determine how to render the variable in the UI.
 */
@SuppressWarnings("all")
public class VariablePresentationHint {
  /**
   * The kind of variable. Before introducing additional values, try to use the listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintKind}
   */
  private String kind;
  
  /**
   * Set of attributes represented as an array of strings. Before introducing additional values, try to use the
   * listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintAttributes}
   */
  private String[] attributes;
  
  /**
   * Visibility of variable. Before introducing additional values, try to use the listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintVisibility}
   */
  private String visibility;
  
  /**
   * The kind of variable. Before introducing additional values, try to use the listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintKind}
   */
  @Pure
  public String getKind() {
    return this.kind;
  }
  
  /**
   * The kind of variable. Before introducing additional values, try to use the listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintKind}
   */
  public void setKind(final String kind) {
    this.kind = kind;
  }
  
  /**
   * Set of attributes represented as an array of strings. Before introducing additional values, try to use the
   * listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintAttributes}
   */
  @Pure
  public String[] getAttributes() {
    return this.attributes;
  }
  
  /**
   * Set of attributes represented as an array of strings. Before introducing additional values, try to use the
   * listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintAttributes}
   */
  public void setAttributes(final String[] attributes) {
    this.attributes = attributes;
  }
  
  /**
   * Visibility of variable. Before introducing additional values, try to use the listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintVisibility}
   */
  @Pure
  public String getVisibility() {
    return this.visibility;
  }
  
  /**
   * Visibility of variable. Before introducing additional values, try to use the listed values.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link VariablePresentationHintVisibility}
   */
  public void setVisibility(final String visibility) {
    this.visibility = visibility;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("kind", this.kind);
    b.add("attributes", this.attributes);
    b.add("visibility", this.visibility);
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
    VariablePresentationHint other = (VariablePresentationHint) obj;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    if (this.attributes == null) {
      if (other.attributes != null)
        return false;
    } else if (!Arrays.deepEquals(this.attributes, other.attributes))
      return false;
    if (this.visibility == null) {
      if (other.visibility != null)
        return false;
    } else if (!this.visibility.equals(other.visibility))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    result = prime * result + ((this.attributes== null) ? 0 : Arrays.deepHashCode(this.attributes));
    return prime * result + ((this.visibility== null) ? 0 : this.visibility.hashCode());
  }
}
