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
import org.eclipse.lsp4j.CompletionItemTag;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Client supports the tag property on a completion item. Clients supporting
 * tags have to handle unknown tags gracefully. Clients especially need to
 * preserve unknown tags when sending a completion item back to the server in
 * a resolve call.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public class CompletionItemTagSupportCapabilities {
  /**
   * The tags supported by the client.
   */
  @NonNull
  private List<CompletionItemTag> valueSet;
  
  public CompletionItemTagSupportCapabilities() {
    ArrayList<CompletionItemTag> _arrayList = new ArrayList<CompletionItemTag>();
    this.valueSet = _arrayList;
  }
  
  public CompletionItemTagSupportCapabilities(@NonNull final List<CompletionItemTag> valueSet) {
    this.valueSet = Preconditions.<List<CompletionItemTag>>checkNotNull(valueSet, "valueSet");
  }
  
  /**
   * The tags supported by the client.
   */
  @Pure
  @NonNull
  public List<CompletionItemTag> getValueSet() {
    return this.valueSet;
  }
  
  /**
   * The tags supported by the client.
   */
  public void setValueSet(@NonNull final List<CompletionItemTag> valueSet) {
    this.valueSet = Preconditions.checkNotNull(valueSet, "valueSet");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("valueSet", this.valueSet);
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
    CompletionItemTagSupportCapabilities other = (CompletionItemTagSupportCapabilities) obj;
    if (this.valueSet == null) {
      if (other.valueSet != null)
        return false;
    } else if (!this.valueSet.equals(other.valueSet))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.valueSet== null) ? 0 : this.valueSet.hashCode());
  }
}
