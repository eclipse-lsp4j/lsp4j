/**
 * Copyright (c) 2017, 2019 Kichwa Coders Ltd. and others.
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
 * The reason for the event.
 * <p>
 * For backward compatibility this string is shown in the UI if the 'description' attribute is missing (but it
 * must not be translated).
 * <p>
 * Possible values include - but not limited to those defined in {@link StoppedEventArgumentsReason}
 */
@SuppressWarnings("all")
public interface StoppedEventArgumentsReason {
  static final String STEP = "step";
  
  static final String BREAKPOINT = "breakpoint";
  
  static final String EXCEPTION = "exception";
  
  static final String PAUSE = "pause";
  
  static final String ENTRY = "entry";
  
  static final String GOTO = "goto";
  
  static final String FUNCTION_BREAKPOINT = "function breakpoint";
  
  static final String DATA_BREAKPOINT = "data breakpoint";
}
