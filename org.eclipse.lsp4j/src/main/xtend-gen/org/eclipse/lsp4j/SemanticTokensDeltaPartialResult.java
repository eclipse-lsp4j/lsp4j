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
import org.eclipse.lsp4j.SemanticTokensEdit;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class SemanticTokensDeltaPartialResult {
  @NonNull
  private List<SemanticTokensEdit> edits;
  
  public SemanticTokensDeltaPartialResult(@NonNull final List<SemanticTokensEdit> edits) {
    this.edits = Preconditions.<List<SemanticTokensEdit>>checkNotNull(edits, "edits");
  }
  
  @Pure
  @NonNull
  public List<SemanticTokensEdit> getEdits() {
    return this.edits;
  }
  
  public void setEdits(@NonNull final List<SemanticTokensEdit> edits) {
    this.edits = Preconditions.checkNotNull(edits, "edits");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("edits", this.edits);
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
    SemanticTokensDeltaPartialResult other = (SemanticTokensDeltaPartialResult) obj;
    if (this.edits == null) {
      if (other.edits != null)
        return false;
    } else if (!this.edits.equals(other.edits))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.edits== null) ? 0 : this.edits.hashCode());
  }
}
