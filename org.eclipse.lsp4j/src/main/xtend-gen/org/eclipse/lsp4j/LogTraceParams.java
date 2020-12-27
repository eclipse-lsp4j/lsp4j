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

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A notification to log the trace of the server's execution. The amount and content of these notifications
 * depends on the current trace configuration. If trace is 'off', the server should not send any logTrace
 * notification. If trace is 'message', the server should not add the 'verbose' field.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class LogTraceParams {
  /**
   * The message to be logged.
   */
  @NonNull
  private String message;
  
  /**
   * Additional information that can be computed if the {@code trace} configuration
   * is set to {@link TraceValue#Verbose}
   */
  private String verbose;
  
  public LogTraceParams() {
  }
  
  public LogTraceParams(@NonNull final String message) {
    this.message = Preconditions.<String>checkNotNull(message, "message");
  }
  
  public LogTraceParams(@NonNull final String message, final String verbose) {
    this.message = Preconditions.<String>checkNotNull(message, "message");
    this.verbose = verbose;
  }
  
  /**
   * The message to be logged.
   */
  @Pure
  @NonNull
  public String getMessage() {
    return this.message;
  }
  
  /**
   * The message to be logged.
   */
  public void setMessage(@NonNull final String message) {
    this.message = Preconditions.checkNotNull(message, "message");
  }
  
  /**
   * Additional information that can be computed if the {@code trace} configuration
   * is set to {@link TraceValue#Verbose}
   */
  @Pure
  public String getVerbose() {
    return this.verbose;
  }
  
  /**
   * Additional information that can be computed if the {@code trace} configuration
   * is set to {@link TraceValue#Verbose}
   */
  public void setVerbose(final String verbose) {
    this.verbose = verbose;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("message", this.message);
    b.add("verbose", this.verbose);
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
    LogTraceParams other = (LogTraceParams) obj;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    if (this.verbose == null) {
      if (other.verbose != null)
        return false;
    } else if (!this.verbose.equals(other.verbose))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    return prime * result + ((this.verbose== null) ? 0 : this.verbose.hashCode());
  }
}
