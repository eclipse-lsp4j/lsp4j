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

import org.eclipse.lsp4j.CreateFileOptions;
import org.eclipse.lsp4j.ResourceOperation;
import org.eclipse.lsp4j.ResourceOperationKind;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Create file operation
 */
@SuppressWarnings("all")
public class CreateFile extends ResourceOperation {
  /**
   * The resource to create.
   */
  @NonNull
  private String uri;
  
  /**
   * Additional options
   */
  private CreateFileOptions options;
  
  public CreateFile() {
    super(ResourceOperationKind.Create);
  }
  
  public CreateFile(@NonNull final String uri) {
    super(ResourceOperationKind.Create);
    this.uri = Preconditions.<String>checkNotNull(uri, "uri");
  }
  
  public CreateFile(@NonNull final String uri, final CreateFileOptions options) {
    super(ResourceOperationKind.Create);
    this.uri = Preconditions.<String>checkNotNull(uri, "uri");
    this.options = options;
  }
  
  /**
   * The resource to create.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The resource to create.
   */
  public void setUri(@NonNull final String uri) {
    this.uri = Preconditions.checkNotNull(uri, "uri");
  }
  
  /**
   * Additional options
   */
  @Pure
  public CreateFileOptions getOptions() {
    return this.options;
  }
  
  /**
   * Additional options
   */
  public void setOptions(final CreateFileOptions options) {
    this.options = options;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
    b.add("options", this.options);
    b.add("kind", getKind());
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
    CreateFile other = (CreateFile) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.options == null) {
      if (other.options != null)
        return false;
    } else if (!this.options.equals(other.options))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    return prime * result + ((this.options== null) ? 0 : this.options.hashCode());
  }
}
