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
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'modules' request.
 */
@SuppressWarnings("all")
public class ModulesResponse {
  /**
   * All modules or range of modules.
   */
  @NonNull
  private org.eclipse.lsp4j.debug.Module[] modules;
  
  /**
   * The total number of modules available.
   * <p>
   * This is an optional property.
   */
  private Integer totalModules;
  
  /**
   * All modules or range of modules.
   */
  @Pure
  @NonNull
  public org.eclipse.lsp4j.debug.Module[] getModules() {
    return this.modules;
  }
  
  /**
   * All modules or range of modules.
   */
  public void setModules(@NonNull final org.eclipse.lsp4j.debug.Module[] modules) {
    this.modules = Preconditions.checkNotNull(modules, "modules");
  }
  
  /**
   * The total number of modules available.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getTotalModules() {
    return this.totalModules;
  }
  
  /**
   * The total number of modules available.
   * <p>
   * This is an optional property.
   */
  public void setTotalModules(final Integer totalModules) {
    this.totalModules = totalModules;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("modules", this.modules);
    b.add("totalModules", this.totalModules);
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
    ModulesResponse other = (ModulesResponse) obj;
    if (this.modules == null) {
      if (other.modules != null)
        return false;
    } else if (!Arrays.deepEquals(this.modules, other.modules))
      return false;
    if (this.totalModules == null) {
      if (other.totalModules != null)
        return false;
    } else if (!this.totalModules.equals(other.totalModules))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.modules== null) ? 0 : Arrays.deepHashCode(this.modules));
    return prime * result + ((this.totalModules== null) ? 0 : this.totalModules.hashCode());
  }
}
