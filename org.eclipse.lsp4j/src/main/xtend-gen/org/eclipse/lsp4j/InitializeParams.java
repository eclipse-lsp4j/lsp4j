package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The initialize request is sent as the first request from the client to the server.
 */
@SuppressWarnings("all")
public class InitializeParams extends WrappedJsonObject {
  private static WrappedJsonProperty<Integer> processIdProperty = new WrappedJsonProperty<>("processId", WrappedJsonConverter.integerConverter);
  
  /**
   * The process Id of the parent process that started the server.
   */
  @Pure
  public Integer getProcessId() {
    return processIdProperty.get(jsonObject);
  }
  
  /**
   * The process Id of the parent process that started the server.
   */
  public void setProcessId(final Integer processId) {
    processIdProperty.set(jsonObject, processId);
  }
  
  /**
   * Removes the property processId from the underlying JSON object.
   */
  public Integer removeProcessId() {
    return processIdProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> rootPathProperty = new WrappedJsonProperty<>("rootPath", WrappedJsonConverter.stringConverter);
  
  /**
   * The rootPath of the workspace. Is null if no folder is open.
   */
  @Pure
  public String getRootPath() {
    return rootPathProperty.get(jsonObject);
  }
  
  /**
   * The rootPath of the workspace. Is null if no folder is open.
   */
  public void setRootPath(final String rootPath) {
    rootPathProperty.set(jsonObject, rootPath);
  }
  
  /**
   * Removes the property rootPath from the underlying JSON object.
   */
  public String removeRootPath() {
    return rootPathProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Object> initializationOptionsProperty = new WrappedJsonProperty<>("initializationOptions", WrappedJsonConverter.anyConverter);
  
  /**
   * User provided initialization options.
   */
  @Pure
  public Object getInitializationOptions() {
    return initializationOptionsProperty.get(jsonObject);
  }
  
  /**
   * User provided initialization options.
   */
  public void setInitializationOptions(final Object initializationOptions) {
    initializationOptionsProperty.set(jsonObject, initializationOptions);
  }
  
  /**
   * Removes the property initializationOptions from the underlying JSON object.
   */
  public Object removeInitializationOptions() {
    return initializationOptionsProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<ClientCapabilities> capabilitiesProperty = new WrappedJsonProperty<>("capabilities", WrappedJsonConverter.objectConverter(ClientCapabilities.class));
  
  /**
   * The capabilities provided by the client (editor)
   */
  @Pure
  public ClientCapabilities getCapabilities() {
    return capabilitiesProperty.get(jsonObject);
  }
  
  /**
   * The capabilities provided by the client (editor)
   */
  public void setCapabilities(final ClientCapabilities capabilities) {
    capabilitiesProperty.set(jsonObject, capabilities);
  }
  
  /**
   * Removes the property capabilities from the underlying JSON object.
   */
  public ClientCapabilities removeCapabilities() {
    return capabilitiesProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> clientNameProperty = new WrappedJsonProperty<>("clientName", WrappedJsonConverter.stringConverter);
  
  /**
   * An optional extension to the protocol.
   * To tell the server what client (editor) is talking to it.
   */
  @Pure
  public String getClientName() {
    return clientNameProperty.get(jsonObject);
  }
  
  /**
   * An optional extension to the protocol.
   * To tell the server what client (editor) is talking to it.
   */
  public void setClientName(final String clientName) {
    clientNameProperty.set(jsonObject, clientName);
  }
  
  /**
   * Removes the property clientName from the underlying JSON object.
   */
  public String removeClientName() {
    return clientNameProperty.remove(jsonObject);
  }
  
  public InitializeParams() {
    super();
  }
  
  public InitializeParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
}
