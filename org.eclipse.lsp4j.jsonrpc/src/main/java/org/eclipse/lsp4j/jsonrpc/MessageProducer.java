package org.eclipse.lsp4j.jsonrpc;

public interface MessageProducer {

	public void listen(MessageConsumer messageConsumer);
	
}
