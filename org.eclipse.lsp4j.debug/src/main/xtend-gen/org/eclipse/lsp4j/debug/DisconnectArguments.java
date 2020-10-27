/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'disconnect' request.
 */
@SuppressWarnings("all")
public class DisconnectArguments {
  /**
   * A value of true indicates that this 'disconnect' request is part of a restart sequence.
   * <p>
   * This is an optional property.
   */
  private Boolean restart;
  
  /**
   * Indicates whether the debuggee should be terminated when the debugger is disconnected.
   * <p>
   * If unspecified, the debug adapter is free to do whatever it thinks is best.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportTerminateDebuggee' is true.
   * <p>
   * This is an optional property.
   */
  private Boolean terminateDebuggee;
  
  /**
   * A value of true indicates that this 'disconnect' request is part of a restart sequence.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getRestart() {
    return this.restart;
  }
  
  /**
   * A value of true indicates that this 'disconnect' request is part of a restart sequence.
   * <p>
   * This is an optional property.
   */
  public void setRestart(final Boolean restart) {
    this.restart = restart;
  }
  
  /**
   * Indicates whether the debuggee should be terminated when the debugger is disconnected.
   * <p>
   * If unspecified, the debug adapter is free to do whatever it thinks is best.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportTerminateDebuggee' is true.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getTerminateDebuggee() {
    return this.terminateDebuggee;
  }
  
  /**
   * Indicates whether the debuggee should be terminated when the debugger is disconnected.
   * <p>
   * If unspecified, the debug adapter is free to do whatever it thinks is best.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportTerminateDebuggee' is true.
   * <p>
   * This is an optional property.
   */
  public void setTerminateDebuggee(final Boolean terminateDebuggee) {
    this.terminateDebuggee = terminateDebuggee;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("restart", this.restart);
    b.add("terminateDebuggee", this.terminateDebuggee);
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
    DisconnectArguments other = (DisconnectArguments) obj;
    if (this.restart == null) {
      if (other.restart != null)
        return false;
    } else if (!this.restart.equals(other.restart))
      return false;
    if (this.terminateDebuggee == null) {
      if (other.terminateDebuggee != null)
        return false;
    } else if (!this.terminateDebuggee.equals(other.terminateDebuggee))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.restart== null) ? 0 : this.restart.hashCode());
    return prime * result + ((this.terminateDebuggee== null) ? 0 : this.terminateDebuggee.hashCode());
  }
}
