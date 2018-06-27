/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
  public final static int Identifier = 1;
  
  public final static int Entity = 2;
  
  public final static int Constructor = 3;
  
  public final static int Operators = 4;
  
  public final static int Tag = 5;
  
  public final static int Namespace = 6;
  
  public final static int Keyword = 7;
  
  public final static int Info_token = 8;
  
  public final static int Type = 9;
  
  public final static int String = 10;
  
  public final static int Warn_token = 11;
  
  public final static int Predefined = 12;
  
  public final static int String_escape = 13;
  
  public final static int Error_token = 14;
  
  public final static int Invalid = 15;
  
  public final static int Comment = 16;
  
  public final static int Debug_token = 17;
  
  public final static int Comment_doc = 18;
  
  public final static int Regexp = 19;
  
  public final static int Constant = 20;
  
  public final static int Attribute = 21;
  
  public final static int Modifier_public = 22;
  
  public final static int Modifier_private = 23;
  
  public final static int Modifier_protected = 24;
  
  public final static int Modifier_static = 25;
  
  public final static int Modifier_final = 26;
}
