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
 * Describes how the debug engine started debugging this process.
 */
@SuppressWarnings("all")
public enum ProcessEventArgumentsStartMethod {
  /**
   * Process was launched under the debugger.
   */
  LAUNCH,
  
  /**
   * Debugger attached to an existing process.
   */
  ATTACH,
  
  /**
   * A project launcher component has launched a new process in a suspended state and then asked the debugger to
   * attach.
   */
  ATTACH_FOR_SUSPENDED_LAUNCH;
}
