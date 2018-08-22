/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.test;

import java.util.ArrayList;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextEdit;
import org.junit.Assert;
import org.junit.Test;

public class EqualityTest {

	@Test public void testEquals() {
		Assert.assertEquals(new TextDocumentIdentifier("foo"), new TextDocumentIdentifier("foo"));
		Assert.assertNotEquals(new TextDocumentIdentifier("foo"), new TextDocumentIdentifier("bar"));
		
		CompletionItem e1 = new CompletionItem();
		e1.setAdditionalTextEdits(new ArrayList<>());
		e1.getAdditionalTextEdits().add(new TextEdit(new Range(new Position(1,1), new Position(1,1)), "foo"));
		
		CompletionItem e2 = new CompletionItem();
		e2.setAdditionalTextEdits(new ArrayList<>());
		e2.getAdditionalTextEdits().add(new TextEdit(new Range(new Position(1,1), new Position(1,1)), "foo"));
		
		CompletionItem e3 = new CompletionItem();
		e3.setAdditionalTextEdits(new ArrayList<>());
		e3.getAdditionalTextEdits().add(new TextEdit(new Range(new Position(1,1), new Position(1,2)), "foo"));
		
		Assert.assertEquals(e1, e2);
		Assert.assertNotEquals(e1, e3);
		
		Assert.assertEquals(e1.hashCode(), e2.hashCode());
		Assert.assertNotEquals(e1.hashCode(), e3.hashCode());
	}
	
}
