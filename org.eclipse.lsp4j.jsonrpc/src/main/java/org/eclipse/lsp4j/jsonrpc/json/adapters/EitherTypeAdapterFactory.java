/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @deprecated Use {@link EitherTypeAdapter.Factory} instead.
 */
@Deprecated
public class EitherTypeAdapterFactory extends EitherTypeAdapter.Factory {
	
	/**
	 * @deprecated Use {@link EitherTypeAdapter} instead.
	 */
	@Deprecated
	protected static class Adapter<L, R> extends EitherTypeAdapter<L, R> {

		public Adapter(Gson gson, TypeToken<Either<L, R>> typeToken) {
			super(gson, typeToken);
		}
		
	}
	
}
