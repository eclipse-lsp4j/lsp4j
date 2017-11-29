/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * The output category. If not specified, 'console' is assumed.
 * <p>
 * Possible values include - but not limited to those defined in {@link OutputEventArgumentsCategory}
 */
@SuppressWarnings("all")
public interface OutputEventArgumentsCategory {
  public final static String CONSOLE = "console";
  
  public final static String STDOUT = "stdout";
  
  public final static String STDERR = "stderr";
  
  public final static String TELEMETRY = "telemetry";
}
