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

/**
 * The granularity of one 'step' in the stepping requests 'next', 'stepIn', 'stepOut', and 'stepBack'.
 */
@SuppressWarnings("all")
public enum SteppingGranularity {
  /**
   * The step should allow the program to run until the current statement has finished executing.
   * The meaning of a
   * statement is determined by the adapter and it may be considered equivalent to a line.
   * For example 'for(int i =
   * 0; i < 10; i++) could be considered to have 3 statements 'int i = 0', 'i < 10', and 'i++'.
   */
  STATEMENT,
  
  /**
   * The step should allow the program to run until the current source line has executed.
   */
  LINE,
  
  /**
   * The step should allow one instruction to execute (e.g. one x86 instruction).
   */
  INSTRUCTION;
}
