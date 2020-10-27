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

import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a single disassembled instruction.
 */
@SuppressWarnings("all")
public class DisassembledInstruction {
  /**
   * The address of the instruction. Treated as a hex value if prefixed with '0x', or as a decimal value otherwise.
   */
  @NonNull
  private String address;
  
  /**
   * Optional raw bytes representing the instruction and its operands, in an implementation-defined format.
   * <p>
   * This is an optional property.
   */
  private String instructionBytes;
  
  /**
   * Text representing the instruction and its operands, in an implementation-defined format.
   */
  @NonNull
  private String instruction;
  
  /**
   * Name of the symbol that corresponds with the location of this instruction, if any.
   * <p>
   * This is an optional property.
   */
  private String symbol;
  
  /**
   * Source location that corresponds to this instruction, if any.
   * <p>
   * Should always be set (if available) on the first instruction returned,
   * <p>
   * but can be omitted afterwards if this instruction maps to the same source file as the previous instruction.
   * <p>
   * This is an optional property.
   */
  private Source location;
  
  /**
   * The line within the source location that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  private Integer line;
  
  /**
   * The column within the line that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  private Integer column;
  
  /**
   * The end line of the range that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  private Integer endLine;
  
  /**
   * The end column of the range that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  private Integer endColumn;
  
  /**
   * The address of the instruction. Treated as a hex value if prefixed with '0x', or as a decimal value otherwise.
   */
  @Pure
  @NonNull
  public String getAddress() {
    return this.address;
  }
  
  /**
   * The address of the instruction. Treated as a hex value if prefixed with '0x', or as a decimal value otherwise.
   */
  public void setAddress(@NonNull final String address) {
    this.address = Preconditions.checkNotNull(address, "address");
  }
  
  /**
   * Optional raw bytes representing the instruction and its operands, in an implementation-defined format.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getInstructionBytes() {
    return this.instructionBytes;
  }
  
  /**
   * Optional raw bytes representing the instruction and its operands, in an implementation-defined format.
   * <p>
   * This is an optional property.
   */
  public void setInstructionBytes(final String instructionBytes) {
    this.instructionBytes = instructionBytes;
  }
  
  /**
   * Text representing the instruction and its operands, in an implementation-defined format.
   */
  @Pure
  @NonNull
  public String getInstruction() {
    return this.instruction;
  }
  
  /**
   * Text representing the instruction and its operands, in an implementation-defined format.
   */
  public void setInstruction(@NonNull final String instruction) {
    this.instruction = Preconditions.checkNotNull(instruction, "instruction");
  }
  
  /**
   * Name of the symbol that corresponds with the location of this instruction, if any.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getSymbol() {
    return this.symbol;
  }
  
  /**
   * Name of the symbol that corresponds with the location of this instruction, if any.
   * <p>
   * This is an optional property.
   */
  public void setSymbol(final String symbol) {
    this.symbol = symbol;
  }
  
  /**
   * Source location that corresponds to this instruction, if any.
   * <p>
   * Should always be set (if available) on the first instruction returned,
   * <p>
   * but can be omitted afterwards if this instruction maps to the same source file as the previous instruction.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Source getLocation() {
    return this.location;
  }
  
  /**
   * Source location that corresponds to this instruction, if any.
   * <p>
   * Should always be set (if available) on the first instruction returned,
   * <p>
   * but can be omitted afterwards if this instruction maps to the same source file as the previous instruction.
   * <p>
   * This is an optional property.
   */
  public void setLocation(final Source location) {
    this.location = location;
  }
  
  /**
   * The line within the source location that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getLine() {
    return this.line;
  }
  
  /**
   * The line within the source location that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  public void setLine(final Integer line) {
    this.line = line;
  }
  
  /**
   * The column within the line that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getColumn() {
    return this.column;
  }
  
  /**
   * The column within the line that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  public void setColumn(final Integer column) {
    this.column = column;
  }
  
  /**
   * The end line of the range that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndLine() {
    return this.endLine;
  }
  
  /**
   * The end line of the range that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  public void setEndLine(final Integer endLine) {
    this.endLine = endLine;
  }
  
  /**
   * The end column of the range that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndColumn() {
    return this.endColumn;
  }
  
  /**
   * The end column of the range that corresponds to this instruction, if any.
   * <p>
   * This is an optional property.
   */
  public void setEndColumn(final Integer endColumn) {
    this.endColumn = endColumn;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("address", this.address);
    b.add("instructionBytes", this.instructionBytes);
    b.add("instruction", this.instruction);
    b.add("symbol", this.symbol);
    b.add("location", this.location);
    b.add("line", this.line);
    b.add("column", this.column);
    b.add("endLine", this.endLine);
    b.add("endColumn", this.endColumn);
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
    DisassembledInstruction other = (DisassembledInstruction) obj;
    if (this.address == null) {
      if (other.address != null)
        return false;
    } else if (!this.address.equals(other.address))
      return false;
    if (this.instructionBytes == null) {
      if (other.instructionBytes != null)
        return false;
    } else if (!this.instructionBytes.equals(other.instructionBytes))
      return false;
    if (this.instruction == null) {
      if (other.instruction != null)
        return false;
    } else if (!this.instruction.equals(other.instruction))
      return false;
    if (this.symbol == null) {
      if (other.symbol != null)
        return false;
    } else if (!this.symbol.equals(other.symbol))
      return false;
    if (this.location == null) {
      if (other.location != null)
        return false;
    } else if (!this.location.equals(other.location))
      return false;
    if (this.line == null) {
      if (other.line != null)
        return false;
    } else if (!this.line.equals(other.line))
      return false;
    if (this.column == null) {
      if (other.column != null)
        return false;
    } else if (!this.column.equals(other.column))
      return false;
    if (this.endLine == null) {
      if (other.endLine != null)
        return false;
    } else if (!this.endLine.equals(other.endLine))
      return false;
    if (this.endColumn == null) {
      if (other.endColumn != null)
        return false;
    } else if (!this.endColumn.equals(other.endColumn))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.address== null) ? 0 : this.address.hashCode());
    result = prime * result + ((this.instructionBytes== null) ? 0 : this.instructionBytes.hashCode());
    result = prime * result + ((this.instruction== null) ? 0 : this.instruction.hashCode());
    result = prime * result + ((this.symbol== null) ? 0 : this.symbol.hashCode());
    result = prime * result + ((this.location== null) ? 0 : this.location.hashCode());
    result = prime * result + ((this.line== null) ? 0 : this.line.hashCode());
    result = prime * result + ((this.column== null) ? 0 : this.column.hashCode());
    result = prime * result + ((this.endLine== null) ? 0 : this.endLine.hashCode());
    return prime * result + ((this.endColumn== null) ? 0 : this.endColumn.hashCode());
  }
}
