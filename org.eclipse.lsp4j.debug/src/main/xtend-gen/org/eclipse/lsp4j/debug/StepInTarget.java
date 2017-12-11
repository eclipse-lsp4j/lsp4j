/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A StepInTarget can be used in the 'stepIn' request and determines into which single target the stepIn request
 * should step.
 */
@SuppressWarnings("all")
public class StepInTarget {
  /**
   * Unique identifier for a stepIn target.
   */
  @NonNull
  private Integer id;
  
  /**
   * The name of the stepIn target (shown in the UI).
   */
  @NonNull
  private String label;
  
  /**
   * Unique identifier for a stepIn target.
   */
  @Pure
  @NonNull
  public Integer getId() {
    return this.id;
  }
  
  /**
   * Unique identifier for a stepIn target.
   */
  public void setId(@NonNull final Integer id) {
    this.id = id;
  }
  
  /**
   * The name of the stepIn target (shown in the UI).
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The name of the stepIn target (shown in the UI).
   */
  public void setLabel(@NonNull final String label) {
    this.label = label;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("label", this.label);
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
    StepInTarget other = (StepInTarget) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    return result;
  }
}
