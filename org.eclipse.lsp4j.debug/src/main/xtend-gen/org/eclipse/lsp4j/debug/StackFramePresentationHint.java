/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

/**
 * An optional hint for how to present this frame in the UI. A value of 'label' can be used to indicate that the
 * frame is an artificial frame that is used as a visual label or separator. A value of 'subtle' can be used to
 * change the appearance of a frame in a 'subtle' way.
 */
@SuppressWarnings("all")
public enum StackFramePresentationHint {
  NORMAL,
  
  LABEL,
  
  SUBTLE;
}
