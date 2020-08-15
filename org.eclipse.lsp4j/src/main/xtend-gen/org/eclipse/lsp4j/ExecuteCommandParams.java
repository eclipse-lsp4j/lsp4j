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

import java.util.List;
import org.eclipse.lsp4j.WorkDoneProgressParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The workspace/executeCommand request is sent from the client to the server to trigger command
 * execution on the server. In most cases the server creates a WorkspaceEdit structure and applies
 * the changes to the workspace using the request workspace/applyEdit which is sent from the server
 * to the client.
 */
@SuppressWarnings("all")
public class ExecuteCommandParams implements WorkDoneProgressParams {
  private Either<String, Number> workDoneToken;
  
  /**
   * The identifier of the actual command handler.
   */
  @NonNull
  private String command;
  
  /**
   * Arguments that the command should be invoked with.
   * The arguments are typically specified when a command is returned from the server to the client.
   * Example requests that return a command are textDocument/codeAction or textDocument/codeLens.
   */
  private List<Object> arguments;
  
  public ExecuteCommandParams() {
  }
  
  public ExecuteCommandParams(@NonNull final String command, final List<Object> arguments) {
    this.command = Preconditions.<String>checkNotNull(command, "command");
    this.arguments = arguments;
  }
  
  public ExecuteCommandParams(@NonNull final String command, final List<Object> arguments, final Either<String, Number> workDoneToken) {
    this.command = Preconditions.<String>checkNotNull(command, "command");
    this.arguments = arguments;
    this.workDoneToken = workDoneToken;
  }
  
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
  
  /**
   * The identifier of the actual command handler.
   */
  @Pure
  @NonNull
  public String getCommand() {
    return this.command;
  }
  
  /**
   * The identifier of the actual command handler.
   */
  public void setCommand(@NonNull final String command) {
    this.command = Preconditions.checkNotNull(command, "command");
  }
  
  /**
   * Arguments that the command should be invoked with.
   * The arguments are typically specified when a command is returned from the server to the client.
   * Example requests that return a command are textDocument/codeAction or textDocument/codeLens.
   */
  @Pure
  public List<Object> getArguments() {
    return this.arguments;
  }
  
  /**
   * Arguments that the command should be invoked with.
   * The arguments are typically specified when a command is returned from the server to the client.
   * Example requests that return a command are textDocument/codeAction or textDocument/codeLens.
   */
  public void setArguments(final List<Object> arguments) {
    this.arguments = arguments;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workDoneToken", this.workDoneToken);
    b.add("command", this.command);
    b.add("arguments", this.arguments);
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
    ExecuteCommandParams other = (ExecuteCommandParams) obj;
    if (this.workDoneToken == null) {
      if (other.workDoneToken != null)
        return false;
    } else if (!this.workDoneToken.equals(other.workDoneToken))
      return false;
    if (this.command == null) {
      if (other.command != null)
        return false;
    } else if (!this.command.equals(other.command))
      return false;
    if (this.arguments == null) {
      if (other.arguments != null)
        return false;
    } else if (!this.arguments.equals(other.arguments))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.workDoneToken== null) ? 0 : this.workDoneToken.hashCode());
    result = prime * result + ((this.command== null) ? 0 : this.command.hashCode());
    return prime * result + ((this.arguments== null) ? 0 : this.arguments.hashCode());
  }
}
