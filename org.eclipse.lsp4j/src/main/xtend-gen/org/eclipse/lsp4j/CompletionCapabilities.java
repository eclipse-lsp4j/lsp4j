/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.CompletionItemCapabilities;
import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class CompletionCapabilities extends DynamicRegistrationCapabilities {
  /**
   * The client supports the following `CompletionItem` specific
   * capabilities.
   */
  private CompletionItemCapabilities completionItem;
  
  public CompletionCapabilities() {
  }
  
  public CompletionCapabilities(final CompletionItemCapabilities completionItem) {
    this.completionItem = completionItem;
  }
  
  public CompletionCapabilities(final CompletionItemCapabilities completionItem, final Boolean dynamicRegistration) {
    super(dynamicRegistration);
    this.completionItem = completionItem;
  }
  
  /**
   * The client supports the following `CompletionItem` specific
   * capabilities.
   */
  @Pure
  public CompletionItemCapabilities getCompletionItem() {
    return this.completionItem;
  }
  
  /**
   * The client supports the following `CompletionItem` specific
   * capabilities.
   */
  public void setCompletionItem(final CompletionItemCapabilities completionItem) {
    this.completionItem = completionItem;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("completionItem", this.completionItem);
    b.add("dynamicRegistration", getDynamicRegistration());
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
    CompletionCapabilities other = (CompletionCapabilities) obj;
    if (this.completionItem == null) {
      if (other.completionItem != null)
        return false;
    } else if (!this.completionItem.equals(other.completionItem))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.completionItem== null) ? 0 : this.completionItem.hashCode());
    return result;
  }
}
