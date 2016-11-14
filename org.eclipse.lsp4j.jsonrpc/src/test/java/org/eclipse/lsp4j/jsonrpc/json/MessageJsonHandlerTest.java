package org.eclipse.lsp4j.jsonrpc.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.reflect.TypeToken;

public class MessageJsonHandlerTest {

	public static class Entry {
		public String name;
		public int kind;
		public Location location;
	}

	public static class Location {
		public String uri;
	}

	@SuppressWarnings({ "serial", "unchecked" })
	@Test
	public void testParseList() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo", new TypeToken<List<? extends Entry>>() {
		}.getType(), new TypeToken<List<? extends Entry>>() {
		}.getType()));
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
	
	@SuppressWarnings({ "serial", "unchecked" })
	@Test
	public void testParseList_02() {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
		supportedMethods.put("foo", JsonRpcMethod.request("foo", new TypeToken<Set<Entry>>() {
		}.getType(), new TypeToken<Set<Entry>>() {
		}.getType()));
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
}
