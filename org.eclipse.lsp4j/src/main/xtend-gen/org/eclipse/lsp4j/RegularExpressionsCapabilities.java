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
 * Client capabilities specific to regular expressions.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class RegularExpressionsCapabilities {
  /**
   * The engine's name.
   */
  @NonNull
  private String engine;
  
  /**
   * The engine's version.
   */
  private String version;
  
  public RegularExpressionsCapabilities() {
  }
  
  public RegularExpressionsCapabilities(@NonNull final String engine) {
    this.engine = Preconditions.<String>checkNotNull(engine, "engine");
  }
  
  public RegularExpressionsCapabilities(@NonNull final String engine, final String version) {
    this(engine);
    this.version = version;
  }
  
  /**
   * The engine's name.
   */
  @Pure
  @NonNull
  public String getEngine() {
    return this.engine;
  }
  
  /**
   * The engine's name.
   */
  public void setEngine(@NonNull final String engine) {
    this.engine = Preconditions.checkNotNull(engine, "engine");
  }
  
  /**
   * The engine's version.
   */
  @Pure
  public String getVersion() {
    return this.version;
  }
  
  /**
   * The engine's version.
   */
  public void setVersion(final String version) {
    this.version = version;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("engine", this.engine);
    b.add("version", this.version);
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
    RegularExpressionsCapabilities other = (RegularExpressionsCapabilities) obj;
    if (this.engine == null) {
      if (other.engine != null)
        return false;
    } else if (!this.engine.equals(other.engine))
      return false;
    if (this.version == null) {
      if (other.version != null)
        return false;
    } else if (!this.version.equals(other.version))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.engine== null) ? 0 : this.engine.hashCode());
    return prime * result + ((this.version== null) ? 0 : this.version.hashCode());
  }
}
