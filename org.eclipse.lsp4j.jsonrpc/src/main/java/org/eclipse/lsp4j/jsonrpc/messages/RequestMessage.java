package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

public class RequestMessage extends Message {

	/**
	 * The request id.
	 */
	@NonNull private String id;

	/**
	 * The request id.
	 */
	@Pure
	@NonNull
	public String getId() {
		return this.id;
	}

	/**
	 * The request id.
	 */
	public void setId(@NonNull final String id) {
		this.id = id;
	}

	/**
	 * The method to be invoked.
	 */
	@NonNull
	private String method;

	/**
	 * The method to be invoked.
	 */
	@Pure
	@NonNull
	public String getMethod() {
		return this.method;
	}

	/**
	 * The method to be invoked.
	 */
	public void setMethod(@NonNull final String method) {
		this.method = method;
	}

	/**
	 * The method's params.
	 */
	private Object params;

	/**
	 * The method's params.
	 */
	@Pure
	public Object getParams() {
		return this.params;
	}

	/**
	 * The method's params.
	 */
	public void setParams(final Object params) {
		this.params = params;
	}
}
