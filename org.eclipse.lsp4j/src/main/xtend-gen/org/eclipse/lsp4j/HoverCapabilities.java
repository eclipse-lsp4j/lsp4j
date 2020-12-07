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
import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to the `textDocument/hover`
 */
@SuppressWarnings("all")
public class HoverCapabilities extends DynamicRegistrationCapabilities {
  /**
   * Client supports the following content formats if the content
   * property refers to {@link MarkupContent}.
   * The order describes the preferred format of the client.
   * 
   * See {@link MarkupKind} for allowed values.
   */
  private List<String> contentFormat;
  
  public HoverCapabilities() {
  }
  
  public HoverCapabilities(final Boolean dynamicRegistration) {
    super(dynamicRegistration);
  }
  
  public HoverCapabilities(final List<String> contentFormat, final Boolean dynamicRegistration) {
    super(dynamicRegistration);
    this.contentFormat = contentFormat;
  }
  
  /**
   * Client supports the following content formats if the content
   * property refers to {@link MarkupContent}.
   * The order describes the preferred format of the client.
   * 
   * See {@link MarkupKind} for allowed values.
   */
  @Pure
  public List<String> getContentFormat() {
    return this.contentFormat;
  }
  
  /**
   * Client supports the following content formats if the content
   * property refers to {@link MarkupContent}.
   * The order describes the preferred format of the client.
   * 
   * See {@link MarkupKind} for allowed values.
   */
  public void setContentFormat(final List<String> contentFormat) {
    this.contentFormat = contentFormat;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("contentFormat", this.contentFormat);
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
    HoverCapabilities other = (HoverCapabilities) obj;
    if (this.contentFormat == null) {
      if (other.contentFormat != null)
        return false;
    } else if (!this.contentFormat.equals(other.contentFormat))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.contentFormat== null) ? 0 : this.contentFormat.hashCode());
  }
}
