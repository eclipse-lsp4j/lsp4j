/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
