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
 * An optional hint for how to present this frame in the UI.
 * <p>
 * A value of 'label' can be used to indicate that the frame is an artificial frame that is used as a visual label
 * or separator. A value of 'subtle' can be used to change the appearance of a frame in a 'subtle' way.
 */
@SuppressWarnings("all")
public enum StackFramePresentationHint {
  NORMAL,
  
  LABEL,
  
  SUBTLE;
}
