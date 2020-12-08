# Core Concepts

The LSP is based on an extended version of [JSON RPC v2.0](http://www.jsonrpc.org/specification), for which LSP4J provides a Java implementation. There are basically three levels of interaction:

# Basic Message Sending

On the lowest Level JSON RPC just sends messages from a client to a server. Those messages can be notifications, requests or responses. The relation between an incoming request and a sent response is done through a request id. As a user you usually don't want to do the wiring yourself, but want to work at least with an `Endpoint`.

# Endpoint

LSP4J provides the notion of an [Endpoint](../org.eclipse.lsp4j.jsonrpc/src/main/java/org/eclipse/lsp4j/jsonrpc/Endpoint.java) that takes care of the connecting a request messages with responses. The interface defines two methods 

``` java
/**
 * An endpoint is a generic interface that accepts jsonrpc requests and notifications.
 */
public interface Endpoint {

	CompletableFuture<?> request(String method, Object parameter);
	
	void notify(String method, Object parameter);
	
}
```

# Notifications

You always work with two `Endpoints`. Usually one of the endpoints, a `RemoteEndpoint`, sits on some remote communication channel, like a socket and receives and sends json messages. A local `Endpoint` implementation is connected bidirectionally such that it can receive and send messages. For instance, when a notification messages comes in the `RemoteEndpoint` simply translates it to a call on your local Endpoint implementation. This simple approach works nicely in both directions.

# Requests

For requests the story is slightly more complicated. When a request message comes in, the `RemoteEndpoint` tracks the request `id` and invokes `request` on the local endpoint. In addition it adds completion stage to the returned `CompletableFuture`, that translates the result into a JSON RPC response message.

For the other direction, if the implementation calls request on the RemoteEndpoint, the message is send and tracked locally. 
The returned `CompletableFuture` will complete once a corresponsing result message is received.

# Cancelling Requests

The LSP defines an extension to the JSON RPC, that allows to cancel requests. It is done through a special notification message, that contains the request id that should be cancelled. If you want to cancel a pending request in LSP4J, you can simply call `cancel(true)` on the returned `CompletableFuture`. The `RemoteEndpoint` will send the cancellation notification. If you are implementing a request message, you should return a `CompletableFuture` created through [`CompletebleFutures.computeAsync`] (../org.eclipse.lsp4j.jsonrpc/src/main/java/org/eclipse/lsp4j/jsonrpc/CompletableFutures.java#L24). It accepts a lambda that is provided with a `CancelChecker`, which you need to ask `checkCanceled` and which will throw a `CancellationException` in case the request got canceled.

``` java
@JsonRequest
public CompletableFuture<CompletionList> completion(
                                         TextDocumentPositionParams position) {
   return CompletableFutures.computeAsync(cancelToken -> {
      // the actual implementation should check for 
      // cancellation like this
      cancelToken.checkCanceled();
      // more code...  and more cancel checking
      return completionList;
   });
}
```

# Static Typing through Service Layer

So far with `Endpoint` and `Object` as parameter and result the API is quite generic. In order to leverage Java's type system and tool support, the JSON RPC module supports the notion of service objects.

# Service Objects

A service object provides methods that are annotated with either `@JsonNotification` or `@JsonRequest`. A `GenericEndpoint` is a reflective implementation of an Endpoint, that simply delegates any calls to `request` or `notify` to the corresponding method in the service object. Here is a simple example:

``` java
public class MyService {
   @JsonNotification public void sayHello(HelloParam param) {
      ... do stuff 
   }
}

// turn it into an Endpoint

MyService service = new MyService();
Endpoint serviceAsEndpoint = ServiceEndpoints.toEndpoint(service);

```

If in turn you want to talk to an Endpoint in a more statically typed fashion, the `EndpointProxy` comes in handy. It is a dynamic proxy for a given service interface with annotated @JsonRequest and @JsonNotification methods. You can create one like this:

``` java
public interface MyService {
   @JsonNotification public void sayHello(HelloParam param);
}


Endpoint endpoint = ...
MyService proxy = ServiceEndpoints.toProxy(endpoint, MyService.class);
```

Of course you can use the same interface, as is done with the [interfaces](../org.eclipse.lsp4j/src/main/java/org/eclipse/lsp4j/services/LanguageServer.java) defining the messages of the LSP.

# Naming of JSON RPC Request and Notifications

When annotated with @JsonRequest or @JsonNotification LSP4J will use the name of the annotated method to create the JSON RPC method name. This naming can be customized by using segments and providing explicit names in the annotations. Here are some examples of method naming options:

```java
@JsonSegment("mysegment")
public interface NamingExample {

    // The JSON RPC method name will be "mysegment/myrequest"
    @JsonRequest
    CompletableFuture<?> myrequest();

    // The JSON RPC method name will be "myotherrequest"
    @JsonRequest(useSegment = false)
    CompletableFuture<?> myotherrequest();

    // The JSON RPC method name will be "mysegment/somethirdrequest"
    @JsonRequest(value="somethirdrequest")
    CompletableFuture<?> notthesamenameasvalue();

    // The JSON RPC method name will be "call/it/what/you/want"
    @JsonRequest(value="call/it/what/you/want", useSegment = false)
    CompletableFuture<?> yetanothername();
}
```

