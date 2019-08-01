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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.Registration;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The client/registerCapability request is sent from the server to the client to register
 * for a new capability on the client side. Not all clients need to support dynamic
 * capability registration. A client opts in via the dynamicRegistration property on the
 * specific client capabilities. A client can even provide dynamic registration for
 * capability A but not for capability B (see TextDocumentClientCapabilities as an example).
 */
@SuppressWarnings("all")
public class RegistrationParams {
  @NonNull
  private List<Registration> registrations;
  
  public RegistrationParams() {
    this(new ArrayList<Registration>());
  }
  
  public RegistrationParams(@NonNull final List<Registration> registrations) {
    this.registrations = Preconditions.<List<Registration>>checkNotNull(registrations, "registrations");
  }
  
  @Pure
  @NonNull
  public List<Registration> getRegistrations() {
    return this.registrations;
  }
  
  public void setRegistrations(@NonNull final List<Registration> registrations) {
    this.registrations = Preconditions.checkNotNull(registrations, "registrations");
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
    return 31 * 1 + ((this.registrations== null) ? 0 : this.registrations.hashCode());
  }
}
