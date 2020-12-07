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

import org.eclipse.lsp4j.AbstractTextDocumentRegistrationAndWorkDoneProgressOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Rename options
 */
@SuppressWarnings("all")
public class RenameOptions extends AbstractTextDocumentRegistrationAndWorkDoneProgressOptions {
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again. See also Registration#id.
   * 
   * @deprecated This options object is not specified for StaticRegistrationOptions
   */
  @Deprecated
  private String id;
  
  /**
   * Renames should be checked and tested before being executed.
   */
  private Boolean prepareProvider;
  
  public RenameOptions() {
  }
  
  @Deprecated
  public RenameOptions(final String id) {
    this.id = id;
  }
  
  public RenameOptions(final Boolean prepareProvider) {
    this.prepareProvider = prepareProvider;
  }
  
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again. See also Registration#id.
   * 
   * @deprecated This options object is not specified for StaticRegistrationOptions
   */
  @Pure
  @Deprecated
  public String getId() {
    return this.id;
  }
  
  /**
   * The id used to register the request. The id can be used to deregister
   * the request again. See also Registration#id.
   * 
   * @deprecated This options object is not specified for StaticRegistrationOptions
   */
  @Deprecated
  public void setId(final String id) {
    this.id = id;
  }
  
  /**
   * Renames should be checked and tested before being executed.
   */
  @Pure
  public Boolean getPrepareProvider() {
    return this.prepareProvider;
  }
  
  /**
   * Renames should be checked and tested before being executed.
   */
  public void setPrepareProvider(final Boolean prepareProvider) {
    this.prepareProvider = prepareProvider;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("prepareProvider", this.prepareProvider);
    b.add("workDoneProgress", getWorkDoneProgress());
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
    RenameOptions other = (RenameOptions) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.prepareProvider == null) {
      if (other.prepareProvider != null)
        return false;
    } else if (!this.prepareProvider.equals(other.prepareProvider))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    return prime * result + ((this.prepareProvider== null) ? 0 : this.prepareProvider.hashCode());
  }
}
