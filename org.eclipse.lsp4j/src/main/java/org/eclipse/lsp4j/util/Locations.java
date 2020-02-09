/******************************************************************************
 * Copyright (c) 2020 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.util;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;

/**
 * Utilities for the {@link Location}.
 */
public final class Locations {

	/**
	 * Convert the given {@code locationLink} to a {@link Location}.
	 * 
	 * @param locationLink the location link to convert.
	 * @return the given {@code locationLink} to a {@link Location}.
	 */
	public static Location toLocation(LocationLink locationLink) {
		return new Location(locationLink.getTargetUri(), locationLink.getTargetRange());
	}

	private Locations() {

	}
}
