/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
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
	public void testParseList() {
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
		List<? extends Entry> result = (List<? extends Entry>) ((ResponseMessage)message).getResult();
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
		Assert.assertEquals("name",result.getLeft());
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
}
