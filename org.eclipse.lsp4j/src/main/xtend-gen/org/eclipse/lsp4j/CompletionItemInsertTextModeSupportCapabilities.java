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

import com.google.common.annotations.Beta;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.InsertTextMode;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The client supports the {@link CompletionItem#insertTextMode} property on
 * a completion item to override the whitespace handling mode
 * as defined by the client.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class CompletionItemInsertTextModeSupportCapabilities {
  @NonNull
  private List<InsertTextMode> valueSet;
  
  public CompletionItemInsertTextModeSupportCapabilities() {
    ArrayList<InsertTextMode> _arrayList = new ArrayList<InsertTextMode>();
    this.valueSet = _arrayList;
  }
  
  public CompletionItemInsertTextModeSupportCapabilities(@NonNull final List<InsertTextMode> valueSet) {
    this.valueSet = Preconditions.<List<InsertTextMode>>checkNotNull(valueSet, "valueSet");
  }
  
  @Pure
  @NonNull
  public List<InsertTextMode> getValueSet() {
    return this.valueSet;
  }
  
  public void setValueSet(@NonNull final List<InsertTextMode> valueSet) {
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
    CompletionItemInsertTextModeSupportCapabilities other = (CompletionItemInsertTextModeSupportCapabilities) obj;
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
