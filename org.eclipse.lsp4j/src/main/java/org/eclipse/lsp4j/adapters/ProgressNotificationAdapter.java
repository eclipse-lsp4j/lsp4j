/*******************************************************************************
 * Copyright (c) 2021 1C-Soft LLC and others.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 *******************************************************************************/
package org.eclipse.lsp4j.adapters;

import java.util.function.Predicate;

import org.eclipse.lsp4j.WorkDoneProgressNotification;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class ProgressNotificationAdapter implements TypeAdapterFactory {

	private static final TypeToken<Either<WorkDoneProgressNotification, Object>> ELEMENT_TYPE
			= new TypeToken<Either<WorkDoneProgressNotification, Object>>() {};

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Gson gson2 = gson.newBuilder().registerTypeHierarchyAdapter(WorkDoneProgressNotification.class,
				new WorkDoneProgressNotificationAdapter()).create();
		Predicate<JsonElement> leftChecker = new PropertyChecker("kind", "begin").or(
				new PropertyChecker("kind", "report")).or(new PropertyChecker("kind", "end"));
		Predicate<JsonElement> rightChecker = t -> true;
		return (TypeAdapter<T>) new EitherTypeAdapter<>(gson2, ELEMENT_TYPE, leftChecker, rightChecker);
	}
}
