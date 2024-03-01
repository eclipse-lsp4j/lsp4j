/******************************************************************************
 * Copyright (c) 2023 1C-Soft LLC and others.
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

import org.eclipse.lsp4j.InlineValueEvaluatableExpression;
import org.eclipse.lsp4j.InlineValueText;
import org.eclipse.lsp4j.InlineValueVariableLookup;
import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class InlineValueResponseAdapter implements TypeAdapterFactory {

	private static final TypeToken<Either3<InlineValueText, InlineValueVariableLookup, InlineValueEvaluatableExpression>> ELEMENT_TYPE
		= new TypeToken<>() {};

	private static final TypeToken<Either<InlineValueVariableLookup, InlineValueEvaluatableExpression>> R_ELEMENT_TYPE
		= new TypeToken<>() {};

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		final var firstChecker = new PropertyChecker("text");
		final var secondChecker = new PropertyChecker("caseSensitiveLookup");
		final var thirdChecker = new PropertyChecker("range");
		final var elementTypeAdapter = new EitherTypeAdapter<>(gson, ELEMENT_TYPE, firstChecker, secondChecker.or(thirdChecker),
						null, new EitherTypeAdapter<>(gson, R_ELEMENT_TYPE, secondChecker, thirdChecker));
		return (TypeAdapter<T>) new CollectionTypeAdapter<>(gson, ELEMENT_TYPE.getType(), elementTypeAdapter, ArrayList::new);
	}
}
