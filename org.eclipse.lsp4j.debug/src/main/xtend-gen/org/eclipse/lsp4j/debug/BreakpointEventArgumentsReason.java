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
 * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
 */
@SuppressWarnings("all")
public interface BreakpointEventArgumentsReason {
  public final static String CHANGED = "changed";
  
  public final static String NEW = "new";
  
  public final static String REMOVED = "removed";
}
