/******************************************************************************
 * Copyright (c) 2022 itemis AG and others.
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

import java.util.function.Predicate;

import org.eclipse.lsp4j.InsertReplaceRange;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class CompletionItemDefaultsEditRangeTypeAdapter implements TypeAdapterFactory {
	private static final TypeToken<Either<Range, InsertReplaceRange>> ELEMENT_TYPE = new TypeToken<Either<Range, InsertReplaceRange>>() {
	};

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Predicate<JsonElement> leftChecker = new PropertyChecker("start");
		Predicate<JsonElement> rightChecker = new PropertyChecker("insert");
		return (TypeAdapter<T>) new EitherTypeAdapter<>(gson, ELEMENT_TYPE, leftChecker, rightChecker);
	}
}