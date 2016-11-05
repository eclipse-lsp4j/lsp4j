package org.eclipse.lsp4j.services.test;

import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.json.InvalidMessageException;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.validation.ReflectiveMessageValidator;
import org.junit.Assert;
import org.junit.Test;

public class ValidationTest {
	
	ReflectiveMessageValidator validator = new ReflectiveMessageValidator((consume)->{});

	@Test
	public void testInvalidCompletion() {
		RequestMessage message = new RequestMessage();
		message.setJsonrpc("2.0");
		message.setId("1");
		message.setMethod(MessageMethods.DOC_COMPLETION);
		
		TextDocumentPositionParams params = new TextDocumentPositionParams();
		params.setTextDocument(new TextDocumentIdentifier("file:///tmp/foo"));
		message.setParams(params);
		
		assertIssues(message, "The property \'position\' must have a non-null value.");
	}
	
	@Test
	public void testInvalidCodeLens() {
		ResponseMessage message = new ResponseMessage();
		message.setId("1");
		CodeLens codeLens = new CodeLens(new Range(new Position(3, 32), new Position(3, 35)), null, null);
		// forbidden self reference!
		codeLens.setData(codeLens);
		message.setResult(codeLens);
		assertIssues(message, "An element of the message has a direct or indirect reference to itself.");
	}
	
	private void assertIssues(Message message, CharSequence expectedIssues) {
		try {
			validator.consume(message);
			Assert.fail("Expected InvalidMessageException: " + expectedIssues + ".");
		} catch (InvalidMessageException e) {
			String expected = expectedIssues.toString();
			String actual = LineEndings.toSystemLineEndings(e.getMessage());
			// The expectation may be a prefix of the actual exception message
			if (!actual.startsWith(expected))
				Assert.assertEquals(expected, actual);
		}
	}
}
