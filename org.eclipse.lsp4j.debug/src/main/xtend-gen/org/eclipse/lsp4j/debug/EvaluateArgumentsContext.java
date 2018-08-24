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
 * The context in which the evaluate request is run.
 * <p>
 * Possible values include - but not limited to those defined in {@link EvaluateArgumentsContext}
 */
@SuppressWarnings("all")
public interface EvaluateArgumentsContext {
  /**
   * evaluate is run in a watch.
   */
  public final static String WATCH = "watch";
  
  /**
   * evaluate is run from REPL console.
   */
  public final static String REPL = "repl";
  
  /**
   * evaluate is run from a data hover.
   */
  public final static String HOVER = "hover";
}
