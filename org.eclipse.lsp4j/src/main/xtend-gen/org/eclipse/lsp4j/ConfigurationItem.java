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
 * A ConfigurationItem consist of the configuration section to ask for and an additional scope URI.
 * The configuration section ask for is defined by the server and doesn’t necessarily need to
 * correspond to the configuration store used be the client. So a server might ask for a configuration
 * cpp.formatterOptions but the client stores the configuration in a XML store layout differently.
 * It is up to the client to do the necessary conversion. If a scope URI is provided the client
 * should return the setting scoped to the provided resource. If the client for example uses
 * EditorConfig to manage its settings the configuration should be returned for the passed resource
 * URI. If the client can’t provide a configuration setting for a given scope then null need to be
 * present in the returned array.
 * 
 * Since 3.6.0
 */
@SuppressWarnings("all")
public class ConfigurationItem {
  /**
   * The scope to get the configuration section for.
   */
  private String scopeUri;
  
  /**
   * The configuration section asked for.
   */
  private String section;
  
  /**
   * The scope to get the configuration section for.
   */
  @Pure
  public String getScopeUri() {
    return this.scopeUri;
  }
  
  /**
   * The scope to get the configuration section for.
   */
  public void setScopeUri(final String scopeUri) {
    this.scopeUri = scopeUri;
  }
  
  /**
   * The configuration section asked for.
   */
  @Pure
  public String getSection() {
    return this.section;
  }
  
  /**
   * The configuration section asked for.
   */
  public void setSection(final String section) {
    this.section = section;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("scopeUri", this.scopeUri);
    b.add("section", this.section);
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
    ConfigurationItem other = (ConfigurationItem) obj;
    if (this.scopeUri == null) {
      if (other.scopeUri != null)
        return false;
    } else if (!this.scopeUri.equals(other.scopeUri))
      return false;
    if (this.section == null) {
      if (other.section != null)
        return false;
    } else if (!this.section.equals(other.section))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.scopeUri== null) ? 0 : this.scopeUri.hashCode());
    result = prime * result + ((this.section== null) ? 0 : this.section.hashCode());
    return result;
  }
}
