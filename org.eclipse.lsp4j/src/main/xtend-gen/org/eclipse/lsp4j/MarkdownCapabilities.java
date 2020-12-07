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
 * Client capabilities specific to the used markdown parser.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class MarkdownCapabilities {
  /**
   * The name of the parser.
   */
  @NonNull
  private String parser;
  
  /**
   * The version of the parser.
   */
  private String version;
  
  public MarkdownCapabilities() {
  }
  
  public MarkdownCapabilities(@NonNull final String parser) {
    this.parser = Preconditions.<String>checkNotNull(parser, "parser");
  }
  
  public MarkdownCapabilities(@NonNull final String parser, final String version) {
    this(parser);
    this.version = version;
  }
  
  /**
   * The name of the parser.
   */
  @Pure
  @NonNull
  public String getParser() {
    return this.parser;
  }
  
  /**
   * The name of the parser.
   */
  public void setParser(@NonNull final String parser) {
    this.parser = Preconditions.checkNotNull(parser, "parser");
  }
  
  /**
   * The version of the parser.
   */
  @Pure
  public String getVersion() {
    return this.version;
  }
  
  /**
   * The version of the parser.
   */
  public void setVersion(final String version) {
    this.version = version;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("parser", this.parser);
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
    MarkdownCapabilities other = (MarkdownCapabilities) obj;
    if (this.parser == null) {
      if (other.parser != null)
        return false;
    } else if (!this.parser.equals(other.parser))
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
    result = prime * result + ((this.parser== null) ? 0 : this.parser.hashCode());
    return prime * result + ((this.version== null) ? 0 : this.version.hashCode());
  }
}
