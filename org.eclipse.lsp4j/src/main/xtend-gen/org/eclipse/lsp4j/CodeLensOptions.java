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
  
  public CodeLensOptions() {
    
  }
  
  public CodeLensOptions(final boolean resolveProvider) {
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
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.resolveProvider ? 1231 : 1237);
    return result;
  }
}
