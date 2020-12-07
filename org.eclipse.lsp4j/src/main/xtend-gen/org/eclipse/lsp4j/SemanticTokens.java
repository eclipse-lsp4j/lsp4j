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
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class SemanticTokens {
  /**
   * An optional result id. If provided and clients support delta updating
   * the client will include the result id in the next semantic token request.
   * A server can then instead of computing all semantic tokens again simply
   * send a delta.
   */
  private String resultId;
  
  /**
   * The actual tokens.
   */
  @NonNull
  private List<Integer> data;
  
  public SemanticTokens(@NonNull final List<Integer> data) {
    this.data = Preconditions.<List<Integer>>checkNotNull(data, "data");
  }
  
  public SemanticTokens(final String resultId, @NonNull final List<Integer> data) {
    this(data);
    this.resultId = resultId;
  }
  
  /**
   * An optional result id. If provided and clients support delta updating
   * the client will include the result id in the next semantic token request.
   * A server can then instead of computing all semantic tokens again simply
   * send a delta.
   */
  @Pure
  public String getResultId() {
    return this.resultId;
  }
  
  /**
   * An optional result id. If provided and clients support delta updating
   * the client will include the result id in the next semantic token request.
   * A server can then instead of computing all semantic tokens again simply
   * send a delta.
   */
  public void setResultId(final String resultId) {
    this.resultId = resultId;
  }
  
  /**
   * The actual tokens.
   */
  @Pure
  @NonNull
  public List<Integer> getData() {
    return this.data;
  }
  
  /**
   * The actual tokens.
   */
  public void setData(@NonNull final List<Integer> data) {
    this.data = Preconditions.checkNotNull(data, "data");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("resultId", this.resultId);
    b.add("data", this.data);
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
    SemanticTokens other = (SemanticTokens) obj;
    if (this.resultId == null) {
      if (other.resultId != null)
        return false;
    } else if (!this.resultId.equals(other.resultId))
      return false;
    if (this.data == null) {
      if (other.data != null)
        return false;
    } else if (!this.data.equals(other.data))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.resultId== null) ? 0 : this.resultId.hashCode());
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
