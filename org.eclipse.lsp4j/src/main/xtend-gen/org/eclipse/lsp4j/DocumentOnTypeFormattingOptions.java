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

import com.google.common.base.Preconditions;
import java.util.List;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Format document on type options
 */
@SuppressWarnings("all")
public class DocumentOnTypeFormattingOptions {
  /**
   * A character on which formatting should be triggered, like `}`.
   */
  @NonNull
  private String firstTriggerCharacter;
  
  /**
   * More trigger characters.
   */
  private List<String> moreTriggerCharacter;
  
  public DocumentOnTypeFormattingOptions() {
  }
  
  public DocumentOnTypeFormattingOptions(@NonNull final String firstTriggerCharacter) {
    this.firstTriggerCharacter = firstTriggerCharacter;
  }
  
  public DocumentOnTypeFormattingOptions(@NonNull final String firstTriggerCharacter, final List<String> moreTriggerCharacter) {
    this.firstTriggerCharacter = Preconditions.<String>checkNotNull(firstTriggerCharacter);
    this.moreTriggerCharacter = moreTriggerCharacter;
  }
  
  /**
   * A character on which formatting should be triggered, like `}`.
   */
  @Pure
  @NonNull
  public String getFirstTriggerCharacter() {
    return this.firstTriggerCharacter;
  }
  
  /**
   * A character on which formatting should be triggered, like `}`.
   */
  public void setFirstTriggerCharacter(@NonNull final String firstTriggerCharacter) {
    this.firstTriggerCharacter = firstTriggerCharacter;
  }
  
  /**
   * More trigger characters.
   */
  @Pure
  public List<String> getMoreTriggerCharacter() {
    return this.moreTriggerCharacter;
  }
  
  /**
   * More trigger characters.
   */
  public void setMoreTriggerCharacter(final List<String> moreTriggerCharacter) {
    this.moreTriggerCharacter = moreTriggerCharacter;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("firstTriggerCharacter", this.firstTriggerCharacter);
    b.add("moreTriggerCharacter", this.moreTriggerCharacter);
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
    DocumentOnTypeFormattingOptions other = (DocumentOnTypeFormattingOptions) obj;
    if (this.firstTriggerCharacter == null) {
      if (other.firstTriggerCharacter != null)
        return false;
    } else if (!this.firstTriggerCharacter.equals(other.firstTriggerCharacter))
      return false;
    if (this.moreTriggerCharacter == null) {
      if (other.moreTriggerCharacter != null)
        return false;
    } else if (!this.moreTriggerCharacter.equals(other.moreTriggerCharacter))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.firstTriggerCharacter== null) ? 0 : this.firstTriggerCharacter.hashCode());
    return prime * result + ((this.moreTriggerCharacter== null) ? 0 : this.moreTriggerCharacter.hashCode());
  }
}
