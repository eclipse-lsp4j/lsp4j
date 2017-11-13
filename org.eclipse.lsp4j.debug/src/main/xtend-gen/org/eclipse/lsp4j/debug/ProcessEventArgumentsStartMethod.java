/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
