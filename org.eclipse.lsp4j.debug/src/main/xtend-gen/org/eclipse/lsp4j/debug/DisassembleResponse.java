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
import org.eclipse.lsp4j.debug.DisassembledInstruction;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'disassemble' request.
 */
@SuppressWarnings("all")
public class DisassembleResponse {
  /**
   * The list of disassembled instructions.
   */
  @NonNull
  private DisassembledInstruction[] instructions;
  
  /**
   * The list of disassembled instructions.
   */
  @Pure
  @NonNull
  public DisassembledInstruction[] getInstructions() {
    return this.instructions;
  }
  
  /**
   * The list of disassembled instructions.
   */
  public void setInstructions(@NonNull final DisassembledInstruction[] instructions) {
    this.instructions = Preconditions.checkNotNull(instructions, "instructions");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("instructions", this.instructions);
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
    DisassembleResponse other = (DisassembleResponse) obj;
    if (this.instructions == null) {
      if (other.instructions != null)
        return false;
    } else if (!Arrays.deepEquals(this.instructions, other.instructions))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.instructions== null) ? 0 : Arrays.deepHashCode(this.instructions));
  }
}
