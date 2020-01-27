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
 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintAttributes}
 */
@SuppressWarnings("all")
public interface VariablePresentationHintAttributes {
  /**
   * Indicates that the object is static.
   */
  static final String STATIC = "static";
  
  /**
   * Indicates that the object is a constant.
   */
  static final String CONSTANT = "constant";
  
  /**
   * Indicates that the object is read only.
   */
  static final String READ_ONLY = "readOnly";
  
  /**
   * Indicates that the object is a raw string.
   */
  static final String RAW_STRING = "rawString";
  
  /**
   * Indicates that the object can have an Object ID created for it.
   */
  static final String HAS_OBJECT_ID = "hasObjectId";
  
  /**
   * Indicates that the object has an Object ID associated with it.
   */
  static final String CAN_HAVE_OBJECT_ID = "canHaveObjectId";
  
  /**
   * Indicates that the evaluation had side effects.
   */
  static final String HAS_SIDE_EFFECTS = "hasSideEffects";
}
