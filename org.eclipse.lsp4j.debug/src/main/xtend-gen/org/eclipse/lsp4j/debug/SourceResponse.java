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

import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'source' request.
 */
@SuppressWarnings("all")
public class SourceResponse {
  /**
   * Content of the source reference.
   */
  @NonNull
  private String content;
  
  /**
   * Optional content type (mime type) of the source.
   * <p>
   * This is an optional property.
   */
  private String mimeType;
  
  /**
   * Content of the source reference.
   */
  @Pure
  @NonNull
  public String getContent() {
    return this.content;
  }
  
  /**
   * Content of the source reference.
   */
  public void setContent(@NonNull final String content) {
    this.content = Preconditions.checkNotNull(content, "content");
  }
  
  /**
   * Optional content type (mime type) of the source.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getMimeType() {
    return this.mimeType;
  }
  
  /**
   * Optional content type (mime type) of the source.
   * <p>
   * This is an optional property.
   */
  public void setMimeType(final String mimeType) {
    this.mimeType = mimeType;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("content", this.content);
    b.add("mimeType", this.mimeType);
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
    SourceResponse other = (SourceResponse) obj;
    if (this.content == null) {
      if (other.content != null)
        return false;
    } else if (!this.content.equals(other.content))
      return false;
    if (this.mimeType == null) {
      if (other.mimeType != null)
        return false;
    } else if (!this.mimeType.equals(other.mimeType))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.content== null) ? 0 : this.content.hashCode());
    return prime * result + ((this.mimeType== null) ? 0 : this.mimeType.hashCode());
  }
}
