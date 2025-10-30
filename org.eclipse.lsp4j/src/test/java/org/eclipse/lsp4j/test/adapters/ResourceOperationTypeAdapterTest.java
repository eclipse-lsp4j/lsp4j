/******************************************************************************
 * Copyright (c) 2025 Vegard IT GmbH and others.
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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.eclipse.lsp4j.CreateFile;
import org.eclipse.lsp4j.ResourceOperation;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Failing tests that demonstrate issues in ResourceOperationTypeAdapter:
 * - reversed isAssignableFrom dispatch prevents handling subclasses
 * - missing guard for unknown subtypes silently produces invalid output
 */
public class ResourceOperationTypeAdapterTest {

	static class CustomCreateFile extends CreateFile {
		CustomCreateFile(String uri) {
			super(uri);
		}
	}

	/**
	 * Verify that a subclass of CreateFile serializes like CreateFile.
	 * Current buggy implementation fails to serialize any output for subclasses.
	 */
	@Test
	public void write_subclassOfCreateFile_serializes() {
		Gson gson = new GsonBuilder().create();
		ResourceOperation op = new CustomCreateFile("file:///tmp/x");
		String json = gson.toJson(op, ResourceOperation.class);
		// Expected: kind=create and uri present in JSON
		assertTrue("Expected kind=create in JSON but was: " + json, json != null && json.contains("\"kind\":\"create\""));
		assertTrue("Expected uri in JSON but was: " + json, json != null && json.contains("\"uri\":\"file:///tmp/x\""));
	}

	/**
	 * An unknown ResourceOperation subtype to demonstrate that the adapter
	 * should fail fast instead of producing empty/invalid output.
	 */
	static class UnknownOp extends ResourceOperation {
		UnknownOp() {
			super("unknown");
		}
	}

	/**
	 * Verify that an unknown subtype causes a IllegalArgumentException during writing.
	 * Current buggy implementation writes nothing and does not signal an error.
	 */
	@Test
	public void write_unknownSubtype_throws() {
		Gson gson = new GsonBuilder().create();
		ResourceOperation op = new UnknownOp();
		assertThrows(IllegalArgumentException.class, () -> gson.toJson(op, ResourceOperation.class));
	}
}
