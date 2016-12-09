package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A notification sent from the client to the server to signal the change of configuration settings.
 */
@SuppressWarnings("all")
public class DidChangeConfigurationParams {
  @NonNull
  private Object settings;
  
  @Pure
  @NonNull
  public Object getSettings() {
    return this.settings;
  }
  
  public void setSettings(@NonNull final Object settings) {
    this.settings = settings;
  }
  
  public DidChangeConfigurationParams() {
    
  }
  
  public DidChangeConfigurationParams(final Object settings) {
    this.settings = settings;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("settings", this.settings);
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
    DidChangeConfigurationParams other = (DidChangeConfigurationParams) obj;
    if (this.settings == null) {
      if (other.settings != null)
        return false;
    } else if (!this.settings.equals(other.settings))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.settings== null) ? 0 : this.settings.hashCode());
    return result;
  }
}
