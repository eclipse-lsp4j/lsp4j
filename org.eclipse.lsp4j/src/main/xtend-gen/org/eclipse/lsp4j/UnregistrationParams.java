package org.eclipse.lsp4j;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.Unregistration;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class UnregistrationParams {
  @NonNull
  private List<Unregistration> unregisterations;
  
  public UnregistrationParams() {
    this(new ArrayList<Unregistration>());
  }
  
  public UnregistrationParams(@NonNull final List<Unregistration> unregisterations) {
    this.unregisterations = unregisterations;
  }
  
  @Pure
  @NonNull
  public List<Unregistration> getUnregisterations() {
    return this.unregisterations;
  }
  
  public void setUnregisterations(@NonNull final List<Unregistration> unregisterations) {
    this.unregisterations = unregisterations;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("unregisterations", this.unregisterations);
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
    UnregistrationParams other = (UnregistrationParams) obj;
    if (this.unregisterations == null) {
      if (other.unregisterations != null)
        return false;
    } else if (!this.unregisterations.equals(other.unregisterations))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.unregisterations== null) ? 0 : this.unregisterations.hashCode());
    return result;
  }
}
