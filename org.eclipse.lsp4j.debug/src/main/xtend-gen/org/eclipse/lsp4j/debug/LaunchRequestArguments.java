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
 * Arguments for 'launch' request. Additional attributes are implementation specific.
 */
@SuppressWarnings("all")
public class LaunchRequestArguments {
  /**
   * If noDebug is true the launch request should launch the program without enabling debugging.
   * <p>
   * This is an optional property.
   */
  private Boolean noDebug;
  
  /**
   * Optional data from the previous, restarted session.
   * <p>
   * The data is sent as the 'restart' attribute of the 'terminated' event.
   * <p>
   * The client should leave the data intact.
   * <p>
   * This is an optional property.
   */
  private Object __restart;
  
  /**
   * If noDebug is true the launch request should launch the program without enabling debugging.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getNoDebug() {
    return this.noDebug;
  }
  
  /**
   * If noDebug is true the launch request should launch the program without enabling debugging.
   * <p>
   * This is an optional property.
   */
  public void setNoDebug(final Boolean noDebug) {
    this.noDebug = noDebug;
  }
  
  /**
   * Optional data from the previous, restarted session.
   * <p>
   * The data is sent as the 'restart' attribute of the 'terminated' event.
   * <p>
   * The client should leave the data intact.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Object get__restart() {
    return this.__restart;
  }
  
  /**
   * Optional data from the previous, restarted session.
   * <p>
   * The data is sent as the 'restart' attribute of the 'terminated' event.
   * <p>
   * The client should leave the data intact.
   * <p>
   * This is an optional property.
   */
  public void set__restart(final Object __restart) {
    this.__restart = __restart;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("noDebug", this.noDebug);
    b.add("__restart", this.__restart);
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
    LaunchRequestArguments other = (LaunchRequestArguments) obj;
    if (this.noDebug == null) {
      if (other.noDebug != null)
        return false;
    } else if (!this.noDebug.equals(other.noDebug))
      return false;
    if (this.__restart == null) {
      if (other.__restart != null)
        return false;
    } else if (!this.__restart.equals(other.__restart))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.noDebug== null) ? 0 : this.noDebug.hashCode());
    return prime * result + ((this.__restart== null) ? 0 : this.__restart.hashCode());
  }
}
