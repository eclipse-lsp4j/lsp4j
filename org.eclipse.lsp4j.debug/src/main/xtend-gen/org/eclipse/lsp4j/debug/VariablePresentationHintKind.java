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
 * The kind of variable. Before introducing additional values, try to use the listed values.
 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintKind}
 */
@SuppressWarnings("all")
public interface VariablePresentationHintKind {
  /**
   * Indicates that the object is a property.
   */
  static final String PROPERTY = "property";
  
  /**
   * Indicates that the object is a method.
   */
  static final String METHOD = "method";
  
  /**
   * Indicates that the object is a class.
   */
  static final String CLASS = "class";
  
  /**
   * Indicates that the object is data.
   */
  static final String DATA = "data";
  
  /**
   * Indicates that the object is an event.
   */
  static final String EVENT = "event";
  
  /**
   * Indicates that the object is a base class.
   */
  static final String BASE_CLASS = "baseClass";
  
  /**
   * Indicates that the object is an inner class.
   */
  static final String INNER_CLASS = "innerClass";
  
  /**
   * Indicates that the object is an interface.
   */
  static final String INTERFACE = "interface";
  
  /**
   * Indicates that the object is the most derived class.
   */
  static final String MOST_DERIVED_CLASS = "mostDerivedClass";
  
  /**
   * Indicates that the object is virtual, that means it is a synthetic object introduced by the adapter for
   * rendering purposes, e.g. an index range for large arrays.
   */
  static final String VIRTUAL = "virtual";
  
  /**
   * Indicates that a data breakpoint is registered for the object.
   */
  static final String DATA_BREAKPOINT = "dataBreakpoint";
}
