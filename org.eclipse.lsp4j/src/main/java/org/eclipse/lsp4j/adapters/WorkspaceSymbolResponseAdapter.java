/******************************************************************************
 * Copyright (c) 2022 KamasamaK and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.adapters;

import java.util.List;

import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbol;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.ListChecker;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class WorkspaceSymbolResponseAdapter implements TypeAdapterFactory {

	private static final TypeToken<Either<List<? extends SymbolInformation>, List<? extends WorkspaceSymbol>>> EITHER_TYPE
			= new TypeToken<>() {};

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		final var leftChecker = new ListChecker(new PropertyChecker("deprecated"), false);
		final var rightChecker = new ListChecker(new PropertyChecker("name"), true);
		return (TypeAdapter<T>) new EitherTypeAdapter<>(gson, EITHER_TYPE, leftChecker, rightChecker);
	}

}
