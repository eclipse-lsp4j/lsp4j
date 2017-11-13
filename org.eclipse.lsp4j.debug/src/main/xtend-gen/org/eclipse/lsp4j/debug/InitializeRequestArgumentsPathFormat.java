/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * Determines in what format paths are specified. The default is 'path', which is the native format.
 * <p>
 * Possible values include - but not limited to those defined in {@link InitializeRequestArgumentsPathFormat}
 */
@SuppressWarnings("all")
public interface InitializeRequestArgumentsPathFormat {
  public final static String PATH = "path";
  
  public final static String URI = "uri";
}
