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

import org.eclipse.lsp4j.PartialResultParams;
import org.eclipse.lsp4j.WorkDoneProgressParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Abstract class which implements work done progress and partial result request parameter.
 * It is not present in protocol specification, so it's just "dry" class.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public abstract class WorkDoneProgressAndPartialResultParams implements WorkDoneProgressParams, PartialResultParams {
  private Either<String, Number> workDoneToken;
  
  private Either<String, Number> partialResultToken;
  
  @Pure
  public Either<String, Number> getWorkDoneToken() {
    return this.workDoneToken;
  }
  
  public void setWorkDoneToken(final Either<String, Number> workDoneToken) {
    this.workDoneToken = workDoneToken;
  }
  
  public void setWorkDoneToken(final String workDoneToken) {
    if (workDoneToken == null) {
      this.workDoneToken = null;
      return;
    }
    this.workDoneToken = Either.forLeft(workDoneToken);
  }
  
  public void setWorkDoneToken(final Number workDoneToken) {
    if (workDoneToken == null) {
      this.workDoneToken = null;
      return;
    }
    this.workDoneToken = Either.forRight(workDoneToken);
  }
  
  @Pure
  public Either<String, Number> getPartialResultToken() {
    return this.partialResultToken;
  }
  
  public void setPartialResultToken(final Either<String, Number> partialResultToken) {
    this.partialResultToken = partialResultToken;
  }
  
  public void setPartialResultToken(final String partialResultToken) {
    if (partialResultToken == null) {
      this.partialResultToken = null;
      return;
    }
    this.partialResultToken = Either.forLeft(partialResultToken);
  }
  
  public void setPartialResultToken(final Number partialResultToken) {
    if (partialResultToken == null) {
      this.partialResultToken = null;
      return;
    }
    this.partialResultToken = Either.forRight(partialResultToken);
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workDoneToken", this.workDoneToken);
    b.add("partialResultToken", this.partialResultToken);
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
    WorkDoneProgressAndPartialResultParams other = (WorkDoneProgressAndPartialResultParams) obj;
    if (this.workDoneToken == null) {
      if (other.workDoneToken != null)
        return false;
    } else if (!this.workDoneToken.equals(other.workDoneToken))
      return false;
    if (this.partialResultToken == null) {
      if (other.partialResultToken != null)
        return false;
    } else if (!this.partialResultToken.equals(other.partialResultToken))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.workDoneToken== null) ? 0 : this.workDoneToken.hashCode());
    return prime * result + ((this.partialResultToken== null) ? 0 : this.partialResultToken.hashCode());
  }
}
