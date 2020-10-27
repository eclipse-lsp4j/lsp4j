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
 * An optional hint for how to present this scope in the UI. If this attribute is missing, the scope is shown with
 * a generic UI.
 * <p>
 * Possible values include - but not limited to those defined in {@link ScopePresentationHint}
 */
@SuppressWarnings("all")
public interface ScopePresentationHint {
  /**
   * Scope contains method arguments.
   */
  public static final String ARGUMENTS = "arguments";
  
  /**
   * Scope contains local variables.
   */
  public static final String LOCALS = "locals";
  
  /**
   * Scope contains registers. Only a single 'registers' scope should be returned from a 'scopes' request.
   */
  public static final String REGISTERS = "registers";
}
