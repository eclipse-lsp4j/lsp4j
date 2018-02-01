/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.json.MessageConstants;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.MethodProvider;
import org.eclipse.lsp4j.jsonrpc.messages.CancelParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;

/**
 * An {@link Endpoint} that can be connected to a {@link MessageConsumer} and {@link MessageProducer}.
 * It handles the translation from {@link Message} objects to calls on {@link Endpoint}.
 */
public class RemoteEndpoint implements Endpoint, MessageConsumer, MethodProvider {

	private static final Logger LOG = Logger.getLogger(RemoteEndpoint.class.getName());
	
	public static final Function<Throwable, ResponseError> DEFAULT_EXCEPTION_HANDLER = (throwable) -> {
		if (throwable instanceof ResponseErrorException) {
			return ((ResponseErrorException) throwable).getResponseError();
		} else if ((throwable instanceof CompletionException || throwable instanceof InvocationTargetException)
				&& throwable.getCause() instanceof ResponseErrorException) {
			return ((ResponseErrorException) throwable.getCause()).getResponseError();
		} else {
			return fallbackResponseError("Internal error", throwable);
		}
	};

	private static ResponseError fallbackResponseError(String header, Throwable throwable) {
		LOG.log(Level.SEVERE, header + ": " + throwable.getMessage(), throwable);
		ResponseError error = new ResponseError();
		error.setMessage(header + ".");
		error.setCode(ResponseErrorCode.InternalError);
		ByteArrayOutputStream stackTrace = new ByteArrayOutputStream();
		PrintWriter stackTraceWriter = new PrintWriter(stackTrace);
		throwable.printStackTrace(stackTraceWriter);
		stackTraceWriter.flush();
		error.setData(stackTrace.toString());
		return error;
	}

	private final MessageConsumer out;
	private final Endpoint localEndpoint;
	private final Function<Throwable, ResponseError> exceptionHandler;
	
	private final AtomicInteger nextRequestId = new AtomicInteger();
	private final Map<String, PendingRequestInfo> sentRequestMap = new LinkedHashMap<>();
	private final Map<String, CompletableFuture<?>> receivedRequestMap = new LinkedHashMap<>();
	
	private static class PendingRequestInfo {
		PendingRequestInfo(RequestMessage requestMessage2, Consumer<ResponseMessage> responseHandler2) {
			this.requestMessage = requestMessage2;
			this.responseHandler = responseHandler2;
		}
		RequestMessage requestMessage;
		Consumer<ResponseMessage> responseHandler;
	}

	/**
	 * @param exceptionHandler An exception handler that should never return null.
	 */
	public RemoteEndpoint(MessageConsumer out, Endpoint localEndpoint, Function<Throwable, ResponseError> exceptionHandler) {
		if (out == null)
			throw new NullPointerException("out");
		if (localEndpoint == null)
			throw new NullPointerException("localEndpoint");
		if (exceptionHandler == null)
			throw new NullPointerException("exceptionHandler");
		this.out = out;
		this.localEndpoint = localEndpoint;
		this.exceptionHandler = exceptionHandler;
	}
	
	public RemoteEndpoint(MessageConsumer out, Endpoint localEndpoint) {
		this(out, localEndpoint, DEFAULT_EXCEPTION_HANDLER);
	}

	@Override
	public void notify(String method, Object parameter) {
		NotificationMessage notificationMessage = createNotificationMessage(method, parameter);
		out.consume(notificationMessage);
	}

	protected NotificationMessage createNotificationMessage(String method, Object parameter) {
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setJsonrpc(MessageConstants.JSONRPC_VERSION);
		notificationMessage.setMethod(method);
		notificationMessage.setParams(parameter);
		return notificationMessage;
	}

