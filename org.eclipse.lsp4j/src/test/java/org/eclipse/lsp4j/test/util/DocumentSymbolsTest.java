/******************************************************************************
 * Copyright (c) 2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.test.util;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.util.DocumentSymbols;
import org.junit.Assert;
import org.junit.Test;

public class DocumentSymbolsTest {

	@Test(expected = NullPointerException.class)
	public void asIterator_null() {
		DocumentSymbols.asIterator(null);
	}

	@Test
	public void asIterator() {
		DocumentSymbol depth0 = new DocumentSymbol();
		depth0.setName("root");

		DocumentSymbol depth1_0 = new DocumentSymbol();
		depth1_0.setName("A1");
		DocumentSymbol depth1_1 = new DocumentSymbol();
		depth1_1.setName("B1");
		DocumentSymbol depth1_2 = new DocumentSymbol();
		depth1_2.setName("C1");
		depth0.setChildren(Arrays.asList(depth1_0, depth1_1, depth1_2));

		DocumentSymbol depth2_0_0 = new DocumentSymbol();
		depth2_0_0.setName("A11");
		DocumentSymbol depth2_0_1 = new DocumentSymbol();
		depth2_0_1.setName("A12");
		depth1_0.setChildren(Arrays.asList(depth2_0_0, depth2_0_1));

		DocumentSymbol depth2_1_0 = new DocumentSymbol();
		depth2_1_0.setName("B11");
		DocumentSymbol depth2_1_1 = new DocumentSymbol();
		depth2_1_1.setName("B12");
		depth1_1.setChildren(Arrays.asList(depth2_1_0, depth2_1_1));

		DocumentSymbol depth2_2_0 = new DocumentSymbol();
		depth2_2_0.setName("C11");
		DocumentSymbol depth2_2_1 = new DocumentSymbol();
		depth2_2_1.setName("C12");
		depth1_2.setChildren(Arrays.asList(depth2_2_0, depth2_2_1));

		Iterator<DocumentSymbol> iterator = DocumentSymbols.asIterator(depth0);
		Iterable<DocumentSymbol> iterable = () -> iterator;
		Stream<DocumentSymbol> stream = StreamSupport.stream(iterable.spliterator(), false);
		List<String> actual = stream.map(DocumentSymbol::getName).collect(toList());
		List<String> expected = Arrays.asList("root, A1", "B1", "C1", "A11", "A12", "B11", "B12", "C11", "C12");

		Assert.assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(actual.toArray()));
	}

}
