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
 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintAttributes}
 */
@SuppressWarnings("all")
public interface VariablePresentationHintAttributes {
  /**
   * Indicates that the object is static.
   */
  public final static String STATIC = "static";
  
  /**
   * Indicates that the object is a constant.
   */
  public final static String CONSTANT = "constant";
  
  /**
   * Indicates that the object is read only.
   */
  public final static String READ_ONLY = "readOnly";
  
  /**
   * Indicates that the object is a raw string.
   */
  public final static String RAW_STRING = "rawString";
  
  /**
   * Indicates that the object can have an Object ID created for it.
   */
  public final static String HAS_OBJECT_ID = "hasObjectId";
  
  /**
   * Indicates that the object has an Object ID associated with it.
   */
  public final static String CAN_HAVE_OBJECT_ID = "canHaveObjectId";
  
  /**
   * Indicates that the evaluation had side effects.
   */
  public final static String HAS_SIDE_EFFECTS = "hasSideEffects";
}
