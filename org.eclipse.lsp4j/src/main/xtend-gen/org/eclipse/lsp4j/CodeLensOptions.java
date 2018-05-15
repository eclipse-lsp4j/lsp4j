/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Code Lens options.
 */
@SuppressWarnings("all")
public class CodeLensOptions {
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
    CodeLensOptions other = (CodeLensOptions) obj;
    if (other.resolveProvider != this.resolveProvider)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + (this.resolveProvider ? 1231 : 1237);
  }
}
