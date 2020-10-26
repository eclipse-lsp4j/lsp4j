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

import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'disassemble' request.
 */
@SuppressWarnings("all")
public class DisassembleArguments {
  /**
   * Memory reference to the base location containing the instructions to disassemble.
   */
  @NonNull
  private String memoryReference;
  
  /**
   * Optional offset (in bytes) to be applied to the reference location before disassembling. Can be negative.
   * <p>
   * This is an optional property.
   */
  private Integer offset;
  
  /**
   * Optional offset (in instructions) to be applied after the byte offset (if any) before disassembling. Can be
   * negative.
   * <p>
   * This is an optional property.
   */
  private Integer instructionOffset;
  
  /**
   * Number of instructions to disassemble starting at the specified location and offset.
   * <p>
   * An adapter must return exactly this number of instructions - any unavailable instructions should be replaced
   * with an implementation-defined 'invalid instruction' value.
   */
  private int instructionCount;
  
  /**
   * If true, the adapter should attempt to resolve memory addresses and other values to symbolic names.
   * <p>
   * This is an optional property.
   */
  private Boolean resolveSymbols;
  
  /**
   * Memory reference to the base location containing the instructions to disassemble.
   */
  @Pure
  @NonNull
  public String getMemoryReference() {
    return this.memoryReference;
  }
  
  /**
   * Memory reference to the base location containing the instructions to disassemble.
   */
  public void setMemoryReference(@NonNull final String memoryReference) {
    this.memoryReference = Preconditions.checkNotNull(memoryReference, "memoryReference");
  }
  
  /**
   * Optional offset (in bytes) to be applied to the reference location before disassembling. Can be negative.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getOffset() {
    return this.offset;
  }
  
  /**
   * Optional offset (in bytes) to be applied to the reference location before disassembling. Can be negative.
   * <p>
   * This is an optional property.
   */
  public void setOffset(final Integer offset) {
    this.offset = offset;
  }
  
  /**
   * Optional offset (in instructions) to be applied after the byte offset (if any) before disassembling. Can be
   * negative.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getInstructionOffset() {
    return this.instructionOffset;
  }
  
  /**
   * Optional offset (in instructions) to be applied after the byte offset (if any) before disassembling. Can be
   * negative.
   * <p>
   * This is an optional property.
   */
  public void setInstructionOffset(final Integer instructionOffset) {
    this.instructionOffset = instructionOffset;
  }
  
  /**
   * Number of instructions to disassemble starting at the specified location and offset.
   * <p>
   * An adapter must return exactly this number of instructions - any unavailable instructions should be replaced
   * with an implementation-defined 'invalid instruction' value.
   */
  @Pure
  public int getInstructionCount() {
    return this.instructionCount;
  }
  
  /**
   * Number of instructions to disassemble starting at the specified location and offset.
   * <p>
   * An adapter must return exactly this number of instructions - any unavailable instructions should be replaced
   * with an implementation-defined 'invalid instruction' value.
   */
  public void setInstructionCount(final int instructionCount) {
    this.instructionCount = instructionCount;
  }
  
  /**
   * If true, the adapter should attempt to resolve memory addresses and other values to symbolic names.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getResolveSymbols() {
    return this.resolveSymbols;
  }
  
  /**
   * If true, the adapter should attempt to resolve memory addresses and other values to symbolic names.
   * <p>
   * This is an optional property.
   */
  public void setResolveSymbols(final Boolean resolveSymbols) {
    this.resolveSymbols = resolveSymbols;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("memoryReference", this.memoryReference);
    b.add("offset", this.offset);
    b.add("instructionOffset", this.instructionOffset);
    b.add("instructionCount", this.instructionCount);
    b.add("resolveSymbols", this.resolveSymbols);
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
    DisassembleArguments other = (DisassembleArguments) obj;
    if (this.memoryReference == null) {
      if (other.memoryReference != null)
        return false;
    } else if (!this.memoryReference.equals(other.memoryReference))
      return false;
    if (this.offset == null) {
      if (other.offset != null)
        return false;
    } else if (!this.offset.equals(other.offset))
      return false;
    if (this.instructionOffset == null) {
      if (other.instructionOffset != null)
        return false;
    } else if (!this.instructionOffset.equals(other.instructionOffset))
      return false;
    if (other.instructionCount != this.instructionCount)
      return false;
    if (this.resolveSymbols == null) {
      if (other.resolveSymbols != null)
        return false;
    } else if (!this.resolveSymbols.equals(other.resolveSymbols))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.memoryReference== null) ? 0 : this.memoryReference.hashCode());
    result = prime * result + ((this.offset== null) ? 0 : this.offset.hashCode());
    result = prime * result + ((this.instructionOffset== null) ? 0 : this.instructionOffset.hashCode());
    result = prime * result + this.instructionCount;
    return prime * result + ((this.resolveSymbols== null) ? 0 : this.resolveSymbols.hashCode());
  }
}
