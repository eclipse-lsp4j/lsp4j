/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A code lens represents a command that should be shown along with source text, like the number of references,
 * a way to run tests, etc.
 * 
 * A code lens is <em>unresolved</em> when no command is associated to it. For performance reasons the creation of a
 * code lens and resolving should be done to two stages.
 */
@SuppressWarnings("all")
public class CodeLens {
  /**
   * The range in which this code lens is valid. Should only span a single line.
   */
  @NonNull
  private Range range;
  
  /**
   * The command this code lens represents.
   */
  private Command command;
  
  /**
   * An data entry field that is preserved on a code lens item between a code lens and a code lens resolve request.
   */
  private Object data;
  
  public CodeLens() {
  }
  
  public CodeLens(@NonNull final Range range) {
    this.range = range;
  }
  
  public CodeLens(@NonNull final Range range, final Command command, final Object data) {
    this(range);
    this.command = command;
    this.data = data;
  }
  
  /**
   * The range in which this code lens is valid. Should only span a single line.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range in which this code lens is valid. Should only span a single line.
   */
  public void setRange(@NonNull final Range range) {
    this.range = range;
  }
  
  /**
   * The command this code lens represents.
   */
  @Pure
  public Command getCommand() {
    return this.command;
  }
  
  /**
   * The command this code lens represents.
   */
  public void setCommand(final Command command) {
    this.command = command;
  }
  
  /**
   * An data entry field that is preserved on a code lens item between a code lens and a code lens resolve request.
   */
  @Pure
  public Object getData() {
    return this.data;
  }
  
  /**
   * An data entry field that is preserved on a code lens item between a code lens and a code lens resolve request.
   */
  public void setData(final Object data) {
    this.data = data;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("command", this.command);
    b.add("data", this.data);
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
    CodeLens other = (CodeLens) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.command == null) {
      if (other.command != null)
        return false;
    } else if (!this.command.equals(other.command))
      return false;
    if (this.data == null) {
      if (other.data != null)
        return false;
    } else if (!this.data.equals(other.data))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.command== null) ? 0 : this.command.hashCode());
    result = prime * result + ((this.data== null) ? 0 : this.data.hashCode());
    return result;
  }
}
