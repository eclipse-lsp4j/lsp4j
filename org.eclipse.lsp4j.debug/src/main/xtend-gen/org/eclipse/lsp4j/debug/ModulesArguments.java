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
 * Arguments for 'modules' request.
 */
@SuppressWarnings("all")
public class ModulesArguments {
  /**
   * The index of the first module to return; if omitted modules start at 0.
   * <p>
   * This is an optional property.
   */
  private Long startModule;
  
  /**
   * The number of modules to return. If moduleCount is not specified or 0, all modules are returned.
   * <p>
   * This is an optional property.
   */
  private Long moduleCount;
  
  /**
   * The index of the first module to return; if omitted modules start at 0.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Long getStartModule() {
    return this.startModule;
  }
  
  /**
   * The index of the first module to return; if omitted modules start at 0.
   * <p>
   * This is an optional property.
   */
  public void setStartModule(final Long startModule) {
    this.startModule = startModule;
  }
  
  /**
   * The number of modules to return. If moduleCount is not specified or 0, all modules are returned.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Long getModuleCount() {
    return this.moduleCount;
  }
  
  /**
   * The number of modules to return. If moduleCount is not specified or 0, all modules are returned.
   * <p>
   * This is an optional property.
   */
  public void setModuleCount(final Long moduleCount) {
    this.moduleCount = moduleCount;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("startModule", this.startModule);
    b.add("moduleCount", this.moduleCount);
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
    ModulesArguments other = (ModulesArguments) obj;
    if (this.startModule == null) {
      if (other.startModule != null)
        return false;
    } else if (!this.startModule.equals(other.startModule))
      return false;
    if (this.moduleCount == null) {
      if (other.moduleCount != null)
        return false;
    } else if (!this.moduleCount.equals(other.moduleCount))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.startModule== null) ? 0 : this.startModule.hashCode());
    return prime * result + ((this.moduleCount== null) ? 0 : this.moduleCount.hashCode());
  }
}
