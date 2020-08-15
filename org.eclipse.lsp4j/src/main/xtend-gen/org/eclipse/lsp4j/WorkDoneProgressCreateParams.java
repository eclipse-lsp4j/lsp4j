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

import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class WorkDoneProgressCreateParams {
  /**
   * The token to be used to report progress.
   */
  private Either<String, Number> token;
  
  public WorkDoneProgressCreateParams() {
  }
  
  public WorkDoneProgressCreateParams(@NonNull final Either<String, Number> token) {
    this.token = token;
  }
  
  /**
   * The token to be used to report progress.
   */
  @Pure
  public Either<String, Number> getToken() {
    return this.token;
  }
  
  /**
   * The token to be used to report progress.
   */
  public void setToken(final Either<String, Number> token) {
    this.token = token;
  }
  
  public void setToken(final String token) {
    if (token == null) {
      this.token = null;
      return;
    }
    this.token = Either.forLeft(token);
  }
  
  public void setToken(final Number token) {
    if (token == null) {
      this.token = null;
      return;
    }
    this.token = Either.forRight(token);
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("token", this.token);
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
    WorkDoneProgressCreateParams other = (WorkDoneProgressCreateParams) obj;
    if (this.token == null) {
      if (other.token != null)
        return false;
    } else if (!this.token.equals(other.token))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.token== null) ? 0 : this.token.hashCode());
  }
}
