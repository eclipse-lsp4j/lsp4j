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

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Structure to capture a description for an error code.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class DiagnosticCodeDescription {
  /**
   * A URI to open with more information about the diagnostic error.
   */
  @NonNull
  private String href;
  
  public DiagnosticCodeDescription() {
  }
  
  public DiagnosticCodeDescription(@NonNull final String href) {
    this.href = Preconditions.<String>checkNotNull(href, "href");
  }
  
  /**
   * A URI to open with more information about the diagnostic error.
   */
  @Pure
  @NonNull
  public String getHref() {
    return this.href;
  }
  
  /**
   * A URI to open with more information about the diagnostic error.
   */
  public void setHref(@NonNull final String href) {
    this.href = Preconditions.checkNotNull(href, "href");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("href", this.href);
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
    DiagnosticCodeDescription other = (DiagnosticCodeDescription) obj;
    if (this.href == null) {
      if (other.href != null)
        return false;
    } else if (!this.href.equals(other.href))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.href== null) ? 0 : this.href.hashCode());
  }
}
