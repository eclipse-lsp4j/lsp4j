/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * The reason for the event.
 * <p>
 * For backward compatibility this string is shown in the UI if the 'description' attribute is missing (but it
 * must not be translated).
 * <p>
 * Possible values include - but not limited to those defined in {@link StoppedEventArgumentsReason}
 */
@SuppressWarnings("all")
public interface StoppedEventArgumentsReason {
  public final static String STEP = "step";
  
  public final static String BREAKPOINT = "breakpoint";
  
  public final static String EXCEPTION = "exception";
  
  public final static String PAUSE = "pause";
  
  public final static String ENTRY = "entry";
}
