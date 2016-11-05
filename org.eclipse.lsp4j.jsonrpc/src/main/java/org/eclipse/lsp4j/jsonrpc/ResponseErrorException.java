package org.eclipse.lsp4j.jsonrpc;

import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;

public class ResponseErrorException extends RuntimeException {

	private static final long serialVersionUID = -5970739895395246885L;
	private ResponseError responseError;

	public ResponseErrorException(ResponseError responseError) {
		this.responseError = responseError;
	}

	@Override
	public String getMessage() {
		return responseError.getMessage();
	}
	
}
