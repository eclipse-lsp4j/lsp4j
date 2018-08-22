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
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Contains additional diagnostic information about the context in which a code action is run.
 */
@SuppressWarnings("all")
public class CodeActionContext {
  /**
   * An array of diagnostics.
   */
  @NonNull
  private List<Diagnostic> diagnostics;
  
  /**
   * Requested kind of actions to return.
   * 
   * Actions not of this kind are filtered out by the client before being shown. So servers
   * can omit computing them.
   * 
   * See {@link CodeActionKind} for allowed values.
   */
  private List<String> only;
  
  public CodeActionContext() {
  }
  
  public CodeActionContext(@NonNull final List<Diagnostic> diagnostics) {
    this.diagnostics = diagnostics;
  }
  
  public CodeActionContext(@NonNull final List<Diagnostic> diagnostics, final List<String> only) {
    this(diagnostics);
    this.only = only;
  }
  
  /**
   * An array of diagnostics.
   */
  @Pure
  @NonNull
  public List<Diagnostic> getDiagnostics() {
    return this.diagnostics;
  }
  
  /**
   * An array of diagnostics.
   */
  public void setDiagnostics(@NonNull final List<Diagnostic> diagnostics) {
    this.diagnostics = diagnostics;
  }
  
  /**
   * Requested kind of actions to return.
   * 
   * Actions not of this kind are filtered out by the client before being shown. So servers
   * can omit computing them.
   * 
   * See {@link CodeActionKind} for allowed values.
   */
  @Pure
  public List<String> getOnly() {
    return this.only;
  }
  
  /**
   * Requested kind of actions to return.
   * 
   * Actions not of this kind are filtered out by the client before being shown. So servers
   * can omit computing them.
   * 
   * See {@link CodeActionKind} for allowed values.
   */
  public void setOnly(final List<String> only) {
    this.only = only;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("diagnostics", this.diagnostics);
    b.add("only", this.only);
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
    CodeActionContext other = (CodeActionContext) obj;
    if (this.diagnostics == null) {
      if (other.diagnostics != null)
        return false;
    } else if (!this.diagnostics.equals(other.diagnostics))
      return false;
    if (this.only == null) {
      if (other.only != null)
        return false;
    } else if (!this.only.equals(other.only))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.diagnostics== null) ? 0 : this.diagnostics.hashCode());
    return prime * result + ((this.only== null) ? 0 : this.only.hashCode());
  }
}
