/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'loadedSources' request.
 */
@SuppressWarnings("all")
public class LoadedSourcesResponse {
  /**
   * Set of loaded sources.
   */
  @NonNull
  private Source[] sources;
  
  /**
   * Set of loaded sources.
   */
  @Pure
  @NonNull
  public Source[] getSources() {
    return this.sources;
  }
  
  /**
   * Set of loaded sources.
   */
  public void setSources(@NonNull final Source[] sources) {
    this.sources = sources;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("sources", this.sources);
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
    LoadedSourcesResponse other = (LoadedSourcesResponse) obj;
    if (this.sources == null) {
      if (other.sources != null)
        return false;
    } else if (!Arrays.deepEquals(this.sources, other.sources))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.sources== null) ? 0 : Arrays.deepHashCode(this.sources));
    return result;
  }
}
