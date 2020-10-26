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
 * Some predefined types for the CompletionItem. Please note that not all clients have specific icons for all of
 * them.
 */
@SuppressWarnings("all")
public enum CompletionItemType {
  METHOD,
  
  FUNCTION,
  
  CONSTRUCTOR,
  
  FIELD,
  
  VARIABLE,
  
  CLASS,
  
  INTERFACE,
  
  MODULE,
  
  PROPERTY,
  
  UNIT,
  
  VALUE,
  
  ENUM,
  
  KEYWORD,
  
  SNIPPET,
  
  TEXT,
  
  COLOR,
  
  FILE,
  
  REFERENCE,
  
  CUSTOMCOLOR;
}
