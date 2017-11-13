/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * An optional hint for how to present the source in the UI. A value of 'deemphasize' can be used to indicate that
 * the source is not available or that it is skipped on stepping.
 */
@SuppressWarnings("all")
public enum SourcePresentationHint {
  NORMAL,
  
  EMPHASIZE,
  
  DEEMPHASIZE;
}
