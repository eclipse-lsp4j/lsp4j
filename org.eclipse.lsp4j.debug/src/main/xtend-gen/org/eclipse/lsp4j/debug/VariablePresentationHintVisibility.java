/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * Visibility of variable. Before introducing additional values, try to use the listed values.
 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintVisibility}
 */
@SuppressWarnings("all")
public interface VariablePresentationHintVisibility {
  public final static String PUBLIC = "public";
  
  public final static String PRIVATE = "private";
  
  public final static String PROTECTED = "protected";
  
  public final static String INTERNAL = "internal";
  
  public final static String FINAL = "final";
}
