/**
 * Copyright (c) 2020 TypeFox and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

package org.eclipse.lsp4j;

/**
 * A TraceValue represents the level of verbosity with which the server systematically reports its execution 
 * trace using {@code $/logTrace} notifications. The initial trace value is set by the client at initialization and 
 * can be modified later using the {@code $/setTrace} notification.
 */
public class TraceValue {
	private TraceValue() {
	}

	public static final String Off = "off";

	public static final String Message = "message";

	public static final String Verbose = "verbose";
}
