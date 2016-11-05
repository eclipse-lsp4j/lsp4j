/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json

import com.google.common.collect.Lists
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.Reader
import java.io.StringReader
import java.io.StringWriter
import java.io.Writer
import java.util.Map
import org.eclipse.lsp4j.jsonrpc.RpcMethod
import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapterFactory
import org.eclipse.lsp4j.jsonrpc.json.adapters.EnumTypeAdapterFactory
import org.eclipse.lsp4j.jsonrpc.messages.Message
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage
import org.eclipse.xtend.lib.annotations.Accessors

class MessageJsonHandler {
	
	val jsonParser = new JsonParser
	val Gson gson
	
	@Accessors(PUBLIC_SETTER)
    MethodProvider methodProvider
	
	Map<String, RpcMethod> supportedMethods
	
	new(Map<String, RpcMethod> supportedMethods) {
		this(defaultGsonBuilder.create, supportedMethods)
	}
	
	new(Gson gson, Map<String, RpcMethod> supportedMethods) {
		this.gson = gson
		this.supportedMethods = supportedMethods
	}
    
	def static GsonBuilder getDefaultGsonBuilder() {
	    new GsonBuilder()
	    	.registerTypeAdapterFactory(new CollectionTypeAdapterFactory)
            .registerTypeAdapterFactory(new EnumTypeAdapterFactory)
	}
	
	def Message parseMessage(CharSequence input) {
		parseMessage(new StringReader(input.toString))
	}
	
	def Message parseMessage(Reader input) {
		val json = jsonParser.parse(input).asJsonObject
		val idElement = json.get('id')
		val methodElement = json.get('method')
		var Message result
		if (idElement !== null && methodElement !== null)
			result = parseRequest(json, idElement.asString, methodElement.asString)
		else if (idElement !== null && (json.get('result') !== null || json.get('error') !== null))
			result = parseResponse(json, idElement.asString)
		else if (methodElement !== null)
			result = parseNotification(json, methodElement.asString)
		else
			throw new IllegalStateException("Unknown message '"+json)
		result.jsonrpc = json.get('jsonrpc')?.asString
		return result
	}
	
	protected def RequestMessage parseRequest(JsonObject json, String requestId, String method) {
		try {
			val result = new RequestMessage
			result.id = requestId
			result.method = method
			val params = json.get('params')?.asJsonObject
			if (params !== null) {
				val paramType = supportedMethods.get(method)?.parameterType
				result.params = gson.fromJson(params, paramType ?: Object)
			}
			return result
		} catch (Exception e) {
			throw new InvalidMessageException("Could not parse request: " + e.message, json, requestId, e)
		}
	}
	
	protected def ResponseMessage parseResponse(JsonObject json, String responseId) {
		if (methodProvider === null)
			throw new IllegalStateException("Response methods are not accepted.")
		try {
			val result = new ResponseMessage
			result.id = responseId
			val resultElem = json.get('result')
			if (resultElem !== null) {
				val method = methodProvider.resolveMethod(responseId)
				if (method !== null) {
					val resultType = supportedMethods.get(method)?.returnType
					if (resultType !== null) {
						if (resultElem.isJsonArray) {
							val arrayElem = resultElem.asJsonArray
							val list = Lists.newArrayListWithExpectedSize(arrayElem.size)
							for (e : arrayElem) {
								list += gson.fromJson(e, resultType)
							}
							result.result = list
						} else {
							result.result = gson.fromJson(resultElem, resultType)
						}
					} else {
						result.result = gson.fromJson(resultElem, Object)
					}
				}
			} else {
				val error = json.get('error')?.asJsonObject
				if (error !== null)
					result.error = gson.fromJson(error, ResponseError)
			}
			return result
		} catch (Exception e) {
			throw new InvalidMessageException("Could not parse response: " + e.message, json, responseId, e)
		}
	}
	
	protected def NotificationMessage parseNotification(JsonObject json, String method) {
		try {
			val result = new NotificationMessage
			result.method = method
			val params = json.get('params')?.asJsonObject
			if (params !== null) {
				val paramType = supportedMethods.get(method)?.parameterType
				result.params = gson.fromJson(params, paramType ?: Object)
			}
			return result
		} catch (Exception e) {
			throw new InvalidMessageException("Could not parse notification: " + e.message, json, null, e)
		}
	}
	
	def String serialize(Message message) {
		val writer = new StringWriter
		serialize(message, writer)
		return writer.toString
	}
	
	def void serialize(Message message, Writer output) {
		gson.toJson(message, output)
	}
	
}
