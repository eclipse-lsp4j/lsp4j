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

import org.eclipse.lsp4j.AbstractWorkDoneProgressOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Code Lens options.
 */
@SuppressWarnings("all")
public class CodeLensOptions extends AbstractWorkDoneProgressOptions {
  /**
   * Code lens has a resolve provider as well.
   */
  private boolean resolveProvider;
  
  public CodeLensOptions() {
  }
  
  public CodeLensOptions(final boolean resolveProvider) {
    this.resolveProvider = resolveProvider;
  }
  
  /**
   * Code lens has a resolve provider as well.
   */
  @Pure
  public boolean isResolveProvider() {
    return this.resolveProvider;
  }
  
  /**
   * Code lens has a resolve provider as well.
   */
  public void setResolveProvider(final boolean resolveProvider) {
    this.resolveProvider = resolveProvider;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("resolveProvider", this.resolveProvider);
    b.add("workDoneProgress", getWorkDoneProgress());
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
    if (!super.equals(obj))
      return false;
    CodeLensOptions other = (CodeLensOptions) obj;
    if (other.resolveProvider != this.resolveProvider)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + (this.resolveProvider ? 1231 : 1237);
  }
}
