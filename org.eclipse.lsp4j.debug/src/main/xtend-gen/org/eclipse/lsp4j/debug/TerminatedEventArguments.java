/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event message for 'terminated' event types.
 * <p>
 * The event indicates that debugging of the debuggee has terminated.
 */
@SuppressWarnings("all")
public class TerminatedEventArguments {
  /**
   * A debug adapter may set 'restart' to true (or to an arbitrary object) to request that the front end restarts
   * the session.
   * <p>
   * The value is not interpreted by the client and passed unmodified as an attribute '__restart' to the
   * launchRequest.
   * <p>
   * This is an optional property.
   */
  private Object restart;
  
  /**
   * A debug adapter may set 'restart' to true (or to an arbitrary object) to request that the front end restarts
   * the session.
   * <p>
   * The value is not interpreted by the client and passed unmodified as an attribute '__restart' to the
   * launchRequest.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Object getRestart() {
    return this.restart;
  }
  
  /**
   * A debug adapter may set 'restart' to true (or to an arbitrary object) to request that the front end restarts
   * the session.
   * <p>
   * The value is not interpreted by the client and passed unmodified as an attribute '__restart' to the
   * launchRequest.
   * <p>
   * This is an optional property.
   */
  public void setRestart(final Object restart) {
    this.restart = restart;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("restart", this.restart);
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
    TerminatedEventArguments other = (TerminatedEventArguments) obj;
    if (this.restart == null) {
      if (other.restart != null)
        return false;
    } else if (!this.restart.equals(other.restart))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.restart== null) ? 0 : this.restart.hashCode());
    return result;
  }
}
