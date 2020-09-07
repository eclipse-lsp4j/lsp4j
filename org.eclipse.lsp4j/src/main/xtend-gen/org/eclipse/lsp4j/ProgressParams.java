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

import com.google.gson.annotations.JsonAdapter;
import org.eclipse.lsp4j.WorkDoneProgressNotification;
import org.eclipse.lsp4j.adapters.WorkDoneProgressNotificationAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The base protocol offers also support to report progress in a generic fashion.
 * This mechanism can be used to report any kind of progress including work done progress
 * (usually used to report progress in the user interface using a progress bar)
 * and partial result progress to support streaming of results.
 * A progress notification has the following properties:
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public class ProgressParams {
  private Either<String, Number> token;
  
  @JsonAdapter(WorkDoneProgressNotificationAdapter.Factory.class)
  private WorkDoneProgressNotification value;
  
  public ProgressParams() {
  }
  
  public ProgressParams(@NonNull final Either<String, Number> token, final WorkDoneProgressNotification value) {
    this.token = token;
    this.value = value;
  }
  
  @Pure
  public Either<String, Number> getToken() {
    return this.token;
  }
  
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
  
  @Pure
  public WorkDoneProgressNotification getValue() {
    return this.value;
  }
  
  public void setValue(final WorkDoneProgressNotification value) {
    this.value = value;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("token", this.token);
    b.add("value", this.value);
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
    ProgressParams other = (ProgressParams) obj;
    if (this.token == null) {
      if (other.token != null)
        return false;
    } else if (!this.token.equals(other.token))
      return false;
    if (this.value == null) {
      if (other.value != null)
        return false;
    } else if (!this.value.equals(other.value))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.token== null) ? 0 : this.token.hashCode());
    return prime * result + ((this.value== null) ? 0 : this.value.hashCode());
  }
}
