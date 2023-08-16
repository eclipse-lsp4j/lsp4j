/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.json;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class MessageJsonHandlerTest {

	public static class Entry {
		public String name;
		public int kind;
		public Location location;
	}

	public static class Location {
		public String uri;
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testParseList_01() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<List<? extends Entry>>() {}.getType(),
				new TypeToken<List<? extends Entry>>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id)->"foo");
		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n" 
				+ " \"result\": [\n"
				+ "  {\"name\":\"$schema\",\"kind\":15,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":1,\"character\":3},\"end\":{\"line\":1,\"character\":55}}}},\n"
				+ "  {\"name\":\"type\",\"kind\":15,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":2,\"character\":3},\"end\":{\"line\":2,\"character\":19}}}},\n"
				+ "  {\"name\":\"title\",\"kind\":15,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":3,\"character\":3},\"end\":{\"line\":3,\"character\":50}}}},\n"
				+ "  {\"name\":\"additionalProperties\",\"kind\":17,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":4,\"character\":4},\"end\":{\"line\":4,\"character\":32}}}},\n"
				+ "  {\"name\":\"properties\",\"kind\":15,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":5,\"character\":3},\"end\":{\"line\":5,\"character\":20}}}}\n"
				+ "]}");
		List<? extends Entry> result = (List<? extends Entry>) ((ResponseMessage) message).getResult();
		Assert.assertEquals(5, result.size());
		for (Entry e : result) {
			Assert.assertTrue(e.location.uri, e.location.uri.startsWith("file:/home/mistria"));
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testParseList_02() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Set<Entry>>() {}.getType(),
				new TypeToken<Set<Entry>>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id)->"foo");
		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n" 
				+ " \"result\": [\n"
				+ "  {\"name\":\"$schema\",\"kind\":15,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":1,\"character\":3},\"end\":{\"line\":1,\"character\":55}}}},\n"
				+ "  {\"name\":\"type\",\"kind\":15,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":2,\"character\":3},\"end\":{\"line\":2,\"character\":19}}}},\n"
				+ "  {\"name\":\"title\",\"kind\":15,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":3,\"character\":3},\"end\":{\"line\":3,\"character\":50}}}},\n"
				+ "  {\"name\":\"additionalProperties\",\"kind\":17,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":4,\"character\":4},\"end\":{\"line\":4,\"character\":32}}}},\n"
				+ "  {\"name\":\"properties\",\"kind\":15,\"location\":{\"uri\":\"file:/home/mistria/runtime-EclipseApplication-with-patch/EclipseConEurope/something.json\",\"range\":{\"start\":{\"line\":5,\"character\":3},\"end\":{\"line\":5,\"character\":20}}}}\n"
				+ "]}");
		Set<Entry> result = (Set<Entry>) ((ResponseMessage)message).getResult();
		Assert.assertEquals(5, result.size());
		for (Entry e : result) {
			Assert.assertTrue(e.location.uri, e.location.uri.startsWith("file:/home/mistria"));
		}
	}

	@Test
	public void testParseNullList() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<List<? extends Entry>>() {}.getType(),
				new TypeToken<List<? extends Entry>>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id)->"foo");
		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ " \"result\": null}");
		Assert.assertNull(((ResponseMessage)message).getResult());
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testParseEmptyList() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<List<? extends Entry>>() {}.getType(),
				new TypeToken<List<? extends Entry>>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id)->"foo");
		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ " \"result\": []}");
		List<Entry> result = (List<Entry>) ((ResponseMessage)message).getResult();
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testSerializeEmptyList() {
		MessageJsonHandler handler = new MessageJsonHandler(Collections.emptyMap());
		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		message.setParams(Collections.EMPTY_LIST);
		String json = handler.serialize(message);
		Assert.assertEquals("{\"jsonrpc\":\"2.0\",\"method\":\"foo\",\"params\":[]}", json);
	}
	
	@Test
	public void testSerializeImmutableList() {
		MessageJsonHandler handler = new MessageJsonHandler(Collections.emptyMap());
		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		List<Object> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		message.setParams(Collections.unmodifiableList(list));
		String json = handler.serialize(message);
		Assert.assertEquals("{\"jsonrpc\":\"2.0\",\"method\":\"foo\",\"params\":[\"a\",\"b\"]}", json);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testEither_01() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Either<String, List<Map<String,String>>>>() {}.getType(),
				new TypeToken<Either<String, Integer>>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ " \"result\": [\n"
				+ "  {\"name\":\"foo\"},\n"
				+ "  {\"name\":\"bar\"}\n"
				+ "]}");
		Either<String, List<Map<String, String>>> result = (Either<String, List<Map<String,String>>>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isRight());
		for (Map<String, String> e : result.getRight()) {
			Assert.assertNotNull(e.get("name"));
		}
		message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n" 
				+ "\"result\": \"name\"\n"
				+ "}");
		result = (Either<String, List<Map<String,String>>>) ((ResponseMessage)message).getResult();
		Assert.assertFalse(result.isRight());
		Assert.assertEquals("name", result.getLeft());
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testEither_02() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Either<MyEnum, Map<String,String>>>() {}.getType(),
				new TypeToken<Object>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"result\": 2\n"
				+ "}");
		Either<MyEnum, List<Map<String, String>>> result = (Either<MyEnum, List<Map<String,String>>>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isLeft());
		Assert.assertEquals(MyEnum.B, result.getLeft());
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testEither_03() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Either<Either<MyEnum, Map<String,String>>, List<Either<MyEnum, Map<String,String>>>>>() {}.getType(),
				new TypeToken<Object>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");

		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"result\": 2\n"
				+ "}");
		Either<Either<MyEnum, Map<String,String>>, List<Either<MyEnum, Map<String,String>>>> result = (Either<Either<MyEnum, Map<String, String>>, List<Either<MyEnum, Map<String, String>>>>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isLeft());
		Assert.assertTrue(result.getLeft().isLeft());
		Assert.assertEquals(MyEnum.B, result.getLeft().getLeft());

		message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ " \"result\": {\n"
				+ "  \"foo\":\"1\",\n"
				+ "  \"bar\":\"2\"\n"
				+ "}}");
		result = (Either<Either<MyEnum, Map<String, String>>, List<Either<MyEnum, Map<String, String>>>>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isLeft());
		Assert.assertTrue(result.getLeft().isRight());
		Assert.assertEquals("1", result.getLeft().getRight().get("foo"));
		Assert.assertEquals("2", result.getLeft().getRight().get("bar"));

		message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ " \"result\": [{\n"
				+ "  \"foo\":\"1\",\n"
				+ "  \"bar\":\"2\"\n"
				+ "}]}");
		result = (Either<Either<MyEnum, Map<String, String>>, List<Either<MyEnum, Map<String, String>>>>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isRight());
		Assert.assertTrue(result.getRight().get(0).isRight());
		Assert.assertEquals("1", result.getRight().get(0).getRight().get("foo"));
		Assert.assertEquals("2", result.getRight().get(0).getRight().get("bar"));
		
		message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ " \"result\": [\n"
				+ "  2\n"
				+ "]}");
		result = (Either<Either<MyEnum, Map<String, String>>, List<Either<MyEnum, Map<String, String>>>>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isRight());
		Assert.assertTrue(result.getRight().get(0).isLeft());
		Assert.assertEquals(MyEnum.B, result.getRight().get(0).getLeft());
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testEither_04() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Either<MyClass, List<? extends MyClass>>>() {}.getType(),
				new TypeToken<Object>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");

		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"result\": {\n"
				+ "  value:\"foo\"\n"
				+ "}}");
		Either<MyClass, List<? extends MyClass>> result = (Either<MyClass, List<? extends MyClass>>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isLeft());
		Assert.assertEquals("foo", result.getLeft().getValue());
		
		message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"result\": [{\n"
				+ "  value:\"bar\"\n"
				+ "}]}");
		result = (Either<MyClass, List<? extends MyClass>>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isRight());
		Assert.assertEquals("bar", result.getRight().get(0).getValue());
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testEither_05() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Either<List<MyClass>, MyClassList>>() {}.getType(),
				new TypeToken<Object>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");

		Message message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"result\": [{\n"
				+ "  value:\"foo\"\n"
				+ "}]}");
		Either<List<MyClass>, MyClassList> result = (Either<List<MyClass>, MyClassList>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isLeft());
		Assert.assertEquals("foo", result.getLeft().get(0).getValue());
		
		message = handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"result\": {\n"
				+ "  items: [{\n"
				+ "    value:\"bar\"\n"
				+ "}]}}");
		result = (Either<List<MyClass>, MyClassList>) ((ResponseMessage)message).getResult();
		Assert.assertTrue(result.isRight());
		Assert.assertEquals("bar", result.getRight().getItems().get(0).getValue());
	}
	
	@Test
	public void testParamsParsing_01() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<Location>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"params\": {\"uri\": \"dummy://mymodel.mydsl\"},\n"
				+ "\"method\":\"foo\"\n"
				+ "}");
		Assert.assertEquals(Location.class, message.getParams().getClass());
	}
	
	@Test
	public void testParamsParsing_02() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<Location>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"method\":\"foo\",\n"
				+ "\"params\": {\"uri\": \"dummy://mymodel.mydsl\"}\n"
				+ "}");
		Assert.assertEquals(Location.class, message.getParams().getClass());
	}
	
	@Test
	public void testParamsParsing_03() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<Location>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"method\":\"bar\",\n"
				+ "\"params\": {\"uri\": \"dummy://mymodel.mydsl\"}\n"
				+ "}");
		Assert.assertEquals(JsonObject.class, message.getParams().getClass());
	}
	
	@Test
	public void testParamsParsing_04() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<Location>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"method\":\"bar\",\n"
				+ "\"params\": null\n"
				+ "}");
		Assert.assertEquals(null, message.getParams());
	}
	
	@Test
	public void testRawMultiParamsParsing_01() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<String>() {}.getType(),
				new TypeToken<Integer>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"method\":\"foo\",\n"
				+ "\"params\": [\"foo\", 2]\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof List);

		List<?> parameters = (List<?>) message.getParams();
		Assert.assertEquals(2, parameters.size());
		Assert.assertEquals("foo", parameters.get(0));
		Assert.assertEquals(2, parameters.get(1));
	}
	
	@Test
	public void testRawMultiParamsParsing_02() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<String>() {}.getType(),
				new TypeToken<Integer>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"method\":\"bar\",\n"
				+ "\"params\": [\"foo\", 2]\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof JsonArray);
	}

	@Test
	public void testRawMultiParamsParsing_03() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<List<String>>() {}.getType(),
				new TypeToken<List<Integer>>() {}.getType(),
				new TypeToken<Location>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"method\":\"foo\",\n"
				+ "\"params\": [[\"foo\", \"bar\"], [1, 2], {\"uri\": \"dummy://mymodel.mydsl\"}]\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof List);

		List<?> parameters = (List<?>) message.getParams();
		Assert.assertEquals(3, parameters.size());
		Assert.assertEquals("[foo, bar]", parameters.get(0).toString());
		Assert.assertEquals("[1, 2]", parameters.get(1).toString());
		Assert.assertTrue("" + parameters.get(2).getClass(), parameters.get(2) instanceof Location);
	}

	@Test
	public void testRawMultiParamsParsing_04() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<List<String>>() {}.getType(),
				new TypeToken<List<Integer>>() {}.getType(),
				new TypeToken<Location>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"method\":\"foo\",\n"
				+ "\"params\": [[\"foo\", \"bar\"], [1, 2]]\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof List);

		List<?> parameters = (List<?>) message.getParams();
		Assert.assertEquals(3, parameters.size());
		Assert.assertEquals("[foo, bar]", parameters.get(0).toString());
		Assert.assertEquals("[1, 2]", parameters.get(1).toString());
		Assert.assertNull(parameters.get(2));
	}
	
	@Test
	public void testMultiParamsParsing_01() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<String>() {}.getType(),
				new TypeToken<Integer>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"params\": [\"foo\", 2],\n"
				+ "\"method\":\"foo\"\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof List);

		List<?> parameters = (List<?>) message.getParams();
		Assert.assertEquals(2, parameters.size());
		Assert.assertEquals("foo", parameters.get(0));
		Assert.assertEquals(2, parameters.get(1));
	}
	
	@Test
	public void testMultiParamsParsing_02() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<String>() {}.getType(),
				new TypeToken<Integer>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"params\": [\"foo\", 2],\n"
				+ "\"method\":\"bar\"\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof JsonArray);
	}
	
	@Test
	public void testMultiParamsParsing_03() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<List<String>>() {}.getType(),
				new TypeToken<List<Integer>>() {}.getType(),
				new TypeToken<Location>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"params\": [[\"foo\", \"bar\"], [1, 2], {\"uri\": \"dummy://mymodel.mydsl\"}],\n"
				+ "\"method\":\"foo\"\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof List);

		List<?> parameters = (List<?>) message.getParams();
		Assert.assertEquals(3, parameters.size());
		Assert.assertEquals("[foo, bar]", parameters.get(0).toString());
		Assert.assertEquals("[1, 2]", parameters.get(1).toString());
		Assert.assertTrue("" + parameters.get(2).getClass(), parameters.get(2) instanceof Location);
	}
	
	@Test
	public void testMultiParamsParsing_04() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<List<String>>() {}.getType(),
				new TypeToken<List<Integer>>() {}.getType(),
				new TypeToken<Location>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"params\": [[\"foo\", \"bar\"], [1, 2]],\n"
				+ "\"method\":\"foo\"\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof List);

		List<?> parameters = (List<?>) message.getParams();
		Assert.assertEquals(3, parameters.size());
		Assert.assertEquals("[foo, bar]", parameters.get(0).toString());
		Assert.assertEquals("[1, 2]", parameters.get(1).toString());
		Assert.assertNull(parameters.get(2));
	}

	@Test
	public void testEnumParam() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<List<MyEnum>>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"params\": [1, 2, 3],\n"
				+ "\"method\":\"foo\"\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof List);

		List<?> parameters = (List<?>) message.getParams();
		Assert.assertEquals(Arrays.asList(MyEnum.A, MyEnum.B, MyEnum.C),
				parameters);
	}

	@Test
	public void testEnumParamNull() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<List<MyEnum>>() {}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		RequestMessage message = (RequestMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"params\": [1, 2, null],\n"
				+ "\"method\":\"foo\"\n"
				+ "}");
		Assert.assertTrue("" + message.getParams().getClass(), message.getParams() instanceof List);

		List<?> parameters = (List<?>) message.getParams();
		Assert.assertEquals(Arrays.asList(MyEnum.A, MyEnum.B, null),
				parameters);
	}

	@Test
	public void testResponseErrorData() {
		MessageJsonHandler handler = new MessageJsonHandler(Collections.emptyMap());
		ResponseMessage message = (ResponseMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\","
				+ "\"id\":\"2\",\n"
				+ "\"error\": { \"code\": -32001, \"message\": \"foo\",\n"
				+ "    \"data\": { \"uri\": \"file:/foo\", \"version\": 5, \"list\": [\"a\", \"b\", \"c\"] }\n"
				+ "  }\n"
				+ "}");
		ResponseError error = message.getError();
		Assert.assertTrue("Expected a JsonObject in error.data", error.getData() instanceof JsonObject);
		JsonObject data = (JsonObject) error.getData();
		Assert.assertEquals("file:/foo", data.get("uri").getAsString());
		Assert.assertEquals(5, data.get("version").getAsInt());
		JsonArray list = data.get("list").getAsJsonArray();
		Assert.assertEquals("a", list.get(0).getAsString());
		Assert.assertEquals("b", list.get(1).getAsString());
		Assert.assertEquals("c", list.get(2).getAsString());
	}

	public static final <T> void swap(T[] a, int i, int j) {
		T t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	public <T> void testAllPermutationsInner(T[] array, int i, int n, Consumer<T[]> test) {
		int j;
		if (i == n) {
			test.accept(array);
		} else {
			for (j = i; j <= n; j++) {
				swap(array, i, j);
				testAllPermutationsInner(array, i + 1, n, test);
				swap(array, i, j);
			}
		}
	}

	public <T> void testAllPermutationsStart(T[] array, Consumer<T[]> test) {
		testAllPermutationsInner(array, 0, array.length - 1, test);
	}

	public void testAllPermutations(String[] properties, Consumer<String> test) {
		testAllPermutationsStart(properties, mutatedProperties -> {
			StringBuilder json = new StringBuilder();
			json.append("{");
			for (int k = 0; k < mutatedProperties.length; k++) {
				json.append(mutatedProperties[k]);
				if (k != mutatedProperties.length - 1) {
					json.append(",");
				}

			}
			json.append("}");
			String jsonString = json.toString();
			try {
				test.accept(jsonString);
			} catch (Exception | AssertionError e) {
				// To make it easier to debug a failing test, add another exception
				// layer that shows the version of the json used -- you may
				// need to turn off "Filter Stack Trace" in JUnit view in Eclipse
				// to see the underlying error.
				throw new AssertionError("Failed with this input json: " + jsonString, e);
			}
		});
	}

	@Test
	public void testThePermutationsTest() {
		// make sure that the testAllPermutations works as expected
		Set<String> collectedPermutations = new HashSet<>();
		Set<String> expectedPermutations = new HashSet<>();
		expectedPermutations.add("{a,b,c}");
		expectedPermutations.add("{a,c,b}");
		expectedPermutations.add("{b,a,c}");
		expectedPermutations.add("{b,c,a}");
		expectedPermutations.add("{c,a,b}");
		expectedPermutations.add("{c,b,a}");
		testAllPermutations(new String[] {"a", "b", "c"}, perm -> collectedPermutations.add(perm));
		Assert.assertEquals(expectedPermutations, collectedPermutations);
	}

	@Test
	public void testRequest_AllOrders() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<Location>() {
				}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		String[] properties = new String[] {
				"\"jsonrpc\":\"2.0\"",
				"\"id\":2",
				"\"method\":\"foo\"",
				"\"params\": {\"uri\": \"dummy://mymodel.mydsl\"}"
				};
		testAllPermutations(properties, json -> {
			RequestMessage message = (RequestMessage) handler.parseMessage(json);
			Object params = message.getParams();
			Class<? extends Object> class1 = params.getClass();
			Assert.assertEquals(Location.class, class1);
			Assert.assertEquals("dummy://mymodel.mydsl", ((Location)params).uri);
		});
	}

	@Test
	public void testNormalResponse_AllOrders() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Location>() {}.getType(),
				new TypeToken<Void>() {
				}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		String[] properties = new String[] {
				"\"jsonrpc\":\"2.0\"",
				"\"id\":2",
				"\"result\": {\"uri\": \"dummy://mymodel.mydsl\"}"
				};
		testAllPermutations(properties, json -> {
			ResponseMessage message = (ResponseMessage) handler.parseMessage(json);
			Object result = message.getResult();
			Class<? extends Object> class1 = result.getClass();
			Assert.assertEquals(Location.class, class1);
			Assert.assertEquals("dummy://mymodel.mydsl", ((Location)result).uri);
			Assert.assertNull(message.getError());
		});
	}

	@Test
	public void testErrorResponse_AllOrders() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Location>() {}.getType(),
				new TypeToken<Void>() {
				}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		String[] properties = new String[] {
				"\"jsonrpc\":\"2.0\"",
				"\"id\":2",
				"\"message\": \"failed\"",
				"\"error\": {\"code\": 123456, \"message\": \"failed\", \"data\": {\"uri\": \"failed\"}}"
				};
		testAllPermutations(properties, json -> {
			ResponseMessage message = (ResponseMessage) handler.parseMessage(json);
			Assert.assertEquals("failed", message.getError().getMessage());
			Object data = message.getError().getData();
			JsonObject expected = new JsonObject();
			expected.addProperty("uri", "failed");
			Assert.assertEquals(expected, data);
			Assert.assertNull(message.getResult());
		});
	}

	@Test
	public void testNotification_AllOrders() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo",
				new TypeToken<Void>() {}.getType(),
				new TypeToken<Location>() {
				}.getType()));
		MessageJsonHandler handler = new MessageJsonHandler(supportedMethods);
		handler.setMethodProvider((id) -> "foo");
		String[] properties = new String[] {
				"\"jsonrpc\":\"2.0\"",
				"\"method\":\"foo\"",
				"\"params\": {\"uri\": \"dummy://mymodel.mydsl\"}"
				};
		testAllPermutations(properties, json -> {
			NotificationMessage message = (NotificationMessage) handler.parseMessage(json);
			Object params = message.getParams();
			Class<? extends Object> class1 = params.getClass();
			Assert.assertEquals(Location.class, class1);
			Assert.assertEquals("dummy://mymodel.mydsl", ((Location)params).uri);
		});
	}
	

	@Test
	public void testWrapPrimitive_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(Boolean.class, int.class);

		var request = new RequestMessage();
		request.setId(1);
		request.setMethod(handler.getMethodProvider().resolveMethod(null));
		request.setParams(3);
		
		// check primitive was wrapped into array
		assertEquals("{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"testMethod\",\"params\":[3]}", handler.serialize(request));
	}
	
	@Test
	public void testWrapPrimitiveWrapper_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(Boolean.class, Character.class);
		
		var request = new RequestMessage();
		request.setId(1);
		request.setMethod(handler.getMethodProvider().resolveMethod(null));
		request.setParams('X');
		
		// check primitive was wrapped into array
		assertEquals("{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"testMethod\",\"params\":[\"X\"]}", handler.serialize(request));
	}

	@Test
	public void testWrapStringWrapper_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(Boolean.class, String.class);
		
		var request = new RequestMessage();
		request.setId(1);
		request.setMethod(handler.getMethodProvider().resolveMethod(null));
		request.setParams("param");
		
		// check primitive was wrapped into array
		assertEquals("{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"testMethod\",\"params\":[\"param\"]}", handler.serialize(request));
	}
	

	@Test
	public void testUnwrapPrimitive_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(String.class, String.class);
		var request = "{\n"
				+ "  \"jsonrpc\": \"2.0\",\n"
				+ "  \"id\": 1,\n"
				+ "  \"method\": \"testMethod\",\n"
				+ "  \"params\": [\n"
				+ "      \"param\"\n"
				+ "  ]\n"
				+ "}";
		// Check parse - unwrap primitive
		RequestMessage message = (RequestMessage) handler.parseMessage(request);
		assertEquals("param", message.getParams());
	}

	@Test
	public void testWrapArray_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(String.class, new TypeToken<List<Boolean>>() {
		}.getType());
		
		var request = new RequestMessage();
		request.setId(1);
		request.setMethod(handler.getMethodProvider().resolveMethod(null));
		request.setParams(List.of(true,false));
		
		// check primitive was wrapped into array
		assertEquals("{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"testMethod\",\"params\":[[true,false]]}", handler.serialize(request));

	}
	
	@Test
	public void testUnwrapArray_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(String.class, new TypeToken<List<Boolean>>() {
		}.getType());
		
		var request = "{\n"
				+ "  \"jsonrpc\": \"2.0\",\n"
				+ "  \"id\": 1,\n"
				+ "  \"method\": \"testMethod\",\n"
				+ "  \"params\": [\n"
				+ "    [\n"
				+ "      true, false\n"
				+ "    ]\n"
				+ "  ]\n"
				+ "}";
		RequestMessage message = (RequestMessage) handler.parseMessage(request);
		
		// Check parse - unwrap array
		assertEquals(List.of(true, false), message.getParams());
		
	}

	@Test
	public void testWrapMultipleParams_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(String.class, Boolean.class, String.class);

		var request = new RequestMessage();
		request.setMethod(handler.getMethodProvider().resolveMethod(null));
		request.setParams(List.of(true, "param2"));
		// Check serialize - wrap primitive wrapper
		assertEquals("{\"jsonrpc\":\"2.0\",\"id\":null,\"method\":\"testMethod\",\"params\":[true,\"param2\"]}",
				handler.serialize(request));
		
	}

	@Test
	public void testUnwrapMultipleParams_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(String.class, Boolean.class, String.class);
		
		var request = "{\n"
				+ "  \"jsonrpc\": \"2.0\",\n"
				+ "  \"id\": 1,\n"
				+ "  \"method\": \"testMethod\",\n"
				+ "  \"params\": [\n"
				+ "      true,\n"
				+ "      \"param2\"\n"
				+ "  ]\n"
				+ "}";
		RequestMessage message = (RequestMessage) handler.parseMessage(request);
		
		// Check parse - unwrap array
		assertEquals(List.of(true, "param2"), message.getParams());
		
	}
	@Test
	public void testWrapMultipleParamsWithArray_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(String.class,  new TypeToken<List<Boolean>>() {
		}.getType(), String.class);
		
		var request = new RequestMessage();
		request.setMethod(handler.getMethodProvider().resolveMethod(null));
		request.setParams(List.of(List.of(true, false), "param2"));
		// Check serialize - wrap primitive wrapper
		assertEquals("{\"jsonrpc\":\"2.0\",\"id\":null,\"method\":\"testMethod\",\"params\":[[true,false],\"param2\"]}",
				handler.serialize(request));
		
	}
	
	@Test
	public void testUnwrapMultipleParamsWithArray_JsonRpc2_0() {
		MessageJsonHandler handler = createSimpleRequestHandler(String.class,  new TypeToken<List<Boolean>>() {
		}.getType(), String.class);
		
		var request = "{\n"
				+ "  \"jsonrpc\": \"2.0\",\n"
				+ "  \"id\": 1,\n"
				+ "  \"method\": \"testMethod\",\n"
				+ "  \"params\": [\n"
				+ "    [\n"
				+ "      true,\n"
				+ "      false\n"
				+ "    ],\n"
				+ "    \"param2\"\n"
				+ "  ]\n"
				+ "}";
		RequestMessage message = (RequestMessage) handler.parseMessage(request);
		
		// Check parse - unwrap array
		assertEquals(List.of(List.of(true, false),"param2"), message.getParams());
		
	}
	private static MessageJsonHandler createSimpleRequestHandler(Class<?> returnType, Type... paramType) {
		JsonRpcMethod requestMethod = JsonRpcMethod.request("testMethod", returnType, paramType);
		MessageJsonHandler handler = new MessageJsonHandler(Map.of(requestMethod.getMethodName(), requestMethod));
		handler.setMethodProvider((id) -> requestMethod.getMethodName());
		return handler;
	}
}
