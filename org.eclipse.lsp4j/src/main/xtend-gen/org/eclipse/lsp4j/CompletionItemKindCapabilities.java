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

import java.util.List;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class CompletionItemKindCapabilities {
  /**
   * The completion item kind values the client supports. When this
   * property exists the client also guarantees that it will
   * handle values outside its set gracefully and falls back
   * to a default value when unknown.
   * 
   * If this property is not present the client only supports
   * the completion items kinds from `Text` to `Reference` as defined in
   * the initial version of the protocol.
   */
  private List<CompletionItemKind> valueSet;
  
  public CompletionItemKindCapabilities() {
  }
  
  public CompletionItemKindCapabilities(final List<CompletionItemKind> valueSet) {
    this.valueSet = valueSet;
  }
  
  /**
   * The completion item kind values the client supports. When this
   * property exists the client also guarantees that it will
   * handle values outside its set gracefully and falls back
   * to a default value when unknown.
   * 
   * If this property is not present the client only supports
   * the completion items kinds from `Text` to `Reference` as defined in
   * the initial version of the protocol.
   */
  @Pure
  public List<CompletionItemKind> getValueSet() {
    return this.valueSet;
  }
  
  /**
   * The completion item kind values the client supports. When this
   * property exists the client also guarantees that it will
   * handle values outside its set gracefully and falls back
   * to a default value when unknown.
   * 
   * If this property is not present the client only supports
   * the completion items kinds from `Text` to `Reference` as defined in
   * the initial version of the protocol.
   */
  public void setValueSet(final List<CompletionItemKind> valueSet) {
    this.valueSet = valueSet;
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
    CompletionItemKindCapabilities other = (CompletionItemKindCapabilities) obj;
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
