/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A Thread
 */
@SuppressWarnings("all")
public class Thread {
  /**
   * Unique identifier for the thread.
   */
  private Integer id;
  
  /**
   * A name of the thread.
   */
  private String name;
  
  /**
   * Unique identifier for the thread.
   */
  @Pure
  public Integer getId() {
    return this.id;
  }
  
  /**
   * Unique identifier for the thread.
   */
  public void setId(final Integer id) {
    this.id = id;
  }
  
  /**
   * A name of the thread.
   */
  @Pure
  public String getName() {
    return this.name;
  }
  
  /**
   * A name of the thread.
   */
  public void setName(final String name) {
    this.name = name;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("name", this.name);
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
    Thread other = (Thread) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    return result;
  }
}
