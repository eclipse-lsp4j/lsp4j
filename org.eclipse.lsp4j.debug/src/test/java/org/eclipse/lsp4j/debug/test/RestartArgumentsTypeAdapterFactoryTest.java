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
package org.eclipse.lsp4j.debug.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.lsp4j.debug.RestartArguments;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestartArgumentsTypeAdapterFactoryTest {

	@Test
	public void test() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		RestartArguments object = gson.fromJson("{\"arguments\": {\"noDebug\":true}}", RestartArguments.class);
		assertTrue(object.toString(), object.getArguments().isLeft());
		object = gson.fromJson("{\"arguments\": {\"noDebug\":true, \"__restart\":{}}}", RestartArguments.class);
		assertTrue(object.toString(), object.getArguments().isLeft());
		object = gson.fromJson("{\"arguments\": {}}", RestartArguments.class);
		assertTrue(object.toString(), object.getArguments().isRight());
		object = gson.fromJson("{\"arguments\": {\"__restart\":{\"dunno\": true}}}", RestartArguments.class);
		assertTrue(object.toString(), object.getArguments().isRight());
	}
	
}
