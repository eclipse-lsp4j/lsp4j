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
package org.eclipse.lsp4j.test.adapters;

import static org.junit.Assert.assertTrue;

import org.eclipse.lsp4j.CompletionItemDefaults;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CompletionItemDefaultsEditRangeTypeAdapterTest {

	@Test
	public void test() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		CompletionItemDefaults object = gson.fromJson("{\"editRange\":{\"start\":{\"line\":0,\"character\":1},\"end\":{\"line\":0,\"character\":2}}}", CompletionItemDefaults.class);
		assertTrue(object.toString(), object.getEditRange().isLeft());
		object = gson.fromJson("{\"editRange\":{\"insert\":{\"start\":{\"line\":0,\"character\":1},\"end\":{\"line\":0,\"character\":2}},\"replace\":{\"start\":{\"line\":1,\"character\":1},\"end\":{\"line\":1,\"character\":2}}}}", CompletionItemDefaults.class);
		assertTrue(object.toString(), object.getEditRange().isRight());
	}
}
