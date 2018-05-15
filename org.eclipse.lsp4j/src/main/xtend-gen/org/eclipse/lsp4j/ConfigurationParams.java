/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.ConfigurationItem;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The workspace/configuration request is sent from the server to the client to fetch configuration
 * settings from the client. The request can fetch n configuration settings in one roundtrip.
 * The order of the returned configuration settings correspond to the order of the passed
 * ConfigurationItems (e.g. the first item in the response is the result for the first
 * configuration item in the params).
 * 
 * Since 3.6.0
 */
@SuppressWarnings("all")
public class ConfigurationParams {
  @NonNull
  private List<ConfigurationItem> items;
  
  public ConfigurationParams() {
  }
  
  public ConfigurationParams(@NonNull final List<ConfigurationItem> items) {
    this.items = items;
  }
  
  @Pure
  @NonNull
  public List<ConfigurationItem> getItems() {
    return this.items;
  }
  
  public void setItems(@NonNull final List<ConfigurationItem> items) {
    this.items = items;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("items", this.items);
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
    ConfigurationParams other = (ConfigurationParams) obj;
    if (this.items == null) {
      if (other.items != null)
        return false;
    } else if (!this.items.equals(other.items))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.items== null) ? 0 : this.items.hashCode());
  }
}
