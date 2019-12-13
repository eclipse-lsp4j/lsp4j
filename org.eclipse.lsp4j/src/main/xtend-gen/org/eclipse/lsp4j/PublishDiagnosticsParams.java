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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Diagnostics notification are sent from the server to the client to signal results of validation runs.
 */
@SuppressWarnings("all")
public class PublishDiagnosticsParams {
  /**
   * The URI for which diagnostic information is reported.
   */
  @NonNull
  private String uri;
  
  /**
   * An array of diagnostic information items.
   */
  @NonNull
  private List<Diagnostic> diagnostics;
  
  /**
   * Optional the version number of the document the diagnostics are published for.
   * 
   * @since 3.15.0
   */
  private int version;
  
  public PublishDiagnosticsParams() {
    ArrayList<Diagnostic> _arrayList = new ArrayList<Diagnostic>();
    this.diagnostics = _arrayList;
  }
  
  public PublishDiagnosticsParams(@NonNull final String uri, @NonNull final List<Diagnostic> diagnostics) {
    this.uri = Preconditions.<String>checkNotNull(uri, "uri");
    this.diagnostics = Preconditions.<List<Diagnostic>>checkNotNull(diagnostics, "diagnostics");
  }
  
  /**
   * The URI for which diagnostic information is reported.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The URI for which diagnostic information is reported.
   */
  public void setUri(@NonNull final String uri) {
    this.uri = Preconditions.checkNotNull(uri, "uri");
  }
  
  /**
   * An array of diagnostic information items.
   */
  @Pure
  @NonNull
  public List<Diagnostic> getDiagnostics() {
    return this.diagnostics;
  }
  
  /**
   * An array of diagnostic information items.
   */
  public void setDiagnostics(@NonNull final List<Diagnostic> diagnostics) {
    this.diagnostics = Preconditions.checkNotNull(diagnostics, "diagnostics");
  }
  
  /**
   * Optional the version number of the document the diagnostics are published for.
   * 
   * @since 3.15.0
   */
  @Pure
  public int getVersion() {
    return this.version;
  }
  
  /**
   * Optional the version number of the document the diagnostics are published for.
   * 
   * @since 3.15.0
   */
  public void setVersion(final int version) {
    this.version = version;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
    b.add("diagnostics", this.diagnostics);
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
    PublishDiagnosticsParams other = (PublishDiagnosticsParams) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.diagnostics == null) {
      if (other.diagnostics != null)
        return false;
    } else if (!this.diagnostics.equals(other.diagnostics))
      return false;
    if (other.version != this.version)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    result = prime * result + ((this.diagnostics== null) ? 0 : this.diagnostics.hashCode());
    return prime * result + this.version;
  }
}
