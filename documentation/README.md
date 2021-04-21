# Introduction

A brief description for using LSP4J to develop a language server.

# Implement your language server

The first thing you should do is to implement your language server. To do so just implement the interface `org.eclipse.lsp4j.services.LanguageServer`.

If you are implementing a client (e.g. an editor) you simply need to implement `org.eclipse.lsp4j.services.LanguageClient` instead.

# Launch and connect with a `LanguageClient`

Now that you have an actual implementation you can connect it with a remote client. Let's assume you have an `Inputstream` and an `Outputstream`, over which you want to communicate with a language client.

The utility class `LSPLauncher` does most of the wiring for you. Here is the code needed.

``` java
LanguageServer server = ... ;
Launcher<LanguageClient> launcher = 
    LSPLauncher.createServerLauncher(server,
                                     inputstream, 
                                     outputstream);
```

With this we have a Launcher object on which we can obtain the remote proxy. Usually a language server should also implement `LanguageClientAware`, which defines a single method `connect(LanguageClient)` over which you can pass the remote proxy to the language server.

``` java
if (myImpl instanceof LanguageClientAware) {
   LanguageClient client = launcher.getRemoteProxy();
   ((LanguageClientAware)myImpl).connect(client);
}
```

Now your language server is not only able to receive messages from the other side, but can send messages back as well.

The final thing you need to to do in order to start listening on the given inputstream, is this:

``` java
launcher.startListening();
```

This will start the listening process in a new thread.

# Extending the Protocol

As explained in the [JSON-RPC implementation documentation](jsonrpc.md#service-objects), protocol messages can be specified through methods annotated with `@JsonNotification` or `@JsonRequest`. If you would like to add more client-to-server messages, just pass a `LanguageServer` implementation with additional annotated methods to the `LSPLauncher`. In case you need to change the protocol so it is no longer compatible with the `LanguageServer` interface, or you would like to add or change some server-to-client messages, use the utility methods in `Launcher` instead of `LSPLauncher`. Those allow you to use arbitrary local service objects and arbitrary remote service interfaces, and you can even combine multiple service objects or interfaces.

# Cancellation Support

The LSP has extended JSON RPC with support for request cancellation. LSP4J supports this through the cancellation of `CompletableFuture`s. To use it a request method needs to be implemented like this:

``` java
@Override
public CompletableFuture<CompletionList> completion(
                                         TextDocumentPositionParams position) {
   return CompletableFutures.computeAsync(cancelToken -> {
      // the actual implementation should check for 
      // cancellation like this
      cancelToken.checkCanceled();
      // more code...  and more cancel checking
   });
}
```
The method `checkCanceled` will throw a `CancellationException` in case the request was cancelled. So make sure you don't catch it accidentally.

If you are on the other side and want to cancel a request you made, you need to call cancel on the returned future :

``` java
CompletableFuture<CompletionList> result = languageServer
                  .getTextDocumentService().completion(myparams);
// cancel the request
result.cancel(true);
```

# JSON RPC

For more information about the underlying json rpc framework see [JSON RPC Documentation](jsonrpc.md).

# Implement your Debug Adapter Client

Note that to implement a debug adapter client, you need to get the `org.eclipse.lsp4j.debug` jar from maven besides the `org.eclipse.lsp4j` jar (in gradle it's something as: `compile group: 'org.eclipse.lsp4j', name: 'org.eclipse.lsp4j.debug', version: '<version>'`)

Now, the code actually connect to a debug adapter server should be something as:

```
IDebugProtocolClient client = <...>  // Custom client that will handle the messages received from the server.
Process process = <...>  // Handle to the debug adapter process.
InputStream in = process.getInputStream();
OutputStream out = process.getOutputStream();

// Bootstrap the actual connection.
Launcher<IDebugProtocolServer> launcher = DSPLauncher.createClientLauncher(client, in, out);
launcher.startListening();

InitializeRequestArguments arguments = new InitializeRequestArguments();
arguments.setClientID("<client id>");
arguments.setAdapterID("<adapter id>");

// Configure initialization as needed.
arguments.setLinesStartAt1(false);
arguments.setColumnsStartAt1(false);
arguments.setSupportsRunInTerminalRequest(false);

IDebugProtocolServer remoteProxy = launcher.getRemoteProxy();
Capabilities capabilities = remoteProxy.initialize(arguments).get(10, TimeUnit.SECONDS);

// Configure launch as needed.
Map<String, Object> launchArgs = new HashMap<>();
launchArgs.put("terminal", "none");
launchArgs.put("target", "/path/to/target");
launchArgs.put("noDebug", false);
launchArgs.put("__sessionId", "sessionId");
CompletableFuture<Void> launch = remoteProxy.launch(launchArgs);
launch.get(10, TimeUnit.SECONDS);

// Add a breakpoint.
SetBreakpointsArguments breakpointArgs = new SetBreakpointsArguments();
Source source = new Source();
source.setName("target");
source.setPath("/path/to/target");
breakpointArgs.setSource(source);
SourceBreakpoint sourceBreakpoint = new SourceBreakpoint();
sourceBreakpoint.setLine(6);
SourceBreakpoint[] breakpoints = new SourceBreakpoint[]{sourceBreakpoint};
breakpointArgs.setBreakpoints(breakpoints);
CompletableFuture<SetBreakpointsResponse> future = remoteProxy.setBreakpoints(breakpointArgs);
SetBreakpointsResponse setBreakpointsResponse = future.get(10, TimeUnit.SECONDS);
Breakpoint[] breakpointsResponse = setBreakpointsResponse.getBreakpoints();

// Signal that the configuration is finished
remoteProxy.configurationDone(new ConfigurationDoneArguments());

// At this point the client may start receiving events such as `stopped`, `terminated`, etc.

```

