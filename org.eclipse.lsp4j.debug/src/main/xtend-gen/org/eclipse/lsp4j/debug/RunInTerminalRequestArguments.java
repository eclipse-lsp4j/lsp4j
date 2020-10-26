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

import java.util.Arrays;
import java.util.Map;
import org.eclipse.lsp4j.debug.RunInTerminalRequestArgumentsKind;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'runInTerminal' request.
 */
@SuppressWarnings("all")
public class RunInTerminalRequestArguments {
  /**
   * What kind of terminal to launch.
   * <p>
   * This is an optional property.
   */
  private RunInTerminalRequestArgumentsKind kind;
  
  /**
   * Optional title of the terminal.
   * <p>
   * This is an optional property.
   */
  private String title;
  
  /**
   * Working directory of the command.
   */
  @NonNull
  private String cwd;
  
  /**
   * List of arguments. The first argument is the command to run.
   */
  @NonNull
  private String[] args;
  
  /**
   * Environment key-value pairs that are added to or removed from the default environment.
   * <p>
   * This is an optional property.
   */
  private Map<String, String> env;
  
  /**
   * What kind of terminal to launch.
   * <p>
   * This is an optional property.
   */
  @Pure
  public RunInTerminalRequestArgumentsKind getKind() {
    return this.kind;
  }
  
  /**
   * What kind of terminal to launch.
   * <p>
   * This is an optional property.
   */
  public void setKind(final RunInTerminalRequestArgumentsKind kind) {
    this.kind = kind;
  }
  
  /**
   * Optional title of the terminal.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getTitle() {
    return this.title;
  }
  
  /**
   * Optional title of the terminal.
   * <p>
   * This is an optional property.
   */
  public void setTitle(final String title) {
    this.title = title;
  }
  
  /**
   * Working directory of the command.
   */
  @Pure
  @NonNull
  public String getCwd() {
    return this.cwd;
  }
  
  /**
   * Working directory of the command.
   */
  public void setCwd(@NonNull final String cwd) {
    this.cwd = Preconditions.checkNotNull(cwd, "cwd");
  }
  
  /**
   * List of arguments. The first argument is the command to run.
   */
  @Pure
  @NonNull
  public String[] getArgs() {
    return this.args;
  }
  
  /**
   * List of arguments. The first argument is the command to run.
   */
  public void setArgs(@NonNull final String[] args) {
    this.args = Preconditions.checkNotNull(args, "args");
  }
  
  /**
   * Environment key-value pairs that are added to or removed from the default environment.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Map<String, String> getEnv() {
    return this.env;
  }
  
  /**
   * Environment key-value pairs that are added to or removed from the default environment.
   * <p>
   * This is an optional property.
   */
  public void setEnv(final Map<String, String> env) {
    this.env = env;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("kind", this.kind);
    b.add("title", this.title);
    b.add("cwd", this.cwd);
    b.add("args", this.args);
    b.add("env", this.env);
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
    RunInTerminalRequestArguments other = (RunInTerminalRequestArguments) obj;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    if (this.title == null) {
      if (other.title != null)
        return false;
    } else if (!this.title.equals(other.title))
      return false;
    if (this.cwd == null) {
      if (other.cwd != null)
        return false;
    } else if (!this.cwd.equals(other.cwd))
      return false;
    if (this.args == null) {
      if (other.args != null)
        return false;
    } else if (!Arrays.deepEquals(this.args, other.args))
      return false;
    if (this.env == null) {
      if (other.env != null)
        return false;
    } else if (!this.env.equals(other.env))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    result = prime * result + ((this.title== null) ? 0 : this.title.hashCode());
    result = prime * result + ((this.cwd== null) ? 0 : this.cwd.hashCode());
    result = prime * result + ((this.args== null) ? 0 : Arrays.deepHashCode(this.args));
    return prime * result + ((this.env== null) ? 0 : this.env.hashCode());
  }
}
