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
 * Logical areas that can be invalidated by the 'invalidated' event.
 * <p>
 * Possible values include - but not limited to those defined in {@link InvalidatedAreas}
 */
@SuppressWarnings("all")
public interface InvalidatedAreas {
  /**
   * All previously fetched data has become invalid and needs to be refetched.
   */
  public static final String ALL = "all";
  
  /**
   * Previously fetched stack related data has become invalid and needs to be refetched.
   */
  public static final String STACKS = "stacks";
  
  /**
   * Previously fetched thread related data has become invalid and needs to be refetched.
   */
  public static final String THREADS = "threads";
  
  /**
   * Previously fetched variable data has become invalid and needs to be refetched.
   */
  public static final String VARIABLES = "variables";
}
