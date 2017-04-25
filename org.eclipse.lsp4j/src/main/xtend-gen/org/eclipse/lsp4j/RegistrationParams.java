package org.eclipse.lsp4j;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.Registration;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class RegistrationParams {
  @NonNull
  private List<Registration> registrations;
  
  public RegistrationParams() {
    ArrayList<Registration> _arrayList = new ArrayList<Registration>();
    this.registrations = _arrayList;
  }
  
  public RegistrationParams(final List<Registration> registrations) {
    this.registrations = registrations;
  }
  
  @Pure
  @NonNull
  public List<Registration> getRegistrations() {
    return this.registrations;
  }
  
  public void setRegistrations(@NonNull final List<Registration> registrations) {
    this.registrations = registrations;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("registrations", this.registrations);
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
    RegistrationParams other = (RegistrationParams) obj;
    if (this.registrations == null) {
      if (other.registrations != null)
        return false;
    } else if (!this.registrations.equals(other.registrations))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.registrations== null) ? 0 : this.registrations.hashCode());
    return result;
  }
}
