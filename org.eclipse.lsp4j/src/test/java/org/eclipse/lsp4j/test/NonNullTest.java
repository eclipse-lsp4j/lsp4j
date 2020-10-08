package org.eclipse.lsp4j.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.lsp4j.CodeAction;
import org.junit.Test;

public class NonNullTest {
	
	@Test
	public void testCodeAction1() {
		try {
			new CodeAction(null);
			fail("Expected a NullPointerException");
		} catch (NullPointerException exc) {
			assertEquals("Property must not be null: title", exc.getMessage());
		}
	}
	
	@Test
	public void testCodeAction2() {
		try {
			CodeAction codeAction = new CodeAction();
			codeAction.setTitle(null);
			fail("Expected a NullPointerException");
		} catch (NullPointerException exc) {
			assertEquals("Property must not be null: title", exc.getMessage());
		}
	}

}
