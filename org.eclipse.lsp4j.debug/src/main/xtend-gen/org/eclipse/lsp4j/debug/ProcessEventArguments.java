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

import org.eclipse.lsp4j.debug.ProcessEventArgumentsStartMethod;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event indicates that the debugger has begun debugging a new process. Either one that it has launched, or
 * one that it has attached to.
 */
@SuppressWarnings("all")
public class ProcessEventArguments {
  /**
   * The logical name of the process. This is usually the full path to process's executable file. Example:
   * /home/example/myproj/program.js.
   */
  @NonNull
  private String name;
  
  /**
   * The system process id of the debugged process. This property will be missing for non-system processes.
   * <p>
   * This is an optional property.
   */
  private Integer systemProcessId;
  
  /**
   * If true, the process is running on the same computer as the debug adapter.
   * <p>
   * This is an optional property.
   */
  private Boolean isLocalProcess;
  
  /**
   * Describes how the debug engine started debugging this process.
   * <p>
   * This is an optional property.
   */
  private ProcessEventArgumentsStartMethod startMethod;
  
  /**
   * The size of a pointer or address for this process, in bits. This value may be used by clients when formatting
   * addresses for display.
   * <p>
   * This is an optional property.
   */
  private Integer pointerSize;
  
  /**
   * The logical name of the process. This is usually the full path to process's executable file. Example:
   * /home/example/myproj/program.js.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The logical name of the process. This is usually the full path to process's executable file. Example:
   * /home/example/myproj/program.js.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  /**
   * The system process id of the debugged process. This property will be missing for non-system processes.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getSystemProcessId() {
    return this.systemProcessId;
  }
  
  /**
   * The system process id of the debugged process. This property will be missing for non-system processes.
   * <p>
   * This is an optional property.
   */
  public void setSystemProcessId(final Integer systemProcessId) {
    this.systemProcessId = systemProcessId;
  }
  
  /**
   * If true, the process is running on the same computer as the debug adapter.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getIsLocalProcess() {
    return this.isLocalProcess;
  }
  
  /**
   * If true, the process is running on the same computer as the debug adapter.
   * <p>
   * This is an optional property.
   */
  public void setIsLocalProcess(final Boolean isLocalProcess) {
    this.isLocalProcess = isLocalProcess;
  }
  
  /**
   * Describes how the debug engine started debugging this process.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ProcessEventArgumentsStartMethod getStartMethod() {
    return this.startMethod;
  }
  
  /**
   * Describes how the debug engine started debugging this process.
   * <p>
   * This is an optional property.
   */
  public void setStartMethod(final ProcessEventArgumentsStartMethod startMethod) {
    this.startMethod = startMethod;
  }
  
  /**
   * The size of a pointer or address for this process, in bits. This value may be used by clients when formatting
   * addresses for display.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getPointerSize() {
    return this.pointerSize;
  }
  
  /**
   * The size of a pointer or address for this process, in bits. This value may be used by clients when formatting
   * addresses for display.
   * <p>
   * This is an optional property.
   */
  public void setPointerSize(final Integer pointerSize) {
    this.pointerSize = pointerSize;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("systemProcessId", this.systemProcessId);
    b.add("isLocalProcess", this.isLocalProcess);
    b.add("startMethod", this.startMethod);
    b.add("pointerSize", this.pointerSize);
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
    ProcessEventArguments other = (ProcessEventArguments) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.systemProcessId == null) {
      if (other.systemProcessId != null)
        return false;
    } else if (!this.systemProcessId.equals(other.systemProcessId))
      return false;
    if (this.isLocalProcess == null) {
      if (other.isLocalProcess != null)
        return false;
    } else if (!this.isLocalProcess.equals(other.isLocalProcess))
      return false;
    if (this.startMethod == null) {
      if (other.startMethod != null)
        return false;
    } else if (!this.startMethod.equals(other.startMethod))
      return false;
    if (this.pointerSize == null) {
      if (other.pointerSize != null)
        return false;
    } else if (!this.pointerSize.equals(other.pointerSize))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.systemProcessId== null) ? 0 : this.systemProcessId.hashCode());
    result = prime * result + ((this.isLocalProcess== null) ? 0 : this.isLocalProcess.hashCode());
    result = prime * result + ((this.startMethod== null) ? 0 : this.startMethod.hashCode());
    return prime * result + ((this.pointerSize== null) ? 0 : this.pointerSize.hashCode());
  }
}
