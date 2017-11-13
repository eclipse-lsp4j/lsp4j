/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * Optional filter to limit the child variables to either named or indexed. If ommited, both types are fetched.
 */
@SuppressWarnings("all")
public enum VariablesArgumentsFilter {
  INDEXED,
  
  NAMED;
}
