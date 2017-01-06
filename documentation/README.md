# Introduction

A brief description for using LSP4j to develop a language server.

# Implement your language server

The first thing you should do is to implement your language server. To do so just implement the interface `org.eclipse.lsp4j.LanguageServer`.

If you are implementing a client (e.g. an editor) you simply need to implement `org.eclipse.lsp4j.LanguageClient` instead.

# Launch and connect with a `LanguageClient`

Now that you have an actual implementation you can connect it with a remote client. Let's assume you have an `Inputstream` and an `Outputstream`, over which you want to communicate with a language client.

The utility class LSPLauncher does most of the wiring for you. Here is the code needed.

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
