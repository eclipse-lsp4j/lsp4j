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
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Additional information that describes document changes.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class ChangeAnnotation {
  /**
   * A human-readable string describing the actual change. The string
   * is rendered prominent in the user interface.
   */
  @NonNull
  private String label;
  
  /**
   * A flag which indicates that user confirmation is needed
   * before applying the change.
   */
  private Boolean needsConfirmation;
  
  /**
   * A human-readable string which is rendered less prominent in
   * the user interface.
   */
  private String description;
  
  public ChangeAnnotation() {
  }
  
  public ChangeAnnotation(@NonNull final String label) {
    this.label = Preconditions.<String>checkNotNull(label, "label");
  }
  
  /**
   * A human-readable string describing the actual change. The string
   * is rendered prominent in the user interface.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * A human-readable string describing the actual change. The string
   * is rendered prominent in the user interface.
   */
  public void setLabel(@NonNull final String label) {
    this.label = Preconditions.checkNotNull(label, "label");
  }
  
  /**
   * A flag which indicates that user confirmation is needed
   * before applying the change.
   */
  @Pure
  public Boolean getNeedsConfirmation() {
    return this.needsConfirmation;
  }
  
  /**
   * A flag which indicates that user confirmation is needed
   * before applying the change.
   */
  public void setNeedsConfirmation(final Boolean needsConfirmation) {
    this.needsConfirmation = needsConfirmation;
  }
  
  /**
   * A human-readable string which is rendered less prominent in
   * the user interface.
   */
  @Pure
  public String getDescription() {
    return this.description;
  }
  
  /**
   * A human-readable string which is rendered less prominent in
   * the user interface.
   */
  public void setDescription(final String description) {
    this.description = description;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("needsConfirmation", this.needsConfirmation);
    b.add("description", this.description);
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
    ChangeAnnotation other = (ChangeAnnotation) obj;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    if (this.needsConfirmation == null) {
      if (other.needsConfirmation != null)
        return false;
    } else if (!this.needsConfirmation.equals(other.needsConfirmation))
      return false;
    if (this.description == null) {
      if (other.description != null)
        return false;
    } else if (!this.description.equals(other.description))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.needsConfirmation== null) ? 0 : this.needsConfirmation.hashCode());
    return prime * result + ((this.description== null) ? 0 : this.description.hashCode());
  }
}
