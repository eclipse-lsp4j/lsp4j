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
 * The kind of variable. Before introducing additional values, try to use the listed values.
 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintKind}
 */
@SuppressWarnings("all")
public interface VariablePresentationHintKind {
  /**
   * Indicates that the object is a property.
   */
  public final static String PROPERTY = "property";
  
  /**
   * Indicates that the object is a method.
   */
  public final static String METHOD = "method";
  
  /**
   * Indicates that the object is a class.
   */
  public final static String CLASS = "class";
  
  /**
   * Indicates that the object is data.
   */
  public final static String DATA = "data";
  
  /**
   * Indicates that the object is an event.
   */
  public final static String EVENT = "event";
  
  /**
   * Indicates that the object is a base class.
   */
  public final static String BASE_CLASS = "baseClass";
  
  /**
   * Indicates that the object is an inner class.
   */
  public final static String INNER_CLASS = "innerClass";
  
  /**
   * Indicates that the object is an interface.
   */
  public final static String INTERFACE = "interface";
  
  /**
   * Indicates that the object is the most derived class.
   */
  public final static String MOST_DERIVED_CLASS = "mostDerivedClass";
  
  /**
   * Indicates that the object is virtual, that means it is a synthetic object introduced by the adapter for
   * rendering purposes, e.g. an index range for large arrays.
   */
  public final static String VIRTUAL = "virtual";
}
