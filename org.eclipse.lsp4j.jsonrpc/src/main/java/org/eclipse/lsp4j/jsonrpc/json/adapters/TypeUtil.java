/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.lang.reflect.Type;

import com.google.gson.internal.$Gson$Types;

/**
 * Wrapper around the internal Gson type utilities.
 */
public class TypeUtil {

	public static Type getCollectionElementType(Type context, Class<?> contextRawType) {
		return $Gson$Types.getCollectionElementType(context, contextRawType);
	}

}
