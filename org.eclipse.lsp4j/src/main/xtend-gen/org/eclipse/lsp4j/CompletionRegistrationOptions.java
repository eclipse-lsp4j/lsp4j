/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.TextDocumentRegistrationOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class CompletionRegistrationOptions extends TextDocumentRegistrationOptions {
  /**
   * The characters that trigger completion automatically.
   */
  private List<String> triggerCharacters;
  
  /**
   * The server provides support to resolve additional information for a completion item.
   */
  private Boolean resolveProvider;
  
  public CompletionRegistrationOptions() {
  }
  
  public CompletionRegistrationOptions(final List<String> triggerCharacters, final Boolean resolveProvider) {
    this.triggerCharacters = triggerCharacters;
    this.resolveProvider = resolveProvider;
  }
  
  /**
   * The characters that trigger completion automatically.
   */
  @Pure
  public List<String> getTriggerCharacters() {
    return this.triggerCharacters;
  }
  
  /**
   * The characters that trigger completion automatically.
   */
  public void setTriggerCharacters(final List<String> triggerCharacters) {
    this.triggerCharacters = triggerCharacters;
  }
  
  /**
   * The server provides support to resolve additional information for a completion item.
   */
  @Pure
  public Boolean getResolveProvider() {
    return this.resolveProvider;
  }
  
  /**
   * The server provides support to resolve additional information for a completion item.
   */
  public void setResolveProvider(final Boolean resolveProvider) {
    this.resolveProvider = resolveProvider;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("triggerCharacters", this.triggerCharacters);
    b.add("resolveProvider", this.resolveProvider);
    b.add("documentSelector", getDocumentSelector());
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
    CompletionRegistrationOptions other = (CompletionRegistrationOptions) obj;
    if (this.triggerCharacters == null) {
      if (other.triggerCharacters != null)
        return false;
    } else if (!this.triggerCharacters.equals(other.triggerCharacters))
      return false;
    if (this.resolveProvider == null) {
      if (other.resolveProvider != null)
        return false;
    } else if (!this.resolveProvider.equals(other.resolveProvider))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.triggerCharacters== null) ? 0 : this.triggerCharacters.hashCode());
    result = prime * result + ((this.resolveProvider== null) ? 0 : this.resolveProvider.hashCode());
    return result;
  }
}
