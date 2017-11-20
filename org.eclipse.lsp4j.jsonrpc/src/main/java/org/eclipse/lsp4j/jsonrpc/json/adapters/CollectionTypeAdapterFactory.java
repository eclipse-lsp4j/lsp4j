/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * @deprecated Use {@link CollectionTypeAdapter.Factory} instead.
 */
@Deprecated
public class CollectionTypeAdapterFactory extends CollectionTypeAdapter.Factory {
	
	/**
	 * @deprecated Use {@link CollectionTypeAdapter} instead.
	 */
	@Deprecated
	protected static class Adapter<E> extends CollectionTypeAdapter<E> {

		public Adapter(Gson gson, Type elementType, TypeAdapter<E> elementTypeAdapter,
				Supplier<Collection<E>> constructor) {
			super(gson, elementType, elementTypeAdapter, constructor);
		}
		
	}
	
}
