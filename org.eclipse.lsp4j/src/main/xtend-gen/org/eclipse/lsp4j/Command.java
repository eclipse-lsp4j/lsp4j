package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a reference to a command. Provides a title which will be used to represent a command in the UI and,
 * optionally, an array of arguments which will be passed to the command handler function when invoked.
 */
@SuppressWarnings("all")
public class Command {
  /**
   * Title of the command, like `save`.
   */
  @NonNull
  private String title;
  
  /**
   * The identifier of the actual command handler.
   */
  @NonNull
  private String command;
  
  /**
   * Arguments that the command handler should be invoked with.
   */
  private List<Object> arguments;
  
  /**
   * Title of the command, like `save`.
   */
  @Pure
  public String getTitle() {
    return this.title;
  }
  
  /**
   * Title of the command, like `save`.
   */
  public void setTitle(final String title) {
    this.title = title;
  }
  
  /**
   * The identifier of the actual command handler.
   */
  @Pure
  public String getCommand() {
    return this.command;
  }
  
  /**
   * The identifier of the actual command handler.
   */
  public void setCommand(final String command) {
    this.command = command;
  }
  
  /**
   * Arguments that the command handler should be invoked with.
   */
  @Pure
  public List<Object> getArguments() {
    return this.arguments;
  }
  
  /**
   * Arguments that the command handler should be invoked with.
   */
  public void setArguments(final List<Object> arguments) {
    this.arguments = arguments;
  }
  
  public Command() {
    
  }
  
  public Command(final String title, final String command, final List<Object> arguments) {
    this.title = title;
    this.command = command;
    this.arguments = arguments;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("title", this.title);
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
    if (!super.equals(obj))
      return false;
    Command other = (Command) obj;
    if (this.title == null) {
      if (other.title != null)
        return false;
    } else if (!this.title.equals(other.title))
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
    int result = super.hashCode();
    result = prime * result + ((this.title== null) ? 0 : this.title.hashCode());
    result = prime * result + ((this.command== null) ? 0 : this.command.hashCode());
    result = prime * result + ((this.arguments== null) ? 0 : this.arguments.hashCode());
    return result;
  }
}
