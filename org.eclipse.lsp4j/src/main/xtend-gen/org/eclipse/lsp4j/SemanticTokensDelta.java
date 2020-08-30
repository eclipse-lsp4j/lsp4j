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
import java.util.List;
import org.eclipse.lsp4j.SemanticTokensEdit;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class SemanticTokensDelta {
  private String resultId;
  
  /**
   * The semantic token edits to transform a previous result into a new result.
   */
  @NonNull
  private List<SemanticTokensEdit> edits;
  
  public SemanticTokensDelta(@NonNull final List<SemanticTokensEdit> edits) {
    this.edits = Preconditions.<List<SemanticTokensEdit>>checkNotNull(edits, "edits");
  }
  
  @Pure
  public String getResultId() {
    return this.resultId;
  }
  
  public void setResultId(final String resultId) {
    this.resultId = resultId;
  }
  
  /**
   * The semantic token edits to transform a previous result into a new result.
   */
  @Pure
  @NonNull
  public List<SemanticTokensEdit> getEdits() {
    return this.edits;
  }
  
  /**
   * The semantic token edits to transform a previous result into a new result.
   */
  public void setEdits(@NonNull final List<SemanticTokensEdit> edits) {
    this.edits = Preconditions.checkNotNull(edits, "edits");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("resultId", this.resultId);
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
    SemanticTokensDelta other = (SemanticTokensDelta) obj;
    if (this.resultId == null) {
      if (other.resultId != null)
        return false;
    } else if (!this.resultId.equals(other.resultId))
      return false;
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
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.resultId== null) ? 0 : this.resultId.hashCode());
    return prime * result + ((this.edits== null) ? 0 : this.edits.hashCode());
  }
}
