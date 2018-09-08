/******************************************************************************
 * Copyright (c) 2018 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *****************************************************************************/
package org.eclipse.lsp4j.launch.websockets;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.JsonRpcException;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.MessageIssueException;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageConstants;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;

public class LSPWebSocketServer<T extends LanguageServer> {

        private static final Logger LOG = Logger.getLogger(LSPWebSocketServer.class.getName());

        private final Map<Session,Endpoint> endpoints = new LinkedHashMap<>();

	private final MessageJsonHandler jsonHandler;
	private final Supplier<T> newServer;
	
	public LSPWebSocketServer(Supplier<T> newServer, Class<T> serverClass) {
		this.newServer = newServer;
		
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.putAll(ServiceEndpoints.getSupportedMethods(LanguageClient.class));
		supportedMethods.putAll(ServiceEndpoints.getSupportedMethods(serverClass));
		
		this.jsonHandler = new MessageJsonHandler(supportedMethods);
	}
	
	@OnOpen
    public void onOpen(Session session) throws IOException{
        System.out.println("Open Connection " + session + " ...");

        LanguageServer server = newServer.get();
		Endpoint local = new GenericEndpoint(Collections.singleton(server));
		endpoints.put(session, local);
		
		if (server instanceof LanguageClientAware) {
			MessageConsumer out = new MessageConsumer() {
				@Override
				public void consume(Message arg0) throws MessageIssueException, JsonRpcException {
				    LOG.log(Level.INFO, "sending message: " + arg0);
					try {
						session.getBasicRemote().sendText(arg0.toString());
					} catch (IOException e) {
						throw new Error(e);
					}
				}
			};

			RemoteEndpoint remote = new RemoteEndpoint(out, local);
			((LanguageClientAware)server).connect(ServiceEndpoints.toServiceObject(remote, LanguageClient.class));
		}
    }

    @OnClose
    public void onClose(Session session) throws IOException{
        System.out.println("Close Connection " + session + " ...");
        endpoints.remove(session);
        session.close();
    }

    @OnMessage
    public String onMessage(String message, Session session) throws InterruptedException, ExecutionException{
        Message m = jsonHandler.parseMessage(message);
        Endpoint localEndpoint = endpoints.get(session);
        
        if (m instanceof RequestMessage) {
            LOG.log(Level.INFO, "Request from the client: " + m);
        	RequestMessage requestMessage = (RequestMessage)m;
        	CompletableFuture<?> result = localEndpoint.request(requestMessage.getMethod(), requestMessage.getParams());
        	Object r = result.join();
        	ResponseMessage responseMessage = new ResponseMessage();
        	responseMessage.setRawId(requestMessage.getRawId());
        	responseMessage.setJsonrpc(MessageConstants.JSONRPC_VERSION);
        	responseMessage.setResult(r);
        	LOG.log(Level.INFO, "reply to client " + responseMessage);
        	return responseMessage.toString();
        
        } else if (m instanceof NotificationMessage) {
            LOG.log(Level.INFO, "Notification from the client: " + m);
        	NotificationMessage notifyMessage = (NotificationMessage)m;
        	localEndpoint.notify(notifyMessage.getMethod(), notifyMessage.getParams());
        	return null;
     
        } else {
        	assert false : "message " + m + " not understood";
        	return null;
        }
    }

    @OnError
    public void onError(Throwable e, Session session){
        LOG.log(Level.SEVERE, e.getMessage());
    }

}
