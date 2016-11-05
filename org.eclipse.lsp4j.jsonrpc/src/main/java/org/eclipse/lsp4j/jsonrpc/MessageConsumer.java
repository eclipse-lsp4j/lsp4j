package org.eclipse.lsp4j.jsonrpc;

import org.eclipse.lsp4j.jsonrpc.messages.Message;

public interface MessageConsumer {

	public void consume(Message message);
	
}
