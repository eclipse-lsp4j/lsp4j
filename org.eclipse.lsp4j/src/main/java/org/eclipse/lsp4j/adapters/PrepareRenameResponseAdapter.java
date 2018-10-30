/******************************************************************************
 * Copyright (c) 2018 Microsoft Corporation and others.
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

import java.util.ArrayList;
import java.util.function.Predicate;

import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class PrepareRenameResponseAdapter implements TypeAdapterFactory {
	
	private static final TypeToken<Either<Range, PrepareRenameResult>> ELEMENT_TYPE
			= new TypeToken<Either<Range, PrepareRenameResult>>() {};

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Predicate<JsonElement> leftChecker = new PropertyChecker("start");
		Predicate<JsonElement> rightChecker = new PropertyChecker("range");
		TypeAdapter<Either<Range, PrepareRenameResult>> elementTypeAdapter = new EitherTypeAdapter<>(gson, ELEMENT_TYPE,
				leftChecker, rightChecker);
		return (TypeAdapter<T>) new CollectionTypeAdapter<>(gson, ELEMENT_TYPE.getType(), elementTypeAdapter,
				ArrayList::new);
	}
}
