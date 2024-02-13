# JSON-RPC Core Concepts

The LSP is based on an extended version of [JSON-RPC v2.0](https://www.jsonrpc.org/specification), for which LSP4J provides a Java implementation. There are basically three levels of interaction:

## Basic Message Sending

On the lowest level, JSON-RPC just sends messages from a client to a server. Those messages can be notifications, requests, or responses. The relation between an incoming request and a sent response is done through a request `id`. As a user, you usually don't want to do the wiring yourself, but want to work at least with an `Endpoint`.

## Endpoint

LSP4J provides the notion of an [Endpoint](../org.eclipse.lsp4j.jsonrpc/src/main/java/org/eclipse/lsp4j/jsonrpc/Endpoint.java) that takes care of the connecting a request messages with responses. The interface defines two methods:

``` java
/**
 * An endpoint is a generic interface that accepts jsonrpc requests and notifications.
 */
public interface Endpoint {

   CompletableFuture<?> request(String method, Object parameter);

   void notify(String method, Object parameter);

}
```

## Notifications

You always work with two `Endpoints`. Usually one of the endpoints, a `RemoteEndpoint`, sits on some remote communication channel, like a socket and receives and sends json messages. A local `Endpoint` implementation is connected bidirectionally such that it can receive and send messages. For instance, when a notification messages comes in the `RemoteEndpoint` simply translates it to a call on your local `Endpoint` implementation. This simple approach works nicely in both directions.

## Requests

For requests, the story is slightly more complicated. When a request message comes in, the `RemoteEndpoint` tracks the request `id` and invokes `request` on the local endpoint. In addition, it adds completion stage to the returned `CompletableFuture`, that translates the result into a JSON-RPC response message.

For the other direction, if the implementation calls request on the RemoteEndpoint, the message is sent and tracked locally. The returned `CompletableFuture` will complete once a corresponding result message is received.

## Response Errors

The receiver of a request always needs to return a response message to conform to the JSON-RPC specification. In case the result value cannot be provided in a response because of an error, the `error` property of the `ResponseMessage` must be set to a `ResponseError` describing the failure.

This can be done by returning a `CompletableFuture` completed exceptionally with a `ResponseErrorException` from the request message handler in a local endpoint. The exception carries a `ResponseError` to attach to the response. The `RemoteEndpoint` will handle the exceptionally completed future and send a response message with the attached error object.

For example:

```java
   @Override
   public CompletableFuture<Object> shutdown() {
      if (!isInitialized()) {
         CompletableFuture<Object> exceptionalResult = new CompletableFuture<>();
         ResponseError error = new ResponseError(ResponseErrorCode.ServerNotInitialized, "Server was not initialized", null);
         exceptionalResult.completeExceptionally(new ResponseErrorException(error));
         return exceptionalResult;
      }
      return doShutdown();
   }
```

## Cancelling Requests

The LSP defines an extension to the JSON-RPC, that allows to cancel requests. It is done through a special notification message, which contains the request `id` that should be cancelled. If you want to cancel a pending request in LSP4J, you can simply call `cancel(true)` on the returned `CompletableFuture`. The `RemoteEndpoint` will send the cancellation notification. If you are implementing a request message, you should return a `CompletableFuture` created through [`CompletableFutures.computeAsync`](../org.eclipse.lsp4j.jsonrpc/src/main/java/org/eclipse/lsp4j/jsonrpc/CompletableFutures.java#L24). It accepts a lambda that is provided with a `CancelChecker`, which you need to ask `checkCanceled` and which will throw a `CancellationException` in case the request got canceled.

``` java
@JsonRequest
public CompletableFuture<CompletionList> completion(TextDocumentPositionParams position) {
   return CompletableFutures.computeAsync(cancelToken -> {
      // the actual implementation should check for
      // cancellation like this
      cancelToken.checkCanceled();
      // more code...  and more cancel checking
      return completionList;
   });
}
```

## Static Typing through Service Layer

So far with `Endpoint` and `Object` as parameter and result the API is quite generic. In order to leverage Java's type system and tool support, the JSON-RPC module supports the notion of service objects.

## Service Objects

A service object provides methods that are annotated with either `@JsonNotification` or `@JsonRequest`. A `GenericEndpoint` is a reflective implementation of an Endpoint that simply delegates any calls to `request` or `notify` to the corresponding method in the service object. Here is a simple example:

``` java
public class MyService {
   @JsonNotification public void sayHello(HelloParam param) {
      // do stuff
   }
}

// turn it into an Endpoint

MyService service = new MyService();
Endpoint serviceAsEndpoint = ServiceEndpoints.toEndpoint(service);
```

If in turn you want to talk to an Endpoint in a more statically typed fashion, the `EndpointProxy` comes in handy. It is a dynamic proxy for a given service interface with annotated `@JsonRequest` and `@JsonNotification` methods. You can create one like this:

``` java
public interface MyService {
   @JsonNotification public void sayHello(HelloParam param);
}

Endpoint endpoint = ...
MyService proxy = ServiceEndpoints.toProxy(endpoint, MyService.class);
```

Of course you can use the same interface, as is done with the [interfaces](../org.eclipse.lsp4j/src/main/java/org/eclipse/lsp4j/services/LanguageServer.java) defining the messages of the LSP.

## Naming of JSON-RPC Request and Notifications

When annotated with `@JsonRequest` or `@JsonNotification` LSP4J will use the name of the annotated method to create the JSON-RPC method name. This naming can be customized by using segments and providing explicit names in the annotations. Here are some examples of method naming options:

```java
@JsonSegment("mysegment")
public interface NamingExample {

   // The JSON-RPC method name will be "mysegment/myrequest"
   @JsonRequest
   CompletableFuture<?> myrequest();

   // The JSON-RPC method name will be "myotherrequest"
   @JsonRequest(useSegment = false)
   CompletableFuture<?> myotherrequest();

   // The JSON-RPC method name will be "mysegment/somethirdrequest"
   @JsonRequest(value="somethirdrequest")
   CompletableFuture<?> notthesamenameasvalue();

   // The JSON-RPC method name will be "call/it/what/you/want"
   @JsonRequest(value="call/it/what/you/want", useSegment = false)
   CompletableFuture<?> yetanothername();
}
```
## LSP4J JSON-RPC generator

LSP4J provides a bundle that helps generate classes that are suitable for use with LSP4J's JSON-RPC.
These files can be written in [Eclipse xtend](https://eclipse.dev/Xtext/xtend/) and use xtend's [active annotation feature](https://eclipse.dev/Xtext/xtend/documentation/204_activeannotations.html) to provide compilation participants.
The annotation to use is [`@JsonRpcData`](https://github.com/eclipse-lsp4j/lsp4j/blob/main/org.eclipse.lsp4j.generator/src/main/java/org/eclipse/lsp4j/generator/JsonRpcData.xtend) which adds getters, setters, equals, toString and other functionality automatically to simply defined classes.

For example, in an xtend file a simple class can be defined such as:

```java
@JsonRpcData
class HelloParam {
   @NonNull	String helloMessage
   int repeatCount
}
```

which will generate a fully functional Java class with all the extra parts suitable for integrating with LSP4J and the rest of your Java application:

```java
@SuppressWarnings("all")
public class HelloParam {
  @NonNull
  private String helloMessage;

  private int repeatCount;

  @NonNull
  public String getHelloMessage() {
    return this.helloMessage;
  }

  public void setHelloMessage(@NonNull final String helloMessage) {
    this.helloMessage = Preconditions.checkNotNull(helloMessage, "helloMessage");
  }

  public int getRepeatCount() {
    return this.repeatCount;
  }

  public void setRepeatCount(final int repeatCount) {
    this.repeatCount = repeatCount;
  }

  @Override
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("helloMessage", this.helloMessage);
    b.add("repeatCount", this.repeatCount);
    return b.toString();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    // rest of the method elided for brevity in the documentation
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.helloMessage== null) ? 0 : this.helloMessage.hashCode());
    return prime * result + this.repeatCount;
  }
}
```

The generation may generate dependencies on some additional classes.
Refer to the following sub-sections for details.

### `ToStringBuilder`, `Preconditions` and other dependent classes

When using the generator the generated code may refer to `ToStringBuilder`, `Preconditions` and other classes in the `org.eclipse.lsp4j.jsonrpc` bundle.
Ensure that there is a runtime dependency on the `org.eclipse.lsp4j.jsonrpc` in your project.
