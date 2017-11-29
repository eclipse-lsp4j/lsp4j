/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.lsp4j.debug.CompletionItem;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'completions' request.
 */
@SuppressWarnings("all")
public class CompletionsResponse {
  /**
   * The possible completions for .
   */
  private CompletionItem[] targets;
  
  /**
   * The possible completions for .
   */
  @Pure
  public CompletionItem[] getTargets() {
    return this.targets;
  }
  
  /**
   * The possible completions for .
   */
  public void setTargets(final CompletionItem[] targets) {
    this.targets = targets;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("targets", this.targets);
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
    CompletionsResponse other = (CompletionsResponse) obj;
    if (this.targets == null) {
      if (other.targets != null)
        return false;
    } else if (!Arrays.deepEquals(this.targets, other.targets))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.targets== null) ? 0 : Arrays.deepHashCode(this.targets));
    return result;
  }
}
