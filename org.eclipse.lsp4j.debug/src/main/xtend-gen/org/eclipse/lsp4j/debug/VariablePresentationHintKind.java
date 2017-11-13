/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * The kind of variable. Before introducing additional values, try to use the listed values.
 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintKind}
 */
@SuppressWarnings("all")
public interface VariablePresentationHintKind {
  public final static String PROPERTY = "property";
  
  public final static String METHOD = "method";
  
  public final static String CLASS = "class";
  
  public final static String DATA = "data";
  
  public final static String EVENT = "event";
  
  public final static String BASE_CLASS = "baseClass";
  
  public final static String INNER_CLASS = "innerClass";
  
  public final static String INTERFACE = "interface";
  
  public final static String MOST_DERIVED_CLASS = "mostDerivedClass";
}
