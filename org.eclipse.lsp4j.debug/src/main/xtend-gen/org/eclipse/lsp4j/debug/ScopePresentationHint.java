/**
 * Copyright (c) 2017, 2018 Kichwa Coders Ltd. and others.
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
 * An optional hint for how to present this scope in the UI. If this attribute is missing, the scope is shown with a
 * generic UI.
 */
@SuppressWarnings("all")
public enum ScopePresentationHint {
  /**
   * Scope contains method arguments.
   */
  ARGUMENTS,
  
  /**
   * Scope contains local variables.
   */
  LOCALS,
  
  /**
   * Scope contains registers. Only a single 'registers' scope should be returned from a 'scopes' request.
   */
  REGISTERS;
}
