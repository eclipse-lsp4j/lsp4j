/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.MessageIssue;

/**
 * An exception thrown to notify the caller of a {@link MessageConsumer} that one or more issues were
 * found while parsing or validating a message. This information can be passed to a {@link MessageIssueHandler}
 * in order to construct a proper response.
 */
public class MessageIssueException extends RuntimeException {
	
	private static final long serialVersionUID = 1118210473728652711L;
	
	private final Message rpcMessage;
	private final List<MessageIssue> issues;

	public MessageIssueException(Message rpcMessage, MessageIssue issue) {
		this.rpcMessage = rpcMessage;
		this.issues = Collections.singletonList(issue);
	}

	public MessageIssueException(Message rpcMessage, Collection<MessageIssue> issues) {
		this.rpcMessage = rpcMessage;
		this.issues = Collections.unmodifiableList(new ArrayList<>(issues));
	}
	
	public MessageIssueException(Message rpcMessage, Stream<MessageIssue> issueStream) {
		this.rpcMessage = rpcMessage;
		this.issues = Collections.unmodifiableList(issueStream.collect(Collectors.toList()));
	}

	@Override
	public String getMessage() {
		return issues.stream().map(issue -> issue.getMessage()).collect(Collectors.joining("\n"));
	}
	
	public Message getRpcMessage() {
		return rpcMessage;
	}
	
	public List<MessageIssue> getIssues() {
		return issues;
	}

}
