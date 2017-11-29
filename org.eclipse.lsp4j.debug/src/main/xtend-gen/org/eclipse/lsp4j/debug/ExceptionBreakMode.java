/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * This enumeration defines all possible conditions when a thrown exception should result in a break.
 * <p>
 * never: never breaks,
 * <p>
 * always: always breaks,
 * <p>
 * unhandled: breaks when excpetion unhandled,
 * <p>
 * userUnhandled: breaks if the exception is not handled by user code.
 */
@SuppressWarnings("all")
public enum ExceptionBreakMode {
  NEVER,
  
  ALWAYS,
  
  UNHANDLED,
  
  USER_UNHANDLED;
}
