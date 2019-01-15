/**
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import com.google.common.annotations.Beta;
import org.eclipse.lsp4j.services.LanguageClient;

/**
 * @deprecated Use {@link LanguageClient#semanticHighlighting} instead.
 */
@Beta
@Deprecated
@SuppressWarnings("all")
public class ColoringStyle {
  public static final int Identifier = 1;
  
  public static final int Entity = 2;
  
  public static final int Constructor = 3;
  
  public static final int Operators = 4;
  
  public static final int Tag = 5;
  
  public static final int Namespace = 6;
  
  public static final int Keyword = 7;
  
  public static final int Info_token = 8;
  
  public static final int Type = 9;
  
  public static final int String = 10;
  
  public static final int Warn_token = 11;
  
  public static final int Predefined = 12;
  
  public static final int String_escape = 13;
  
  public static final int Error_token = 14;
  
  public static final int Invalid = 15;
  
  public static final int Comment = 16;
  
  public static final int Debug_token = 17;
  
  public static final int Comment_doc = 18;
  
  public static final int Regexp = 19;
  
  public static final int Constant = 20;
  
  public static final int Attribute = 21;
  
  public static final int Modifier_public = 22;
  
  public static final int Modifier_private = 23;
  
  public static final int Modifier_protected = 24;
  
  public static final int Modifier_static = 25;
  
  public static final int Modifier_final = 26;
}
