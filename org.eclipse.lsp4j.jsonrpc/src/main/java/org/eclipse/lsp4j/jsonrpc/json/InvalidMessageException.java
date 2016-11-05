package org.eclipse.lsp4j.jsonrpc.json;

public class InvalidMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Object messageObject;
	private String requestId;

	public InvalidMessageException(String message, Object messageObject, String requestId) {
		this(message, messageObject, requestId, null);
	}
	
	public InvalidMessageException(String message, Object messageObject, String requestId, Throwable cause) {
		super(message, cause);
		this.messageObject = messageObject;
		this.requestId = requestId;
	}
	
	public Object getMessageObject() {
		return messageObject;
	}
	
	public String getRequestId() {
		return requestId;
	}
}
