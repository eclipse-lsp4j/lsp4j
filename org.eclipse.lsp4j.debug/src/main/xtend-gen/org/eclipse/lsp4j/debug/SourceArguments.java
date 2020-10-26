/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.Source;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'source' request.
 */
@SuppressWarnings("all")
public class SourceArguments {
  /**
   * Specifies the source content to load. Either source.path or source.sourceReference must be specified.
   * <p>
   * This is an optional property.
   */
  private Source source;
  
  /**
   * The reference to the source. This is the same as source.sourceReference.
   * <p>
   * This is provided for backward compatibility since old backends do not understand the 'source' attribute.
   */
  private int sourceReference;
  
  /**
   * Specifies the source content to load. Either source.path or source.sourceReference must be specified.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Source getSource() {
    return this.source;
  }
  
  /**
   * Specifies the source content to load. Either source.path or source.sourceReference must be specified.
   * <p>
   * This is an optional property.
   */
  public void setSource(final Source source) {
    this.source = source;
  }
  
  /**
   * The reference to the source. This is the same as source.sourceReference.
   * <p>
   * This is provided for backward compatibility since old backends do not understand the 'source' attribute.
   */
  @Pure
  public int getSourceReference() {
    return this.sourceReference;
  }
  
  /**
   * The reference to the source. This is the same as source.sourceReference.
   * <p>
   * This is provided for backward compatibility since old backends do not understand the 'source' attribute.
   */
  public void setSourceReference(final int sourceReference) {
    this.sourceReference = sourceReference;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("source", this.source);
    b.add("sourceReference", this.sourceReference);
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
    SourceArguments other = (SourceArguments) obj;
    if (this.source == null) {
      if (other.source != null)
        return false;
    } else if (!this.source.equals(other.source))
      return false;
    if (other.sourceReference != this.sourceReference)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.source== null) ? 0 : this.source.hashCode());
    return prime * result + this.sourceReference;
  }
}
