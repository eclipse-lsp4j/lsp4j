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

import java.io.IOException;

import org.eclipse.lsp4j.CreateFile;
import org.eclipse.lsp4j.DeleteFile;
import org.eclipse.lsp4j.RenameFile;
import org.eclipse.lsp4j.ResourceOperation;
import org.eclipse.lsp4j.ResourceOperationKind;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ResourceOperationTypeAdapter implements TypeAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

		if (!ResourceOperation.class.isAssignableFrom(type.getRawType())) {
			return null;
		}

		return (TypeAdapter<T>) new InnerResourceOperationTypeAdapter(this, gson).nullSafe();
	}

	private static class InnerResourceOperationTypeAdapter extends TypeAdapter<ResourceOperation> {

		TypeAdapter<CreateFile> createFileAdapter;
		TypeAdapter<DeleteFile> deleteFileAdapter;
		TypeAdapter<RenameFile> renameFileAdapter;

		InnerResourceOperationTypeAdapter(TypeAdapterFactory factory, Gson gson) {
			createFileAdapter = gson.getDelegateAdapter(factory, TypeToken.get(CreateFile.class));
			deleteFileAdapter = gson.getDelegateAdapter(factory, TypeToken.get(DeleteFile.class));
			renameFileAdapter = gson.getDelegateAdapter(factory, TypeToken.get(RenameFile.class));
		}

		@Override
		public void write(JsonWriter out, ResourceOperation value) throws IOException {
			if (value.getClass().isAssignableFrom(CreateFile.class)) {
				createFileAdapter.write(out, (CreateFile) value);
			} else if (value.getClass().isAssignableFrom(DeleteFile.class)) {
				deleteFileAdapter.write(out, (DeleteFile) value);
			} else if (value.getClass().isAssignableFrom(RenameFile.class)) {
				renameFileAdapter.write(out, (RenameFile) value);
			}
		}

		@Override
		public ResourceOperation read(JsonReader in) throws IOException {
			JsonObject objectJson = JsonParser.parseReader(in).getAsJsonObject();
			JsonElement value = objectJson.get("kind");
			if (value != null && value.isJsonPrimitive()) {
				String kindValue = value.getAsString();

				if (ResourceOperationKind.Create.equals(kindValue)) {
					return createFileAdapter.fromJsonTree(objectJson);
				} else if (ResourceOperationKind.Delete.equals(kindValue)) {
					return deleteFileAdapter.fromJsonTree(objectJson);
				} else if (ResourceOperationKind.Rename.equals(kindValue)) {
					return renameFileAdapter.fromJsonTree(objectJson);
				}
			}

			throw new JsonParseException(
					"The ResourceOperation object either has null \"kind\" value or the \"kind\" value is not valid.");
		}
	}
}
