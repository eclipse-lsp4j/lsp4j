/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
