/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import java.util.ArrayList;
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
  
  public CodeActionContext() {
    this(new ArrayList<Diagnostic>());
  }
  
  public CodeActionContext(@NonNull final List<Diagnostic> diagnostics) {
    this.diagnostics = diagnostics;
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
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("diagnostics", this.diagnostics);
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.diagnostics== null) ? 0 : this.diagnostics.hashCode());
    return result;
  }
}
