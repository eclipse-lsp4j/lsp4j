package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class ExecuteCommandParams {
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
    this.command = command;
    this.arguments = arguments;
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
    this.command = command;
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
    result = prime * result + ((this.command== null) ? 0 : this.command.hashCode());
    result = prime * result + ((this.arguments== null) ? 0 : this.arguments.hashCode());
    return result;
  }
}
