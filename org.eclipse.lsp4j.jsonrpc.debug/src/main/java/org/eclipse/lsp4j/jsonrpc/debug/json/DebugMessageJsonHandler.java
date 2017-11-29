/*******************************************************************************
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug.json;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.lsp4j.jsonrpc.debug.adapters.DebugEnumTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.debug.adapters.DebugMessageTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;

import com.google.gson.GsonBuilder;

public class DebugMessageJsonHandler extends MessageJsonHandler {
	public DebugMessageJsonHandler(Map<String, JsonRpcMethod> supportedMethods) {
		super(supportedMethods);
	}

	public DebugMessageJsonHandler(Map<String, JsonRpcMethod> supportedMethods, Consumer<GsonBuilder> configureGson) {
		super(supportedMethods, configureGson);
	}

	public GsonBuilder getDefaultGsonBuilder() {
		return super.getDefaultGsonBuilder().registerTypeAdapterFactory(new DebugMessageTypeAdapter.Factory(this))
				.registerTypeAdapterFactory(new DebugEnumTypeAdapter.Factory());
	}

}
