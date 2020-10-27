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
 * Support for keeping an output log organized by grouping related messages.
 */
@SuppressWarnings("all")
public enum OutputEventArgumentsGroup {
  /**
   * Start a new group in expanded mode. Subsequent output events are members of the group and should be shown
   * indented.
   * The 'output' attribute becomes the name of the group and is not indented.
   */
  START,
  
  /**
   * Start a new group in collapsed mode. Subsequent output events are members of the group and should be shown
   * indented (as soon as the group is expanded).
   * The 'output' attribute becomes the name of the group and is not
   * indented.
   */
  START_COLLAPSED,
  
  /**
   * End the current group and decreases the indentation of subsequent output events.
   * A non empty 'output' attribute
   * is shown as the unindented end of the group.
   */
  END;
}
