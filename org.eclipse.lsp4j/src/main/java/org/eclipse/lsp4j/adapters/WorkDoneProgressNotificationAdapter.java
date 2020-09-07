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
package org.eclipse.lsp4j.adapters;

import java.io.EOFException;
import java.io.IOException;

import org.eclipse.lsp4j.WorkDoneProgressBegin;
import org.eclipse.lsp4j.WorkDoneProgressEnd;
import org.eclipse.lsp4j.WorkDoneProgressKind;
import org.eclipse.lsp4j.WorkDoneProgressNotification;
import org.eclipse.lsp4j.WorkDoneProgressReport;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

/**
 * A type adapter for class which implements WorkDoneProgressNotification interface.
 */
public class WorkDoneProgressNotificationAdapter extends TypeAdapter<WorkDoneProgressNotification> {

	public static class Factory implements TypeAdapterFactory {

		@SuppressWarnings("unchecked")
		@Override
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			Class<?> rawType = typeToken.getRawType();
			if (!WorkDoneProgressNotification.class.isAssignableFrom(rawType)) {
				return null;
			}
			return (TypeAdapter<T>) new WorkDoneProgressNotificationAdapter();
		}

	}

	public WorkDoneProgressNotificationAdapter() {
	}

	@Override
	public WorkDoneProgressNotification read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		
		in.beginObject();
		String kind = null, message = null, title = null;
		Boolean cancellable = null;
		Integer percentage = null;
		try {
			
			while (in.hasNext()) {
				String name = in.nextName();
				switch (name) {
				case "kind": {
					kind = in.nextString();
					break;
				}
				case "title": {
					title = in.nextString();
					break;
				}
				case "message": {
					message = in.nextString();
					break;
				}
				case "cancellable": {
					cancellable = in.nextBoolean();
					break;
				}
				case "percentage": {
					percentage = in.nextInt();
					break;
				}
				default:
					in.skipValue();
				}
			}
			in.endObject();
			return createNotification(kind, message, title, cancellable, percentage);
			
		} catch (JsonSyntaxException | MalformedJsonException | EOFException exception) {
			if (kind != null) {
				throw new JsonParseException(exception);
			} else {
				throw exception;
			}
		}
	}

	private WorkDoneProgressNotification createNotification(String kind, String message, String title,
			Boolean cancellable, Integer percentage) throws MalformedJsonException {
		if(kind == null) {
			throw new MalformedJsonException("Kind of progress notification is empty");
		}
		switch (kind) {
		case "begin":
			WorkDoneProgressBegin begin = new WorkDoneProgressBegin();
			begin.setMessage(message);
			begin.setCancellable(cancellable);
			begin.setPercentage(percentage);
			begin.setTitle(title);
			return begin;
		case "report":
			WorkDoneProgressReport report = new WorkDoneProgressReport();
			report.setMessage(message);
			report.setCancellable(cancellable);
			report.setPercentage(percentage);
			return report;
		case "end":
			WorkDoneProgressEnd end = new WorkDoneProgressEnd();
			end.setMessage(message);
			return end;
		default:
			throw new MalformedJsonException("Kind of progress notification is unknown: "+kind);
		}
	}

	@Override
	public void write(JsonWriter out, WorkDoneProgressNotification notification) throws IOException {
		out.beginObject();
		out.name("kind");
		WorkDoneProgressKind kind = notification.getKind();
		out.value(kind.toString());
		
		switch (kind) {
		case begin:
			WorkDoneProgressBegin begin = (WorkDoneProgressBegin) notification;
			out.name("title");
			out.value(begin.getTitle());
			out.name("cancellable");
			out.value(begin.getCancellable());
			out.name("message");
			out.value(begin.getMessage());
			out.name("percentage");
			out.value(begin.getPercentage());
			break;
		case report:
			WorkDoneProgressReport report = (WorkDoneProgressReport) notification;
			out.name("cancellable");
			out.value(report.getCancellable());
			out.name("message");
			out.value(report.getMessage());
			out.name("percentage");
			out.value(report.getPercentage());
			break;
		case end:
			WorkDoneProgressEnd end = (WorkDoneProgressEnd) notification;
			out.name("message");
			out.value(end.getMessage());
			break;

		default:
			throw new MalformedJsonException("Kind of progress notification is unknown: "+kind);
		}
		out.endObject();
	}

}
