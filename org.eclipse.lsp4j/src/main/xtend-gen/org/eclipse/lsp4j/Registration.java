package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * General parameters to register for a capability.
 */
@SuppressWarnings("all")
public class Registration {
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again.
   */
  @NonNull
  private String id;
  
  /**
   * The method / capability to register for.
   */
  @NonNull
  private String method;
  
  /**
   * Options necessary for the registration.
   */
  private Object registerOptions;
  
  public Registration() {
  }
  
  public Registration(@NonNull final String id, @NonNull final String method) {
    this.id = id;
    this.method = method;
  }
  
  public Registration(@NonNull final String id, @NonNull final String method, final Object registerOptions) {
    this.id = id;
    this.method = method;
    this.registerOptions = registerOptions;
  }
  
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again.
   */
  @Pure
  @NonNull
  public String getId() {
    return this.id;
  }
  
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again.
   */
  public void setId(@NonNull final String id) {
    this.id = id;
  }
  
  /**
   * The method / capability to register for.
   */
  @Pure
  @NonNull
  public String getMethod() {
    return this.method;
  }
  
  /**
   * The method / capability to register for.
   */
  public void setMethod(@NonNull final String method) {
    this.method = method;
  }
  
  /**
   * Options necessary for the registration.
   */
  @Pure
  public Object getRegisterOptions() {
    return this.registerOptions;
  }
  
  /**
   * Options necessary for the registration.
   */
  public void setRegisterOptions(final Object registerOptions) {
    this.registerOptions = registerOptions;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("method", this.method);
    b.add("registerOptions", this.registerOptions);
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
    Registration other = (Registration) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.method == null) {
      if (other.method != null)
        return false;
    } else if (!this.method.equals(other.method))
      return false;
    if (this.registerOptions == null) {
      if (other.registerOptions != null)
        return false;
    } else if (!this.registerOptions.equals(other.registerOptions))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.method== null) ? 0 : this.method.hashCode());
    result = prime * result + ((this.registerOptions== null) ? 0 : this.registerOptions.hashCode());
    return result;
  }
}