	@Override
	public CompletableFuture<Object> request(String method, Object parameter) {
		final RequestMessage requestMessage = createRequestMessage(method, parameter);
		final CompletableFuture<Object> result = new CompletableFuture<Object>() {
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				sendCancelNotification(requestMessage.getRawId());
				return super.cancel(mayInterruptIfRunning);
			}
		};
		Consumer<ResponseMessage> responseHandler = (responseMessage) -> {
			if (responseMessage.getError() != null) {
				result.completeExceptionally(new ResponseErrorException(responseMessage.getError()));
			} else {
				result.complete(responseMessage.getResult());
			}
		};
		synchronized(sentRequestMap) {
			sentRequestMap.put(requestMessage.getId(), new PendingRequestInfo(requestMessage, responseHandler));
		}
		out.consume(requestMessage);
		return result;
	}

	protected RequestMessage createRequestMessage(String method, Object parameter) {
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setId(String.valueOf(nextRequestId.incrementAndGet()));
		requestMessage.setMethod(method);
		requestMessage.setParams(parameter);
		return requestMessage;
	}

	protected void sendCancelNotification(Either<String, Integer> id) {
		CancelParams cancelParams = new CancelParams();
		cancelParams.setRawId(id);
		notify(MessageJsonHandler.CANCEL_METHOD.getMethodName(), cancelParams);
	}

	@Override
	public void consume(Message message) {
		if (message instanceof NotificationMessage) {
			NotificationMessage notificationMessage = (NotificationMessage) message;
			handleNotification(notificationMessage);
		} else if (message instanceof RequestMessage) {
			RequestMessage requestMessage = (RequestMessage) message;
			handleRequest(requestMessage);
		} else if (message instanceof ResponseMessage) {
			ResponseMessage responseMessage = (ResponseMessage) message;
			handleResponse(responseMessage);
		}
	}

	protected void handleResponse(ResponseMessage responseMessage) {
		PendingRequestInfo pendingRequestInfo;
		synchronized (sentRequestMap) {
			pendingRequestInfo = sentRequestMap.remove(responseMessage.getId());
		}
		if (pendingRequestInfo == null) {
			LOG.log(Level.WARNING, "Unmatched response message: " + responseMessage);
		} else {
			try {
				pendingRequestInfo.responseHandler.accept(responseMessage);
			} catch (RuntimeException e) {
				LOG.log(Level.WARNING, "Handling response threw an exception: " + responseMessage, e);
			}
		}
	}

	protected void handleNotification(NotificationMessage notificationMessage) {
		if (!handleCancellation(notificationMessage)) {
			try {
				localEndpoint.notify(notificationMessage.getMethod(), notificationMessage.getParams());
			} catch (RuntimeException e) {
				LOG.log(Level.WARNING, "Notification threw an exception: " + notificationMessage, e);
			}
		}
	}
	
	protected boolean handleCancellation(NotificationMessage notificationMessage) {
		if (MessageJsonHandler.CANCEL_METHOD.getMethodName().equals(notificationMessage.getMethod())) {
			Object cancelParams = notificationMessage.getParams();
			if (cancelParams != null) {
				if (cancelParams instanceof CancelParams) {
					synchronized (receivedRequestMap) {
						String id = ((CancelParams) cancelParams).getId();
						CompletableFuture<?> future = receivedRequestMap.get(id);
						if (future != null)
							future.cancel(true);
						else
							LOG.warning("Unmatched cancel notification for request id " + id);
					}
					return true;
				} else {
					LOG.warning("Cancellation support disabled, since the '" + MessageJsonHandler.CANCEL_METHOD.getMethodName() + "' method has been registered explicitly.");
					return false;
				}
			} else {
				LOG.warning("Missing 'params' attribute of cancel notification.");
			}
		}
		return false;
	}
	
	protected void handleRequest(RequestMessage requestMessage) {
		CompletableFuture<?> future;
		try {
			future = localEndpoint.request(requestMessage.getMethod(), requestMessage.getParams());
		} catch (Throwable e) {
			ResponseError errorObject = exceptionHandler.apply(e);
			if (errorObject == null) {
				errorObject = fallbackResponseError("Internal error. Exception handler provided no error object", e);
			}
			ResponseMessage responseMessage = createErrorResponseMessage(requestMessage, errorObject);
			out.consume(responseMessage);
			return;
		}
		final String messageId = requestMessage.getId();
		synchronized (receivedRequestMap) {
			receivedRequestMap.put(messageId, future);
		}
		future.thenAccept((result) -> {
			ResponseMessage responseMessage = createResultResponseMessage(requestMessage, result);
			out.consume(responseMessage);
		}).exceptionally((Throwable t) -> {
			ResponseMessage responseMessage;
			if (isCancellation(t)) {
				String message = "The request (id: " + messageId + ", method: '" + requestMessage.getMethod()  + "') has been cancelled";
				ResponseError errorObject = new ResponseError(ResponseErrorCode.RequestCancelled, message, null);
				responseMessage = createErrorResponseMessage(requestMessage, errorObject);
			} else {
				ResponseError errorObject = exceptionHandler.apply(t);
				if (errorObject == null) {
					errorObject = fallbackResponseError("Internal error. Exception handler provided no error object", t);
				}
				responseMessage = createErrorResponseMessage(requestMessage, errorObject);
			}
			out.consume(responseMessage);
			return null;
		}).thenApply((obj) -> {
			synchronized (receivedRequestMap) {
				receivedRequestMap.remove(messageId);
			}
			return null;
		});
	}

	protected ResponseMessage createResponseMessage(RequestMessage requestMessage) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setRawId(requestMessage.getRawId());
		responseMessage.setJsonrpc(MessageConstants.JSONRPC_VERSION);
		return responseMessage;
	}

	protected ResponseMessage createResultResponseMessage(RequestMessage requestMessage, Object result) {
		ResponseMessage responseMessage = createResponseMessage(requestMessage);
		responseMessage.setResult(result);
		return responseMessage;
	}

	protected ResponseMessage createErrorResponseMessage(RequestMessage requestMessage, ResponseError errorObject) {
		ResponseMessage responseMessage = createResponseMessage(requestMessage);
		responseMessage.setError(errorObject);
		return responseMessage;
	}

	protected boolean isCancellation(Throwable t) {
		if (t instanceof CompletionException) {
			return isCancellation(t.getCause());
		}
		return (t instanceof CancellationException);
	}

	@Override
	public String resolveMethod(String requestId) {
		synchronized (sentRequestMap) {
			PendingRequestInfo requestInfo = sentRequestMap.get(requestId);
			if (requestInfo != null) {
				return requestInfo.requestMessage.getMethod();
			}
		}
		return null;
	}
	
}
