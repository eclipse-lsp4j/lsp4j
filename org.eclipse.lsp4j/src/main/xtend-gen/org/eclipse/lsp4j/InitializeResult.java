package org.eclipse.lsp4j;

import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class InitializeResult {
  /**
   * The capabilities the language server provides.
   */
  @NonNull
  private ServerCapabilities capabilities;
  
  public InitializeResult() {
  }
  
  public InitializeResult(@NonNull final ServerCapabilities capabilities) {
    this.capabilities = capabilities;
  }
  
  /**
   * The capabilities the language server provides.
   */
  @Pure
  @NonNull
  public ServerCapabilities getCapabilities() {
    return this.capabilities;
  }
  
  /**
   * The capabilities the language server provides.
   */
  public void setCapabilities(@NonNull final ServerCapabilities capabilities) {
    this.capabilities = capabilities;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("capabilities", this.capabilities);
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
    InitializeResult other = (InitializeResult) obj;
    if (this.capabilities == null) {
      if (other.capabilities != null)
        return false;
    } else if (!this.capabilities.equals(other.capabilities))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.capabilities== null) ? 0 : this.capabilities.hashCode());
    return result;
  }
}
