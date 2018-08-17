/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import com.google.gson.annotations.JsonAdapter;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.adapters.VersionedTextDocumentIdentifierTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An identifier to denote a specific version of a text document.
 */
@JsonAdapter(VersionedTextDocumentIdentifierTypeAdapter.Factory.class)
@SuppressWarnings("all")
public class VersionedTextDocumentIdentifier extends TextDocumentIdentifier {
  /**
   * The version number of this document. If a versioned text document identifier
   * is sent from the server to the client and the file is not open in the editor
   * (the server has not received an open notification before) the server can send
   * `null` to indicate that the version is known and the content on disk is the
   * truth (as specified with document content ownership).
   */
  private Integer version;
  
  public VersionedTextDocumentIdentifier() {
  }
  
  public VersionedTextDocumentIdentifier(@NonNull final String uri, final Integer version) {
    super(uri);
    this.version = version;
  }
  
  @Deprecated
  public VersionedTextDocumentIdentifier(final Integer version) {
    this.version = version;
  }
  
  /**
   * The version number of this document. If a versioned text document identifier
   * is sent from the server to the client and the file is not open in the editor
   * (the server has not received an open notification before) the server can send
   * `null` to indicate that the version is known and the content on disk is the
   * truth (as specified with document content ownership).
   */
  @Pure
  public Integer getVersion() {
    return this.version;
  }
  
  /**
   * The version number of this document. If a versioned text document identifier
   * is sent from the server to the client and the file is not open in the editor
   * (the server has not received an open notification before) the server can send
   * `null` to indicate that the version is known and the content on disk is the
   * truth (as specified with document content ownership).
   */
  public void setVersion(final Integer version) {
    this.version = version;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("version", this.version);
    b.add("uri", getUri());
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
    VersionedTextDocumentIdentifier other = (VersionedTextDocumentIdentifier) obj;
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
    return 31 * super.hashCode() + ((this.version== null) ? 0 : this.version.hashCode());
  }
}
